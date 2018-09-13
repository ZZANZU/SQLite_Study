package com.teamtreehouse.friendlyforecast.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.teamtreehouse.friendlyforecast.services.Forecast;

public class ForecastDataSource {

    private static final String TAG = "TreeHouse Project";
    private SQLiteDatabase mDatabase;
    private ForecastHelper mForecastHelper;
    private Context mContext;

    public ForecastDataSource(Context context) {
        mContext = context;
        mForecastHelper = new ForecastHelper(mContext);
    }

    // open
    public void open() throws SQLException {
        mDatabase = mForecastHelper.getWritableDatabase(); // 없으면 만들고 있으면 불러옴
        Log.d(TAG, "open: Database open!");
    }

    // close
    public void close() {
        mDatabase.close();
        Log.d(TAG, "close: Database close!");
    }

    // insert
    public void insertForecast(Forecast forecast) {
        mDatabase.beginTransaction();

        try{
            for(Forecast.HourData hour : forecast.hourly.data) {
                ContentValues values = new ContentValues(); // insert를 위해 ContentValue를 사용했다!
                values.put(ForecastHelper.COLUMN_TEMPERATURE, hour.temperature);
                mDatabase.insert(ForecastHelper.TABLE_TEMPERATURES, null, values);
            }
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }

    }

    // select
    public Cursor selectAllTempteratures() {
        Cursor cursor = mDatabase.query(
                ForecastHelper.TABLE_TEMPERATURES, // table
                new String[] { ForecastHelper.COLUMN_TEMPERATURE }, // column names
                null, // where clause
                null, // where parameters
                null, // groupby
                null, // having
                null // orderby
        );

        return cursor;
    }

    public Cursor selectAllTempsGreaterThan(String minTemp) {
        String whereClause = ForecastHelper.COLUMN_TEMPERATURE + "> ?";

        Cursor cursor = mDatabase.query(
                ForecastHelper.TABLE_TEMPERATURES, // table
                new String[] { ForecastHelper.COLUMN_TEMPERATURE }, // column names
                whereClause, // where clause
                new String[] { minTemp }, // where parameters
                null, // groupby
                null, // having
                null // orderby
        );

        return cursor;
    }

    // update
    public int updateTemperature(double newTemp) {
        ContentValues values = new ContentValues();
        values.put(ForecastHelper.COLUMN_TEMPERATURE, newTemp);

        int rowsUpdated = mDatabase.update(
                ForecastHelper.TABLE_TEMPERATURES, // table
                values,
                null,
                null
        );

        return rowsUpdated;
    }

    // delete
    public void deleteAll() {
        mDatabase.delete(
                ForecastHelper.TABLE_TEMPERATURES,
                null,
                null
        );
    }

}

