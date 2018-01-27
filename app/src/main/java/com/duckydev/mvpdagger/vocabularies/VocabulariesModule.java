package com.duckydev.mvpdagger.vocabularies;

import com.duckydev.mvpdagger.di.ActivityScoped;
import com.duckydev.mvpdagger.di.FragmentScoped;

import butterknife.BindView;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by duckyng on 1/26/2018.
 */

@Module
public abstract class VocabulariesModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract VocabularyFragment vocabularyFragment();

    @ActivityScoped
    @Binds
    abstract VocabulariesContract.Presenter vocabularyPresenter(VocabulariesPresenter vocabulariesPresenter);
}
