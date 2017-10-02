package com.unicorn.modem.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import com.unicorn.modem.model.db.Sms;
import com.unicorn.modem.model.db.SmsStatus;
import com.unicorn.modem.model.db.dao.SMSDaoImpl;
import com.unicorn.modem.model.request.SmsDto;
import com.unicorn.modem.service.impl.SmsServiceImpl;

/**
 * Created by arash on 9/22/17.
 */

public class SmsBroadcastReceiver extends BroadcastReceiver {

  public static final String SMS_BUNDLE = "pdus";
  final SmsManager manager = SmsManager.getDefault();

  public void onReceive(Context context, Intent intent) {

    final Bundle bundle = intent.getExtras();

    try {
      if (bundle != null) {

        final Object[] pdusObj = (Object[]) bundle.get("pdus");

        for (int i = 0; i < pdusObj.length; i++) {

          SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
          String phoneNumber = currentMessage.getDisplayOriginatingAddress();
          String message = currentMessage.getDisplayMessageBody();

          Log.i("SmsReceiver", "senderNum: " + phoneNumber + "; message: " + message);

          Sms temp = new Sms(0L, 0, phoneNumber, message);
          temp.setStatus(SmsStatus.RECEIVED.getValue());
          long id = new SMSDaoImpl().create(temp);

          SmsDto smsDto = new SmsDto(id, phoneNumber, message);
          new SmsServiceImpl(context).sendSms(smsDto);
        }
      }
    } catch (Exception e) {
      Log.e("SmsReceiver", "Exception smsReceiver" + e);
    }
  }
}

