package com.duckydev.mvpdagger.category;

import android.support.annotation.Nullable;
import android.util.Log;

import com.duckydev.mvpdagger.data.Episode;
import com.duckydev.mvpdagger.data.source.EpisodesDataSource;
import com.duckydev.mvpdagger.data.source.EpisodesRepository;
import com.duckydev.mvpdagger.di.ActivityScoped;
import com.duckydev.mvpdagger.util.EpisodeType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
    private int NUMBER_OF_PREVIEW = 5;

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
        mEpisodesRepository.getFirstNumberOfEpisodeByType(type, NUMBER_OF_PREVIEW, new EpisodesDataSource.LoadEpisodesCallback() {
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

//        mEpisodesRepository.getFirstNumberOfEpisodeByType(type, 10, new EpisodesDataSource.LoadEpisodesCallback() {
//            @Override
//            public void onEpisodesLoaded(List<Episode> episodes) {
//                Gson gson = new GsonBuilder().setPrettyPrinting().create();
////                Gson gson = new Gson();
//                String json = gson.toJson(episodes, new TypeToken<ArrayList<Episode>>() {
//                }.getType());
//
//                Log.d("ky.nd", json);
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//
//            }
//        });
        ArrayList<Episode> arrayList = new ArrayList();
        Gson gson = new Gson();

        arrayList = gson.fromJson(loadJSONFromAsset(), new TypeToken<ArrayList<Episode>>() {
        }.getType());

        Log.d("ky.nd", "" + arrayList.size());

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = ((CategoryFragment)mCategoryView).getActivity().getAssets().open("yourfilename.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
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
