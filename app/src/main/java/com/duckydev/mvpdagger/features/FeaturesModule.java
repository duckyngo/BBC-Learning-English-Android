package com.duckydev.mvpdagger.features;

import com.duckydev.mvpdagger.di.ActivityScoped;
import com.duckydev.mvpdagger.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by duckyng on 12/25/2017.
 */
@Module
public abstract class FeaturesModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract FeaturesFragment featuresFragment();

    @ActivityScoped
    @Binds
    abstract FeaturesContract.Presenter taskPresenter(FeaturesPresenter presenter);

}
