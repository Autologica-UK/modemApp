package com.unicorn.modem.model.db;

public enum SmsStatus {
  SENT(1, "SENT"),
  PENDING(2, "PENDING"),
  FAILED(3, "FAILED"),
  FAKE(4, "FAKE"),
  DELIVERED(5, "DELIVERED"),
  RECEIVED(6, "RECEIVED"),
  RECEIVED_SERVER(7, "RECEIVED_SERVER");

  private int value;
  private String title;

  SmsStatus(int value, String title) {
    this.value = value;
    this.title = title;
  }

  public static SmsStatus getByValue(long value) {
    SmsStatus found = null;
    for (SmsStatus status : SmsStatus.values()) {
      if (status.getValue() == value) {
        found = status;
        break;
      }
    }
    return found;
  }

  public static SmsStatus getByTitle(String title) {
    SmsStatus found = null;
    for (SmsStatus status : SmsStatus.values()) {
      if (status.getTitle().equals(title)) {
        found = status;
        break;
      }
    }
    return found;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
