package com.duckydev.mvpdagger.vocabularies;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.duckydev.mvpdagger.R;
import com.duckydev.mvpdagger.data.Vocabulary;
import com.duckydev.mvpdagger.util.wiget.CommonAdapter;
import com.duckydev.mvpdagger.util.wiget.ViewHolder;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

public class VocabularyFragment extends DaggerFragment implements VocabulariesContract.View {

    @Inject
    VocabulariesContract.Presenter mPresenter;

    public ArrayList<Vocabulary> loadedDataList = new ArrayList<>();
    public CommonAdapter<Vocabulary> adapter;


    @BindView(R.id.vocabulary_listview)
    ListView listView;

    @BindView(R.id.noVocabularies)
    View mNoVocabulariesView;

    @BindView(R.id.noVocabulariesIcon)
    ImageView mNoVocabulariesIcon;

    @BindView(R.id.noVocabulariesMain)
    TextView mNoVocabulariesMainView;

    @BindView(R.id.noVocabulariesAdd)
    TextView mNoVocabulariesAddView;

    @Inject
    public VocabularyFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.vocabulary_frag, container, false);
        ButterKnife.bind(this,rootView);

        setupListview();

        setHasOptionsMenu(true);
        return rootView;
    }

    private void setupListview() {
        if (isAdded()) {
            adapter = new CommonAdapter<Vocabulary>(getActivity(), loadedDataList, R.layout.vocabulary_item) {

                public void convert(ViewHolder helper, final Vocabulary item, final int pos) {
                    if (isAdded() && item != null) {
                        TextView vocabularyTextView = helper.getView(R.id.vocabulary_tv);
                        TextView definition = helper.getView(R.id.definition_tv);
                        TextView orderNumber = helper.getView(R.id.order_number);

                        if (vocabularyTextView != null) {
                            vocabularyTextView.setText(item.getVocabulary());
                        }

                        if (definition != null) {
                            definition.setText(item.getDefinition());
                        }

                        if (orderNumber != null) {
                            orderNumber.setText(String.valueOf(pos));
                        }

                    }
                }
            };
            listView.setAdapter(this.adapter);

        }
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.features_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_filter:
                showFilteringPopUpMenu();
                break;
//            case R.id.menu_refresh:
//                mPresenter.loadFeaturedEpisode();
//                break;
        }
        return true;
    }

    @Override
    public void showVocabularies(ArrayList<Vocabulary> vocabularies) {
        loadedDataList = vocabularies;
        adapter.notifyDataSetChanged();
        adapter.refreshListView(vocabularies);
        mNoVocabulariesView.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    private void showNoEpisodeViews(String mainText, int iconRes, boolean showAddView) {
        listView.setVisibility(View.GONE);
        mNoVocabulariesView.setVisibility(View.VISIBLE);

        mNoVocabulariesMainView.setText(mainText);
        mNoVocabulariesIcon.setImageDrawable(getResources().getDrawable(iconRes));
        mNoVocabulariesAddView.setVisibility(showAddView ? View.VISIBLE : View.GONE);
    }


    @Override
    public void showSuccessfullyFavoriteMessage() {
        showMessage("Episode marked favorited");
    }

    @Override
    public void showSuccessfullyUnFavoriteMessage() {
        showMessage("Episode removed favorited");
    }

    @Override
    public void showSuccessfullyDeletedMessage() {
        showMessage("Vocabulary deleted from offline");
    }

    @Override
    public void showVocabularyDetailDialog(Vocabulary vocabulary) {

    }

    @Override
    public void showNoFavoritedEpisode() {
        showNoEpisodeViews("You have no favorited Episode", R.drawable.ic_action_book, true);
    }

    @Override
    public void showNoEpisode() {
        showNoEpisodeViews("You have no Episodes", R.drawable.ic_action_book, true);
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void showFilteringPopUpMenu() {
        PopupMenu popupMenu = new PopupMenu(getContext(), getActivity().findViewById(R.id.menu_filter));
        popupMenu.getMenuInflater().inflate(R.menu.filter_vocabulary, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.filter_new:
                        mPresenter.setFiltering(VocabularyFilterType.NEW_VOCABULARY);
                        break;
                    case R.id.filter_favorited:
                        mPresenter.setFiltering(VocabularyFilterType.FAVORITE_VOCABULARY);
                        break;
                    case R.id.filter_learned:
                        mPresenter.setFiltering(VocabularyFilterType.LEARNED_VOCABULARY);
                        break;
                    case R.id.filter_delete:
                        mPresenter.setFiltering(VocabularyFilterType.IN_TRASH_VOCABULARY);
                        break;
                    default:
                        mPresenter.setFiltering(VocabularyFilterType.ALL_VOCABULARY);
                        break;
                }
                mPresenter.loadVocabularies();
                return true;
            }
        });

        popupMenu.show();
    }
}
