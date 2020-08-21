package interactiondesign.arni0010.umu.se.a2dgame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Creates an Obstacle containing two horizontally aligned Rects with a gap between them.
 */

public class Obstacle implements GameObject, Parcelable {

    private Rect rectangle;
    private Rect rectangle2;
    private int color;

    /**
     * Returns one of the rectangles to be used to get the vertical orientation of the obstacle.
     * Since both are aligned only one rectangle is needed.
     * @return one of the obstacle's rectangles.
     */
    public Rect getRectangle(){return rectangle;}

    /**
     * Increments the y value.
     * @param y the y value.
     */
    public void incrementY(float y){

        rectangle.top += y;
        rectangle.bottom += y;
        rectangle2.top += y;
        rectangle2.bottom += y;
    }

    /**
     * Sets the colot of the obstacle to the param color and initiates two Rects one on either
     * side of the screen.
     * @param rectHeight The height of the rectangle.
     * @param color The Color of the rectangles
     * @param startX The start position in X direction (horizontally).
     * @param startY The start position in Y direction (vertically).
     * @param playerGap The gap between the rectangles (horizontally).
     */
    public Obstacle(int rectHeight, int color, int startX, int startY, int playerGap, int width){

        this.color = color;
        //left, top, right, bottom
        rectangle = new Rect(0, startY, startX, startY + rectHeight);
        rectangle2 = new Rect(startX + playerGap, startY, width, startY +
                rectHeight);
    }

    /**
     * By using the class Rect's method intersects() the RectPlayer can be checked if it has
     * collided with either of these two rectangles.
     * @param player The RectPlayer.
     * @return True or false depending on if the RectPlayer has collided with any of the rectangles
     * or not.
     */
    public boolean playerCollide(RectPlayer player){

        return Rect.intersects(rectangle, player.getRectangle()) || Rect.intersects(rectangle2,
                player.getRectangle());
    }

    /**
     * Draws this obstacle to the canvas.
     * @param canvas The canvas on which everything is drawn.
     */
    @Override
    public void draw(Canvas canvas) {

        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
        canvas.drawRect(rectangle2, paint);

    }

    /**
     * Not used. Just has to be overwritten.
     */
    @Override
    public void update() {

    }

    protected Obstacle(Parcel in) {
        rectangle = (Rect) in.readValue(Rect.class.getClassLoader());
        rectangle2 = (Rect) in.readValue(Rect.class.getClassLoader());
        color = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(rectangle);
        dest.writeValue(rectangle2);
        dest.writeInt(color);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Obstacle> CREATOR = new Parcelable.Creator<Obstacle>() {
        @Override
        public Obstacle createFromParcel(Parcel in) {
            return new Obstacle(in);
        }

        @Override
        public Obstacle[] newArray(int size) {
            return new Obstacle[size];
        }
    };
}