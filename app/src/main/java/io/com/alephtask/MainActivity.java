package io.com.alephtask;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.appBar)
    AppBarLayout appBarLayout;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;
    private boolean temp = true;

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
            }
        });


        hideToolBarAndBottomBar();
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
        slideDown(bottomNavigationView);
    }

    private void showToolBarAndBottomBar(){
        appBarLayout.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
        slideUp(bottomNavigationView);
    }

    @OnClick(R.id.btn_test)
    void testclick(){
        if(temp){
            hideToolBarAndBottomBar();
            temp = false;

        }else{
            showToolBarAndBottomBar();
            temp = true;
        }
    }

    private void slideUp(BottomNavigationView child) {
        child.clearAnimation();
        child.animate().translationY(0).setDuration(200);
    }

    private void slideDown(BottomNavigationView child) {
        child.clearAnimation();
        child.animate().translationY(child.getHeight()).setDuration(200);
    }


}
