package com.duckydev.mvpdagger.category;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.duckydev.mvpdagger.BuildConfig;
import com.duckydev.mvpdagger.R;
import com.duckydev.mvpdagger.data.Episode;
import com.duckydev.mvpdagger.data.source.EpisodesDataSource;
import com.duckydev.mvpdagger.data.source.EpisodesRepository;
import com.duckydev.mvpdagger.di.ActivityScoped;
import com.duckydev.mvpdagger.util.EpisodeType;
import com.duckydev.mvpdagger.util.SharePreferenceUtils;
import com.duckydev.mvpdagger.util.wiget.DialogManager;
import com.duckydev.mvpdagger.util.wiget.RateManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
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

    // Remote Config keys
    private static final String LASTED_DATABASE_VERSION = "lasted_database_version";
    private static final String LASTED_DATABASE_URL = "lasted_database_url";
    private static final String LASTED_APPLICATION_VERSION = "lasted_application_version";
    private static final String APPLICATION_PACKAGE_ID = "application_package_id";
    private static final String APPLICATION_PACKAGE_ID_NEED_TO_UPDATE = "application_package_id_need_to_update";

    private final EpisodesRepository mEpisodesRepository;

    @Nullable
    private CategoryContract.View mCategoryView;
    private int PREVIEW_EPISODE_SIZE = 6;
    private int DUCKY_EPISODE_SIZE = 7;
    boolean isFirstRun = true;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    List<Episode> duckyEpisodeList = new ArrayList<>();

    @Inject
    public CategoryPresenter(EpisodesRepository episodesRepository) {
        mEpisodesRepository = episodesRepository;
    }

    List<Episode> episodes3 = new ArrayList<>();
    List<Episode> episodes2 = new ArrayList<>();
    List<Episode> episodes1 = new ArrayList<>();
    List<Episode> episodes0 = new ArrayList<>();

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
//        mEpisodesRepository.getFirstNumberOfEpisodeByType(EpisodeType.DRAMA , 15, new EpisodesDataSource.LoadEpisodesCallback() {
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
//        episodes0.clear();
//        episodes1.clear();
//        episodes2.clear();
//        episodes3.clear();
//
// Create a storage reference from our app
        // Create a storage reference from our app


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

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);

        fetchWelcome();
        loadAllPreviewEpisode();


    }

    @Override
    public void dropView() {
        mCategoryView = null;
    }

    private void updateDatabase() {
        int closeVersion = (int) mFirebaseRemoteConfig.getLong(LASTED_DATABASE_VERSION);
        if (mCategoryView != null && closeVersion != SharePreferenceUtils.getDatabaseVersion(((CategoryFragment) mCategoryView).getActivity())) {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReferenceFromUrl("gs://bbc-learning-english-1e193.appspot.com/database");
            StorageReference islandRef = storageRef.child(closeVersion + ".json");

            File rootPath = new File(Environment.getExternalStorageDirectory(), "cached_database");
            if (!rootPath.exists()) {
                rootPath.mkdirs();
            }

            final File localFile = new File(rootPath, "database_update.json");
            final long ONE_MEGABYTE = 1024 * 1024;
            islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {

                @Override
                public void onSuccess(byte[] bytes) {
                    String json = new String(bytes);
                    ArrayList<Episode> arrayList = new ArrayList();
                    Gson gson = new Gson();

                    arrayList = gson.fromJson(json, new TypeToken<ArrayList<Episode>>() {
                    }.getType());

                    Log.d("ky.nd", "" + arrayList.size());

                    mEpisodesRepository.insertEpisodeList(arrayList);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.e("firebase ", ";local tem file not created  created " + exception.toString());
                }
            });
        }


    }

    private void fetchWelcome() {
//        mWelcomeTextView.setText(mFirebaseRemoteConfig.getString(LOADING_PHRASE_CONFIG_KEY));

        long cacheExpiration = 3600; // 1 hour in seconds.
        // If your app is using developer mode, cacheExpiration is set to 0, so each fetch will
        // retrieve values from the service.
        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }


    }


}



