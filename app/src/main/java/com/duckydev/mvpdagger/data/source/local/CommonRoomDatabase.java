package com.duckydev.mvpdagger.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.duckydev.mvpdagger.data.Episode;
import com.duckydev.mvpdagger.data.Vocabulary;


@Database(entities = {Episode.class, Vocabulary.class}, version = 1)
public abstract class CommonRoomDatabase extends RoomDatabase {

    public abstract EpisodeDao episodeDao();


    public abstract VocabularyDao vocabularyDao();

}
