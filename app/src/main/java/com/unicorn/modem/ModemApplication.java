package com.unicorn.modem;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Arashmidos on 2017-03-31.
 */
public class ModemApplication extends Application
{
    public static ModemApplication sInstance;
    public static SharedPreferences sPreference;

    public static ModemApplication getInstance()
    {
        return sInstance;
    }

    public static synchronized SharedPreferences getPreference()
    {
        if (sPreference == null)
            sPreference = PreferenceManager.getDefaultSharedPreferences(
                    sInstance.getApplicationContext());

        return sPreference;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        sInstance = this;
    }
}
