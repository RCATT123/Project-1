package com.specifix.pureleagues.manager;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.specifix.pureleagues.App;
import com.specifix.pureleagues.api.UserManager;

public class PrefsManager {
    private static final String PREFS_PREFIX = "unread_";

    private SharedPreferences preferences;

    public PrefsManager() {
        this(String.valueOf(UserManager.getInstance().getCurrentTeamId()));
    }

    public PrefsManager(String teamId) {
        preferences = App.getInstance().getSharedPreferences(PREFS_PREFIX + teamId, Context.MODE_PRIVATE);
    }

    public void clearPrefs() {
        preferences.edit()
                .clear()
                .apply();
    }

    public void setLastMessageId(String eventId, String messageId) {
        preferences.edit()
                .putString(eventId, messageId)
                .apply();
    }

    public String getLastMessageId(String eventId) {
        return preferences.getString(eventId, null);
    }
}
