package com.duckydev.mvpdagger.features;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.widget.Button;

import com.duckydev.mvpdagger.MVPDaggerApplication;
import com.duckydev.mvpdagger.R;
import com.duckydev.mvpdagger.data.Episode;
import com.duckydev.mvpdagger.data.source.EpisodesDataSource;
import com.duckydev.mvpdagger.data.source.EpisodesRepository;
import com.duckydev.mvpdagger.util.EpisodeType;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by duckyng on 12/25/2017.
 */

public class FeaturesPresenter implements FeaturesContract.Presenter  {

    FeaturesContract.View mView;

    private final EpisodesRepository mEpisodesRepository;
    private DownloadManager downloadManager;
    private EpisodesFilterType mCurrentFiltering = EpisodesFilterType.ALL_EPISODES;


    @Inject
    public FeaturesPresenter(EpisodesRepository episodesRepository) {
        mEpisodesRepository = episodesRepository;
    }

    @Override
    public void takeView(FeaturesContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void getEpisodeByType(EpisodeType type) {
        mEpisodesRepository.getEpisodesByType(type, new EpisodesDataSource.LoadEpisodesCallback() {
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
    }

    @Override
    public void getFavoriteEpisodes() {
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
    public void getDownloadedEpisodes() {
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
    public void getRecentAudioEpisodes() {

    }

    @Override
    public void openEpisodeDetails(@NonNull Episode requestedEpisode) {
        mView.showEpisodeDetail(requestedEpisode);
    }

    @Override
    public void favoriteEpisode(@NonNull Episode favoritedEpisode) {
        mEpisodesRepository.updateFavorite(favoritedEpisode, true);
    }

    @Override
    public void unFavoriteEpisode(@NonNull Episode unFavoriteEpisode) {
        mEpisodesRepository.updateFavorite(unFavoriteEpisode, false);
    }

    @Override
    public void downloadEpisode(@NonNull Episode downloadedEpisode) {

        long downloadReference;

        // Create request for android download manager
        downloadManager = (DownloadManager)((FeaturesFragment)mView).getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        Uri downloadUri = Uri.parse(downloadedEpisode.getMediaUrl());
        DownloadManager.Request request = new DownloadManager.Request(downloadUri);

        //Setting title of request
        request.setTitle(downloadedEpisode.getTitle() + " Downloading");

        //Setting description of request
        request.setDescription(downloadedEpisode.getTitle() + "Downloading Data..");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);

        //Set the local destination for the downloaded file to a path
        //within the application's external files directory
            request.setDestinationInExternalFilesDir(((FeaturesFragment)mView).getActivity(),
                    Environment.DIRECTORY_DOWNLOADS, downloadedEpisode.getTitle() + ".mp3");
        //Enqueue download and save into referenceId
        downloadReference = downloadManager.enqueue(request);

        mEpisodesRepository.markDownloadedEpisodeMedia(downloadedEpisode);
    }

    @Override
    public void deleteDownloadedEpisode(@NonNull Episode unDownloadedEpisode) {
        mEpisodesRepository.markUndownloadedEpisodeMedia(unDownloadedEpisode);
    }

    @Override
    public EpisodesFilterType getFiltering() {
        return null;
    }

    @Override
    public void setFiltering(EpisodesFilterType requestType) {
        mCurrentFiltering = requestType;
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
            default:
                if (mView != null) {
                    mView.showAllFilterLabel();
                }
        }
    }
}
