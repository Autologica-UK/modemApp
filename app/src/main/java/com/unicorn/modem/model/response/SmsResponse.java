package com.unicorn.modem.model.response;

import java.util.List;

/**
 * Created by Arashmidos on 2017-03-31.
 */

public class SmsResponse {

  private List<SMSMessage> SMSMessages = null;

  public List<SMSMessage> getSMSMessages() {
    return SMSMessages;
  }

  public void setSMSMessages(List<SMSMessage> sMSMessages) {
    this.SMSMessages = sMSMessages;
  }

}
