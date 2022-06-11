package com.pavlovic.cubes.events24.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {

    private final String EMAIL = "prefs_email",LANGUAGE = "prefs_language",NOTIFICATION = "prefs_notification";

    private static SharedPrefs instance;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    private SharedPrefs(Activity activity){
        preferences = activity.getPreferences(Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static SharedPrefs getInstance(Activity activity){

        if(instance == null){
            instance = new SharedPrefs(activity);
        }

        return instance;
    }

    public void saveEmail(String email){
        editor.putString(EMAIL,email);
        editor.apply();
    }

    public String getEmail(){
        return preferences.getString(EMAIL,"");
    }

    public void saveAppLanguage(int languageIndex){
        editor.putInt(LANGUAGE,languageIndex);
        editor.apply();
    }

    public int getAppLanguage(){
       return preferences.getInt(LANGUAGE,0);
    }

    public String getAppLanguageString(){
        if (getAppLanguage() == 0){
            return "en";
        }
        else {
            return "sr";
        }
    }

    public boolean isNotificationOn(){
        return preferences.getBoolean(NOTIFICATION,true);
    }

    public void setNotificationStatus(boolean inOn){
        editor.putBoolean(NOTIFICATION,inOn);
        editor.apply();
    }
}
