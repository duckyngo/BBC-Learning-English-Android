package com.duckydev.mvpdagger.di;

import com.duckydev.mvpdagger.category.CategoryActivity;
import com.duckydev.mvpdagger.category.CategoryModule;
import com.duckydev.mvpdagger.episodedetail.EpisodeDetailActivity;
import com.duckydev.mvpdagger.episodedetail.EpisodeDetailModule;
import com.duckydev.mvpdagger.features.FeaturesActivity;
import com.duckydev.mvpdagger.features.FeaturesModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by duckyng on 12/21/2017.
 */

@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = CategoryModule.class)
    abstract CategoryActivity categoryActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = FeaturesModule.class)
    abstract FeaturesActivity featuresActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = EpisodeDetailModule.class)
    abstract EpisodeDetailActivity episodeDetailActivity();

}
