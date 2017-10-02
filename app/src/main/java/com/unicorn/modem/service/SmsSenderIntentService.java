package com.unicorn.modem.service;

import static com.unicorn.modem.util.Constant.ACTION_SMS_DELIVERED;
import static com.unicorn.modem.util.Constant.ACTION_SMS_SENT;

import android.Manifest;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v13.app.ActivityCompat;
import android.telephony.SmsManager;
import android.widget.Toast;
import com.unicorn.modem.model.db.Sms;
import com.unicorn.modem.model.db.SmsStatus;
import com.unicorn.modem.model.db.dao.SMSDaoImpl;
import com.unicorn.modem.model.event.UpdateEvent;
import com.unicorn.modem.util.Constant;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class SmsSenderIntentService extends IntentService {

  public SMSDaoImpl smsDao = new SMSDaoImpl();

  public SmsSenderIntentService() {
    super("SmsSenderIntentService");
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    List<Sms> smsList = smsDao.retrieveAllByStatus(SmsStatus.PENDING);
    for (int i = 0; i < smsList.size(); i++) {
      Sms sms = smsList.get(i);
      sendMsg(sms);
    }
    EventBus.getDefault().post(new UpdateEvent());
  }

  public void sendMsg(Sms sms) {
    String msg = sms.getMsg();
    String to = sms.getRecordNo();
    SmsManager smsManager = SmsManager.getDefault();

    ArrayList<String> messages = smsManager.divideMessage(msg);

    try {
      Intent sentIntent = new Intent(ACTION_SMS_SENT);
      sentIntent.putExtra(Constant.SMS_ID, sms.getId().longValue());
      Intent deliveryIntent = new Intent(ACTION_SMS_DELIVERED);
      deliveryIntent.putExtra(Constant.SMS_ID, sms.getId().longValue());

      int numParts = messages.size();

      ArrayList<PendingIntent> sentIntents = new ArrayList<>();
      ArrayList<PendingIntent> deliveryIntents = new ArrayList<>();

      for (int i = 0; i < numParts; i++) {
        sentIntents.add(PendingIntent.getBroadcast(this, 0, sentIntent, 0));
        deliveryIntents.add(PendingIntent.getBroadcast(this, 0, deliveryIntent, 0));
      }

      smsManager.sendMultipartTextMessage(to, null, messages, sentIntents, deliveryIntents);
      sms.setStatus(SmsStatus.SENT.getValue());
      smsDao.update(sms);

    } catch (SecurityException ex) {
      Toast.makeText(this, "SEND_SMS permission denied", Toast.LENGTH_LONG).show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
