package interactiondesign.arni0010.umu.se.a2dgame;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Interface for each Scene in the game.
 */

public interface Scene {

    void update();
    void draw(Canvas canvas);
    void terminate();
    void recieveTouch(MotionEvent event);
}
