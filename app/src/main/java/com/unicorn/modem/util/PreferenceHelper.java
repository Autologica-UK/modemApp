package com.unicorn.modem.util;

import com.unicorn.modem.ModemApplication;

public class PreferenceHelper {

  public static final String SERVER_URL = "SERVER_URL";
  private static final String MODEM_NO = "MODEM_NO";
  private static final String UPDATE_INTERVAL = "UPDATE_INTERVAL";
  private static final String BIZ_ID = "BIZ_ID";
  private static final String BIZ_PORT = "BIZ_PORT";

  public static int getModemNo() {
    return ModemApplication.getPreference().getInt(MODEM_NO, 1);
  }

  public static void setModemNo(int modemNo) {
    ModemApplication.getPreference().edit().putInt(MODEM_NO, modemNo).apply();
  }

  public static int getBizId() {
    return ModemApplication.getPreference().getInt(BIZ_ID, 1);
  }

  public static void setBizId(int bizId) {
    ModemApplication.getPreference().edit().putInt(BIZ_ID, bizId).apply();
  }

  public static int getUpdateInterval() {
    return ModemApplication.getPreference().getInt(UPDATE_INTERVAL, 60);
  }

  public static void setUpdateInterval(int interval) {
    ModemApplication.getPreference().edit().putInt(UPDATE_INTERVAL, interval).apply();
  }

  public static String getServerUrl() {
    return ModemApplication.getPreference().getString(SERVER_URL, "");
  }

  public static void setServerUrl(String url) {
    ModemApplication.getPreference().edit().putString(SERVER_URL, url).apply();
  }

  public static String getBizPort() {
    return ModemApplication.getPreference().getString(BIZ_PORT, "80");
  }

  public static void setBizPort(String port) {
    ModemApplication.getPreference().edit().putString(BIZ_PORT, port).apply();
  }
}
