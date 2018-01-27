package com.duckydev.mvpdagger.vocabularies;

import com.duckydev.mvpdagger.R;
import com.duckydev.mvpdagger.data.Vocabulary;
import com.duckydev.mvpdagger.data.source.VocabularyRepository;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by duckyng on 1/26/2018.
 */

public class VocabulariesPresenter implements VocabulariesContract.Presenter {

    VocabulariesContract.View mView;

    private VocabularyFilterType mCurrentFiltering = VocabularyFilterType.ALL_VOCABULARY;

    private final VocabularyRepository mVocabularyRepository;

    @Inject
    public VocabulariesPresenter(VocabularyRepository vocabularyRepository) {
        mVocabularyRepository = vocabularyRepository;
    }

    @Override
    public void takeView(VocabulariesContract.View view) {
        mView = view;
        loadVocabularies();
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void loadVocabularies() {
        if (mView != null) {
            String vocabulary[] = ((VocabularyFragment) mView).getActivity().getResources().getStringArray(R.array.list_vocabulary);
            String definition[] = ((VocabularyFragment) mView).getActivity().getResources().getStringArray(R.array.list_definition);

            ArrayList<Vocabulary> vocabularies = new ArrayList<>();

            for (int i = 0; i < vocabulary.length; i++) {

                vocabularies.add(new Vocabulary(vocabulary[i], definition[i], null, null, false));
            }

            mView.showVocabularies(vocabularies);
        }
    }

    @Override
    public void openVocabulary(Vocabulary requestedVocabulary) {

    }

    @Override
    public void favoriteVocabulary(Vocabulary favoriteVocabulary) {

    }

    @Override
    public void unFavoriteVocabulary(Vocabulary unFavoriteVocabulary) {

    }

    @Override
    public VocabularyFilterType getFiltering() {
        return null;
    }

    @Override
    public void setFiltering(VocabularyFilterType requestType) {

    }
}
