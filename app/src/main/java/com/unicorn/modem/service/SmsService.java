package com.unicorn.modem.service;

import com.unicorn.modem.model.request.SmsDto;
import com.unicorn.modem.model.response.BizData;
import com.unicorn.modem.model.response.SmsResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Arash on 2017-03-31.
 */

public interface SmsService {

  //  @GET("/utdsweb/smsoutboundandj.jsp")
  @GET()
  Call<SmsResponse> getSmsList(@Url String url, @Query("biz") int bizId, @Query("mdm") int modemNo);

  @POST("/utds/pda/receiveSMS")
  Call<SmsResponse> sendSms(@Body SmsDto smsDto);

  @GET()
  Call<BizData> getBizData(@Url String url);
}
