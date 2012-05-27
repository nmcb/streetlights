/*
**Created By: MBlokhuijzen
**Date: May 26, 2012, 3:59:49 PM
*/

package nl.loxia.streetlights.fragments;

import android.app.Activity;
import android.content.SharedPreferences;

public class Settings {
    public static final String PREFS_NAME = "MyPreferences";
    
    public static void saveSetting(Activity activity, String key, String value) {
        SharedPreferences settings = activity.getSharedPreferences(Settings.PREFS_NAME, 0);
        
        SharedPreferences.Editor prefsEditor = settings.edit();
        
        prefsEditor.putString(key, value);
            
        prefsEditor.commit();
    }
    
    public static String getSetting(Activity activity, String key, String defaultValue) {
        SharedPreferences settings = activity.getSharedPreferences(Settings.PREFS_NAME, 0);
        String result;
        
        result = settings.getString(key, defaultValue);
        
        return result;
    }
    
    public static void clearSettings(Activity activity) {
        SharedPreferences settings = activity.getSharedPreferences(Settings.PREFS_NAME, 0);
        SharedPreferences.Editor prefsEditor = settings.edit();
        
        prefsEditor.clear();
        prefsEditor.commit();
        
    }
}
