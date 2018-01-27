package com.duckydev.mvpdagger.category;

import android.support.annotation.Nullable;
import android.widget.Toast;

import com.duckydev.mvpdagger.data.Episode;
import com.duckydev.mvpdagger.data.source.EpisodesDataSource;
import com.duckydev.mvpdagger.data.source.EpisodesRepository;
import com.duckydev.mvpdagger.di.ActivityScoped;
import com.duckydev.mvpdagger.util.EpisodeType;
import com.duckydev.mvpdagger.util.wiget.DialogManager;
import com.duckydev.mvpdagger.util.wiget.RateManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by duckyng on 12/21/2017.
 */

@ActivityScoped
public class CategoryPresenter implements CategoryContract.Presenter {

    private final EpisodesRepository mEpisodesRepository;

    @Nullable
    private CategoryContract.View mCategoryView;
    private int PREVIEW_EPISODE_SIZE = 6;
    private int DUCKY_EPISODE_SIZE = 7;
    boolean isFirstRun = true;
    List<Episode> duckyEpisodeList = new ArrayList<>();

    @Inject
    public CategoryPresenter(EpisodesRepository episodesRepository) {
        mEpisodesRepository = episodesRepository;
    }

//    List<Episode> episodes3 = new ArrayList<>();
//    List<Episode> episodes2 = new ArrayList<>();
//    List<Episode> episodes1 = new ArrayList<>();
//    List<Episode> episodes0 = new ArrayList<>();

    @Override
    public void loadAllPreviewEpisode() {


//        mEpisodesRepository.getFirstNumberOfEpisodeByType(EpisodeType.ENGLISH_WE_SPEAK, 15, new EpisodesDataSource.LoadEpisodesCallback() {
//            @Override
//            public void onEpisodesLoaded(List<Episode> episodes) {
//                for (int i = 0; i <15; i++) {
//                    if (i < 5) {
//                        episodes3.add(episodes.get(i));
//                    } else if (i < 10) {
//                        episodes2.add(episodes.get(i));
//                    } else if (i < 12) {
//                        episodes1.add(episodes.get(i));
//                    } else {
//                        episodes0.add(episodes.get(i));
//                    }
//                }
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//
//            }
//        });
//
//        mEpisodesRepository.getFirstNumberOfEpisodeByType(EpisodeType.LINGOHACK, 15, new EpisodesDataSource.LoadEpisodesCallback() {
//            @Override
//            public void onEpisodesLoaded(List<Episode> episodes) {
//                for (int i = 0; i <15; i++) {
//                    if (i < 5) {
//                        episodes3.add(episodes.get(i));
//                    } else if (i < 10) {
//                        episodes2.add(episodes.get(i));
//                    } else if (i < 12) {
//                        episodes1.add(episodes.get(i));
//                    } else {
//                        episodes0.add(episodes.get(i));
//                    }
//                }
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//
//            }
//        });
//
//        mEpisodesRepository.getFirstNumberOfEpisodeByType(EpisodeType.SIX_MINUTE_ENGLISH, 15, new EpisodesDataSource.LoadEpisodesCallback() {
//            @Override
//            public void onEpisodesLoaded(List<Episode> episodes) {
//                for (int i = 0; i <15; i++) {
//                    if (i < 5) {
//                        episodes3.add(episodes.get(i));
//                    } else if (i < 10) {
//                        episodes2.add(episodes.get(i));
//                    } else if (i < 12) {
//                        episodes1.add(episodes.get(i));
//                    } else {
//                        episodes0.add(episodes.get(i));
//                    }
//                }
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//
//            }
//        });
//
//        mEpisodesRepository.getFirstNumberOfEpisodeByType(EpisodeType.DRAMA, 15, new EpisodesDataSource.LoadEpisodesCallback() {
//            @Override
//            public void onEpisodesLoaded(List<Episode> episodes) {
//                for (int i = 0; i <15; i++) {
//                    if (i < 5) {
//                        episodes3.add(episodes.get(i));
//                    } else if (i < 10) {
//                        episodes2.add(episodes.get(i));
//                    } else if (i < 12) {
//                        episodes1.add(episodes.get(i));
//                    } else {
//                        episodes0.add(episodes.get(i));
//                    }
//                }
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//
//            }
//        });
//
//
//
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
////                Gson gson = new Gson();
//        String version0 = gson.toJson(episodes0, new TypeToken<ArrayList<Episode>>() {
//        }.getType());
//
//        String version1 = gson.toJson(episodes1, new TypeToken<ArrayList<Episode>>() {
//        }.getType());
//
//        String version2 = gson.toJson(episodes2, new TypeToken<ArrayList<Episode>>() {
//        }.getType());
//
//        String version3 = gson.toJson(episodes3, new TypeToken<ArrayList<Episode>>() {
//        }.getType());
//
//
//        Log.d("ky.nd", version0);
//        Log.d("ky.nd", version1);
//        Log.d("ky.nd", version2);
//        Log.d("ky.nd", version3);

//        ArrayList<Episode> arrayList = new ArrayList();
//        Gson gson = new Gson();
//
//        arrayList = gson.fromJson(loadJSONFromAsset(), new TypeToken<ArrayList<Episode>>() {
//        }.getType());
//
//        Log.d("ky.nd", "" + arrayList.size());
//
//        if (!isFirstRun) {
//                mEpisodesRepository.insertEpisodeList(arrayList);
//        }
//        isFirstRun = false;
        duckyEpisodeList.clear();

        loadPreviewEpisodes(EpisodeType.SIX_MINUTE_ENGLISH);
        loadPreviewEpisodes(EpisodeType.DRAMA);
        loadPreviewEpisodes(EpisodeType.WORK_IN_THE_NEWS);
        loadPreviewEpisodes(EpisodeType.LINGOHACK);
        loadPreviewEpisodes(EpisodeType.ENGLISH_AT_WORK);
        loadPreviewEpisodes(EpisodeType.NEWS_REPORT);
        loadPreviewEpisodes(EpisodeType.ENGLISH_WE_SPEAK);

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
    public void loadPreviewEpisodes(final EpisodeType type) {
        if (mCategoryView != null) {
            mCategoryView.setLoadingIndicator(true);
        }
        mEpisodesRepository.getFirstNumberOfEpisodeByType(type, PREVIEW_EPISODE_SIZE, new EpisodesDataSource.LoadEpisodesCallback() {
            @Override
            public void onEpisodesLoaded(List<Episode> episodes) {
                if (mCategoryView != null) {
                    duckyEpisodeList.add(episodes.get(0));
                    episodes.remove(0);
                    mCategoryView.showPreviewEpisodes(type, episodes);
                    mCategoryView.setLoadingIndicator(false);
                    if (duckyEpisodeList.size() == DUCKY_EPISODE_SIZE) {
                        mCategoryView.showDuckyEpisodes(duckyEpisodeList);
                    }
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = ((CategoryFragment) mCategoryView).getActivity().getAssets().open("yourfilename.json");
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
    public void checkForDatabaseUpdate() {

    }

    @Override
    public void checkInternetConnection() {

    }

    @Override
    public void shareApplication() {
        if (mCategoryView != null) {
            Toast.makeText(((CategoryFragment) mCategoryView).getActivity(), "", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void rateApplication() {
        if (mCategoryView != null) {
            new RateManager(((CategoryFragment) mCategoryView).getActivity(), new DialogManager()).showRate();
        }
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
