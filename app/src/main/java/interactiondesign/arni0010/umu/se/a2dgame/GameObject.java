package interactiondesign.arni0010.umu.se.a2dgame;

import android.graphics.Canvas;

/**
 * Interface for each object in the game.
 */

public interface GameObject {

    public void draw(Canvas canvas);
    public void update();
}
