package com.duckydev.mvpdagger;

import com.duckydev.mvpdagger.data.source.EpisodesRepository;
import com.duckydev.mvpdagger.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * Created by duckyng on 12/20/2017.
 */

public class MVPDaggerApplication extends DaggerApplication {

    @Inject
    EpisodesRepository episodesRepository;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();

    }

    public EpisodesRepository getEpisodesRepository() {
        return episodesRepository;
    }

}
