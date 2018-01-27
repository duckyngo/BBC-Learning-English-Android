package com.duckydev.mvpdagger.util;

import android.arch.persistence.room.TypeConverter;
import android.content.Context;

import com.duckydev.mvpdagger.R;

/**
 * Created by duckyng on 12/20/2017.
 */

public enum EpisodeType {
    DRAMA(0), ENGLISH_AT_UNIVERSITY(1), ENGLISH_AT_WORK(2), ENGLISH_WE_SPEAK(3), LINGOHACK(4),

    NEWS_REPORT(5), PRONUNCIATION(6), SIX_MINUTE_ENGLISH(7), WORK_IN_THE_NEWS(8),

    EXPRESS_ENGLISH(9), SIX_MINUTE_GRAMMAR(10), SIX_MINUTE_VOCABULARY(11),

    RECENT_AUDIOS(12), FAVORITES(13), DOWNLOADS(14);

    private final int primitive_type;

    @TypeConverter
    public static EpisodeType fromPrimetiveType(Integer primitive_type) {
        for (EpisodeType episodeType : values()) {
            if (episodeType.primitive_type == primitive_type) {
                return (episodeType);
            }
        }

        return null;
    }

    @TypeConverter
    public static Integer fromEpisodeType(EpisodeType type) {
        return type.primitive_type;
    }

    EpisodeType(int type) {
        this.primitive_type = type;
    }

    public static String getEpisodeTypeText(Context context, EpisodeType type) {
        return context.getResources().getStringArray(R.array.list_features)[fromEpisodeType(type)];
    }
}
