package io.com.alephtask;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.ImageView;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.com.alephtask.adapters.ShopAdapter;
import io.com.alephtask.models.Shop;

public class MainActivity extends BaseActivity {

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
    private boolean temp = true;
    private ShopAdapter mAdapter;

    private ArrayList<Shop> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                hideToolBarAndBottomBar();

                int resId = R.anim.layout_animation_right_left;
                LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(recyclerView.getContext(), resId);
                recyclerView.setLayoutAnimation(animation);
                recyclerView.getAdapter().notifyDataSetChanged();
                recyclerView.scheduleLayoutAnimation();
            }
        });

        showToolBarAndBottomBar();
        setupRecycler();
        setupPullDownListener();
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

    private void hideToolBarAndBottomBar(){
        appBarLayout.animate().translationY(-appBarLayout.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
        appBarLayout.setLayoutAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                appBarLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        slideDown(bottomNavigationView);

    }

    private void showToolBarAndBottomBar(){
        if (appBarLayout.getVisibility() == View.GONE) {
            appBarLayout.setVisibility(View.VISIBLE);
            appBarLayout.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
        }

        slideUp(bottomNavigationView);
    }

    private void slideUp(BottomNavigationView child) {
        if (child.getVisibility() == View.GONE) {
            child.setVisibility(View.VISIBLE);
            child.clearAnimation();
            child.animate().translationY(0).setDuration(200000);
        }

    }

    private void slideDown(final BottomNavigationView child) {
        child.clearAnimation();
        child.animate().translationY(child.getHeight()).setDuration(200);
        child.setLayoutAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                child.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_left_right);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
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

}
