package com.duckydev.mvpdagger.data.source;

import android.support.annotation.NonNull;
import com.duckydev.mvpdagger.data.Episode;
import com.duckydev.mvpdagger.util.EpisodeType;
import java.util.List;

public interface EpisodesDataSource {

    void getEpisodes(@NonNull LoadEpisodesCallback callback);

    void getEpisodesByType(@NonNull EpisodeType type, LoadEpisodesCallback callback);

    void getEpisode(@NonNull int id, @NonNull GetEpisodeCallback callback);

    void getEpisodesByPlayState(@NonNull boolean isPlayed, @NonNull LoadEpisodesCallback callback);

    void getEpisodesByTypeAndPlayState(@NonNull EpisodeType type, @NonNull boolean isPlayed, @NonNull LoadEpisodesCallback callback);

    void getDownloadedEpisodes(@NonNull boolean isDownloaded, LoadEpisodesCallback callback);

    void getFavoritedEpisodes(@NonNull boolean isFavorited, LoadEpisodesCallback callback);

    void getFirstNumberOfEpisodeByType(@NonNull EpisodeType type, int number, LoadEpisodesCallback callback);

    void markDownloadedEpisodeMedia(@NonNull Episode episode);

    void markUndownloadedEpisodeMedia(@NonNull Episode episode);

    void updateDownloadedMediaUrl(@NonNull Episode Episode, String mediaUrl);

    void markPlayedEpisode(@NonNull Episode episode);

    void markUnplayedEpisode(@NonNull Episode episode);

    void insertEpisode(@NonNull Episode episode);

    void updateEpisode(@NonNull Episode episode);

    void updateFavorite(@NonNull Episode episode, boolean isFavorite);
    
    interface LoadEpisodesCallback {

        void onEpisodesLoaded(List<Episode> episodes);

        void onDataNotAvailable();
    }

    interface GetEpisodeCallback {

        void onEpisodeLoaded(Episode episode);

        void onDataNotAvailable();

    }
}
