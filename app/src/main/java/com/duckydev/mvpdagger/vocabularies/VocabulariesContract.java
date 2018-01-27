package com.duckydev.mvpdagger.vocabularies;

import com.duckydev.mvpdagger.BasePresenter;
import com.duckydev.mvpdagger.BaseView;
import com.duckydev.mvpdagger.data.Vocabulary;

import java.util.ArrayList;

/**
 * Created by duckyng on 1/26/2018.
 */

public interface VocabulariesContract {

    interface View extends BaseView<Presenter> {

        void showVocabularies(ArrayList<Vocabulary> vocabularies);

        void showSuccessfullyFavoriteMessage();

        void showSuccessfullyUnFavoriteMessage();

        void showSuccessfullyDeletedMessage();

        void showVocabularyDetailDialog(Vocabulary vocabulary);

        void showNoFavoritedEpisode();

        void showNoEpisode();

        boolean isActive();

        void showFilteringPopUpMenu();

    }


    interface Presenter extends BasePresenter<View> {

        void loadVocabularies();

        void openVocabulary(Vocabulary requestedVocabulary);

        void favoriteVocabulary(Vocabulary favoriteVocabulary);

        void unFavoriteVocabulary(Vocabulary unFavoriteVocabulary);

        VocabularyFilterType getFiltering();

        void setFiltering(VocabularyFilterType requestType);



    }
}

