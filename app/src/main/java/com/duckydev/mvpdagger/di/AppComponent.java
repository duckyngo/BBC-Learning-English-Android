package com.duckydev.mvpdagger.di;

import android.app.Application;

import com.duckydev.mvpdagger.MVPDaggerApplication;
import com.duckydev.mvpdagger.data.source.EpisodesRepository;
import com.duckydev.mvpdagger.data.source.EpisodesRepositoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by duckyng on 12/21/2017.
 */

@Singleton
@Component(modules = {EpisodesRepositoryModule.class,
        ApplicationModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<MVPDaggerApplication> {

    EpisodesRepository getEpisodesRepository();

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();

    }


}
