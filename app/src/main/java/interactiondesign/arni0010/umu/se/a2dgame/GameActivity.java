package interactiondesign.arni0010.umu.se.a2dgame;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Creates the SurfaceView GamePanel on which the game will play and initializes the Constants
 * SCREEN_HEIGHT, SCREEN_WIDTH, HIGHSCORE and GAMEOVER to later be used in the game.
 */
public class GameActivity extends AppCompatActivity {

    protected GamePanel gamePanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_game);

        LinearLayout ll = findViewById(R.id.gamePanel);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels - getNavigationBarHeight();

        gamePanel = new GamePanel(this, width, height);

        ll.setOrientation(LinearLayout.VERTICAL);

        ll.addView(gamePanel);
        gamePanel.setVisibility(View.VISIBLE);
    }

    /**
     * Saves all valuable values if the GameActivity were to get destroyed.
     * @param outState The Bundle.
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        gamePanel.getData(outState);
        outState.putInt("Highscore", Constants.HIGHSCORE);
        outState.putInt("Character", Constants.CHOSEN_CHARACTER);
        outState.putInt("Difficulty", Constants.DIFFICULTY);
        outState.putLong("Time", Constants.INIT_TIME);
        outState.putBoolean("Gameover", Constants.GAMEOVER);
        outState.putInt("Menu", Constants.MENU_HEIGHT);
        gamePanel.getSceneManager().getGameplayScene().getOrientationData().pause();
        System.out.println("Save: "+ Constants.HIGHSCORE);
    }

    /**
     * Restores valuable data from the savedInstanceBundle.
     * @param savedInstanceState The Bundle.
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        System.out.println("Restore: "+ Constants.HIGHSCORE);
        Constants.HIGHSCORE = savedInstanceState.getInt("Highscore");
        System.out.println("Restore-2: "+ Constants.HIGHSCORE);
        gamePanel = new GamePanel(this, savedInstanceState);
    }

    /**
     * Pauses the listeners for the sensors when the game pauses.
     */
    @Override
    protected void onPause() {
        super.onPause();

        gamePanel.getSceneManager().getGameplayScene().getOrientationData().pause();
    }

    /**
     * Restarts the listeners for the sensors when the game starts after being paused.
     */
    @Override
    protected void onResume() {
        super.onResume();

        gamePanel.getSceneManager().getGameplayScene().getOrientationData().register();
    }

    /**
     * Gets the navigationbars height to subtract it from the games screen height to prevent the
     * view of the game to be too long making the character go totally off screen.
     * @return the navigationbars height in pixels.
     */
    private int getNavigationBarHeight(){

        Resources resources = this.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }

        return 0;
    }
}
