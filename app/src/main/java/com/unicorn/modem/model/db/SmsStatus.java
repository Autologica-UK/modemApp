package com.unicorn.modem.model.db;

public enum SmsStatus
{
    SENT(1L, "SENT"),
    PENDING(2L, "PENDING"),
    FAILED(3L, "FAILED"),
    FAKE(4L, "FAKE")
    ;

    private long value;
    private String title;

    SmsStatus(long value, String title)
    {
        this.value = value;
        this.title = title;
    }

    public static SmsStatus getByValue(long value)
    {
        SmsStatus found = null;
        for (SmsStatus status : SmsStatus.values())
        {
            if (status.getValue() == value)
            {
                found = status;
                break;
            }
        }
        return found;
    }

    public static SmsStatus getByTitle(String title)
    {
        SmsStatus found = null;
        for (SmsStatus status : SmsStatus.values())
        {
            if (status.getTitle().equals(title))
            {
                found = status;
                break;
            }
        }
        return found;
    }

    public long getValue()
    {
        return value;
    }

    public void setValue(long value)
    {
        this.value = value;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
}
