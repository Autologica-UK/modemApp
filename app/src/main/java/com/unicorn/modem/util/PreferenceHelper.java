package com.unicorn.modem.util;

import com.unicorn.modem.ModemApplication;

public class PreferenceHelper
{
    public static final String MODEM_NO = "MODEM_NO";
    public static final String UPDATE_INTERVAL = "UPDATE_INTERVAL";
    private static final String BIZ_ID = "BIZ_ID";

    public static int getModemNo()
    {
        return ModemApplication.getPreference().getInt(MODEM_NO, 1);
    }

    public static void setModemNo(int modemNo)
    {
        ModemApplication.getPreference().edit().putInt(MODEM_NO, modemNo).apply();
    }

    public static int getBizId()
    {
        return ModemApplication.getPreference().getInt(BIZ_ID, 1);
    }

    public static void setBizId(int bizId)
    {
        ModemApplication.getPreference().edit().putInt(BIZ_ID, bizId).apply();
    }

    public static void setUpdateInterval(int interval)
    {
        ModemApplication.getPreference().edit().putInt(UPDATE_INTERVAL, interval).apply();
    }

    public static int getUpdateInterval()
    {
        return ModemApplication.getPreference().getInt(UPDATE_INTERVAL, 60);
    }

}
