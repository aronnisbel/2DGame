package interactiondesign.arni0010.umu.se.a2dgame;

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
    }

    /**
     * Saves all valuable values if the game were to stop.
     * @param outState The Bundle.
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("Highscore", Constantsv1.HIGHSCORE);
        outState.putInt("Character", Constantsv1.CHOSEN_CHARACTER);
        outState.putInt("Difficulty", Constantsv1.DIFFICULTY);
        outState.putInt("Menu", Constantsv1.MENU_HEIGHT);
    }

    /**
     * Restores valuable data from the savedInstanceBundle.
     * @param savedInstanceState The Bundle.
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Constantsv1.HIGHSCORE = savedInstanceState.getInt("Highscore");
        Constantsv1.CHOSEN_CHARACTER = savedInstanceState.getInt("Character");
        Constantsv1.DIFFICULTY = savedInstanceState.getInt("Difficulty");
        Constantsv1.MENU_HEIGHT = savedInstanceState.getInt("Menu");

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
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

        Log.d(TAG, "Difficulty: " + Constantsv1.DIFFICULTY);

        switch(Constantsv1.DIFFICULTY){
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

        if(Constantsv1.DIFFICULTY != 0 && Constantsv1.DIFFICULTY != 1 && Constantsv1
                .DIFFICULTY != 2 && Constantsv1.DIFFICULTY != 3 && Constantsv1
                .DIFFICULTY != 4){

            Constantsv1.DIFFICULTY = 0;
        }
    }

    /**
     * If the chosen character Constant have not been initialized it will be after this method.
     */
    private void setChosenCharacter(){

        if(Constantsv1.CHOSEN_CHARACTER != 0 && Constantsv1.CHOSEN_CHARACTER != 1 && Constantsv1
                .CHOSEN_CHARACTER != 2 && Constantsv1.CHOSEN_CHARACTER != 3 && Constantsv1
                .CHOSEN_CHARACTER != 4){

            Constantsv1.CHOSEN_CHARACTER = 0;
        }
    }
}
