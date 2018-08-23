package interactiondesign.arni0010.umu.se.a2dgame;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_game);

        LinearLayout ll = findViewById(R.id.gamePanel);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels - getNavigationBarHeight();
        Constants.GAMEOVER = false;

        GamePanel gamePanel = new GamePanel(this);

        ll.setOrientation(LinearLayout.VERTICAL);

        ll.addView(gamePanel);
        gamePanel.setVisibility(View.VISIBLE);

        Constants.CURRENT_CONTEXT = this;
    }

    /**
     * Saves all valuable values if the game were to stop.
     * @param outState The Bundle.
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("Highscore", Constants.HIGHSCORE);
        outState.putInt("Character", Constants.CHOSEN_CHARACTER);
        outState.putInt("Difficulty", Constants.DIFFICULTY);
        outState.putInt("Width", Constants.SCREEN_WIDTH);
        outState.putInt("Height", Constants.SCREEN_HEIGHT);
        outState.putLong("Time", Constants.INIT_TIME);
        outState.putBoolean("Gameover", Constants.GAMEOVER);
        outState.putInt("Menu", Constants.MENU_HEIGHT);
    }

    /**
     * Restores valuable data from the savedInstanceBundle.
     * @param savedInstanceState The Bundle.
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Constants.HIGHSCORE = savedInstanceState.getInt("Highscore");
        Constants.CHOSEN_CHARACTER = savedInstanceState.getInt("Character");
        Constants.DIFFICULTY = savedInstanceState.getInt("Difficulty");
        Constants.SCREEN_HEIGHT = savedInstanceState.getInt("Height");
        Constants.SCREEN_WIDTH = savedInstanceState.getInt("Width");
        Constants.INIT_TIME = savedInstanceState.getLong("Time");
        Constants.GAMEOVER = savedInstanceState.getBoolean("Gameover");
        Constants.MENU_HEIGHT = savedInstanceState.getInt("Menu");
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
