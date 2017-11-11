package com.unicorn.modem.model.request;

import com.unicorn.modem.util.PreferenceHelper;

/**
 * Created by arash on 9/23/17.
 */

public class SmsDto {

  private Long id;
  private String mobileNumber;
  private String smsText;
  private Integer businessId;

  public SmsDto() {
  }

  public SmsDto(long id, String mobileNumber, String smsText) {
    this.id = id;
    this.mobileNumber = mobileNumber;
    this.smsText = smsText;
    this.businessId = PreferenceHelper.getBizId();
  }

  @Override
  public String toString() {
    return "SmsDto{" +
        "id=" + id +
        ", mobileNumber='" + mobileNumber + '\'' +
        ", smsText='" + smsText + '\'' +
        ", businessId=" + businessId +
        '}';
  }

  public String getMobileNumber() {
    return mobileNumber;
  }

  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  public String getSmsText() {
    return smsText;
  }

  public void setSmsText(String smsText) {
    this.smsText = smsText;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getBusinessId() {
    return businessId;
  }

  public void setBusinessId(Integer businessId) {
    this.businessId = businessId;
  }
}
