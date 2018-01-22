package com.duckydev.mvpdagger.category;

import com.duckydev.mvpdagger.BasePresenter;
import com.duckydev.mvpdagger.BaseView;
import com.duckydev.mvpdagger.data.Episode;
import com.duckydev.mvpdagger.util.EpisodeType;

import java.util.List;

/**
 * Created by duckyng on 12/21/2017.
 */

public interface CategoryContract {

    interface View extends BaseView<Presenter>{

        void setLoadingIndicator(boolean active);

        void showPreviewEpisodes(EpisodeType type, List<Episode> episodes);

        void showEpisodes(List<Episode> episodes);

        void showFeatures(EpisodeType type);

        void showEpisodeDetail(Episode episode);

        void showRecents();

        void showDownloads();

        void showNumDownloaded(int numberOfDownloaded);

        void showVocabularies();

        void showNumVocabularies(int numberOfVocabularies);

        void showFavorites();

        void showNumFavorites(int numberOfFavorites);

        void showHistories();

        void showNewUpdateAvailable();

        void showNoInternetConnection();

        void showRateDialog();

        void showFeedback();

        void showPrivacyPolicy();

        void showSettings();

        void updateFavoriteBadge(int number);

        void updateDownloadedBadge(int number);

    }

    interface Presenter extends BasePresenter<View>{

        void loadAllPreviewEpisode();

        void loadPreviewEpisodes(EpisodeType type);

        void checkForUpdate();

        void checkInternetConnection();

        void takeView(CategoryContract.View view);

        void dropView();
    }
}
