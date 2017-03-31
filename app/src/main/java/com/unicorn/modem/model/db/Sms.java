package com.unicorn.modem.model.db;

public class Sms extends BaseEntity<Long>
{
    public static final String TABLE_NAME = "SMS";
    public static final String COL_ID = "_id";
    public static final String COL_MSG_ID = "MSG_ID";
    public static final String COL_PRIORITY = "PRIORITY";
    public static final String COL_RECORD_NO = "RECORD_NO";
    public static final String COL_MSG = "MSG";
    public static final String COL_STATUS = "STATUS";

    public static final String CREATE_TABLE_SQL = "CREATE TABLE " + Sms.TABLE_NAME + " (" +
            " " + Sms.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, " +
            " " + Sms.COL_MSG_ID + " INTEGER," +
            " " + Sms.COL_PRIORITY + " INTEGER," +
            " " + Sms.COL_RECORD_NO + " TEXT," +
            " " + Sms.COL_MSG + " TEXT," +
            " " + Sms.COL_STATUS + " INTEGER," +
            " " + Sms.COL_CREATE_DATE_TIME + " TEXT," +
            " " + Sms.COL_UPDATE_DATE_TIME + " TEXT," +
            " " + "UNIQUE([MSG_ID]) ON CONFLICT REPLACE" +
            " );";

    private Long id;
    private Long msgId;
    private Integer priority;
    private String recordNo;
    private String msg;
    private Integer status;

    public Sms()
    {
    }

    public Sms(Long msgId, Integer priority, String recordNo, String msg)
    {
        this.msgId = msgId;
        this.priority = priority;
        this.recordNo = recordNo;
        this.msg = msg;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getMsgId()
    {
        return msgId;
    }

    public void setMsgId(Long msgId)
    {
        this.msgId = msgId;
    }

    public Integer getPriority()
    {
        return priority;
    }

    public void setPriority(Integer priority)
    {
        this.priority = priority;
    }

    public String getRecordNo()
    {
        return recordNo;
    }

    public void setRecordNo(String recordNo)
    {
        this.recordNo = recordNo;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    @Override
    public String toString()
    {
        return "Sms{" +
                "id=" + id +
                ", msgId=" + msgId +
                ", priority=" + priority +
                ", recordNo='" + recordNo + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    @Override
    public Long getPrimaryKey()
    {
        return id;
    }
}
