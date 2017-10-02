package com.unicorn.modem.model.event;

public class ErrorEvent extends Event {

  public ErrorEvent(String message, int statusCode) {
    this.message = message;
    this.statusCode = statusCode;
  }

  public ErrorEvent() {
  }
}
