package io.com.alephtask;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.Menu;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import io.com.alephtask.adapters.ShopAdapter;
import io.com.alephtask.fragments.ShopListFragment;

public class MainActivity extends AppCompatActivity {

    // double back pressed function
    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showFragment(new ShopListFragment(), "movieList");
    }

    /**
     * function to show the fragment
     *
     * @param name fragment to be shown
     * @param tag  fragment tag
     */
    private void showFragment(Fragment name, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        // check if the fragment is in back stack
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(tag, 0);
        if (fragmentPopped) {
            // fragment is pop from backStack
        } else {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, name, tag);
            fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.commit();
        }
    }

    /**
     * function to show the fragment
     *
     * @param current current visible fragment
     * @param tag     fragment tag
     */
    public void showFragmentWithTransition(Fragment current, Fragment newFragment, String tag, RecyclerView recyclerView) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        // check if the fragment is in back stack
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(tag, 0);
        if (fragmentPopped) {
            // fragment is pop from backStack
        } else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                Slide slideTransition = new Slide();
                slideTransition.setSlideEdge(Gravity.LEFT);
                slideTransition.setDuration(800);

                Fade fadeIn = new Fade();
                fadeIn.setInterpolator(new AccelerateDecelerateInterpolator());

                Slide slideRightTransition = new Slide();
                slideRightTransition.setSlideEdge(Gravity.RIGHT);
                slideRightTransition.setDuration(3000);

                newFragment.setEnterTransition(slideTransition); // done


                Transition autoTransition =
                        TransitionInflater.from(this).
                                inflateTransition(R.transition.auto_transition);
                newFragment.setSharedElementEnterTransition(autoTransition);
                current.setSharedElementReturnTransition(autoTransition);


                // current.setExitTransition(slideRightTransition);

                // newFragment.setEnterTransition(slideTransition);


            }

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, newFragment, tag);
            fragmentTransaction.addToBackStack(tag);

            for (int i = 0; i < recyclerView.getAdapter().getItemCount(); i++) {
                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(i);

                if (viewHolder instanceof ShopAdapter.ShopCollapseViewHolder) {
                    ShopAdapter.ShopCollapseViewHolder collapseViewHolder = (ShopAdapter.ShopCollapseViewHolder) viewHolder;
                    fragmentTransaction.addSharedElement(collapseViewHolder.getShopName(), collapseViewHolder.getShopName().getTransitionName());
                    fragmentTransaction.addSharedElement(collapseViewHolder.getShopDistance(), collapseViewHolder.getShopDistance().getTransitionName());
                    fragmentTransaction.addSharedElement(collapseViewHolder.getRightArrow(), collapseViewHolder.getRightArrow().getTransitionName());
                }
            }

            fragmentTransaction.commit();
        }
    }

    /**
     * function to go back to previous fragment
     */
    private void oneStepBack() {
        FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() >= 2) {
            fragmentManager.popBackStackImmediate();
            fts.commit();
        } else {
            doubleClickToExit();
        }
    }

    private void doubleClickToExit() {
        if ((back_pressed + 2000) > System.currentTimeMillis())
            finish();
        else
            Toast.makeText(MainActivity.this, "Click again to exit", Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }

    @Override
    public void onBackPressed() {
        oneStepBack();
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}