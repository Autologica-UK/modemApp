package com.unicorn.modem.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.unicorn.modem.model.db.Sms;

public class ModemDatabaseHelper extends SQLiteOpenHelper {

  public static final String TAG = ModemDatabaseHelper.class.getSimpleName();
  public static final String DATABASE_NAME = "sms";
  public static final Integer DATABASE_VERSION = 1;

  private static ModemDatabaseHelper sInstance;

  public ModemDatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  public static synchronized ModemDatabaseHelper getInstance(Context context) {
    if (sInstance == null) {
      sInstance = new ModemDatabaseHelper(context.getApplicationContext());
    }
    return sInstance;
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    Log.i(TAG, "start creating tables");
    db.execSQL(Sms.CREATE_TABLE_SQL);
    Log.i(TAG, "end of creating tables");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.i(TAG, "on Database Upgrade");
  }
}
