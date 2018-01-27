package com.duckydev.mvpdagger.data.source.local;

import android.support.annotation.NonNull;

import com.duckydev.mvpdagger.data.Episode;
import com.duckydev.mvpdagger.data.source.EpisodesDataSource;
import com.duckydev.mvpdagger.util.AppExecutors;
import com.duckydev.mvpdagger.util.EpisodeType;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EpisodeLocalDataSource implements EpisodesDataSource {

    private final EpisodeDao mEpisodeDao;

    private final AppExecutors mAppExecutors;

    @Inject
    public EpisodeLocalDataSource(EpisodeDao episodeDao, AppExecutors appExecutors) {
        mEpisodeDao = episodeDao;
        mAppExecutors = appExecutors;
    }



    @Override
    public void getEpisodes(@NonNull final LoadEpisodesCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Episode> episodes = mEpisodeDao.getEpisodes();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (episodes.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            callback.onEpisodesLoaded(episodes);
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getEpisodesByType(@NonNull final EpisodeType type, final LoadEpisodesCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Episode> episodes = mEpisodeDao.getEpisodesByType(type);
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (episodes.isEmpty()) {
                            callback.onDataNotAvailable();
                        } else {
                            callback.onEpisodesLoaded(episodes);
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);

    }

    @Override
    public void getEpisode(@NonNull final int id, @NonNull final GetEpisodeCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Episode episode = mEpisodeDao.getEpisodeById(id);

                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (episode != null) {
                            callback.onEpisodeLoaded(episode);
                        } else {
                            callback.onDataNotAvailable();
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }


    @Override
    public void getEpisodesByPlayState(@NonNull final boolean isPlayed, @NonNull final LoadEpisodesCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Episode> episodes = mEpisodeDao.getEpisodesByPlayState(isPlayed);
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (episodes.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            callback.onEpisodesLoaded(episodes);
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getEpisodesByListId(final int[] ids, @NonNull final LoadEpisodesCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Episode> episodes = new ArrayList<>();

                for (int id : ids) {
                    if (id == 0) {
                        break;
                    } else {
                        episodes.add(mEpisodeDao.getEpisodeById(id));
                    }
                }
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (episodes.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            callback.onEpisodesLoaded(episodes);
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getEpisodesByTypeAndPlayState(@NonNull final EpisodeType type, @NonNull final boolean isPlayed, @NonNull final LoadEpisodesCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Episode> episodes = mEpisodeDao.getEpisodesByTypeAndPlayState(type, isPlayed);
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (episodes.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            callback.onEpisodesLoaded(episodes);
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }


    @Override
    public void getDownloadedEpisodes(@NonNull final boolean isDownloaded, final LoadEpisodesCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Episode> episodes = mEpisodeDao.getEpisodesByDownloadState(isDownloaded);
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (episodes.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            callback.onEpisodesLoaded(episodes);
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getFavoritedEpisodes(@NonNull final boolean isFavorited, final LoadEpisodesCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Episode> episodes = mEpisodeDao.getFavoriteEpisodes(isFavorited);
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (episodes.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            callback.onEpisodesLoaded(episodes);
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getFirstNumberOfEpisodeByType(@NonNull final EpisodeType type, final int number, final LoadEpisodesCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Episode> episodes = mEpisodeDao.getFirstNumberOfEpisodesEachType(type, number);
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (episodes.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            callback.onEpisodesLoaded(episodes);
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void markDownloadedEpisodeMedia(@NonNull final Episode episode) {
        Runnable downloadedRunnable = new Runnable() {
            @Override
            public void run() {
                mEpisodeDao.updateDownloaded(episode.get_id(), true);
            }
        };
        mAppExecutors.diskIO().execute(downloadedRunnable);
    }

    @Override
    public void markUndownloadedEpisodeMedia(@NonNull final Episode episode) {
        Runnable deleteDownloadedRunnable = new Runnable() {
            @Override
            public void run() {
                mEpisodeDao.updateDownloaded(episode.get_id(), false);
            }
        };
        mAppExecutors.diskIO().execute(deleteDownloadedRunnable);
    }

    @Override
    public void updateDownloadedMediaUrl(@NonNull final Episode episode, final String mediaUrl) {
        Runnable updateDownloadedUrlRunnable = new Runnable() {
            @Override
            public void run() {
                mEpisodeDao.updateDownloadedMediaUrl(episode.get_id(), mediaUrl);
            }
        };
        mAppExecutors.diskIO().execute(updateDownloadedUrlRunnable);
    }

    @Override
    public void markPlayedEpisode(@NonNull final Episode episode) {
        Runnable playedEpisode = new Runnable() {
            @Override
            public void run() {
                mEpisodeDao.updatePlayed(episode.get_id(), true);
            }
        };
        mAppExecutors.diskIO().execute(playedEpisode);
    }

    @Override
    public void markUnplayedEpisode(@NonNull final Episode episode) {
        Runnable unplayedEpisode = new Runnable() {
            @Override
            public void run() {
                mEpisodeDao.updatePlayed(episode.get_id(), false);
            }
        };
        mAppExecutors.diskIO().execute(unplayedEpisode);
    }

    @Override
    public void insertEpisode(@NonNull final Episode episode) {
        Runnable insertRunnable = new Runnable() {
            @Override
            public void run() {
                mEpisodeDao.insertEpisode(episode);
            }
        };
        mAppExecutors.diskIO().execute(insertRunnable);
    }

    @Override
    public void insertEpisodeList(@NonNull final List<Episode> episodes) {
        Runnable insertRunnable = new Runnable() {
            @Override
            public void run() {
                mEpisodeDao.insertEpisodeList(episodes);
            }
        };
        mAppExecutors.diskIO().execute(insertRunnable);
    }


    @Override
    public void updateEpisode(@NonNull Episode episode) {

    }

    @Override
    public void updateFavorite(@NonNull final Episode episode, final boolean isFavorite) {
        Runnable markFavoriteEpisode = new Runnable() {
            @Override
            public void run() {
                mEpisodeDao.updateFavorite(episode.get_id(), isFavorite);
            }
        };
        mAppExecutors.diskIO().execute(markFavoriteEpisode);
    }
}