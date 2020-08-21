package interactiondesign.arni0010.umu.se.a2dgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ar0n_ on 12/03/2018.
 */

public class Animation implements Parcelable {

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

    protected Animation(Parcel in) {
        frames = (Bitmap[]) in.readValue(Bitmap[].class.getClassLoader());
        frameIndex = in.readInt();
        isPlaying = in.readByte() != 0x00;
        framTime = in.readFloat();
        lastFrame = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(frames);
        dest.writeInt(frameIndex);
        dest.writeByte((byte) (isPlaying ? 0x01 : 0x00));
        dest.writeFloat(framTime);
        dest.writeLong(lastFrame);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Animation> CREATOR = new Parcelable.Creator<Animation>() {
        @Override
        public Animation createFromParcel(Parcel in) {
            return new Animation(in);
        }

        @Override
        public Animation[] newArray(int size) {
            return new Animation[size];
        }
    };
}