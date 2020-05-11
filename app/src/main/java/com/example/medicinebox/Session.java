package com.example.medicinebox;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {
    static final private String PREF_USER_ID = "id";

    static SharedPreferences getSharedPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setUserData(Context context, String id) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(PREF_USER_ID, id);
        editor.commit();
    }

    public static String getUserData(Context context) {
        return getSharedPreference(context).getString(PREF_USER_ID, "");
    }

    public static void clearUserData(Context context) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.clear();
        editor.commit();
    }
}
