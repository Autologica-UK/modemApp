package com.unicorn.modem.service;

import com.unicorn.modem.model.response.SmsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Arash on 2017-03-31.
 */

public interface SmsService
{
    @GET("/utdsweb/smsoutboundandj.jsp")
    Call<SmsResponse> getSmsList(@Query("biz") int bizId, @Query("mdm") int modemNo);
}
