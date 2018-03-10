package io.com.alephtask;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.widget.ImageView;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.com.alephtask.adapters.ShopAdapter;
import io.com.alephtask.models.Shop;

public class HomeActivity extends AppCompatActivity {


    @BindView(R.id.rv_products_list)
    RecyclerView recyclerView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private ArrayList<Shop> dataList = new ArrayList<>();

    private ShopAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setupWindowAnimations();
        setupRecycler();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setupPullDownListener();
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

//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//            }
//        });
    }


    private void setupWindowAnimations() {
        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setExitTransition(slide);
    }


//
//    public void transitionToActivity(Class target, ShopAdapter.ShopCollapseViewHolder viewHolder) {
//        recyclerView.getAdapter().get
//        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this, false,
//                new Pair<>(viewHolder.binding.sampleIcon, getString(R.string.shop_name)),
//                new Pair<>(viewHolder.binding.sampleName, getString(R.string.shop_disance)));
//        startActivity(target, pairs);
//    }
//
//    private void startActivity(Class target, Pair<View, String>[] pairs) {
//        Intent i = new Intent(HomeActivity.this, target);
//        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);
//        i.putParcelableArrayListExtra("sample", dataList);
//        startActivity(i, transitionActivityOptions.toBundle());
//    }
}
