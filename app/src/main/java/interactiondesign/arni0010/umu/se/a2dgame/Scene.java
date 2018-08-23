package interactiondesign.arni0010.umu.se.a2dgame;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Interface for each Scene in the game.
 */

public interface Scene {

    public void update();
    public void draw(Canvas canvas);
    public void terminate();
    public  void recieveTouch(MotionEvent event);
}
