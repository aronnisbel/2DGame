package interactiondesign.arni0010.umu.se.a2dgame;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Creates an array with all character images and plays animations between them in the right order.
 */

public class AnimationManager {

    private Animation[] animations;
    private int animationIndex = 0;

    /**
     * Instantiates the array with the character images from the param.
     * @param animations an array of images on which the animations will go between.
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
}
