package com.example.cubel.sensoresprueba;

/**
 * Created by cubel on 29/07/16.
 */

import android.app.Activity;
import android.content.Intent;

public class Utils {
    private static int sTheme;
    public final static int THEME_CLARO = 0;
    public final static int THEME_OSCURO = 1;

    /**
     * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
     */
    public static void changeToTheme(Activity activity, int theme) {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    /**
     * Set the theme of the activity, according to the configuration.
     */
    public static void onActivityCreateSetTheme(Activity activity) {
        switch (sTheme) {
            default:
            case THEME_CLARO:
                activity.setTheme(R.style.Soleado);
                break;
            case THEME_OSCURO:
                activity.setTheme(R.style.NoSoleado);
                break;
        }
    }
}