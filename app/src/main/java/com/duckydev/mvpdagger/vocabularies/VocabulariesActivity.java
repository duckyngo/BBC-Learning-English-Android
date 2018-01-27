package com.duckydev.mvpdagger.vocabularies;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.duckydev.mvpdagger.R;
import com.duckydev.mvpdagger.features.FeaturesFragment;
import com.duckydev.mvpdagger.util.ActivityUtils;

import javax.inject.Inject;

import dagger.android.DaggerActivity;
import dagger.android.support.DaggerAppCompatActivity;

public class VocabulariesActivity extends DaggerAppCompatActivity {


    @Inject
    VocabularyFragment injectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vocabulary_act);
        // Setup toolbar

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Vocabulary");

        VocabularyFragment featuresFragment = (VocabularyFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

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
