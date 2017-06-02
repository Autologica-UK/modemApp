package com.unicorn.modem.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author Arashmidos 2017-04-03
 */
public class BootReceiver extends BroadcastReceiver
{

    TrackerAlarmReceiver alarm = new TrackerAlarmReceiver();

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            alarm.setAlarm(context);
        }
    }
}