package com.duckydev.mvpdagger.features;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.duckydev.mvpdagger.R;
import com.duckydev.mvpdagger.data.Episode;
import com.duckydev.mvpdagger.data.source.EpisodesDataSource;
import com.duckydev.mvpdagger.data.source.EpisodesRepository;
import com.duckydev.mvpdagger.util.EpisodeType;
import com.duckydev.mvpdagger.util.SharePreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by duckyng on 12/25/2017.
 */

public class FeaturesPresenter implements FeaturesContract.Presenter  {

    FeaturesContract.View mView;

    private final EpisodesRepository mEpisodesRepository;
    private DownloadManager mDownloadManager;
    private EpisodesFilterType mCurrentFiltering = EpisodesFilterType.ALL_EPISODES;
    private EpisodeType mCurrentEpisodeType = EpisodeType.SIX_MINUTE_ENGLISH;


    @Inject
    public FeaturesPresenter(EpisodesRepository episodesRepository) {
        mEpisodesRepository = episodesRepository;
    }

    @Override
    public void takeView(FeaturesContract.View view) {
        mView = view;
        mDownloadManager = (DownloadManager) ((FeaturesFragment) mView).getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        ((FeaturesFragment) mView).getActivity().registerReceiver(onComplete,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        ((FeaturesFragment) mView).getActivity().registerReceiver(onNotificationClick,
                new IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED));
    }

    @Override
    public void dropView() {
        ((FeaturesFragment) mView).getActivity().unregisterReceiver(onComplete);
        ((FeaturesFragment) mView).getActivity().unregisterReceiver(onNotificationClick);
        mView = null;
    }

