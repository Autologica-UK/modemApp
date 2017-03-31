package com.unicorn.modem.model.response;

/**
 * Created by Arashmidos on 2017-03-31.
 */

public class SMSMessage
{
    private String msgid;
    private String priority;
    private String recno;
    private String msg;

    public SMSMessage()
    {
    }

    public SMSMessage(String msgid, String priority, String recno, String msg)
    {
        this.msgid = msgid;
        this.priority = priority;
        this.recno = recno;
        this.msg = msg;
    }

    public String getMsgid()
    {
        return msgid;
    }

    public void setMsgid(String msgid)
    {
        this.msgid = msgid;
    }

    public String getPriority()
    {
        return priority;
    }

    public void setPriority(String priority)
    {
        this.priority = priority;
    }

    public String getRecno()
    {
        return recno;
    }

    public void setRecno(String recno)
    {
        this.recno = recno;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

}
