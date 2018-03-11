package io.com.alephtask.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.com.alephtask.MainActivity;
import io.com.alephtask.R;
import io.com.alephtask.adapters.ShopAdapter;
import io.com.alephtask.models.Shop;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopListFragment extends Fragment {
    @BindView(R.id.rv_products_list)
    RecyclerView recyclerView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private ArrayList<Shop> dataList = new ArrayList<>();

    private ShopAdapter mAdapter;
    private Context context;

    public ShopListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        context = getActivity();
        ButterKnife.bind(this, rootView);
        setupRecycler();
        setupPullDownListener();
        return rootView;
    }


    /**
     * function to open the movie detail fragment
     *
     * @param
     */
    public void openMovieDetailFragment(RecyclerView recyclerView) {
        if (context instanceof MainActivity) {
            ((MainActivity) context).showFragmentWithTransition(this, new ShopDetailFragment(), "movieDetail", recyclerView);
        }
    }


    private void setupRecycler() {
        dataList.clear();
        dataList.addAll(Shop.createShopsIntialList(1));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ShopAdapter(dataList, getActivity());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addItemDecoration(
                new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
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
                openMovieDetailFragment(recyclerView);
            }
        });
    }
}