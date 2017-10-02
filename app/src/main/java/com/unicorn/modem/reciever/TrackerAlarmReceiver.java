package com.unicorn.modem.reciever;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import com.unicorn.modem.service.SmsRecieverService;
import com.unicorn.modem.util.PreferenceHelper;

/**
 * Created by Arash on 2017-04-03
 */
public class TrackerAlarmReceiver extends WakefulBroadcastReceiver {

  public static final String TAG = TrackerAlarmReceiver.class.getSimpleName();
  private AlarmManager alarmMgr;
  private PendingIntent alarmIntent;

  @Override
  public void onReceive(Context context, Intent intent) {
    Log.i(TAG, "Received");

    Intent recieverService = new Intent(context, SmsRecieverService.class);
//        Intent senderService = new Intent(context, SmsSenderIntentService.class);
    startWakefulService(context, recieverService);
//        startWakefulService(context, senderService);
  }

  public void setAlarm(Context context) {
    alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    Intent intent = new Intent(context, TrackerAlarmReceiver.class);
    alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

    int timeInterval = PreferenceHelper.getUpdateInterval();
    alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(),
        timeInterval * 1000, alarmIntent);

    //TODO if android api > 19 put exact
    ComponentName receiver = new ComponentName(context, TrackerAlarmReceiver.class);
    PackageManager pm = context.getPackageManager();

    pm.setComponentEnabledSetting(receiver,
        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
        PackageManager.DONT_KILL_APP);
  }

  public void cancelAlarm(Context context) {
    try {
      if (alarmMgr != null) {
        alarmMgr.cancel(alarmIntent);
      }

      ComponentName receiver = new ComponentName(context, TrackerAlarmReceiver.class);
      PackageManager pm = context.getPackageManager();

      pm.setComponentEnabledSetting(receiver,
          PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
          PackageManager.DONT_KILL_APP);
    } catch (Exception ex) {
      Log.e(TAG, "error in canceling alarm", ex);
    }
  }
}
