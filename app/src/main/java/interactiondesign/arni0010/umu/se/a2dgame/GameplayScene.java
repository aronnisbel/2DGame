package interactiondesign.arni0010.umu.se.a2dgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;

/**
 * Draws the game to the Canvas.
 */
public class GameplayScene implements Scene {

    private Rect r = new Rect();

    private RectPlayer player;
    private Point playerPoint;
    private ObstacleManager obstacleManager;

    private boolean gameOver;

    private OrientationData orientationData;
    private long frameTime;
    private float speed;

    private int width;
    private int height;
    private int highscore;

    /**
     * Creates a player as a RectPlayer and a point on which the player always will start. The
     * constructor also initializes the ObstacleManager and the OrientationData.
     */
    public GameplayScene(int width, int height, Context context){

        this.width = width;
        this.height = height;

        player = new RectPlayer(new Rect(100, 100, 200, 200), context);
        playerPoint = new Point(width/2, 3* height/4);
        player.update(playerPoint);

        highscore = Constants.HIGHSCORE;
        Constants.GAMEOVER = false;

        speed = 0;

        initializeObstacleManager();

        orientationData = new OrientationData(context);
        orientationData.register();
        frameTime = System.currentTimeMillis();
    }

    /**
     * Restores the state of the class.
     * @param savedInstanceState the bundle with the saved data in it.
     * @param context the current context.
     */
    public GameplayScene(Bundle savedInstanceState, Context context){
        
        int x = savedInstanceState.getInt("PlayerX");
        int y = savedInstanceState.getInt("PlayerY");

        player = new RectPlayer((Rect)savedInstanceState.getParcelable("Rect"), context);
        playerPoint = new Point(x, y);
        player.update(playerPoint);

        highscore = Constants.HIGHSCORE;
        gameOver = true;

        speed = 0;

        obstacleManager = new ObstacleManager(savedInstanceState);

        orientationData = new OrientationData(context);
        orientationData.register();
        frameTime = savedInstanceState.getLong("FrameTime");
    }

    /**
     * Saves the data from this class to the outState bundle and continues by calling the next
     * class to save data.
     * @param outState the soon to be savedInstance bundle.
     */
    public void getData(Bundle outState){

        outState.putParcelable("Rect", player.getRectangle());
        obstacleManager.getData(outState);
        outState.putInt("PlayerX", player.getRectangle().centerX());
        outState.putInt("PlayerY", player.getRectangle().centerY());
        outState.putLong("RestoredCurrentTime", System.currentTimeMillis());
        outState.putLong("StartTime", obstacleManager.getStartTime());
        outState.putLong("InitTime", obstacleManager.getInitTime());
        outState.putLong("FrameTime", frameTime);
        orientationData.pause();
    }

    /**
     * Resets the player starting position to the same spot as usual and then re-initializes the
     * ObstacleManager to re-create and re-draw the game.
     */
    private void reset(){

        playerPoint = new Point(width/2, 3* height/4);
        player.update(playerPoint);

        initializeObstacleManager();
    }

