package com.duckydev.mvpdagger.features;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.duckydev.mvpdagger.R;
import com.duckydev.mvpdagger.util.ActivityUtils;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class FeaturesActivity extends DaggerAppCompatActivity {

    public static String EXTRA_FEATURE = "EXTRA_FEATURE";

//    @Inject
//    FeaturesPresenter mFeaturesPresenter;

    @Inject
    FeaturesFragment injectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.features_act);

        // Setup toolbar

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        FeaturesFragment featuresFragment = (FeaturesFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (featuresFragment == null) {
            featuresFragment = injectedFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), featuresFragment, R.id.contentFrame);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}


