package com.duckydev.mvpdagger.data.source;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by duckyng on 1/26/2018.
 */
@Singleton
public class VocabularyRepository implements VocabularyDataSource {

    //    private final EpisodesDataSource mTasksRemoteDataSource;
//
    private final EpisodesDataSource mEpisodeLocalDataSource;

    @Inject
    VocabularyRepository(@Local EpisodesDataSource episodeLocalDataSource) {
//        mTasksRemoteDataSource = tasksRemoteDataSource;
        mEpisodeLocalDataSource = episodeLocalDataSource;
    }

}
