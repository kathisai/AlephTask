package io.com.alephtask;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.transition.Visibility;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.com.alephtask.adapters.ShopAdapter;
import io.com.alephtask.models.Shop;

public class DetailedActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.appBar)
    AppBarLayout appBarLayout;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.rv_products_list)
    RecyclerView recyclerView;


    private ShopAdapter mAdapter;
    private ArrayList<Shop> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        setupWindowAnimations();

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideActionBar();
                slideDown(bottomNavigationView);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DetailedActivity.this.onBackPressed();
                    }
                }, 3000);


            }
        });
        setupRecycler();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // do stuff
                showActionBar();
                slideUp(bottomNavigationView);
            }
        }, 3000);
    }

    private void setupRecycler() {

        dataList.addAll(Shop.createContactsList(10, 0));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ShopAdapter(dataList, this);
        recyclerView.setAdapter(mAdapter);

        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }


    void hideActionBar() {
        appBarLayout.animate().translationY(-appBarLayout.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
    }

    void showActionBar() {
        if (appBarLayout.getVisibility() == View.GONE) {
            appBarLayout.setVisibility(View.VISIBLE);
            appBarLayout.clearAnimation();
            appBarLayout.animate().translationY(0).setDuration(600).setInterpolator(new DecelerateInterpolator()).start();
        }
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

    private void setupWindowAnimations() {
        // Re-enter transition is executed when returning to this activity
        Slide slideTransition = new Slide();
        slideTransition.excludeTarget(android.R.id.statusBarBackground, true);
        slideTransition.excludeTarget(android.R.id.navigationBarBackground, true);
        slideTransition.setSlideEdge(Gravity.LEFT);
        slideTransition.setMode(Visibility.MODE_IN);
        slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        getWindow().setEnterTransition(slideTransition);
        getWindow().setExitTransition(slideTransition);
    }


}
