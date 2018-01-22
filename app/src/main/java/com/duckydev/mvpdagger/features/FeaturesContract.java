package com.duckydev.mvpdagger.features;

import android.support.annotation.NonNull;

import com.duckydev.mvpdagger.BasePresenter;
import com.duckydev.mvpdagger.BaseView;
import com.duckydev.mvpdagger.data.Episode;
import com.duckydev.mvpdagger.util.EpisodeType;

import java.util.ArrayList;

/**
 * Created by duckyng on 12/25/2017.
 */

public interface FeaturesContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showEpisodeDetail(Episode episode);

        void showEpisodes(ArrayList<Episode> episodes);

        void showSuccessfullyFavoritedMessage();

        void showSuccessfullyUnFavoritedMessage();

        void showSuccessfullyAddToDownloadedMessge();

        void showSuccessfullyDeleteMessage();

        void showDownloadComplete(Episode episode);

        void showDownloadedFilterLabel();

        void showFavoritedFilterLabel();

        void showAllFilterLabel();

        void showNoDownloadedEpisode();

        void showNoFavoritedEpisode();

        boolean isActive();

        void showFilteringPopUpMenu();
    }


    interface Presenter extends BasePresenter<View> {

        void getEpisodeByType(EpisodeType type);

        void getFavoriteEpisodes();

        void getDownloadedEpisodes();

        void getRecentAudioEpisodes();

        void openEpisodeDetails(@NonNull Episode requestedEpisode);

        void favoriteEpisode(@NonNull Episode favoritedEpisode);

        void unFavoriteEpisode(@NonNull Episode unFavoriteEpisode);

        void downloadEpisode(@NonNull Episode downloadedEpisode);

        void deleteDownloadedEpisode(@NonNull Episode unDownloadedEpisode);

        EpisodesFilterType getFiltering();

        void setFiltering(EpisodesFilterType requestType);

    }


}
