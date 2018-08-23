package interactiondesign.arni0010.umu.se.a2dgame;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates a List with the three Fragments in the BottomNavigationView to handle them easily.
 */
class ViewPagerAdapter extends FragmentPagerAdapter{

    private final List<Fragment> mFragmentList = new ArrayList<>();

    /**
     * Creates the Manager.
     * @param manager The FragmentManager.
     */
    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    /**
     * Gets a Fragment from a specific position in the List.
     * @param position The wanted position in the List.
     * @return The Fragment.
     */
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    /**
     * @return The size of the List of Fragments.
     */
    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    /**
     * Adds a Fragment to the Fragment List.
     * @param fragment The Fragment to be added.
     */
    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }
}
