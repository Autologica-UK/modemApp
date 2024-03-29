package com.unicorn.modem.model.db.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.unicorn.modem.model.db.Sms;
import com.unicorn.modem.model.db.SmsStatus;

import java.util.List;

/**
 * @author Arashmidos
 */
public class SMSDaoImpl extends AbstractDao<Sms, Long>
{
    public SMSDaoImpl()
    {
    }

    @Override
    protected ContentValues getContentValues(Sms entity)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Sms.COL_ID, entity.getId());
        contentValues.put(Sms.COL_MSG_ID, entity.getMsgId());
        contentValues.put(Sms.COL_PRIORITY, entity.getPriority());
        contentValues.put(Sms.COL_RECORD_NO, entity.getRecordNo());
        contentValues.put(Sms.COL_MSG, entity.getMsg());
        contentValues.put(Sms.COL_STATUS, entity.getStatus());
        contentValues.put(Sms.COL_CREATE_DATE_TIME, entity.getCreateDateTime());
        contentValues.put(Sms.COL_UPDATE_DATE_TIME, entity.getUpdateDateTime());
        return contentValues;
    }

    @Override
    protected String getTableName()
    {
        return Sms.TABLE_NAME;
    }

    @Override
    protected String getPrimaryKeyColumnName()
    {
        return Sms.COL_ID;
    }

    @Override
    protected String[] getProjection()
    {
        String[] projection = {
                Sms.COL_ID,
                Sms.COL_MSG_ID,
                Sms.COL_PRIORITY,
                Sms.COL_RECORD_NO,
                Sms.COL_MSG,
                Sms.COL_STATUS,
                Sms.COL_CREATE_DATE_TIME,
                Sms.COL_UPDATE_DATE_TIME
        };

        return projection;
    }

    @Override
    protected Sms createEntityFromCursor(Cursor cursor)
    {
        Sms Sms = new Sms();
        Sms.setId(cursor.getLong(0));
        Sms.setMsgId(cursor.getLong(1));
        Sms.setPriority(cursor.getInt(2));
        Sms.setRecordNo(cursor.getString(3));
        Sms.setMsg(cursor.getString(4));
        Sms.setStatus(cursor.getInt(5));
        Sms.setCreateDateTime(cursor.getString(6));
        Sms.setUpdateDateTime(cursor.getString(7));
        return Sms;
    }

    public Sms get(String SmsId)
    {
        String selection = " " + Sms.COL_MSG_ID + " = ? ";
        String[] args = {SmsId};

        List<Sms> list = retrieveAll(selection, args, null, null, null, "1");
        if (list.size() > 0)
        {
            return list.get(0);
        } else
        {
            return null;
        }
    }

    public boolean contains(String SmsId)
    {
        Sms Sms = get(SmsId);
        return Sms != null;
    }

    public int totalCount()
    {
        return retrieveAll().size();
    }

    public int deliveredCount()
    {
        String selection = " " + Sms.COL_STATUS + " = ? ";
        String[] args = {String.valueOf(SmsStatus.DELIVERED.getValue())};
        return retrieveAll(selection, args, null, null, null, null).size();
    }

    public int sentCount()
    {
        String selection = " " + Sms.COL_STATUS + " = ? ";
        String[] args = {String.valueOf(SmsStatus.SENT.getValue())};
        return retrieveAll(selection, args, null, null, null, null).size();
    }

    public int failedCount()
    {
        String selection = " " + Sms.COL_STATUS + " = ? ";
        String[] args = {String.valueOf(SmsStatus.FAILED.getValue())};
        return retrieveAll(selection, args, null, null, null, null).size();
    }


    public List<Sms> retrieveAllByStatus(SmsStatus status)
    {
        String selection = " " + Sms.COL_STATUS + " = ? ";
        if (status.equals(SmsStatus.SENT))
        {
            selection = selection + " OR " + Sms.COL_STATUS + " = 5 ";
        }
        String[] args = {String.valueOf(status.getValue())};
        String orderBy = Sms.COL_PRIORITY + " ASC ";
        return retrieveAll(selection, args, null, null, orderBy, null);
    }
}
