package com.duckydev.mvpdagger.episodedetail;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.duckydev.mvpdagger.R;
import com.duckydev.mvpdagger.util.ActivityUtils;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class EpisodeDetailActivity extends DaggerAppCompatActivity {

    public static final String EXTRA_EPISODE_ID = "EXTRA_EPISOE_ID";

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.appbar)
    AppBarLayout mAppBarLayout;

    String mTitle = " ";

    @Inject
    EpisodeDetailFragment injectedFragment;


    @Inject
    EpisodeDetailPresenter mDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set fullscreen activity
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.episodedetail_act);
        ButterKnife.bind(this);

        // Setup collapsing toolbar
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCollapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white));
        mCollapsingToolbarLayout.setTitle("");
        mCollapsingToolbarLayout.setTitleEnabled(false);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                scrollRange = appBarLayout.getTotalScrollRange();
                String text = "";

                if (scrollRange + verticalOffset == 0) {
                    text = mTitle;
                    isShow = true;
                } else if (isShow) {
                    ViewCompat.setElevation(appBarLayout, 0.0f);
                    isShow = false;
                }

                if (!getSupportActionBar().getTitle().toString().equals(text)) {
                            getSupportActionBar().setTitle(text);
                }
            }
        });

        EpisodeDetailFragment episodeDetailFragment = (EpisodeDetailFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (episodeDetailFragment == null) {
            episodeDetailFragment = injectedFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), episodeDetailFragment, R.id.contentFrame);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDetailPresenter.cleanUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return true;
    }

    public void loadBackdrop(String imageUrl) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        int appbarLayoutHeight = (width * 356) / 624;

        ViewGroup.LayoutParams layoutParams = mAppBarLayout.getLayoutParams();
        layoutParams.height = appbarLayoutHeight;
        layoutParams.width = width;
        mAppBarLayout.setLayoutParams(layoutParams);

        final ImageView imageView = findViewById(R.id.backdrop);
        Picasso.with(this).load(imageUrl).into(imageView);
    }

    public void loadToolbarTitle(String title) {
        mTitle = title;
        mCollapsingToolbarLayout.setTitle(mTitle);
        Log.d("ky.nd", title);
    }


}
