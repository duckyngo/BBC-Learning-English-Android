package com.duckydev.mvpdagger.util;

import android.arch.persistence.room.TypeConverter;

/**
 * Created by duckyng on 1/26/2018.
 */

public enum  VocabularyType {

    NEW_VOCABULARY(0),

    LEARNED_VOCABULARY(1),

    DELETED_VOCABULARY(2);

    private final int primitive_type;

    VocabularyType(int primitive_type) {
        this.primitive_type = primitive_type;
    }

    @TypeConverter
    public static VocabularyType fromPrimetiveType(Integer primitive_type) {
        for (VocabularyType vocabularyType : values()) {
            if (vocabularyType.primitive_type == primitive_type) {
                return vocabularyType;
            }
        }
        return null;
    }

    @TypeConverter
    public static Integer fromVocabularyType(VocabularyType vocabularyType) {
        return vocabularyType.primitive_type;
    }

}
