package com.duckydev.mvpdagger.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.duckydev.mvpdagger.util.EpisodeType;

@Entity(tableName = "Episode")
public class Episode {

    @PrimaryKey
    private int _id;

    private int mTypeIndex;

    @TypeConverters(EpisodeType.class)
    private EpisodeType mType;

    private String mThumbImageUrl;

    private String mTitle;

    private String mEpisodeDate;

    private String mDescription;

    private String mMediaUrl;

    private String mMediaDuration;

    private String mSummary;

    private String mTranscript;

    private String mTodaysHeadline;

    private String mVocabulary;

    private String mExercises;

    private String mAnswers;

    private String mDownloadedMediaUrl;

    private String mDownloadedThumbImageUrl;

    private boolean isPlayed = false;

    private boolean isDownloaded = false;

    private boolean isFavorite = false;

    private boolean isAddedWatchList = false;

    public Episode(int _id, int typeIndex, EpisodeType type, String thumbImageUrl, String title, String episodeDate, String description, String mediaUrl,

                   String mediaDuration, String summary, String transcript, String todaysHeadline, String vocabulary, String exercises, String answers,
                   String downloadedMediaUrl, String downloadedThumbImageUrl, boolean isPlayed, boolean isDownloaded, boolean isFavorite, boolean isAddedWatchList) {
        this._id = _id;
        mTypeIndex = typeIndex;
        mType = type;
        mThumbImageUrl = thumbImageUrl;
        mTitle = title;
        mEpisodeDate = episodeDate;
        mDescription = description;
        mMediaUrl = mediaUrl;
        mMediaDuration = mediaDuration;
        mSummary = summary;
        mTranscript = transcript;
        mTodaysHeadline = todaysHeadline;
        mVocabulary = vocabulary;
        mExercises = exercises;
        mAnswers = answers;
        mDownloadedMediaUrl = downloadedMediaUrl;
        mDownloadedThumbImageUrl = downloadedThumbImageUrl;
        this.isPlayed = isPlayed;
        this.isDownloaded = isDownloaded;
        this.isFavorite = isFavorite;
        this.isAddedWatchList = isAddedWatchList;
    }

    public int get_id() {
        return _id;
    }

    public EpisodeType getType() {
        return mType;
    }

    public String getThumbImageUrl() {
        return mThumbImageUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getEpisodeDate() {
        return mEpisodeDate;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getMediaUrl() {
        return mMediaUrl;
    }

    public String getSummary() {
        return mSummary;
    }

    public String getTranscript() {
        return mTranscript;
    }

    public String getTodaysHeadline() {
        return mTodaysHeadline;
    }

    public String getVocabulary() {
        return mVocabulary;
    }

    public String getExercises() {
        return mExercises;
    }

    public String getAnswers() {
        return mAnswers;
    }

    public boolean isPlayed() {
        return isPlayed;
    }

    public boolean isDownloaded() {
        return isDownloaded;
    }

    public void setPlayed(boolean played) {
        isPlayed = played;
    }

    public void setDownloaded(boolean downloaded) {
        isDownloaded = downloaded;
    }

    public String getDownloadedMediaUrl() {
        return mDownloadedMediaUrl;
    }

    public void setDownloadedMediaUrl(String downloadedMediaUrl) {
        mDownloadedMediaUrl = downloadedMediaUrl;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public int getTypeIndex() {
        return mTypeIndex;
    }

    public void setTypeIndex(int typeIndex) {
        mTypeIndex = typeIndex;
    }

    public String getMediaDuration() {
        return mMediaDuration;
    }

    public void setMediaDuration(String mediaDuration) {
        mMediaDuration = mediaDuration;
    }

    public boolean isAddedWatchList() {
        return isAddedWatchList;
    }

    public void setAddedWatchList(boolean addedWatchList) {
        isAddedWatchList = addedWatchList;
    }

    public String getDownloadedThumbImageUrl() {
        return mDownloadedThumbImageUrl;
    }

    public void setDownloadedThumbImageUrl(String downloadedThumbImageUrl) {
        mDownloadedThumbImageUrl = downloadedThumbImageUrl;
    }
}
