package com.duckydev.mvpdagger.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.duckydev.mvpdagger.data.Episode;


@Database(entities = {Episode.class}, version = 1)
public abstract class EpisodeDatabase extends RoomDatabase {

    public abstract EpisodeDao episodeDao();


}
