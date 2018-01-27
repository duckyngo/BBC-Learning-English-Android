package com.duckydev.mvpdagger.episodedetail;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.devbrackets.android.exomedia.AudioPlayer;
import com.devbrackets.android.exomedia.listener.OnCompletionListener;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.duckydev.mvpdagger.Constants;
import com.duckydev.mvpdagger.data.Episode;
import com.duckydev.mvpdagger.data.source.EpisodesDataSource;
import com.duckydev.mvpdagger.data.source.EpisodesRepository;
import com.duckydev.mvpdagger.util.SharePreferenceUtils;
import com.duckydev.mvpdagger.util.TxtUtils;

import javax.inject.Inject;

/**
 * Created by duckyng on 12/25/2017.
 */

public class EpisodeDetailPresenter implements EpisodeDetailContract.Presenter {

    private EpisodeDetailContract.View mView;
    private final EpisodesRepository mEpisodesRepository;
    private AudioPlayer mAudioPlayer;
    private Handler mHandler = new Handler();
    private int currentPlaybackSpeedItemIndex = 2;

    private Episode mCurrentEpisode;


    private Runnable mProgressRunnable = new Runnable() {
        @Override
        public void run() {
            mView.setCurrentProgressSeekbar((int) mAudioPlayer.getCurrentPosition());
            mView.setCurrentTimeTextView(TxtUtils.ConvertMilistoMinuteSecondString((int) mAudioPlayer.getCurrentPosition()));
            mHandler.postDelayed(mProgressRunnable, 100);
        }
    };

    @Inject
    public EpisodeDetailPresenter(EpisodesRepository episodesRepository) {
        mEpisodesRepository = episodesRepository;
    }

    @Override
    public void takeView(EpisodeDetailContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void getEpisode(int episodeId) {
        mEpisodesRepository.getEpisode(episodeId, new EpisodesDataSource.GetEpisodeCallback() {
            @Override
            public void onEpisodeLoaded(Episode episode) {
                if (mView != null) {
                    SharePreferenceUtils.updateEpisodeHistoryId(((EpisodeDetailFragment) mView).getActivity(), episode.get_id());
                    mEpisodesRepository.markPlayedEpisode(episode);
                    mView.showEpisode(episode);
                    setAudioPlayerDataSource(episode.getMediaUrl());
                    mCurrentEpisode = episode;
                }

            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void initAudioPlayer(Context context) {
        if (mAudioPlayer == null) {
            mAudioPlayer = new AudioPlayer(context);
        }
        mAudioPlayer.reset();

        mAudioPlayer.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared() {
                Log.d("ky.nd", "OnPrepared: " + mAudioPlayer.getDuration());
                if (mView != null) {
                    mView.hideProgressBar();
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!mAudioPlayer.isPlaying() && mView != null) {
                            mView.setMaxProgressSeekbar((int) mAudioPlayer.getDuration());
                            mView.setDurationTimeTextView(TxtUtils.ConvertMilistoMinuteSecondString((int) mAudioPlayer.getDuration()));
                            play();
                        }
                    }
                }, 1000);
            }


        });

        mAudioPlayer.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion() {
                mAudioPlayer.seekTo(0);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        play();
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void setAudioPlayerDataSource(String path) {
        if (!TextUtils.isEmpty(path)) {
            Uri uri = Uri.parse(path);
            mAudioPlayer.setDataSource(uri);
        }
    }

    @Override
    public void play() {
        mHandler.post(mProgressRunnable);
        mView.showPauseControlButton();
        if (!mAudioPlayer.isPlaying()) {
            mAudioPlayer.start();
        }
    }

    @Override
    public void pause() {
        mHandler.removeCallbacks(mProgressRunnable);
        mView.showPlayControlButton();
        if (mAudioPlayer.isPlaying()) {
            mAudioPlayer.pause();
        }
    }

    @Override
    public void seekTo(int pos) {
        if (mAudioPlayer != null) {
            mAudioPlayer.seekTo(pos);
            mView.setCurrentProgressSeekbar(pos);
            play();
        }
    }

    @Override
    public void toggle() {
        if (mAudioPlayer.isPlaying()) {
            pause();
        } else {
            play();
        }
    }

    @Override
    public void cleanUp() {
        if (mAudioPlayer != null) {
            mHandler.removeCallbacks(mProgressRunnable);
            mAudioPlayer.release();
        }
    }

    @Override
    public void selectPlaybackSpeed(int which) {
        currentPlaybackSpeedItemIndex = which;
        mAudioPlayer.setPlaybackSpeed(Constants.PLAYBACK_SPEED[currentPlaybackSpeedItemIndex]);
        mView.setPlaySpeedText(Constants.PLAYBACK_SPEED_STRING[which]);
    }

    @Override
    public void selectPlaybackSpeedClick() {
        mView.showPlaybackSpeedDialog(currentPlaybackSpeedItemIndex);
    }

    @Override
    public void favoriteEpisode() {
        if (mCurrentEpisode.isFavorite()) {
            mCurrentEpisode.setFavorite(false);
            mEpisodesRepository.updateFavorite(mCurrentEpisode, false);
        } else {
            mCurrentEpisode.setFavorite(true);
            mEpisodesRepository.updateFavorite(mCurrentEpisode, true);
        }
        mView.showFavorite(mCurrentEpisode.isFavorite());

    }

    @Override
    public void downloadEpisode() {
        if (mCurrentEpisode.isDownloaded()) {
            mCurrentEpisode.setDownloaded(false);
            mEpisodesRepository.markUndownloadedEpisodeMedia(mCurrentEpisode);
        } else {
            mCurrentEpisode.setDownloaded(true);
            mEpisodesRepository.markDownloadedEpisodeMedia(mCurrentEpisode);
        }
        mView.showDownload(mCurrentEpisode.isDownloaded());
    }

    @Override
    public void shareEpisode() {

    }

}
