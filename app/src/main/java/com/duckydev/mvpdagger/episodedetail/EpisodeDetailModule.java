package com.duckydev.mvpdagger.episodedetail;

import com.duckydev.mvpdagger.di.ActivityScoped;
import com.duckydev.mvpdagger.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

import static com.duckydev.mvpdagger.episodedetail.EpisodeDetailActivity.EXTRA_EPISODE_ID;

/**
 * Created by duckyng on 12/25/2017.
 */
@Module
public abstract class EpisodeDetailModule {


    @FragmentScoped
    @ContributesAndroidInjector
    abstract EpisodeDetailFragment episodeDetailFragment();

    @ActivityScoped
    @Binds
    abstract EpisodeDetailContract.Presenter episodeDetailPresenter(EpisodeDetailPresenter presenter);

    @Provides
    @ActivityScoped
    static int provideEpisodeId(EpisodeDetailActivity activity) {
        return activity.getIntent().getIntExtra(EXTRA_EPISODE_ID, 0);
    }

}
