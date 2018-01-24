package com.duckydev.mvpdagger.episodedetail;

import android.content.Context;

import com.duckydev.mvpdagger.BasePresenter;
import com.duckydev.mvpdagger.BaseView;
import com.duckydev.mvpdagger.data.Episode;

/**
 * Created by duckyng on 12/25/2017.
 */

public interface EpisodeDetailContract {

    interface View extends BaseView<Presenter>{

        void showPlaybackSpeedDialog(int currentPlaybackSpeedItemIndex);

        void showEpisode(Episode episode);

        void showProgressBar();

        void hideProgressBar();

        void setCurrentProgressSeekbar(int position);

        void setMaxProgressSeekbar(int maxProgress);

        void showPlayControlButton();

        void showPauseControlButton();

        void setCurrentTimeTextView(String text);

        void setDurationTimeTextView(String text);

        void updateBackgroundSC(boolean enabled);

        void setPlaySpeedText(String text);

        void showFavorite(boolean isFavorited);

        void showDownload(boolean isDownloaded);
    }

    interface Presenter extends BasePresenter<View> {

        void getEpisode(int episodeId);

        void initAudioPlayer(Context context);

        void setAudioPlayerDataSource(String path);

        void play();

        void pause();

        void seekTo(int pos);

        void toggle();

        void cleanUp();

        void selectPlaybackSpeed(int which);

        void selectPlaybackSpeedClick();

        void favoriteEpisode();

        void downloadEpisode();

        void shareEpisode();
    }



}
