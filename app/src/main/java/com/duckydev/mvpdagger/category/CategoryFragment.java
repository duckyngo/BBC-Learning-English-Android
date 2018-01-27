package com.duckydev.mvpdagger.category;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.duckydev.mvpdagger.R;
import com.duckydev.mvpdagger.data.Episode;
import com.duckydev.mvpdagger.di.ActivityScoped;
import com.duckydev.mvpdagger.episodedetail.EpisodeDetailActivity;
import com.duckydev.mvpdagger.features.FeaturesActivity;
import com.duckydev.mvpdagger.util.EpisodeType;
import com.duckydev.mvpdagger.util.adapter.FeaturedPagerAdapter;
import com.duckydev.mvpdagger.util.subview.DuckyViewPager;
import com.duckydev.mvpdagger.util.subview.FeatureCirclePageIndicator;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

import static com.duckydev.mvpdagger.features.FeaturesActivity.EXTRA_FEATURE;


@ActivityScoped
public class CategoryFragment extends DaggerFragment implements CategoryContract.View{

    Activity mActivity;

    @Inject
    CategoryContract.Presenter mPresenter;

    @BindView(R.id.custom_horizontal_listview_01)
    LinearLayout mCustomListView01;

    @BindView(R.id.custom_horizontal_listview_02)
    LinearLayout mCustomListView02;

    @BindView(R.id.custom_horizontal_listview_03)
    LinearLayout mCustomListView03;

    @BindView(R.id.custom_horizontal_listview_04)
    LinearLayout mCustomListView04;

    @BindView(R.id.custom_horizontal_listview_05)
    LinearLayout mCustomListView05;

    @BindView(R.id.custom_horizontal_listview_06)
    LinearLayout mCustomListView06;

    @BindView(R.id.title_1)
    TextView mTitle01;

    @BindView(R.id.title_2)
    TextView mTitle02;

    @BindView(R.id.title_3)
    TextView mTitle03;

    @BindView(R.id.title_4)
    TextView mTitle04;

    @BindView(R.id.title_5)
    TextView mTitle05;

    @BindView(R.id.title_6)
    TextView mTitle06;

    @BindView(R.id.scroll_view)
    NestedScrollView mNestedScrollView;

    protected DuckyViewPager mDuckyViewPager;


    private Handler mHandler;
    private List<Episode> mEpisodeList;
    private ViewPagerCallback mViewPagerCallback;
    private FeaturedPagerAdapter mFeaturedPagerAdapter;


    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (getActivity() != null) {
                mDuckyViewPager.setCurrentItem(mDuckyViewPager.getCurrentItem() + 1, true);
                mHandler.postDelayed(mRunnable, 4000);
            }
        }
    };


    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), FeaturesActivity.class);
            switch (v.getId()) {
                case R.id.title_1:
                    intent.putExtra(EXTRA_FEATURE, EpisodeType.fromEpisodeType(EpisodeType.SIX_MINUTE_ENGLISH));
                    break;
                case R.id.title_2:
                    intent.putExtra(EXTRA_FEATURE, EpisodeType.fromEpisodeType(EpisodeType.ENGLISH_WE_SPEAK));
                    break;
                case R.id.title_3:
                    intent.putExtra(EXTRA_FEATURE, EpisodeType.fromEpisodeType(EpisodeType.ENGLISH_AT_WORK));
                    break;
                case R.id.title_4:
                    intent.putExtra(EXTRA_FEATURE, EpisodeType.fromEpisodeType(EpisodeType.LINGOHACK));
                    break;
                case R.id.title_5:
                    intent.putExtra(EXTRA_FEATURE, EpisodeType.fromEpisodeType(EpisodeType.DRAMA));
                    break;
                case R.id.title_6:
                    intent.putExtra(EXTRA_FEATURE, EpisodeType.fromEpisodeType(EpisodeType.NEWS_REPORT));
                    break;
            }
            startActivity(intent);
        }
    };


    @Inject
    public CategoryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = getActivity();
        mViewPagerCallback = (ViewPagerCallback) getActivity();
        mDuckyViewPager = mViewPagerCallback.getViewPager();
        mHandler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.category_frag, container, false);
        ButterKnife.bind(this, rootView);

        mTitle01.setText(EpisodeType.getEpisodeTypeText(getActivity(), EpisodeType.SIX_MINUTE_ENGLISH));
        mTitle02.setText(EpisodeType.getEpisodeTypeText(getActivity(), EpisodeType.ENGLISH_WE_SPEAK));
        mTitle03.setText(EpisodeType.getEpisodeTypeText(getActivity(), EpisodeType.ENGLISH_AT_WORK));
        mTitle04.setText(EpisodeType.getEpisodeTypeText(getActivity(), EpisodeType.LINGOHACK));
        mTitle05.setText(EpisodeType.getEpisodeTypeText(getActivity(), EpisodeType.DRAMA));
        mTitle06.setText(EpisodeType.getEpisodeTypeText(getActivity(), EpisodeType.NEWS_REPORT));

        mTitle01.setOnClickListener(mOnClickListener);
        mTitle02.setOnClickListener(mOnClickListener);
        mTitle03.setOnClickListener(mOnClickListener);
        mTitle04.setOnClickListener(mOnClickListener);
        mTitle05.setOnClickListener(mOnClickListener);
        mTitle06.setOnClickListener(mOnClickListener);


        // Set up progress indicator
        final SwipeRefreshLayout swipeRefreshLayout =
                rootView.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
        // Set the scrolling view in the custom SwipeRefreshLayout.
//        swipeRefreshLayout.setScrollUpChild(mNestedScrollView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadAllPreviewEpisode();
            }
        });
