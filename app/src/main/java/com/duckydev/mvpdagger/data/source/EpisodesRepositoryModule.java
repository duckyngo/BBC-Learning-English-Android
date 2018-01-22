package com.duckydev.mvpdagger.data.source;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.duckydev.mvpdagger.data.source.local.EpisodeDatabase;
import com.duckydev.mvpdagger.data.source.local.EpisodeDao;
import com.duckydev.mvpdagger.data.source.local.EpisodeLocalDataSource;
import com.duckydev.mvpdagger.util.AppExecutors;
import com.duckydev.mvpdagger.util.DiskIOThreadExecutor;
import com.fstyle.library.helper.AssetSQLiteOpenHelperFactory;

import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by duckyng on 12/21/2017.
 */
@Module
public abstract class EpisodesRepositoryModule {

    private static final int THREAD_COUNT = 3;

    @Singleton
    @Binds
    @Local
    abstract EpisodesDataSource provideEpisodesLocalDataSource(EpisodeLocalDataSource dataSource);

    @Singleton
    @Provides
    static EpisodeDatabase provideDb(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(), EpisodeDatabase.class, "Episodes.db")
                .openHelperFactory(new AssetSQLiteOpenHelperFactory())
                .build();

//        return Room.databaseBuilder(context.getApplicationContext(), EpisodeDatabase.class, "Episodes.db")
//                .build();
    }

    @Singleton
    @Provides
    static EpisodeDao provideEpisodeDao(EpisodeDatabase db) {
        return db.episodeDao();
    }

    @Singleton
    @Provides
    static AppExecutors provideAppExecutors() {
        return new AppExecutors(new DiskIOThreadExecutor(),
                Executors.newFixedThreadPool(THREAD_COUNT),
                new AppExecutors.MainThreadExecutor());
    }


}
