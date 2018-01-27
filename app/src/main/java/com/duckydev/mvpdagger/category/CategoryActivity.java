package com.duckydev.mvpdagger.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.duckydev.mvpdagger.R;
import com.duckydev.mvpdagger.data.Episode;
import com.duckydev.mvpdagger.features.FeaturesActivity;
import com.duckydev.mvpdagger.util.ActivityUtils;
import com.duckydev.mvpdagger.util.EpisodeType;
import com.duckydev.mvpdagger.util.RateUtils;
import com.duckydev.mvpdagger.util.subview.DuckyViewPager;
import com.duckydev.mvpdagger.util.subview.FeatureCirclePageIndicator;
import com.duckydev.mvpdagger.util.subview.FlingBehavior;
import com.duckydev.mvpdagger.vocabularies.VocabulariesActivity;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.StringHolder;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;

import static com.duckydev.mvpdagger.features.FeaturesActivity.EXTRA_FEATURE;
import static com.duckydev.mvpdagger.util.EpisodeType.fromEpisodeType;

public class CategoryActivity extends DaggerAppCompatActivity implements CategoryFragment.ViewPagerCallback {

    private Drawer result = null;
    private Drawer resultAppended = null;

    @Inject
    CategoryPresenter mCategoryPresenter;

    @Inject
    Lazy<CategoryFragment> categoryFragmentProvider;

    @BindView(R.id.main_view_pager)
    protected DuckyViewPager mDuckyViewPager;

    @BindView(R.id.view_pager_content_wrapper)
    protected View viewPagerContentContainer;

    @BindView(R.id.appbar)
    protected AppBarLayout mAppBarLayout;

    @BindView(R.id.collapsing_toolbar_layout)
    protected CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.indicator)
    protected FeatureCirclePageIndicator mFeatureCirclePageIndicator;

    @BindView(R.id.placeholder)
    protected View placeHolder;


    private boolean mShouldScrool;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.category_act);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("Features");

        result = new DrawerBuilder()
                .withActivity(this)
                .withHeader(R.layout.header)
                .withTranslucentStatusBar(false)
                .withSavedInstance(savedInstanceState)
                .addDrawerItems(
                        new SectionDrawerItem().withName(R.string.drawer_item_playlist).withSelectable(false),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_recent_audios).withIcon(R.drawable.ic_action_book).withSelectable(false),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_videos).withIcon(R.drawable.ic_video_library).withSelectable(false),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_history).withIcon(R.drawable.ic_history).withSelectable(false),
                        new SectionDrawerItem().withName(R.string.drawer_item_my_collections),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_vocabularies).withIcon(R.drawable.ic_action_book).withSelectable(false),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_favorites).withIcon(R.drawable.ic_favorite).withBadge("0").withSelectable(false)
                                .withIdentifier(4),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_downloads).withIcon(R.drawable.ic_offline_pin).withBadge("0").withSelectable(false)
                                .withIdentifier(5),
                        new SectionDrawerItem().withName(R.string.drawer_item_others).withSelectable(false),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_dictionary).withIcon(R.drawable.ic_action_book).withSelectable(false),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_rate).withIcon(R.drawable.ic_rate_review).withSelectable(false),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_share).withIcon(R.drawable.ic_share).withSelectable(false)
                )
