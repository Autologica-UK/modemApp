package com.unicorn.modem.service.impl;

import android.util.Log;

import com.unicorn.modem.model.db.Sms;
import com.unicorn.modem.model.db.dao.SMSDaoImpl;
import com.unicorn.modem.model.response.SMSMessage;
import com.unicorn.modem.model.response.SmsResponse;
import com.unicorn.modem.service.ServiceGenerator;
import com.unicorn.modem.service.SmsService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Arashmidos on 2017-03-31.
 */

public class SmsServiceImpl
{
    private static final String TAG = SmsServiceImpl.class.getSimpleName();

    public void getSmsList()
    {
        SmsService userService = ServiceGenerator.createService(SmsService.class);
        Call<SmsResponse> call = userService.getSmsList(1, 1);

        call.enqueue(new Callback<SmsResponse>()
        {
            @Override
            public void onResponse(Call<SmsResponse> call, Response<SmsResponse> response)
            {
                if (response.isSuccessful())
                {
                    SmsResponse smsResponse = response.body();
                    List<SMSMessage> msgList = smsResponse.getSMSMessages();
                    SMSDaoImpl smsDao = new SMSDaoImpl();
                    for (int i = 0; i < msgList.size(); i++)
                    {
                        SMSMessage smsMessage = msgList.get(i);
                        Sms temp = new Sms(Long.valueOf(smsMessage.getMsgid())
                                , Integer.valueOf(smsMessage.getPriority())
                                , smsMessage.getRecno(), smsMessage.getMsg());
                        long id = smsDao.create(temp);
                        Log.d(TAG, String.format("Msg id %s inserted with id %s", temp.getMsgId(), id));
                    }
                }
            }

            @Override
            public void onFailure(Call<SmsResponse> call, Throwable t)
            {
            }
        });
    }
}