//        exportDatabse("Episodes.db");

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.dropView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    public void setLoadingIndicator(final boolean active) {

        if (getView() == null) {
            return;
        }

        final SwipeRefreshLayout swipeRefreshLayout = getView().findViewById(R.id.refresh_layout);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(active);
            }
        });

    }

    @Override
    public void showPreviewEpisodes(EpisodeType type, List<Episode> episodes) {
        switch (type) {
            case ENGLISH_AT_WORK:
                feedCustomListView(type, mCustomListView03, episodes);
                break;
//            case ENGLISH_AT_UNIVERSITY:
//                feedCustomListView(mCustomListView01, episodes);
//                break;
            case DRAMA:
                feedCustomListView(type, mCustomListView05, episodes);
                break;
            case LINGOHACK:
                feedCustomListView(type, mCustomListView04, episodes);
                break;
            case NEWS_REPORT:
                feedCustomListView(type, mCustomListView06, episodes);
                break;
//            case PRONUNCIATION:
//                feedCustomListView(mCustomListView01, episodes);
//                break;
            case ENGLISH_WE_SPEAK:
                feedCustomListView(type, mCustomListView02, episodes);
                break;
//            case WORK_IN_THE_NEWS:
//                feedCustomListView(mCustomListView01, episodes);
//                break;
            case SIX_MINUTE_ENGLISH:
                feedCustomListView(type, mCustomListView01, episodes);
                break;
        }
    }

    @Override
    public void showDuckyEpisodes(List<Episode> episodes) {
        if (mDuckyViewPager != null && episodes != null && !episodes.isEmpty()) {
            if (mFeaturedPagerAdapter == null) {
                mFeaturedPagerAdapter = new FeaturedPagerAdapter(getActivity(), episodes);
                mDuckyViewPager.setAdapter(mFeaturedPagerAdapter);
            }

            mFeaturedPagerAdapter.setInitialSelectedPosition();

            mDuckyViewPager.setCurrentItem(mFeaturedPagerAdapter.getInitialSelectedPosition(), false);
            mDuckyViewPager.addOnPageChangeListener(mFeaturedPagerAdapter);

            FeatureCirclePageIndicator featureCirclePageIndicator = mViewPagerCallback.getCirclePageIndicator();
            featureCirclePageIndicator.setCourceSize(mFeaturedPagerAdapter.getCount() / FeaturedPagerAdapter.LOOP_COUNT);
            featureCirclePageIndicator.setViewPager(mDuckyViewPager);

            mHandler.removeCallbacks(mRunnable);
            mHandler.postDelayed(mRunnable, 4000);
        }
    }

    @Override
    public void showEpisodes(List<Episode> episodes) {

    }

    @Override
    public void showFeatures(EpisodeType type) {

    }

    @Override
    public void showEpisodeDetail(Episode episode) {

    }

    @Override
    public void showRecents() {

    }

    @Override
    public void showDownloads() {

    }

    @Override
    public void showNumDownloaded(int numberOfDownloaded) {

    }

    @Override
    public void showVocabularies() {

    }

    @Override
    public void showNumVocabularies(int numberOfVocabularies) {

    }

    @Override
    public void showFavorites() {

    }

    @Override
    public void showNumFavorites(int numberOfFavorites) {

    }

    @Override
    public void showHistories() {

    }

    @Override
    public void showNewUpdateAvailable() {

    }

    @Override
    public void showNoInternetConnection() {

    }

    @Override
    public void showRateDialog() {

    }

    @Override
    public void showFeedback() {

    }

    @Override
    public void showPrivacyPolicy() {

    }

    @Override
    public void showSettings() {

    }

    @Override
    public void updateFavoriteBadge(int number) {
        if (mActivity != null) {
            ((CategoryActivity)mActivity).updateFavoritesBadge(number);
        }
    }

    @Override
    public void updateDownloadedBadge(int number) {
        if (mActivity != null) {
            ((CategoryActivity)mActivity).updateDownloadedBadge(number);
        }
    }

    public void feedCustomListView(final EpisodeType type, LinearLayout layout, final List<Episode> episodes) {

        for (int i = 0; i < 5; i++) {
            CardView cardView = (CardView) layout.getChildAt(i);
            cardView.setClickable(true);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), EpisodeDetailActivity.class);
                    intent.putExtra(EpisodeDetailActivity.EXTRA_EPISODE_ID, episodes.get(finalI).get_id());
                    startActivity(intent);
                }
            });

            ViewGroup viewGroup = (ViewGroup) cardView.getChildAt(0);

            ImageView episodeImage = (ImageView) viewGroup.getChildAt(0);
            TextView textView = (TextView) viewGroup.getChildAt(1);

            Picasso.with(getActivity()).load(episodes.get(i).getThumbImageUrl()).into(episodeImage);
            textView.setText(episodes.get(i).getTitle());
        }

        CardView cardView = (CardView) layout.getChildAt(5);
        cardView.setClickable(true);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FeaturesActivity.class);
                intent.putExtra(EXTRA_FEATURE, EpisodeType.fromEpisodeType(type));
                startActivity(intent);
            }
        });
    }

    public interface ViewPagerCallback{
        FeatureCirclePageIndicator getCirclePageIndicator();

        DuckyViewPager getViewPager();

        void setCategoriesList(List<Episode> categoriesList);

        boolean shouldScrool();
    }

    public void exportDatabse(String databaseName) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//"+getActivity().getPackageName()+"//databases//"+databaseName+"";
                String backupDBPath = "backupname.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {

        }
    }
}
