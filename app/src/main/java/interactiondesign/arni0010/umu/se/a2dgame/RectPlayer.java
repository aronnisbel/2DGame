package interactiondesign.arni0010.umu.se.a2dgame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Creates a rectangle with an image and uses the Animation class to animate these images
 * depending on movement of the player in the game.
 */

public class RectPlayer implements GameObject {

    private Rect rectangle;

    private Animation idle, walkRight, walkLeft;
    private AnimationManager animManager;

    /**
     * @return the Rect.
     */
    public Rect getRectangle(){return rectangle;}

    /**
     * The constructor initializes the Rect to the param Rect. It decodes three different images
     * (BitMapFactory) that are different depending on the chosen character. These images
     * currently display an alien on the run and by using the class Animation it animates between
     * these images as the player move right or left. The raw images are all turned to the right
     * but by using a Matrix and its method preScale() these images are flipped and can be used
     * the same way when the player is moving left.
     * @param rectangle The Rect designated to the player image.
     */
    public RectPlayer(Rect rectangle){

        this.rectangle = rectangle;

        BitmapFactory bf = new BitmapFactory();
        Bitmap idleImg, walk1, walk2;

        idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable
                .alienbeige);
        walk1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable
                .alienbeige_walk1);
        walk2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable
                .alienbeige_walk2);

        switch(Constants.CHOSEN_CHARACTER) {

            case 1:
                idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable
                        .alienblue);
                walk1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable
                        .alienblue_walk1);
                walk2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable
                        .alienblue_walk2);
                break;

            case 2:
                idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable
                        .aliengreen);
                walk1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable
                        .aliengreen_walk1);
                walk2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable
                        .aliengreen_walk2);
                break;

            case 3:
                idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable
                        .alienpink);
                walk1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable
                        .alienpink_walk1);
                walk2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable
                        .alienpink_walk2);
                break;

            case 4:
                idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable
                        .alienyellow);
                walk1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable
                        .alienyellow_walk1);
                walk2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable
                        .alienyellow_walk2);
                break;
        }

        idle = new Animation(new Bitmap[]{idleImg}, 2);
        walkRight = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);

        Matrix m = new Matrix();
        m.preScale(-1, 1);
        walk1 = Bitmap.createBitmap(walk1, 0, 0, walk1.getWidth(), walk1.getHeight(), m,
                false);
        walk2 = Bitmap.createBitmap(walk2, 0, 0, walk2.getWidth(), walk2.getHeight(), m,
                false);

        walkLeft = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);

        animManager = new AnimationManager(new Animation[]{idle, walkRight, walkLeft});
    }

    /**
     * Since the images are drawn by animation it passes the canvas on to the AnimationManager.
     * @param canvas The canvas on which everything is drawn.
     */
    @Override
    public void draw(Canvas canvas) {

        animManager.draw(canvas, rectangle);
    }

    /**
     * Calls the AnimationManagers update method.
     */
    @Override
    public void update() {

        animManager.update();
    }

    /**
     * This method is called when the player point changes. Calls the AnimationManagers update
     * method in the end.
     * @param point The new Point.
     */
    public void update(Point point){

        float oldLeft = rectangle.left;

        rectangle.set(point.x - rectangle.width()/2, point.y - rectangle
                        .height()/2, point.x +
                rectangle.width()/2, point.y + rectangle.height()/2);

        int state = 0;

        if(rectangle.left - oldLeft > 5)
            state = 1;
        else if(rectangle.left - oldLeft < -5)
            state = 2;

        animManager.playAnim(state);
        animManager.update();
    }
}