    @Override
    public void loadFeaturedEpisode() {
        mEpisodesRepository.getEpisodesByType(mCurrentEpisodeType, new EpisodesDataSource.LoadEpisodesCallback() {
            @Override
            public void onEpisodesLoaded(List<Episode> episodes) {
                ArrayList<Episode> episodesToShow = new ArrayList<>();


                for (Episode episode : episodes) {
                    switch (mCurrentFiltering) {
                        case ALL_EPISODES:
                            episodesToShow.add(episode);
                            break;
                        case FAVORITED_EPISODES:
                            if (episode.isFavorite()) {
                                episodesToShow.add(episode);
                            }
                            break;
                        case DOWNLOADED_EPISODES:
                            if (episode.isDownloaded()) {
                                episodesToShow.add(episode);
                            }
                            break;
                        case WATCHED_EPISODES:
                            if (episode.isPlayed()) {
                                episodesToShow.add(episode);
                            }

                    }
                }

                if (mView == null || !mView.isActive()) {
                    return;
                }

                processEpisodes(episodesToShow);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void processEpisodes(ArrayList<Episode> episodesToShow) {
        if (episodesToShow.isEmpty()) {
            processEmptyEpisodes();
        } else {
            if (mView != null) {
                mView.showEpisodes(episodesToShow);
            }

            showFilterLabel();
        }
    }

    private void processEmptyEpisodes() {
        if (mView == null) return;
        switch (mCurrentFiltering) {
            case DOWNLOADED_EPISODES:
                mView.showNoDownloadedEpisode();
                break;
            case FAVORITED_EPISODES:
                mView.showNoFavoritedEpisode();
                break;
            case WATCHED_EPISODES:
                mView.showNoWatchedEpisode();
                break;
            default:
                mView.showNoEpisode();
                break;
        }
    }

    @Override
    public void loadFavoriteEpisodes() {
        mEpisodesRepository.getFavoritedEpisodes(true, new EpisodesDataSource.LoadEpisodesCallback() {
            @Override
            public void onEpisodesLoaded(List<Episode> episodes) {
                ArrayList<Episode> episodesToShow = new ArrayList<>(episodes);
                mView.showEpisodes(episodesToShow);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void loadDownloadedEpisodes() {
        mEpisodesRepository.getDownloadedEpisodes(true, new EpisodesDataSource.LoadEpisodesCallback() {
            @Override
            public void onEpisodesLoaded(List<Episode> episodes) {
                ArrayList<Episode> episodesToShow = new ArrayList<>(episodes);
                mView.showEpisodes(episodesToShow);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void getRecentAudioEpisodes(Context context) {
        int[] ids = SharePreferenceUtils.getEpisodeHistoryId(context);
        mEpisodesRepository.getEpisodesByListId(ids, new EpisodesDataSource.LoadEpisodesCallback() {
            @Override
            public void onEpisodesLoaded(List<Episode> episodes) {
                ArrayList<Episode> episodesToShow = new ArrayList<>(episodes);
                mView.showEpisodes(episodesToShow);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void openEpisodeDetails(@NonNull Episode requestedEpisode) {
        mView.showEpisodeDetail(requestedEpisode);
    }

    @Override
    public void favoriteEpisode(@NonNull Episode favoritedEpisode) {
        mEpisodesRepository.updateFavorite(favoritedEpisode, true);
        if (mView != null) {
            mView.showSuccessfullyFavoritedMessage();
        }
        loadFeaturedEpisode();
    }

    @Override
    public void unFavoriteEpisode(@NonNull Episode unFavoriteEpisode) {
        mEpisodesRepository.updateFavorite(unFavoriteEpisode, false);
        if (mView != null) {
            mView.showSuccessfullyUnFavoritedMessage();
        }
        loadFeaturedEpisode();
    }

    @Override
    public void downloadEpisode(@NonNull Episode downloadedEpisode) {

        long downloadReference;

        // Create request for android download manager
//        mDownloadManager = (DownloadManager)((FeaturesFragment)mView).getContext().getSystemService(Context.DOWNLOAD_SERVICE);

        Uri downloadUri = Uri.parse(downloadedEpisode.getMediaUrl());
        DownloadManager.Request request = new DownloadManager.Request(downloadUri);

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);

        //Setting title of request

        request.setTitle(downloadedEpisode.getTitle() + " Downloading");

        //Setting description of request
        request.setDescription(downloadedEpisode.getTitle() + "Downloading Data..");
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);

        //Set the local destination for the downloaded file to a path
        //within the application's external files directory
            request.setDestinationInExternalFilesDir(((FeaturesFragment)mView).getActivity(),
                    Environment.DIRECTORY_DOWNLOADS, downloadedEpisode.getTitle() + ".mp3");
        //Enqueue download and save into referenceId
        downloadReference = mDownloadManager.enqueue(request);

        mEpisodesRepository.markDownloadedEpisodeMedia(downloadedEpisode);

        if (mView != null) {
            mView.showSuccessfullyAddToDownloadedMessge();
        }
        loadFeaturedEpisode();

    }

    @Override
    public void deleteDownloadedEpisode(@NonNull Episode unDownloadedEpisode) {
        mEpisodesRepository.markUndownloadedEpisodeMedia(unDownloadedEpisode);
        if (mView != null) {
            mView.showSuccessfullyDeleteMessage();
        }
        loadFeaturedEpisode();
    }

    @Override
    public EpisodesFilterType getFiltering() {
        return null;
    }

    @Override
    public void setFiltering(EpisodesFilterType requestType) {
        mCurrentFiltering = requestType;
    }

    @Override
    public void setEpisodeType(EpisodeType episodeType) {
        mCurrentEpisodeType = episodeType;
    }

    private void showFilterLabel() {
        switch (mCurrentFiltering) {
            case DOWNLOADED_EPISODES:
                if (mView != null) {
                    mView.showDownloadedFilterLabel();
                }
                break;
            case FAVORITED_EPISODES:
                if (mView != null) {
                    mView.showFavoritedFilterLabel();
                }
                break;
            case WATCHED_EPISODES:
                if (mView != null) {
                    mView.showWatchedFilterLabel();
                }
            default:
                if (mView != null) {
                    mView.showAllFilterLabel();
                }
        }
    }

    private BroadcastReceiver onComplete=new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            mView.showDownloadComplete();
        }
    };

    private BroadcastReceiver onNotificationClick=new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            Toast.makeText(ctxt, "Ummmm...hi!", Toast.LENGTH_LONG).show();
        }
    };
}
