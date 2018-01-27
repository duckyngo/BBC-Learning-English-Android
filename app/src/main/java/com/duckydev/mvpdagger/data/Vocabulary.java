package com.duckydev.mvpdagger.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.duckydev.mvpdagger.util.VocabularyType;

import java.util.UUID;

/**
 * Created by duckyng on 1/26/2018.
 */

@Entity(tableName = "Vocabulary")
public class Vocabulary {

    @PrimaryKey
    @NonNull
    private String mId;

    private String mVocabulary;

    private String mDefinition;

    private String mPronunciationText;

    private String mPronunciationAudioUrl;

    @TypeConverters(VocabularyType.class)
    private VocabularyType mVocabularyType = VocabularyType.NEW_VOCABULARY;

    private boolean isFavorite = false;

    public Vocabulary(String vocabulary, String definition, String pronunciationText, String pronunciationAudioUrl,
                      boolean isFavorite) {
        mId = UUID.randomUUID().toString();

        mVocabulary = vocabulary;
        mDefinition = definition;
        mPronunciationText = pronunciationText;
        mPronunciationAudioUrl = pronunciationAudioUrl;
        this.isFavorite = isFavorite;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getVocabulary() {
        return mVocabulary;
    }

    public void setVocabulary(String vocabulary) {
        mVocabulary = vocabulary;
    }

    public String getDefinition() {
        return mDefinition;
    }

    public void setDefinition(String definition) {
        mDefinition = definition;
    }

    public String getPronunciationText() {
        return mPronunciationText;
    }

    public void setPronunciationText(String pronunciationText) {
        mPronunciationText = pronunciationText;
    }

    public String getPronunciationAudioUrl() {
        return mPronunciationAudioUrl;
    }

    public void setPronunciationAudioUrl(String pronunciationAudioUrl) {
        mPronunciationAudioUrl = pronunciationAudioUrl;
    }

    public VocabularyType getVocabularyType() {
        return mVocabularyType;
    }

    public void setVocabularyType(VocabularyType vocabularyType) {
        mVocabularyType = vocabularyType;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
