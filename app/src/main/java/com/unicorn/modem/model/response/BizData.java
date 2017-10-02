package com.unicorn.modem.model.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by arash on 10/2/17.
 */
@Root(name = "root")
public class BizData {

  @Element(name = "bizdata")
  private BizInfo bizInfo;

  public BizData() {
  }

  public BizInfo getBizInfo() {
    return bizInfo;
  }

  public void setBizInfo(BizInfo bizInfo) {
    this.bizInfo = bizInfo;
  }
}
