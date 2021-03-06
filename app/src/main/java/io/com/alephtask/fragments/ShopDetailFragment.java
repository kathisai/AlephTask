package io.com.alephtask.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.com.alephtask.R;
import io.com.alephtask.adapters.ShopAdapter;
import io.com.alephtask.models.Shop;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShopDetailFragment extends Fragment {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.rv_products_list)
    RecyclerView recyclerView;

    @BindView(R.id.ll_container)
    LinearLayout container;


    private ShopAdapter mAdapter;
    private ArrayList<Shop> dataList = new ArrayList<>();


    public ShopDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, rootView);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideDown(bottomNavigationView);
                hideActionBar();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().onBackPressed();
                    }
                }, 700);


            }
        });
        setupRecycler();
        hideActionBar();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // do stuff
                TransitionManager.beginDelayedTransition(container);
                showActionBar();
                slideUp(bottomNavigationView);
            }
        }, 500);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void setupRecycler() {

        dataList.addAll(Shop.createShopsList(0));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ShopAdapter(dataList, getActivity());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addItemDecoration(
                new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
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
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }

    void hideActionBar() {
        toolbar.animate().setStartDelay(200).translationY(-toolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
    }

    void showActionBar() {
        if (toolbar.getVisibility() == View.GONE) {
            toolbar.setVisibility(View.VISIBLE);
            toolbar.clearAnimation();
            toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
        }
    }


}