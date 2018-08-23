package interactiondesign.arni0010.umu.se.a2dgame;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Creates an ArrayList to handle all available Scenes. Currently there is only one Scene and
 * that's the GameplayScene.
 */
public class SceneManager {

    private ArrayList<interactiondesign.arni0010.umu.se.a2dgame.Scene> scenes = new ArrayList<>();
    public static int ACTIVE_SCENE;

    /**
     * Sets the Constant ACTIVE_SCENE to 0 to represent the GameplayScene which is instantiated and
     * put in the ArrayList at index 0.
     */
    public SceneManager(){

        ACTIVE_SCENE = 0;
        GameplayScene gameplayScene = new GameplayScene();

        scenes.add(gameplayScene);
    }

    /**
     * Passes the MotionEvent on to the current Scene.
     * @param event The MotionEvent.
     */
    public void recieveTouch(MotionEvent event){

        scenes.get(ACTIVE_SCENE).recieveTouch(event);
    }

    /**
     * Calls the current Scenes update method.
     */
    public void update(){

        scenes.get(ACTIVE_SCENE).update();
    }

    /**
     * Calls the current Scenes draw method and passes in the Canvas.
     */
    public void draw(Canvas canvas){

        scenes.get(ACTIVE_SCENE).draw(canvas);
    }
}
