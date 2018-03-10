package io.com.alephtask;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.com.alephtask.adapters.ShopAdapter;
import io.com.alephtask.models.Shop;

public class MainActivityOld extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.appBar)
    AppBarLayout appBarLayout;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.rv_products_list)
    RecyclerView recyclerView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    int mToolbarHeight, mAnimDuration = 600/* milliseconds */;
    ValueAnimator mVaActionBar;
    private ShopAdapter mAdapter;
    private ArrayList<Shop> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_old);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                hideActionBar();
                slideDown(bottomNavigationView);
                int resId = R.anim.layout_animation_right_left;
                LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(recyclerView.getContext(), resId);
                recyclerView.setLayoutAnimationListener(null);
                recyclerView.setLayoutAnimation(animation);
                recyclerView.getAdapter().notifyDataSetChanged();
                recyclerView.scheduleLayoutAnimation();

                recyclerView.setLayoutAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                        dataList.clear();
                        dataList.addAll(Shop.createContactsList(2, 1));
                        recyclerView.getAdapter().notifyDataSetChanged();
                        recyclerView.setLayoutAnimationListener(null);
//                recyclerView.scheduleLayoutAnimation();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
//

            }
        });
        setupRecycler();
        setupPullDownListener();
        slideDown(bottomNavigationView);
        hideActionBar();
    }

    private void setupPullDownListener() {
        // Remove mSwipeRefreshLayout spinner view
        try {
            Field f = mSwipeRefreshLayout.getClass().getDeclaredField("mCircleView");
            f.setAccessible(true);
            ImageView img = (ImageView) f.get(mSwipeRefreshLayout);
            img.setAlpha(0.0f);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // do something
                showActionBar();
                dataList.clear();
                dataList.addAll(Shop.createContactsList(11, 0));
                runLayoutAnimation(recyclerView);

            }
        });
    }


    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


//    private void slideUp(BottomNavigationView child) {
//        if (child.getVisibility() == View.GONE) {
//            child.setVisibility(View.VISIBLE);
//            child.clearAnimation();
//            child.animate().translationY(0).setDuration(2000);
//        }
//
//    }
//
//    private void slideDown(final BottomNavigationView child) {
//        child.clearAnimation();
//        child.animate().translationY(child.getHeight()).setDuration(200);
//    }


    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_left_right);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
        recyclerView.setLayoutAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                slideUp(bottomNavigationView);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void setupRecycler() {

        dataList.addAll(Shop.createContactsList(2, 1));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ShopAdapter(dataList, this);
        recyclerView.setAdapter(mAdapter);

        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }


    // slide the view from below itself to the current position
    public void slideUp(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(800);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    // slide the view from its current position to below itself
    public void slideDown(View view) {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(800);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }


    void hideActionBar() {
        appBarLayout.animate().translationY(-appBarLayout.getBottom()).setInterpolator(new AccelerateInterpolator()).start();


//        // initialize `mToolbarHeight`
//        if (mToolbarHeight == 0) {
//            mToolbarHeight = toolbar.getHeight();
//        }
//
//        if (mVaActionBar != null && mVaActionBar.isRunning()) {
//            // we are already animating a transition - block here
//            return;
//        }
//
//        // animate `Toolbar's` height to zero.
//        mVaActionBar = ValueAnimator.ofInt(mToolbarHeight , 0);
//        mVaActionBar.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                // update LayoutParams
//                ((AppBarLayout.LayoutParams)toolbar.getLayoutParams()).height
//                        = (Integer)animation.getAnimatedValue();
//                toolbar.requestLayout();
//            }
//        });
//
//        mVaActionBar.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//
//                if (getSupportActionBar() != null) { // sanity check
//                    getSupportActionBar().hide();
//                }
//            }
//        });
//
//        mVaActionBar.setDuration(mAnimDuration);
//        mVaActionBar.start();
    }

    void showActionBar() {
        if (appBarLayout.getVisibility() == View.GONE) {
            appBarLayout.setVisibility(View.VISIBLE);
            appBarLayout.clearAnimation();
            appBarLayout.animate().translationY(0).setDuration(600).setInterpolator(new DecelerateInterpolator()).start();
        }


//        if (mVaActionBar != null && mVaActionBar.isRunning()) {
//            // we are already animating a transition - block here
//            return;
//        }
//
//        // restore `Toolbar's` height
//        mVaActionBar = ValueAnimator.ofInt(0 , mToolbarHeight);
//        mVaActionBar.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                // update LayoutParams
//                ((AppBarLayout.LayoutParams)toolbar.getLayoutParams()).height
//                        = (Integer)animation.getAnimatedValue();
//                toolbar.requestLayout();
//            }
//        });
//
//        mVaActionBar.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//                super.onAnimationStart(animation);
//
//                if (getSupportActionBar() != null) { // sanity check
//                    getSupportActionBar().show();
//                }
//            }
//        });
//
//        mVaActionBar.setDuration(mAnimDuration);
//        mVaActionBar.start();
    }

}
