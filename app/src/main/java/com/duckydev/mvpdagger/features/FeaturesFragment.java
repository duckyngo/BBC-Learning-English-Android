package com.duckydev.mvpdagger.features;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.duckydev.mvpdagger.R;
import com.duckydev.mvpdagger.data.Episode;
import com.duckydev.mvpdagger.episodedetail.EpisodeDetailActivity;
import com.duckydev.mvpdagger.util.EpisodeType;
import com.duckydev.mvpdagger.util.wiget.CommonAdapter;
import com.duckydev.mvpdagger.util.wiget.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

import static com.duckydev.mvpdagger.features.FeaturesActivity.EXTRA_FEATURE;

public class FeaturesFragment extends DaggerFragment implements FeaturesContract.View {

    @Inject
    FeaturesContract.Presenter mPresenter;
    private ListView listView;
    public ArrayList<Episode> loadedDataList = new ArrayList<>();
    public CommonAdapter<Episode> adapter;

    private Episode mEpisode;
    private int type;
    private String[] mFeatureNames;

    private TextView mFilteringLabelView;
    private View mNoEpisodesView;
    private ImageView mNoEpisodeIcon;
    private TextView mNoEpisodeMainView;
    private TextView mNoEpisodeAddView;
    private LinearLayout mEpisodesView;

    EpisodeItemListener mEpisodeItemListener = new EpisodeItemListener() {
        @Override
        public void onEpisodeClick(Episode clickedEpisode) {
            mPresenter.openEpisodeDetails(clickedEpisode);
        }

        @Override
        public void onDownloadEpisodeClick(Episode downloadEpisodeClicked) {
            Toast.makeText(getActivity(), "Downloaded.", Toast.LENGTH_SHORT).show();
            mPresenter.downloadEpisode(downloadEpisodeClicked);
        }

        @Override
        public void onFavoriteEpisodeClick(Episode favoriteEpisodeClicked) {
            Toast.makeText(getActivity(), "Favorited", Toast.LENGTH_SHORT).show();
            mPresenter.favoriteEpisode(favoriteEpisodeClicked);
        }

        @Override
        public void onUnDownloadEpisodeClick(Episode unDownloadEpisodeClicked) {
            Toast.makeText(getActivity(), "Remove Offline", Toast.LENGTH_SHORT).show();
            mPresenter.deleteDownloadedEpisode(unDownloadEpisodeClicked);
        }

        @Override
        public void onUnFavoriteEpisodeClick(Episode unFavoriteEpisodeClicked) {
            Toast.makeText(getActivity(), "Unfavorite", Toast.LENGTH_SHORT).show();
            mPresenter.unFavoriteEpisode(unFavoriteEpisodeClicked);
        }
    };

