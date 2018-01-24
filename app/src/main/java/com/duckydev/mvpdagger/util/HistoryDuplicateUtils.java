package com.duckydev.mvpdagger.util;

/**
 * Created by duckyng on 1/24/2018.
 */

class HistoryDuplicateUtils {
    static int isConversationHistoryIdExist(int[] history, int conversationID) {

        for (int i = 0 ; i < history.length; i++) {
            if (history[i] == conversationID) {
                return i;
            }
        }

        return -1;
    }
}
