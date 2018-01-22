package com.duckydev.mvpdagger.di;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

/**
 * Created by duckyng on 12/21/2017.
 */

@Module
public abstract class ApplicationModule {

    @Binds
    abstract Context bindContext(Application application);


}
