package com.duckydev.mvpdagger.category;

import com.duckydev.mvpdagger.di.ActivityScoped;
import com.duckydev.mvpdagger.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by duckyng on 12/21/2017.
 */

@Module
public abstract class CategoryModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract CategoryFragment categoryFragment();

    @ActivityScoped
    @Binds
    abstract CategoryContract.Presenter categoryPresenter(CategoryPresenter presenter);

}