    /**
     * At its current state this method only sets the current Scene to this Scene again. Its real
     * mission would be to terminate the this Scene and change it back to a starting Scene. But
     * since this is the starting Scene and the only Scene available this doesn't do much at the
     * moment. A method available for future updates of the game.
     */
    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 0;
    }

    /**
     * This method handles the event of touch on the screen while the game is running. but the only
     * time to method does something of importance is when it's Game Over for the player. The
     * game then resets by the player touching the display. That's when this method resets the
     * OrientationData and resets the whole game to start from the beginning again.
     * @param event The MotionEvent.
     */
    @Override
    public void recieveTouch(MotionEvent event) {

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:

                /*Add: if(&& System.currentTimeMillis() - gameOverTime >= 200), if game start
                delay is needed.*/
                if(gameOver){

                    reset();
                    gameOver = false;
                    Constants.GAMEOVER = false;
                    orientationData.newGame();
                    orientationData.register();
                }
                break;
        }
    }

    /**
     * This method draws the game to the Canvas. It draws the RectPlayer and the ObstacleManager
     * which handles both the the player, obstacles and the movement of the obstacles. If it's
     * Game Over for the player it draws two Paints with texts that tells the highscore and
     * "GameOver".
     * @param canvas The canvas on which everything is drawn.
     */
    @Override
    public void draw(Canvas canvas) {

        canvas.drawColor(Color.DKGRAY);

        player.draw(canvas);
        obstacleManager.draw(canvas);

        if(gameOver){

            Paint paintHighscore = new Paint();
            paintHighscore.setTextSize(100);
            paintHighscore.setColor(Color.CYAN);
            drawText(canvas, paintHighscore, "Highscore: " + highscore, -300);

            Paint paintGameOver = new Paint();
            paintGameOver.setTextSize(100);
            paintGameOver.setColor(Color.MAGENTA);
            drawText(canvas, paintGameOver, "Game Over", 0);

            Paint paintRestart = new Paint();
            paintRestart.setTextSize(100);
            paintRestart.setColor(Color.WHITE);
            drawText(canvas, paintRestart, "Touch to restart", 300);
        }
    }

    /**
     * Keeps track of the elapsed time of each game session. By doing so it can update the
     * players position and the ObstacleManager. It also checks constantly if the player has
     * collided with an Obstacle. If the player has collided the Sensor Listener in
     * OrientationData is paused to maximize performance.
     */
    @Override
    public void update() {

        if(!gameOver) {

            if(frameTime < Constants.INIT_TIME)
                frameTime = Constants.INIT_TIME;

            int elapsedTime = (int) (System.currentTimeMillis() - frameTime);
            frameTime = System.currentTimeMillis();

            if(orientationData.getOrientation() != null && orientationData.getStartOrientation()
                    != null) {

                float pitch = orientationData.getOrientation()[1] - orientationData
                        .getStartOrientation()[1];
                float roll = orientationData.getOrientation()[2] - orientationData
                        .getStartOrientation()[2];

                float xSpeed = 2 * roll * width/speed;
                float ySpeed = pitch * height/speed;

                playerPoint.x += Math.abs(xSpeed * elapsedTime) > 5 ? xSpeed * elapsedTime : 0;
                playerPoint.y -= Math.abs(ySpeed * elapsedTime) > 5 ? ySpeed * elapsedTime : 0;
            }

            if(playerPoint.x < 0)
                playerPoint.x = 0;
            else if(playerPoint.x > width)
                playerPoint.x = width;
            if(playerPoint.y < 0)
                playerPoint.y = 0;
            else if(playerPoint.y > height)
                playerPoint.y = height;


            player.update(playerPoint);
            obstacleManager.update();

            if(obstacleManager.playerCollide(player)){

                gameOver = true;
                Constants.GAMEOVER = true;
                orientationData.pause();
            }
        }
    }

    /**
     * Aligns the text to the center of the layout and then moves it vertically depending on the
     * padding value.
     * @param canvas The canvas on which everything is drawn.
     * @param paint the Paint.
     * @param text The text which is about to be drawn.
     * @param padding The int value to move the text vertically, negative value moves it up and
     *                positive moves it down. Zero leaves it centered.
     */
    private void drawText(Canvas canvas, Paint paint, String text, int padding) {

        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;

        //Moves the text vertically.
        y += padding;
        canvas.drawText(text, x, y, paint);
    }

    /**
     * Initializes the OnstacleManager differently depending on chosen difficulty. It changes the
     * player gap (the gap in which the player is moving through the obstacles), the obstacle
     * gap (the gap between each row of obstacles), the color of the obstacles and the
     * acceleration of the speed both for the obstacles and the RectPlayer.
     */
    private void initializeObstacleManager(){

        switch(Constants.DIFFICULTY){
            case 0:
                obstacleManager = new ObstacleManager(400, 600, 75, Color.BLACK, 10000.0f, width,
                        height);
                speed = 1000f;
                break;
            case 1:
                obstacleManager = new ObstacleManager(300, 400, 75, Color.GREEN, 8000.0f, width,
                        height);
                speed = 1000f;
                break;
            case 2:
                obstacleManager = new ObstacleManager(250, 400, 75, Color.YELLOW, 6000.0f, width,
                        height);
                speed = 1000f;
                break;
            case 3:
                obstacleManager = new ObstacleManager(200, 400, 75, Color.RED, 5000.0f, width,
                        height);
                speed = 800f;
                break;
            case 4:
                obstacleManager = new ObstacleManager(200, 400, 75, Color.WHITE, 3000.0f, width,
                        height);
                speed = 600f;
                break;
        }
    }

    /**
     * @return the orientationData.
     */
    public OrientationData getOrientationData(){
        return orientationData;
    }
}