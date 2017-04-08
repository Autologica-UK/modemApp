package com.unicorn.modem;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.crashlytics.android.Crashlytics;
import com.unicorn.modem.util.PreferenceHelper;

import io.fabric.sdk.android.Fabric;

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
        Fabric.with(this, new Crashlytics());
        sInstance = this;
        logUserInfo();
    }

    private void logUserInfo()
    {
        Crashlytics.setUserIdentifier(BuildConfig.HOST);
//        Crashlytics.setUserEmail("user@fabric.io");
        Crashlytics.setUserName("Modem " + PreferenceHelper.getModemNo());
    }
}
