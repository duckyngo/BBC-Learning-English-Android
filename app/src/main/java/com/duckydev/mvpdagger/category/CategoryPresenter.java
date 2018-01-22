package com.duckydev.mvpdagger.category;

import android.support.annotation.Nullable;

import com.duckydev.mvpdagger.data.Episode;
import com.duckydev.mvpdagger.data.source.EpisodesDataSource;
import com.duckydev.mvpdagger.data.source.EpisodesRepository;
import com.duckydev.mvpdagger.di.ActivityScoped;
import com.duckydev.mvpdagger.util.EpisodeType;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by duckyng on 12/21/2017.
 */

@ActivityScoped
public class CategoryPresenter implements CategoryContract.Presenter{

    private final EpisodesRepository mEpisodesRepository;

    @Nullable
    private CategoryContract.View mCategoryView;

    @Inject
    public CategoryPresenter(EpisodesRepository episodesRepository) {
        mEpisodesRepository = episodesRepository;
    }

    @Override
    public void loadAllPreviewEpisode() {
        loadPreviewEpisodes(EpisodeType.SIX_MINUTE_ENGLISH);
        loadPreviewEpisodes(EpisodeType.DRAMA);
        loadPreviewEpisodes(EpisodeType.WORK_IN_THE_NEWS);
        loadPreviewEpisodes(EpisodeType.LINGOHACK);
        loadPreviewEpisodes(EpisodeType.ENGLISH_AT_WORK);
        loadPreviewEpisodes(EpisodeType.NEWS_REPORT);
        loadPreviewEpisodes(EpisodeType.ENGLISH_WE_SPEAK);
    }

    @Override
    public void loadPreviewEpisodes(final EpisodeType type) {
        if (mCategoryView != null) {
            mCategoryView.setLoadingIndicator(true);
        }
        mEpisodesRepository.getFirstFiveEpisodeByType(type, new EpisodesDataSource.LoadEpisodesCallback() {
            @Override
            public void onEpisodesLoaded(List<Episode> episodes) {
                if (mCategoryView != null) {
                    mCategoryView.showPreviewEpisodes(type, episodes);
                    mCategoryView.setLoadingIndicator(false);
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });

        mEpisodesRepository.getDownloadedEpisodes(true, new EpisodesDataSource.LoadEpisodesCallback() {
            @Override
            public void onEpisodesLoaded(List<Episode> episodes) {
                if (mCategoryView != null) {
                    mCategoryView.updateDownloadedBadge(episodes.size());
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });

        mEpisodesRepository.getFavoritedEpisodes(true, new EpisodesDataSource.LoadEpisodesCallback() {
            @Override
            public void onEpisodesLoaded(List<Episode> episodes) {
                if (mCategoryView != null) {
                    mCategoryView.updateFavoriteBadge(episodes.size());
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void checkForUpdate() {

    }

    @Override
    public void checkInternetConnection() {

    }

    @Override
    public void takeView(CategoryContract.View view) {
        mCategoryView = view;
        loadAllPreviewEpisode();
    }

    @Override
    public void dropView() {
        mCategoryView = null;
    }
}
