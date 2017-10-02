package com.unicorn.modem.service;

import android.app.IntentService;
import android.content.Intent;
import com.unicorn.modem.service.impl.SmsServiceImpl;

public class SmsRecieverService extends IntentService {

  public SmsRecieverService() {
    super("SmsRecieverIntentService");
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    SmsServiceImpl smsService = new SmsServiceImpl(this);
    smsService.getSmsList();
  }
}
