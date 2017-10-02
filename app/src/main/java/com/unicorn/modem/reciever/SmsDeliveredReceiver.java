package com.unicorn.modem.reciever;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import com.unicorn.modem.model.db.Sms;
import com.unicorn.modem.model.db.SmsStatus;
import com.unicorn.modem.model.db.dao.SMSDaoImpl;
import com.unicorn.modem.model.event.UpdateEvent;
import com.unicorn.modem.util.Constant;
import com.unicorn.modem.util.DateConverter;
import org.greenrobot.eventbus.EventBus;

public class SmsDeliveredReceiver extends BroadcastReceiver {

  public static final String TAG = SmsDeliveredReceiver.class.getSimpleName();
  private SMSDaoImpl smsDao = new SMSDaoImpl();

  @Override
  public void onReceive(Context context, Intent intent) {
    String message = null;
    boolean error = true;
    switch (getResultCode()) {
      case Activity.RESULT_OK:
        message = "Message sent!";
        error = false;
        break;
      case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
        message = "Error.";
        break;
      case SmsManager.RESULT_ERROR_NO_SERVICE:
        message = "Error: No service.";
        break;
      case SmsManager.RESULT_ERROR_NULL_PDU:
        message = "Error: Null PDU.";
        break;
      case SmsManager.RESULT_ERROR_RADIO_OFF:
        message = "Error: Radio off.";
        break;
    }

    long msgId = -1;
    if (intent != null) {
      msgId = intent.getLongExtra(Constant.SMS_ID, -1);
      if (msgId != -1) {
        Sms sms = smsDao.retrieve(Long.valueOf(msgId));
        sms.setStatus(error ? SmsStatus.FAILED.getValue() : SmsStatus.DELIVERED.getValue());
        sms.setUpdateDateTime(DateConverter.getCurrentDate());
        smsDao.update(sms);
      }
    }
    EventBus.getDefault().post(new UpdateEvent());
    Log.d(TAG, "Msg delivery report :" + message);
  }
}
