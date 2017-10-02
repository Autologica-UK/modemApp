package com.unicorn.modem.model.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.unicorn.modem.ModemApplication;
import com.unicorn.modem.data.ModemDatabaseHelper;
import com.unicorn.modem.model.db.BaseEntity;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T extends BaseEntity, PK extends Long> {

  protected static SQLiteDatabase writableDb;

  public PK create(T entity) {
    ModemDatabaseHelper databaseHelper = ModemDatabaseHelper
        .getInstance(ModemApplication.getInstance());
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    db.beginTransaction();
    Long id = db.insert(getTableName(), null, getContentValues(entity));
    db.setTransactionSuccessful();
    db.endTransaction();
    return (PK) id;
  }

  public T retrieve(PK id) {
    ModemDatabaseHelper databaseHelper = ModemDatabaseHelper
        .getInstance(ModemApplication.getInstance());
    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    String selection = getPrimaryKeyColumnName() + " = ?";
    String[] args = {String.valueOf(id)};
    Cursor cursor = db.query(getTableName(), getProjection(), selection, args, null, null, null);
    T entity = null;
    if (cursor.moveToFirst()) {
      entity = createEntityFromCursor(cursor);
    }
    cursor.close();
    return entity;
  }

  public void update(T entity) {
    ModemDatabaseHelper databaseHelper = ModemDatabaseHelper
        .getInstance(ModemApplication.getInstance());
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    db.beginTransaction();
    String whereClause = getPrimaryKeyColumnName() + " = ?";
    String[] args = {String.valueOf(entity.getPrimaryKey())};
    db.update(getTableName(), getContentValues(entity), whereClause, args);
    db.setTransactionSuccessful();
    db.endTransaction();
  }

  public void delete(PK id) {
    ModemDatabaseHelper databaseHelper = ModemDatabaseHelper
        .getInstance(ModemApplication.getInstance());
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    db.beginTransaction();
    String whereClause = getPrimaryKeyColumnName() + " = ?";
    String[] args = {String.valueOf(id)};
    db.delete(getTableName(), whereClause, args);
    db.setTransactionSuccessful();
    db.endTransaction();
  }

  public List<T> retrieveAll() {
    return retrieveAll(null, null, null, null, null, null);
  }

  public List<T> retrieveAll(String selection, String[] args, String groupBy, String having,
      String orderBy, String limit) {
    ModemDatabaseHelper databaseHelper = ModemDatabaseHelper
        .getInstance(ModemApplication.getInstance());
    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    Cursor cursor = db
        .query(getTableName(), getProjection(), selection, args, groupBy, having, orderBy, limit);
    List<T> entities = new ArrayList<>();
    while (cursor.moveToNext()) {
      entities.add(createEntityFromCursor(cursor));
    }
    cursor.close();
    return entities;
  }

  public void deleteAll() {
    ModemDatabaseHelper databaseHelper = ModemDatabaseHelper
        .getInstance(ModemApplication.getInstance());
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    db.beginTransaction();
    db.execSQL("DELETE FROM " + getTableName());
    db.setTransactionSuccessful();
    db.endTransaction();
  }

  public void delete(String selection, String[] args) {
    ModemDatabaseHelper databaseHelper = ModemDatabaseHelper
        .getInstance(ModemApplication.getInstance());
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    db.beginTransaction();
    db.delete(getTableName(), selection, args);
    db.setTransactionSuccessful();
    db.endTransaction();
  }

  protected abstract ContentValues getContentValues(T entity);

  protected abstract String getTableName();

  protected abstract String getPrimaryKeyColumnName();

  protected abstract String[] getProjection();

  protected abstract T createEntityFromCursor(Cursor cursor);
}