//                .withOnDrawerListener(new Drawer.OnDrawerListener() {
//                    @Override
//                    public void onDrawerOpened(View drawerView) {
//                        Toast.makeText(CategoryActivity.this, "onDrawerOpened", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onDrawerClosed(View drawerView) {
//                        Toast.makeText(CategoryActivity.this, "onDrawerClosed", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onDrawerSlide(View drawerView, float slideOffset) {
//
//                    }
//                })
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        //check if the drawerItem is set.
                        //there are different reasons for the drawerItem to be null
                        //--> click on the header
                        //--> click on the footer
                        //those items don't contain a drawerItem
                        Intent intent = new Intent(CategoryActivity.this, FeaturesActivity.class);
                        if (drawerItem instanceof Nameable) {
                            switch (position) {
//                                case 0:
//                                    intent.putExtra(EXTRA_FEATURE, EpisodeType.fromEpisodeType(EpisodeType.DRAMA));
//                                    break;
//                                case 1:
//                                    intent.putExtra(EXTRA_FEATURE, EpisodeType.fromEpisodeType(EpisodeType.ENGLISH_AT_UNIVERSITY));
//                                    break;
                                case 2:
                                    // Recent Audios
                                    intent.putExtra(EXTRA_FEATURE, fromEpisodeType(EpisodeType.RECENT_AUDIOS));
                                    startActivity(intent);
                                    break;
                                case 3:
                                    // Videos
//                                    intent.putExtra(EXTRA_FEATURE, fromEpisodeType(EpisodeType.ENGLISH_WE_SPEAK));
                                    break;
                                case 4:
                                    // History
//                                    intent.putExtra(EXTRA_FEATURE, fromEpisodeType(EpisodeType.LINGOHACK));
                                    break;
                                case 5:
//                                    intent.putExtra(EXTRA_FEATURE, fromEpisodeType(EpisodeType.NEWS_REPORT));
                                    break;
                                case 6:
                                    // Vocabularies
                                    Intent vocaIntent = new Intent(CategoryActivity.this, VocabulariesActivity.class);
                                    startActivity(vocaIntent);
                                    break;
                                case 7:
                                    // Favorites
                                    intent.putExtra(EXTRA_FEATURE, fromEpisodeType(EpisodeType.FAVORITES));
                                    startActivity(intent);
                                    break;
                                case 8:
                                    // Downloads
                                    intent.putExtra(EXTRA_FEATURE, fromEpisodeType(EpisodeType.DOWNLOADS));
                                    startActivity(intent);
                                    break;
                                case 9:
                                    intent.putExtra(EXTRA_FEATURE, fromEpisodeType(EpisodeType.RECENT_AUDIOS));
                                    startActivity(intent);
                                    break;
                                case 10:
                                    // Dictionary
                                    intent.putExtra(EXTRA_FEATURE, fromEpisodeType(EpisodeType.FAVORITES));
                                    startActivity(intent);
                                    break;
                                case 11:
                                    // Rate
                                    mCategoryPresenter.rateApplication();
                                    break;
                                case 14:
                                    // Share
                                    intent.putExtra(EXTRA_FEATURE, fromEpisodeType(EpisodeType.DOWNLOADS));
                                    startActivity(intent);
                                    break;

                            }

                        }

                        return false;
                    }
                })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem instanceof SecondaryDrawerItem) {
                            Toast.makeText(CategoryActivity.this, ((SecondaryDrawerItem) drawerItem).getName().getText(CategoryActivity.this),
                                    Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                })
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        if (drawerView == result.getSlider()) {
                            Log.e("sample", "left opened");
                        } else if (drawerView == resultAppended.getSlider()) {
                            Log.e("sample", "right opened");
                        }
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        if (drawerView == result.getSlider()) {
                            Log.e("sample", "left closed");
                        } else if (drawerView == resultAppended.getSlider()) {
                            Log.e("sample", "right closed");
                        }
                    }

                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {

                    }
                })
                .build();

        //now we add the second drawer on the other site.
        //use the .append method to add this drawer to the first one
        resultAppended = new DrawerBuilder()
                .withActivity(this)
                .withHeader(R.layout.header)
