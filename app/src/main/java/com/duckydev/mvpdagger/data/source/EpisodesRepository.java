package com.duckydev.mvpdagger.data.source;

import android.support.annotation.NonNull;

import com.duckydev.mvpdagger.data.Episode;
import com.duckydev.mvpdagger.util.EpisodeType;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EpisodesRepository implements EpisodesDataSource {

//    private final EpisodesDataSource mTasksRemoteDataSource;
//
    private final EpisodesDataSource mEpisodeLocalDataSource;

    @Inject
    EpisodesRepository(@Local EpisodesDataSource episodeLocalDataSource) {
//        mTasksRemoteDataSource = tasksRemoteDataSource;
        mEpisodeLocalDataSource = episodeLocalDataSource;
    }

    @Override
    public void getEpisodes(@NonNull final LoadEpisodesCallback callback) {

        mEpisodeLocalDataSource.getEpisodes(new LoadEpisodesCallback() {
            @Override
            public void onEpisodesLoaded(List<Episode> episodes) {
                callback.onEpisodesLoaded(episodes);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getEpisodesByType(@NonNull EpisodeType type, final LoadEpisodesCallback callback) {
        mEpisodeLocalDataSource.getEpisodesByType(type, new LoadEpisodesCallback() {
            @Override
            public void onEpisodesLoaded(List<Episode> episodes) {
                callback.onEpisodesLoaded(episodes);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getEpisode(@NonNull int id, @NonNull final GetEpisodeCallback callback) {
        mEpisodeLocalDataSource.getEpisode(id, new GetEpisodeCallback() {
            @Override
            public void onEpisodeLoaded(Episode episode) {
                callback.onEpisodeLoaded(episode);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getEpisodesByPlayState(@NonNull boolean isPlayed, @NonNull final LoadEpisodesCallback callback) {
        mEpisodeLocalDataSource.getEpisodesByPlayState(isPlayed, new LoadEpisodesCallback() {
            @Override
            public void onEpisodesLoaded(List<Episode> episodes) {
                callback.onEpisodesLoaded(episodes);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getEpisodesByTypeAndPlayState(@NonNull EpisodeType type, @NonNull boolean isPlayed, @NonNull final LoadEpisodesCallback callback) {
        mEpisodeLocalDataSource.getEpisodesByTypeAndPlayState(type, isPlayed, new LoadEpisodesCallback() {
            @Override
            public void onEpisodesLoaded(List<Episode> episodes) {
                callback.onEpisodesLoaded(episodes);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getDownloadedEpisodes(@NonNull boolean isDownloaded, final LoadEpisodesCallback callback) {
        mEpisodeLocalDataSource.getDownloadedEpisodes(isDownloaded, new LoadEpisodesCallback() {
            @Override
            public void onEpisodesLoaded(List<Episode> episodes) {
                callback.onEpisodesLoaded(episodes);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getFavoritedEpisodes(@NonNull boolean isFavorited, final LoadEpisodesCallback callback) {
        mEpisodeLocalDataSource.getFavoritedEpisodes(isFavorited, new LoadEpisodesCallback() {
            @Override
            public void onEpisodesLoaded(List<Episode> episodes) {
                callback.onEpisodesLoaded(episodes);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    public void getFirstNumberOfEpisodeByType(@NonNull EpisodeType type, int number, final LoadEpisodesCallback callback) {
        mEpisodeLocalDataSource.getFirstNumberOfEpisodeByType(type, number, new LoadEpisodesCallback() {
            @Override
            public void onEpisodesLoaded(List<Episode> episodes) {
                callback.onEpisodesLoaded(episodes);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void markDownloadedEpisodeMedia(@NonNull Episode episode) {
        mEpisodeLocalDataSource.markDownloadedEpisodeMedia(episode);
    }

    @Override
    public void markUndownloadedEpisodeMedia(@NonNull Episode episode) {
        mEpisodeLocalDataSource.markUndownloadedEpisodeMedia(episode);
    }

    @Override
    public void updateDownloadedMediaUrl(@NonNull Episode episode, String mediaUrl) {
        mEpisodeLocalDataSource.updateDownloadedMediaUrl(episode, mediaUrl);
    }

    @Override
    public void markPlayedEpisode(@NonNull Episode episode) {
        mEpisodeLocalDataSource.markPlayedEpisode(episode);
    }

    @Override
    public void markUnplayedEpisode(@NonNull Episode episode) {
        mEpisodeLocalDataSource.markUnplayedEpisode(episode);
    }

    @Override
    public void insertEpisode(@NonNull Episode episode) {
        mEpisodeLocalDataSource.insertEpisode(episode);
    }

    @Override
    public void insertEpisodeList(@NonNull List<Episode> episodes) {
        mEpisodeLocalDataSource.insertEpisodeList(episodes);
    }

    @Override
    public void updateEpisode(@NonNull Episode episode) {
        mEpisodeLocalDataSource.updateEpisode(episode);
    }

    @Override
    public void updateFavorite(@NonNull Episode episode, boolean isFavorite) {
        mEpisodeLocalDataSource.updateFavorite(episode, isFavorite);
    }
}
