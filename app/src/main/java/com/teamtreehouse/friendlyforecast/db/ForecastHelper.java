package com.teamtreehouse.friendlyforecast.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ForecastHelper extends SQLiteOpenHelper {

    public static final String TABLE_TEMPERATURES = "TEMPERATURES";
    public static final String COLUMN_ID = "_ID";
    public static final String COLUMN_TEMPERATURE = "TEMPERATURE";
    private static final String COLUMN_TIME = "TIME";

    private static final String DB_NAME = "temperatures.db";
    private static final int DB_VERSION = 1; // 다른 숫자로 바꾸면 onUpgrade 실행됨
    private static final String DB_CREATE =
            "CREATE TABLE " + TABLE_TEMPERATURES + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TEMPERATURE + " REAL, " +
            COLUMN_TIME + " INTEGER)";

    private static final String DB_ALTER =
            "ALTER TABLE " + TABLE_TEMPERATURES + " ADD COLUMN " + COLUMN_TIME + " INTEGER";

    public ForecastHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
    }

    // DB_VERSION과 비교해서 다를 경우 실행됨
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DB_ALTER);
    }
}
