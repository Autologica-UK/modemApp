package com.unicorn.modem.model.request;

/**
 * Created by arash on 9/23/17.
 */

public class SmsDto {

  private Long id;
  private String mobileNumber;
  private String smsText;

  public SmsDto() {
  }

  public SmsDto(long id, String mobileNumber, String smsText) {
    this.id = id;
    this.mobileNumber = mobileNumber;
    this.smsText = smsText;
  }

  @Override
  public String toString() {
    return "SmsDto{" +
        "id=" + id +
        ", mobileNumber='" + mobileNumber + '\'' +
        ", smsText='" + smsText + '\'' +
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
}
