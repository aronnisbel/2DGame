package interactiondesign.arni0010.umu.se.a2dgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Creates a MainThread and a SceneManager to handle the view of the game and run-time.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    private MainThread thread;

    private SceneManager manager;

    public GamePanel(Context context){

        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        manager = new SceneManager();

        setFocusable(true);
    }

    /**
     * Overrides the SurfaceViews onDraw method to take in a Canvas.
     * @param canvas The canvas on which everything is drawn.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * Never used. Has to be overwritten to be able to use the class SurfaceView.
     * @param holder The SurfaceHolder.
     * @param format The int format.
     * @param width The width in pixels.
     * @param height The height in pixels.
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    /**
     * Initializes and starts the MainThread on which the game run.
     * @param holder The SurfaceHolder.
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder){

        thread = new MainThread(getHolder(), this);

        Constants.INIT_TIME = System.currentTimeMillis();

        thread.setRunning(true);
        thread.start();
    }

    /**
     * When the game view is destroyed this method prevents the MainThread from continuing to
     * maximize performance of the App.
     * @param holder the SurfaceHolder.
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder){

        boolean retry = true;

        while(retry) {
            try{
                thread.setRunning(false);
                thread.join();
            }catch(Exception e){e.printStackTrace();}

            retry = false;
        }
    }

    /**
     * Passes the MotionEvent on for the SceneManager to handle.
     * @param event The MotionEvent.
     * @return true.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event){

        manager.recieveTouch(event);

        return true;
    }

    /**
     * Passes the update on to the SceneManager.
     */
    public void update(){

        manager.update();
    }

    /**
     * Draws to the canvas and calls the SceneManagers draw method.
     * @param canvas The canvas on which everything is drawn.
     */
    @Override
    public void draw(Canvas canvas){

        super.draw(canvas);

        manager.draw(canvas);
    }
}
