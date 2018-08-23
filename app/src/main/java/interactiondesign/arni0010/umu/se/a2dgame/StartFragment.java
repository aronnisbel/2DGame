package interactiondesign.arni0010.umu.se.a2dgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * This class is one of three views attached to the BottomNavigationBar. It handles the play
 * button which launches the game activity.
 */
public class StartFragment extends Fragment{

    /**
     *  Creates the fragments view and instantiates the layout XML file into the corresponding the
     *  views objects. It instantiates a button with the an onClickListener that calls the method
     *  launchActivity().
     * @param inflater The LayoutInflater.
     * @param container The views layout area.
     * @param savedInstanceState Data of the activity's previous initialization part.
     * @return The view of this fragment.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.start_fragment, container, false);

        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.new_relativeButtonLayout);

        layout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams
                .MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));

        Button playButton = new Button(view.getContext());
        playButton.setText(R.string.title_play);
        playButton.setId(R.id.play_button);
        RelativeLayout.LayoutParams rl =  new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams
                .WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        rl.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        playButton.setLayoutParams(rl);

        layout.addView(playButton);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchActivity();
            }
        });

        return view;
    }

    /**
     * Creates an Intent with a GameActivity and starts that GameActivity.
     */
    public void launchActivity() {

        Intent intent = new Intent(getActivity(), GameActivity.class);
        startActivity(intent);
    }
}
