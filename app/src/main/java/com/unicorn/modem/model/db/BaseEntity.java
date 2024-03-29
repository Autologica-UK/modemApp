package com.unicorn.modem.model.db;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseEntity<PK extends Serializable> implements Serializable
{
    public static final String COL_CREATE_DATE_TIME = "CREATE_DATE_TIME";
    public static final String COL_UPDATE_DATE_TIME = "UPDATE_DATE_TIME";

    protected String createDateTime;
    protected String updateDateTime;

    public String getCreateDateTime()
    {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime)
    {
        this.createDateTime = createDateTime;
    }

    public String getUpdateDateTime()
    {
        return updateDateTime;
    }

    public void setUpdateDateTime(String updateDateTime)
    {
        this.updateDateTime = updateDateTime;
    }

    public abstract PK getPrimaryKey();
}
