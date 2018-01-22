package com.duckydev.mvpdagger.splash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.duckydev.mvpdagger.R;

import dagger.android.support.DaggerAppCompatActivity;

public class SplashActivity extends DaggerAppCompatActivity implements SplashContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_act);
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void presentHome() {

    }

    @Override
    public void setLanguage() {

    }

    @Override
    public void showLoading() {

    }
}
