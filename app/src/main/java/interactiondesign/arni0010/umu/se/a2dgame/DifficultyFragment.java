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
 * selectable difficulties in the game.
 */
public class DifficultyFragment extends Fragment{

    private ImageView easy, medium, hard, insane, extreme;

    private MainActivity main;

    /**
     *  Creates the fragments view and instantiates the layout XML file into the corresponding the
     *  views objects. It instantiates the 5 imageviews used to represent each selectable
     *  difficulty in the game.
     * @param inflater The LayoutInflater.
     * @param container The views layout area.
     * @param savedInstanceState Data of the activity's previous initialization part.
     * @return The view of this fragment.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.difficulty_fragment, container, false);

        main = (MainActivity)getActivity();

        easy = (ImageView)view.findViewById(R.id.easy);
        medium = (ImageView)view.findViewById(R.id.medium);
        hard = (ImageView)view.findViewById(R.id.hard);
        insane = (ImageView)view.findViewById(R.id.insane);
        extreme = (ImageView)view.findViewById(R.id.extreme);


        switch (Constantsv1.DIFFICULTY){

            case 0:
                easy.setBackgroundColor(getResources().getColor(R.color.colorMarker));
                break;

            case 1:
                medium.setBackgroundColor(getResources().getColor(R.color.colorMarker));
                break;

            case 2:
                hard.setBackgroundColor(getResources().getColor(R.color.colorMarker));
                break;

            case 3:
                insane.setBackgroundColor(getResources().getColor(R.color.colorMarker));
                break;

            case 4:
                extreme.setBackgroundColor(getResources().getColor(R.color.colorMarker));
                break;

        }

        setOnClickListeners();

        return view;
    }

    /**
     * Depending on the selected difficulty it changes the background color on each
     * individual image when one imageview is clicked. Since every case has a small
     * difference from all others the method is unfortunately quite long. (It looks like copied
     * code but it is not, the change of background color of the images changes slightly for each
     * onClickListener).
     */
    public void setOnClickListeners(){

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Constantsv1.DIFFICULTY = 0;
                main.setDifficultyIcon();
                easy.setBackgroundColor(getResources().getColor(R.color.colorMarker));
                medium.setBackgroundColor(view.getSolidColor());
                hard.setBackgroundColor(view.getSolidColor());
                insane.setBackgroundColor(view.getSolidColor());
                extreme.setBackgroundColor(view.getSolidColor());
            }
        });

        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Constantsv1.DIFFICULTY = 1;
                main.setDifficultyIcon();
                medium.setBackgroundColor(getResources().getColor(R.color.colorMarker));
                easy.setBackgroundColor(view.getSolidColor());
                hard.setBackgroundColor(view.getSolidColor());
                insane.setBackgroundColor(view.getSolidColor());
                extreme.setBackgroundColor(view.getSolidColor());
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Constantsv1.DIFFICULTY = 2;
                main.setDifficultyIcon();
                hard.setBackgroundColor(getResources().getColor(R.color.colorMarker));
                medium.setBackgroundColor(view.getSolidColor());
                easy.setBackgroundColor(view.getSolidColor());
                insane.setBackgroundColor(view.getSolidColor());
                extreme.setBackgroundColor(view.getSolidColor());
            }
        });

        insane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Constantsv1.DIFFICULTY = 3;
                main.setDifficultyIcon();
                insane.setBackgroundColor(getResources().getColor(R.color.colorMarker));
                medium.setBackgroundColor(view.getSolidColor());
                hard.setBackgroundColor(view.getSolidColor());
                easy.setBackgroundColor(view.getSolidColor());
                extreme.setBackgroundColor(view.getSolidColor());
            }
        });

        extreme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Constantsv1.DIFFICULTY = 4;
                main.setDifficultyIcon();
                extreme.setBackgroundColor(getResources().getColor(R.color.colorMarker));
                medium.setBackgroundColor(view.getSolidColor());
                hard.setBackgroundColor(view.getSolidColor());
                insane.setBackgroundColor(view.getSolidColor());
                easy.setBackgroundColor(view.getSolidColor());
            }
        });
    }
}
