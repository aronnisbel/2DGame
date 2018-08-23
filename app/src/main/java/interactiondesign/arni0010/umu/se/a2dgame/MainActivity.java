package interactiondesign.arni0010.umu.se.a2dgame;

import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.io.File;

/**
 * Creates the MainActivity with a BottomNavigationView with three MenuItems in it. Each
 * corresponding to a specific Fragment. It also instantiates some important Constants that are
 * used in many places in the App.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ViewPager viewPager;

    private BottomNavigationView bottomNavigationView;

    private MenuItem prevMenuItem, item;

    public File file;

    /**
     * Instantiates all necessary components for the BottomNaviagtionView with a ViewPager to
     * control each selected Fragment.
     * @param savedInstanceState Data of the activity's previous initialization part.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {

            Constants.HIGHSCORE = savedInstanceState.getInt("Highscore");
            Constants.CHOSEN_CHARACTER = savedInstanceState.getInt("Character");
            Constants.DIFFICULTY = savedInstanceState.getInt("Difficulty");
            Constants.SCREEN_HEIGHT = savedInstanceState.getInt("Height");
            Constants.SCREEN_WIDTH = savedInstanceState.getInt("Width");
            Constants.INIT_TIME = savedInstanceState.getLong("Time");
            Constants.GAMEOVER = savedInstanceState.getBoolean("Gameover");
            Constants.MENU_HEIGHT = savedInstanceState.getInt("Menu");
        }

        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Starting.");

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Log.d(TAG, "Menuitem: " + item.getItemId());
                switch (item.getItemId()) {

                    case R.id.navigation_home:
                        viewPager.setCurrentItem(0);
                        return true;

                    case R.id.navigation_character:
                        viewPager.setCurrentItem(1);
                        return true;

                    case R.id.navigation_difficulty:
                        viewPager.setCurrentItem(2);
                        return true;
                }
                return false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }

                Log.d("page", "onPageSelected: " + position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupViewPager(viewPager);

        setChosenCharacter();
        setDifficulty();

        item = bottomNavigationView.getMenu().findItem(R.id.navigation_difficulty);

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
     * Creates an ViewPagerAdapter and adds all three Fragments to it which stores these
     * Fragments and their states to easily be collected at any time.
     * @param viewPager The ViewPager.
     */
    private void setupViewPager(ViewPager viewPager){

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new StartFragment());
        adapter.addFragment(new CharacterFragment());
        adapter.addFragment(new DifficultyFragment());
        viewPager.setAdapter(adapter);
    }

    /**
     * Changes the BottomNavigationView's difficulty icon to correspond to the selected
     * difficulty's icon.
     */
    public void setDifficultyIcon(){

        Log.d(TAG, "Difficulty: " + Constants.DIFFICULTY);

        switch(Constants.DIFFICULTY){
            case 0:
                item.setIcon(R.drawable.easy);
                break;
            case 1:
                item.setIcon(R.drawable.medium);
                break;
            case 2:
                item.setIcon(R.drawable.hard);
                break;
            case 3:
                item.setIcon(R.drawable.insane);
                break;
            case 4:
                item.setIcon(R.drawable.extreme
                );
                break;
        }
    }

    /**
     * If the difficulty Constant have not been initialized it will be after this method.
     */
    private void setDifficulty(){

        if(Constants.DIFFICULTY != 0 && Constants.DIFFICULTY != 1 && Constants
                .DIFFICULTY != 2 && Constants.DIFFICULTY != 3 && Constants
                .DIFFICULTY != 4){

            Constants.DIFFICULTY = 0;
        }
    }

    /**
     * If the chosen character Constant have not been initialized it will be after this method.
     */
    private void setChosenCharacter(){

        if(Constants.CHOSEN_CHARACTER != 0 && Constants.CHOSEN_CHARACTER != 1 && Constants
                .CHOSEN_CHARACTER != 2 && Constants.CHOSEN_CHARACTER != 3 && Constants
                .CHOSEN_CHARACTER != 4){

            Constants.CHOSEN_CHARACTER = 0;
        }
    }
}
