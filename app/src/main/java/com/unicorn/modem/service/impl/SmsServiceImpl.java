package com.unicorn.modem.service.impl;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import com.unicorn.modem.model.db.Sms;
import com.unicorn.modem.model.db.SmsStatus;
import com.unicorn.modem.model.db.dao.SMSDaoImpl;
import com.unicorn.modem.model.event.Event;
import com.unicorn.modem.model.event.UpdateEvent;
import com.unicorn.modem.model.request.SmsDto;
import com.unicorn.modem.model.response.BizData;
import com.unicorn.modem.model.response.SMSMessage;
import com.unicorn.modem.model.response.SmsResponse;
import com.unicorn.modem.service.ServiceGenerator;
import com.unicorn.modem.service.SmsSenderIntentService;
import com.unicorn.modem.service.SmsService;
import com.unicorn.modem.util.DateConverter;
import com.unicorn.modem.util.PreferenceHelper;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Arashmidos on 2017-03-31.
 */

public class SmsServiceImpl {

  private static final String TAG = SmsServiceImpl.class.getSimpleName();
  private final Context context;

  public SmsServiceImpl(Context context) {
    this.context = context;
  }

  public void getSmsList() {
    Log.d(TAG, DateConverter.getCurrentDate());
    SmsService userService = null;
    try {
      userService = ServiceGenerator.createService(SmsService.class,true);
      if (userService == null) {
        Toast.makeText(context, "Server not found!", Toast.LENGTH_LONG).show();
        return;
      }
    } catch (Exception ex) {
      Toast.makeText(context, "Invalid URL!", Toast.LENGTH_LONG).show();
      return;
    }
    Call<SmsResponse> call = userService
        .getSmsList(PreferenceHelper.getBizId(), PreferenceHelper.getModemNo());

    call.enqueue(new Callback<SmsResponse>() {
      @Override
      public void onResponse(Call<SmsResponse> call, Response<SmsResponse> response) {
        if (response.isSuccessful()) {
          SmsResponse smsResponse = response.body();
          List<SMSMessage> msgList = smsResponse.getSMSMessages();
          SMSDaoImpl smsDao = new SMSDaoImpl();
          for (int i = 0; i < msgList.size(); i++) {
            SMSMessage smsMessage = msgList.get(i);
            if (smsDao.contains(smsMessage.getMsgid())) {
              continue;
            }
            Sms temp = new Sms(Long.valueOf(smsMessage.getMsgid())
                , Integer.valueOf(smsMessage.getPriority())
                , smsMessage.getRecno(), smsMessage.getMsg());
            temp.setStatus(SmsStatus.PENDING.getValue());
            long id = smsDao.create(temp);
            Log.d(TAG, String.format("Msg id %s inserted with id %s", temp.getMsgId(), id));
          }

          EventBus.getDefault().post(new UpdateEvent());

          context.startService(new Intent(context, SmsSenderIntentService.class));
        }
      }

      @Override
      public void onFailure(Call<SmsResponse> call, Throwable t) {
        Log.d(TAG, "Server call failed");
      }
    });
  }

  public void sendSms(final SmsDto smsDto) {

    SmsService userService = null;
    try {
      userService = ServiceGenerator.createService(SmsService.class,true);
      if (userService == null) {
        Toast.makeText(context, "Server not found!", Toast.LENGTH_LONG).show();
        return;
      }
    } catch (Exception ex) {
      Toast.makeText(context, "Invalid URL!", Toast.LENGTH_LONG).show();
      return;
    }
    Call<SmsResponse> call = userService.sendSms(smsDto);

    call.enqueue(new Callback<SmsResponse>() {
      @Override
      public void onResponse(Call<SmsResponse> call, Response<SmsResponse> response) {
        if (response.isSuccessful()) {

          SMSDaoImpl smsDao = new SMSDaoImpl();
          Sms temp = smsDao.retrieve(smsDto.getId());
          temp.setStatus(SmsStatus.RECEIVED_SERVER.getValue());

          smsDao.update(temp);
          Log.d(TAG, "Msg sent to server successfully" + smsDto.toString());

          EventBus.getDefault().post(new UpdateEvent());
        }
      }

      @Override
      public void onFailure(Call<SmsResponse> call, Throwable t) {
        Log.d(TAG, "Server call failed");
      }
    });
  }

  public void getData(String passwordValue) {

    SmsService userService = null;
    try {
      userService = ServiceGenerator.createService(SmsService.class,false);
      if (userService == null) {
        Toast.makeText(context, "Server not found!", Toast.LENGTH_LONG).show();
        EventBus.getDefault().post(new Event(400));
        return;
      }
    } catch (Exception ex) {
      Toast.makeText(context, "Invalid URL!", Toast.LENGTH_LONG).show();
      EventBus.getDefault().post(new Event(400));
      return;
    }
    Call<BizData> call = userService
        .getBizData("http://www.autologicasystems.com/xmlmobilespec.asp?mypass="+passwordValue);

    call.enqueue(new Callback<BizData>() {
      @Override
      public void onResponse(Call<BizData> call, Response<BizData> response) {
        if (response.isSuccessful()) {

          BizData bizData = response.body();

          PreferenceHelper.setServerUrl(bizData.getBizInfo().getUrl());
          PreferenceHelper.setBizId(bizData.getBizInfo().getBizId());
          EventBus.getDefault().post(new Event(200));
        }
      }

      @Override
      public void onFailure(Call<BizData> call, Throwable t) {
        Log.d(TAG, "Server call failed");
        EventBus.getDefault().post(new Event(500));
      }
    });
  }
}
