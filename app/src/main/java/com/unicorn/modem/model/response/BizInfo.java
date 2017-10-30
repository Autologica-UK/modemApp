package com.unicorn.modem.model.response;

import org.simpleframework.xml.Element;

/**
 * Created by arash on 10/2/17.
 */
public class BizInfo {

  @Element(name = "biz", required = false)
  private int bizId;
  @Element(name = "utdsbiz", required = false)
  private int utdsBiz;
  @Element(name = "bizname")
  private String bizName;
  @Element(name = "bizemail", required = false)
  private String bizEmail;
  @Element(name = "bizphone", required = false)
  private String bizPhone;
  @Element(name = "bizpc")
  private String bizPostCode;
  @Element(name = "murl")
  private String url;
  @Element(name = "mcall", required = false)
  private int mcall;
  @Element(name = "mport")
  private int port;

  public BizInfo() {
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public int getBizId() {
    return bizId;
  }

  public void setBizId(int bizId) {
    this.bizId = bizId;
  }

  public String getBizName() {
    return bizName;
  }

  public void setBizName(String bizName) {
    this.bizName = bizName;
  }

  public String getBizEmail() {
    return bizEmail;
  }

  public void setBizEmail(String bizEmail) {
    this.bizEmail = bizEmail;
  }

  public String getBizPhone() {
    return bizPhone;
  }

  public void setBizPhone(String bizPhone) {
    this.bizPhone = bizPhone;
  }

  public String getBizPostCode() {
    return bizPostCode;
  }

  public void setBizPostCode(String bizPostCode) {
    this.bizPostCode = bizPostCode;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public int getMcall() {
    return mcall;
  }

  public void setMcall(int mcall) {
    this.mcall = mcall;
  }

  public int getUtdsBiz() {
    return utdsBiz;
  }

  public void setUtdsBiz(int utdsBiz) {
    this.utdsBiz = utdsBiz;
  }
}