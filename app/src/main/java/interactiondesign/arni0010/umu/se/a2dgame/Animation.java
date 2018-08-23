package interactiondesign.arni0010.umu.se.a2dgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by ar0n_ on 12/03/2018.
 */

public class Animation {

    private Bitmap[] frames;
    private int frameIndex;

    private boolean isPlaying = false;

    public boolean isPlaying(){return isPlaying;}

    public void play(){

        isPlaying = true;
        frameIndex = 0;
        lastFrame = System.currentTimeMillis();
    }

    public void stop(){isPlaying = false;}

    private float framTime;

    private long lastFrame;

    public Animation(Bitmap[] frames, float animTime){

        this.frames = frames;
        frameIndex = 0;

        framTime = animTime/frames.length;

        lastFrame = System.currentTimeMillis();
    }

    public void draw(Canvas canvas, Rect destination){

        if(!isPlaying)
            return;

        scaleRect(destination);

        canvas.drawBitmap(frames[frameIndex], null, destination, new Paint());
    }

    private void scaleRect(Rect rect){

        float whRatio = (float)(frames[frameIndex].getWidth())/frames[frameIndex].getWidth();

        if(rect.width() > rect.height())
            rect.left = rect.right - (rect.height() * (int)whRatio);
        else
            rect.top = rect.bottom - (int)(rect.width() * (1/whRatio));
    }

    public void update(){

        if(!isPlaying){
            return;
        }

        if(System.currentTimeMillis() - lastFrame > framTime*1000){

            frameIndex++;
            frameIndex = frameIndex >= frames.length ? 0 : frameIndex;
            lastFrame = System.currentTimeMillis();
        }
    }
}
