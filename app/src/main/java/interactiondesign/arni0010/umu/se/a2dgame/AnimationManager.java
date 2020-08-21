package interactiondesign.arni0010.umu.se.a2dgame;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Creates an array with all character images and plays animations between them in the right order.
 */

public class AnimationManager implements Parcelable {

    private Animation[] animations;
    private int animationIndex = 0;

    /**
     * Instantiates the array with the character images from the param.
     * @param animations an array of images on which the animations will go-between.
     */
    public AnimationManager(Animation[] animations){

        this.animations = animations;
    }

    /**
     * Plays the animations.
     * @param index the wanted index of animation.
     */
    public void playAnim(int index){

        for(int i=0; i < animations.length; i++){

            if(i == index) {
                if(!animations[index].isPlaying())
                    animations[i].play();
            } else
                animations[i].stop();
        }

        animationIndex = index;
    }

    /**
     * Draws the animations to the canvas if the animation is playing.
     * @param canvas The canvas on which everything is drawn.
     * @param rect The RectPlayer.
     */
    public void draw(Canvas canvas, Rect rect){

        if(animations[animationIndex].isPlaying()) {
            animations[animationIndex].draw(canvas, rect);
        }
    }

    /**
     * Updates the animation when the animation is playing.
     */
    public void update(){

        if(animations[animationIndex].isPlaying()){
            animations[animationIndex].update();
        }
    }

    protected AnimationManager(Parcel in) {
        animations = (Animation[]) in.readValue(Animation[].class.getClassLoader());
        animationIndex = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(animations);
        dest.writeInt(animationIndex);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<AnimationManager> CREATOR = new Parcelable.Creator<AnimationManager>() {
        @Override
        public AnimationManager createFromParcel(Parcel in) {
            return new AnimationManager(in);
        }

        @Override
        public AnimationManager[] newArray(int size) {
            return new AnimationManager[size];
        }
    };
}