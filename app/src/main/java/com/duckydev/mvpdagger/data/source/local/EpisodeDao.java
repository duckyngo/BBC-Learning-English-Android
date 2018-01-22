package com.duckydev.mvpdagger.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import com.duckydev.mvpdagger.data.Episode;
import com.duckydev.mvpdagger.util.EpisodeType;

import java.util.List;

@Dao
public interface EpisodeDao {

    @Query("SELECT * FROM Episode")
    List<Episode> getEpisodes();

    @TypeConverters(EpisodeType.class)
    @Query("SELECT * FROM Episode WHERE mType = :type ORDER BY mTypeIndex DESC")
    List<Episode> getEpisodesByType(EpisodeType type);

    @Query("SELECT * FROM Episode WHERE isPlayed = :isPlayed ORDER BY mTypeIndex DESC")
    List<Episode> getEpisodesByPlayState(boolean isPlayed);

    @TypeConverters(EpisodeType.class)
    @Query("SELECT * FROM Episode WHERE mType = :type AND isPlayed = :isPlayed ORDER BY mTypeIndex DESC")
    List<Episode> getEpisodesByTypeAndPlayState(EpisodeType type, boolean isPlayed);

    @Query("SELECT * FROM Episode WHERE isDownloaded = :isDownloaded ORDER BY mTypeIndex DESC")
    List<Episode> getEpisodesByDownloadState(boolean isDownloaded);

    @Query("SELECT * FROM Episode WHERE isFavorite = :isFavorite")
    List<Episode> getFavoriteEpisodes(boolean isFavorite);

    @Query("SELECT * FROM Episode WHERE isAddedWatchList = :isAddedWatchList")
    List<Episode> getWatchedListEpisodes(boolean isAddedWatchList);

    @TypeConverters(EpisodeType.class)
    @Query("SELECT * FROM Episode WHERE mType = :type  ORDER BY mTypeIndex DESC LIMIT 5")
    List<Episode> getFirstFiveEpisodesEachType(EpisodeType type);

    @Query("SELECT * FROM Episode WHERE _id = :id")
    Episode getEpisodeById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEpisode(Episode episode);

    @Update
    void updateEpisode(Episode episode);

    @Query("UPDATE Episode SET isPlayed = :played WHERE _id = :id")
    void updatePlayed(int id, boolean played);

    @Query("UPDATE Episode SET isDownloaded = :downloaded WHERE _id = :id")
    void updateDownloaded(int id, boolean downloaded);

    @Query("UPDATE Episode SET mDownloadedMediaUrl = :downloadedMediaUrl WHERE _id = :id")
    void updateDownloadedMediaUrl(int id, String downloadedMediaUrl);

    @Query("UPDATE Episode SET isFavorite = :isFavorite WHERE _id = :id")
    void updateFavorite(int id, boolean isFavorite);

    @Query("UPDATE Episode SET isAddedWatchList = :isAddedWatchList WHERE _id = :id")
    void updateWatchList(int id, boolean isAddedWatchList);
}
