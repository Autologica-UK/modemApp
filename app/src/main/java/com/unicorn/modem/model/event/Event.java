package com.unicorn.modem.model.event;

import java.io.Serializable;

/**
 * Created by Arashmidos on 2017-03-31.
 */
public class Event implements Serializable
{
    protected String message;
    protected int statusCode;

    public int getStatusCode()
    {
        return statusCode;
    }

    public void setStatusCode(int statusCode)
    {
        this.statusCode = statusCode;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}