package interactiondesign.arni0010.umu.se.a2dgame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * This class is one of three views attached to the BottomNavigationBar. It handles the different
 * usable characters available in the game.
 */
public class CharacterFragment extends Fragment{

    private ImageView biegeAlien, blueAlien, greenAlien, pinkAlien, yellowAlien;
    private View view;

    /**
     * Creates this fragment.
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     *  Creates the fragments view and instantiates the layout XML file into the corresponding the
     *  views objects. It instantiates the 5 imageviews used to represent each useable character
     *  in the game.
     * @param inflater The LayoutInflater.
     * @param container The views layout area.
     * @param savedInstanceState Data of the activity's previous initialization part.
     * @return The view of this fragment.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.character_fragment, container, false);

        biegeAlien = (ImageView)view.findViewById(R.id.biege_alien);
        blueAlien = (ImageView)view.findViewById(R.id.blue_alien);
        greenAlien = (ImageView)view.findViewById(R.id.green_alien);
        pinkAlien = (ImageView)view.findViewById(R.id.pink_alien);
        yellowAlien = (ImageView)view.findViewById(R.id.yellow_alien);

        switch (Constantsv1.CHOSEN_CHARACTER){

            case 0:
                biegeAlien.setBackgroundColor(getResources().getColor(R.color.colorMarker));
                biegeAlien.setImageResource(R.drawable.alienbeige_jump);
                break;

            case 1:
                blueAlien.setBackgroundColor(getResources().getColor(R.color.colorMarker));
                blueAlien.setImageResource(R.drawable.alienblue_jump);
                break;

            case 2:
                greenAlien.setBackgroundColor(getResources().getColor(R.color.colorMarker));
                greenAlien.setImageResource(R.drawable.aliengreen_jump);
                break;

            case 3:
                pinkAlien.setBackgroundColor(getResources().getColor(R.color.colorMarker));
                pinkAlien.setImageResource(R.drawable.alienpink_jump);
                break;

            case 4:
                yellowAlien.setBackgroundColor(getResources().getColor(R.color.colorMarker));
                yellowAlien.setImageResource(R.drawable.alienyellow_jump);
                break;

        }

        setOnClickListeners();

        return view;
    }

    /**
     * Sets an onClickListener to each ImageView. Depending on the selected character it changes
     * the background color and image source on each individual image when one imageview is clicked.
     */
    public void setOnClickListeners(){

        biegeAlien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                normalizeImages();
                Constantsv1.CHOSEN_CHARACTER = 0;
                biegeAlien.setImageResource(R.drawable.alienbeige_jump);
                biegeAlien.setBackgroundColor(getResources().getColor(R.color.colorMarker));
            }
        });

        blueAlien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                normalizeImages();
                Constantsv1.CHOSEN_CHARACTER = 1;
                blueAlien.setImageResource(R.drawable.alienblue_jump);
                blueAlien.setBackgroundColor(getResources().getColor(R.color.colorMarker));
            }
        });

        greenAlien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                normalizeImages();
                Constantsv1.CHOSEN_CHARACTER = 2;
                greenAlien.setImageResource(R.drawable.aliengreen_jump);
                greenAlien.setBackgroundColor(getResources().getColor(R.color.colorMarker));
            }
        });

        pinkAlien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                normalizeImages();
                Constantsv1.CHOSEN_CHARACTER = 3;
                pinkAlien.setImageResource(R.drawable.alienpink_jump);
                pinkAlien.setBackgroundColor(getResources().getColor(R.color.colorMarker));
            }
        });

        yellowAlien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                normalizeImages();
                Constantsv1.CHOSEN_CHARACTER = 4;
                yellowAlien.setImageResource(R.drawable.alienyellow_jump);
                yellowAlien.setBackgroundColor(getResources().getColor(R.color.colorMarker));
            }
        });
    }

    /**
     * Resets all images to unselected/normal state.
     */
    public void normalizeImages(){

        biegeAlien.setImageResource(R.drawable.alienbeige);
        greenAlien.setImageResource(R.drawable.aliengreen);
        pinkAlien.setImageResource(R.drawable.alienpink);
        blueAlien.setImageResource(R.drawable.alienblue);
        yellowAlien.setImageResource(R.drawable.alienyellow);
        yellowAlien.setBackgroundColor(view.getSolidColor());
        blueAlien.setBackgroundColor(view.getSolidColor());
        greenAlien.setBackgroundColor(view.getSolidColor());
        pinkAlien.setBackgroundColor(view.getSolidColor());
        biegeAlien.setBackgroundColor(view.getSolidColor());
    }
}