//                .withFooter(R.layout.footer)
//                .withDisplayBelowStatusBar(true)
                .withTranslucentStatusBar(false)
                .withSavedInstance(savedInstanceState)
                .addDrawerItems(
//                        new PrimaryDrawerItem().withName(R.string.drawer_item_first),

                        new SectionDrawerItem().withName(R.string.drawer_item_beginner).withSelectable(false),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_the_english_we_speak).withIcon(R.drawable.ic_action_book).withSelectable(false),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_express_english).withIcon(R.drawable.ic_action_book).withSelectable(false),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_news_report).withIcon(R.drawable.ic_action_book).withSelectable(false),

                        new SectionDrawerItem().withName(R.string.drawer_item_intermediate).withSelectable(false),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_english_at_university).withIcon(R.drawable.ic_action_book).withSelectable(false),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_english_at_work).withIcon(R.drawable.ic_action_book).withSelectable(false),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_lingohack).withIcon(R.drawable.ic_action_book).withSelectable(false),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_words_in_the_news).withIcon(R.drawable.ic_action_book).withSelectable(false),

                        new SectionDrawerItem().withName(R.string.drawer_item_advanced).withSelectable(false),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_six_minute_english).withIcon(R.drawable.ic_action_book).withSelectable(false),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_six_minute_grammar).withIcon(R.drawable.ic_action_book).withSelectable(false),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_six_minute_vocabulary).withIcon(R.drawable.ic_action_book).withSelectable(false),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_drama).withIcon(R.drawable.ic_action_book).withSelectable(false),
                        new SecondaryDrawerItem().withName("Prononciation").withIcon(R.drawable.ic_action_book).withSelectable(false)

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Intent intent = new Intent(CategoryActivity.this, FeaturesActivity.class);
                        if (drawerItem instanceof Nameable) {
//                            Toast.makeText(CategoryActivity.this, ((Nameable) drawerItem).getName().getText(CategoryActivity.this), Toast
// .LENGTH_SHORT).show();
                            switch (position) {
//                                case 0:
//                                    intent.putExtra(EXTRA_FEATURE, EpisodeType.fromEpisodeType(EpisodeType.ENGLISH_WE_SPEAK));
//                                    break;

//                                case 1:
//                                    intent.putExtra(EXTRA_FEATURE, EpisodeType.fromEpisodeType(EpisodeType.ENGLISH_WE_SPEAK));
//                                    break;

                                case 2:
                                    intent.putExtra(EXTRA_FEATURE, fromEpisodeType(EpisodeType.ENGLISH_WE_SPEAK));
                                    break;

                                case 3:
                                    intent.putExtra(EXTRA_FEATURE, EpisodeType.fromEpisodeType(EpisodeType.EXPRESS_ENGLISH));
                                    break;

                                case 4:
                                    intent.putExtra(EXTRA_FEATURE, fromEpisodeType(EpisodeType.NEWS_REPORT));
                                    break;

//                                case 5:
//                                    intent.putExtra(EXTRA_FEATURE, EpisodeType.fromEpisodeType(EpisodeType.ENGLISH_WE_SPEAK));
//                                    break;

                                case 6:
                                    intent.putExtra(EXTRA_FEATURE, fromEpisodeType(EpisodeType.ENGLISH_AT_UNIVERSITY));
                                    break;

                                case 7:
                                    intent.putExtra(EXTRA_FEATURE, fromEpisodeType(EpisodeType.ENGLISH_AT_WORK));
                                    break;

                                case 8:
                                    intent.putExtra(EXTRA_FEATURE, fromEpisodeType(EpisodeType.LINGOHACK));
                                    break;

                                case 9:
                                    intent.putExtra(EXTRA_FEATURE, fromEpisodeType(EpisodeType.WORK_IN_THE_NEWS));
                                    break;

                                case 11:
                                    intent.putExtra(EXTRA_FEATURE, fromEpisodeType(EpisodeType.SIX_MINUTE_ENGLISH));
                                    break;

                                case 12:
                                    intent.putExtra(EXTRA_FEATURE, fromEpisodeType(EpisodeType.SIX_MINUTE_GRAMMAR));
                                    break;

                                case 13:
                                    intent.putExtra(EXTRA_FEATURE, fromEpisodeType(EpisodeType.SIX_MINUTE_VOCABULARY));
                                    break;

                                case 14:
                                    intent.putExtra(EXTRA_FEATURE, fromEpisodeType(EpisodeType.DRAMA));
                                    break;

                                case 15:
                                    intent.putExtra(EXTRA_FEATURE, fromEpisodeType(EpisodeType.PRONUNCIATION));
                                    break;

                            }

                            startActivity(intent);
                        }

                        return false;
                    }
                })
                .withDrawerGravity(Gravity.END)
                .append(result);

        CategoryFragment categoryFragment = (CategoryFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (categoryFragment == null) {
            categoryFragment = categoryFragmentProvider.get();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), categoryFragment, R.id.contentFrame);
        }

        new RateUtils().checkRate(this);

        if (mCollapsingToolbarLayout != null) {
            mCollapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white));
            mCollapsingToolbarLayout.setTitle("");
            mCollapsingToolbarLayout.setTitleEnabled(false);
        }


        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
        layoutParams.setBehavior(new FlingBehavior(this, null));
        mAppBarLayout.setLayoutParams(layoutParams);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                scrollRange = appBarLayout.getTotalScrollRange();
                String text = "";

                if (scrollRange + verticalOffset == 0) {
                    text = "Features";
                    isShow = true;
                } else if (isShow) {
                    ViewCompat.setElevation(appBarLayout, 0.0f);
                    isShow = false;
                }

                if (!getSupportActionBar().getTitle().toString().equals(text)) {
                    getSupportActionBar().setTitle(text);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.category_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                result.openDrawer();
                break;

            case R.id.right_menu_item:
                resultAppended.openDrawer();
                break;
        }

        return true;
    }

    public void updateFavoritesBadge(int number) {
        result.updateBadge(4, new StringHolder(String.valueOf(number)));
    }

    public void updateDownloadedBadge(int number) {
        result.updateBadge(5, new StringHolder(String.valueOf(number)));
    }


    @Override
    public FeatureCirclePageIndicator getCirclePageIndicator() {
        return mFeatureCirclePageIndicator;
    }

    @Override
    public DuckyViewPager getViewPager() {
        return mDuckyViewPager;
    }

    @Override
    public void setCategoriesList(List<Episode> categoriesList) {

    }

    @Override
    public boolean shouldScrool() {
        return mShouldScrool;
    }


    private CategoryFragment getCurrentFragment() {
        return (CategoryFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
    }
}