    @Inject
    public FeaturesFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        type = getActivity().getIntent().getIntExtra(EXTRA_FEATURE, 0);
        mFeatureNames = getActivity().getResources().getStringArray(R.array.list_features);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(mFeatureNames[type]);
    }

    @Override
    public void onResume() {
        mPresenter.takeView(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mPresenter.dropView();
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.features_frag, container, false);
        listView = view.findViewById(R.id.listview);
        setupListview();

        if (type < EpisodeType.fromEpisodeType(EpisodeType.RECENT_AUDIOS)) {
            mPresenter.getEpisodeByType(EpisodeType.fromPrimetiveType(type));
        } else if (type == EpisodeType.fromEpisodeType(EpisodeType.DOWNLOADS)){
            mPresenter.getDownloadedEpisodes();
        } else if (type == EpisodeType.fromEpisodeType(EpisodeType.RECENT_AUDIOS)) {
            mPresenter.getRecentAudioEpisodes();
        } else if (type == EpisodeType.fromEpisodeType(EpisodeType.FAVORITES)) {
            mPresenter.getFavoriteEpisodes();
        }

        // Set up  no tasks view
        mNoEpisodesView = view.findViewById(R.id.noEpisodes);
        mNoEpisodeIcon = view.findViewById(R.id.noEpisodesIcon);
        mNoEpisodeMainView = view.findViewById(R.id.noEpisodesMain);
        mNoEpisodeAddView = view.findViewById(R.id.noEpisodesAdd);
        mNoEpisodeAddView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showAddTask();
            }
        });

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.features_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_filter:
                showFilteringPopUpMenu();
                break;
            case R.id.menu_refresh:
                mPresenter.getEpisodeByType(EpisodeType.fromPrimetiveType(type));
                break;
        }
        return true;
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showEpisodeDetail(Episode episode) {
        Intent intent = new Intent(getContext(), EpisodeDetailActivity.class);
        intent.putExtra(EpisodeDetailActivity.EXTRA_EPISODE_ID, episode.get_id());
        startActivity(intent);
    }

    @Override
    public void showEpisodes(ArrayList<Episode> episodes) {
        loadedDataList = episodes;
        adapter.notifyDataSetChanged();
        adapter.refreshListView(episodes);
        mNoEpisodesView.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSuccessfullyFavoritedMessage() {

    }

    @Override
    public void showSuccessfullyUnFavoritedMessage() {

    }

    @Override
    public void showSuccessfullyAddToDownloadedMessge() {

    }

    @Override
    public void showSuccessfullyDeleteMessage() {

    }

    @Override
    public void showDownloadComplete(Episode episode) {

    }

    @Override
    public void showDownloadedFilterLabel() {

    }

    @Override
    public void showFavoritedFilterLabel() {

    }

    @Override
    public void showWatchedFilterLabel() {

    }

    @Override
    public void showAllFilterLabel() {

    }

    @Override
    public void showNoDownloadedEpisode() {
        showNoEpisodeViews("You have no downloaded Episode", R.drawable.ic_report, false);
    }

    @Override
    public void showNoFavoritedEpisode() {
        showNoEpisodeViews("You have no favorited Episode", R.drawable.ic_action_book, true);
    }

    @Override
    public void showNoWatchedEpisode() {
        showNoEpisodeViews("You have no watched Episode", R.drawable.ic_delete_forever, false);

    }

    @Override
    public void showNoEpisode() {
        showNoEpisodeViews("You have no Episodes", R.drawable.ic_action_book, true);
    }

    private void showNoEpisodeViews(String mainText, int iconRes, boolean showAddView) {
        listView.setVisibility(View.GONE);
        mNoEpisodesView.setVisibility(View.VISIBLE);

        mNoEpisodeMainView.setText(mainText);
        mNoEpisodeIcon.setImageDrawable(getResources().getDrawable(iconRes));
        mNoEpisodeAddView.setVisibility(showAddView ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showFilteringPopUpMenu() {
        PopupMenu popupMenu = new PopupMenu(getContext(), getActivity().findViewById(R.id.menu_filter));
        popupMenu.getMenuInflater().inflate(R.menu.filter_episode, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.filter_downloaded:
                        mPresenter.setFiltering(EpisodesFilterType.DOWNLOADED_EPISODES);
                        break;
                    case R.id.filter_favorited:
                        mPresenter.setFiltering(EpisodesFilterType.FAVORITED_EPISODES);
                        break;
                    case R.id.filter_watched:
                        mPresenter.setFiltering(EpisodesFilterType.WATCHED_EPISODES);
                        break;
                    default:
                        mPresenter.setFiltering(EpisodesFilterType.ALL_EPISODES);
                        break;
                }
                mPresenter.getEpisodeByType(EpisodeType.fromPrimetiveType(type));
                return true;
            }
        });

        popupMenu.show();
    }

    private void showPopupMenu(View view, final int position) {
        final PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.getMenuInflater().inflate(R.menu.features_popup_menu, popupMenu.getMenu());

        Menu menu = popupMenu.getMenu();
        MenuItem downloadItem = menu.findItem(R.id.menu_download);
        MenuItem favoriteItem = menu.findItem(R.id.menu_addtomylist);

        final Episode episode = loadedDataList.get(position);
        if (episode.isDownloaded()) {
            downloadItem.setTitle("Delete Offline");
        } else {
            downloadItem.setTitle("Download Offline");
        }

        if (episode.isFavorite()) {
            favoriteItem.setTitle("Remove from Favorites");
        } else {
            favoriteItem.setTitle("Add to Favorites");
        }

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_download:
                        if (episode.isDownloaded()) {

                        } else {
                            mEpisodeItemListener.onDownloadEpisodeClick(episode);
                        }

                        break;
                    case R.id.menu_listen:
                        mEpisodeItemListener.onEpisodeClick(episode);
                        break;
                    case R.id.menu_addtomylist:
                        if (episode.isFavorite()) {
                        } else {
                            mEpisodeItemListener.onFavoriteEpisodeClick(episode);
                        }
                        break;
                }

                return false;
            }
        });

        popupMenu.show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    private void setupListview() {
        if (isAdded()) {
            adapter = new CommonAdapter<Episode>(getActivity(), loadedDataList, R.layout.features_item_copy) {
                public void convert(ViewHolder helper, final Episode item, final int pos) {
                    if (isAdded()) {
                        CardView normalItem = helper.getView(R.id.cardview_normal);
                        normalItem.setVisibility(View.VISIBLE);
                        ImageView imageIv = helper.getView(R.id.iv_image);
                        TextView titleTv = helper.getView(R.id.tv_title);
                        TextView presenterTv = helper.getView(R.id.tv_description);
//                        TextView durationTv = helper.getView(R.id.tv_duration);
                        TextView postDateTv = helper.getView(R.id.tv_post_date);

                        ImageView moreIv = helper.getView(R.id.iv_more);
//                        ImageView downloadIv = helper.getView(R.id.download_iv);
//                        ImageView favoriteIv = helper.getView(R.id.favorite_iv);

                        titleTv.setText(item.getTitle());
                        presenterTv.setText(item.getDescription());
//                        durationTv.setText(item.getType().toString());
                        postDateTv.setText(item.getEpisodeDate());

                        if (item.getThumbImageUrl() == null || item.getThumbImageUrl().length() <= 0) {
                            imageIv.setImageResource(R.drawable.first_thumb);
                        } else {
                            Picasso.with(mContext).load(item.getThumbImageUrl()).placeholder(R.drawable.first_thumb).into(imageIv);
                        }

                        normalItem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mEpisodeItemListener.onEpisodeClick(item);
                            }
                        });

                        moreIv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showPopupMenu(v, pos);
                            }
                        });

//                        downloadIv.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                mEpisodeItemListener.onDownloadEpisodeClick(item);
//                            }
//                        });
//
//                        favoriteIv.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                mEpisodeItemListener.onFavoriteEpisodeClick(item);
//                            }
//                        });

                    }
                }
            };
            listView.setAdapter(this.adapter);

//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Intent intent = new Intent(getContext(), EpisodeDetailActivity.class);
//                    intent.putExtra(EpisodeDetailActivity.EXTRA_EPISODE_ID, episodes.get(finalI).get_id());
//                    startActivity(intent);
//
//                }
//            });
        }
    }

    public interface EpisodeItemListener {

        void onEpisodeClick(Episode clickedEpisode);

        void onDownloadEpisodeClick(Episode downloadEpisodeClicked);

        void onFavoriteEpisodeClick(Episode favoriteEpisodeClicked);

        void onUnDownloadEpisodeClick(Episode unDownloadEpisodeClicked);

        void onUnFavoriteEpisodeClick(Episode unFavoriteEpisodeClicked);
    }
}
