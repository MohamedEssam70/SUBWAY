package com.example.subway.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.subway.LineModel;
import com.example.subway.MetroStationModel;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "stations.db";
    public static final String TABLE_NAME = "stations";
    public static final String STATION_ID_COL = "id";
    public static final String STATION_NAME_COL = "name";
    public static final String STATION_LINES_COL = "lines";

    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table "+TABLE_NAME+" " +
                        "(id integer primary key, name text,lines text)"
        );

        List<LineModel> lineModels = new ArrayList<>();
        lineModels.add(new LineModel(1, 1));
        MetroStationModel metroStationModel = new MetroStationModel("New El Marg", lineModels);
        db.execSQL("INSERT INTO " +TABLE_NAME+ "("+STATION_NAME_COL+", "+STATION_LINES_COL+")" +
                " VALUES ('"+metroStationModel.getMetroStationName()+"', '"+metroStationModel.getLinesJson()+"')");


        lineModels = new ArrayList<>();
        lineModels.add(new LineModel(1, 14));
        lineModels.add(new LineModel(2, 8));
        metroStationModel = new MetroStationModel("Al Shohadaa", lineModels);
        db.execSQL("INSERT INTO " +TABLE_NAME+ "("+STATION_NAME_COL+", "+STATION_LINES_COL+")" +
                " VALUES ('"+metroStationModel.getMetroStationName()+"', '"+metroStationModel.getLinesJson()+"')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(database);
    }

    public boolean insertStation (MetroStationModel metroStationModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(STATION_NAME_COL, metroStationModel.getMetroStationName());
        contentValues.put(STATION_LINES_COL, metroStationModel.getLinesJson());
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public MetroStationModel getStation(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery( "select * from "+TABLE_NAME+" where "+STATION_ID_COL+"="+id, null );
        result.moveToFirst();
        MetroStationModel station = null;
        if(!result.isAfterLast()) {
            try {
                station = new MetroStationModel(
                        result.getInt(result.getColumnIndexOrThrow(STATION_ID_COL)),
                        result.getString(result.getColumnIndexOrThrow(STATION_NAME_COL)),
                        result.getString(result.getColumnIndexOrThrow(STATION_LINES_COL))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        result.close();
        return station;
    }

    public ArrayList<MetroStationModel> getStationInLine(int line) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery( "select * from "+TABLE_NAME, null );//+" WHERE json_extract("+STATION_LINES_COL+", '$.line') = "+line
        ArrayList<MetroStationModel> stations = new ArrayList<>();
        result.moveToFirst();
        while(!result.isAfterLast()){
            try {
                stations.add(new MetroStationModel(
                        result.getInt(result.getColumnIndexOrThrow(STATION_ID_COL)),
                        result.getString(result.getColumnIndexOrThrow(STATION_NAME_COL)),
                        result.getString(result.getColumnIndexOrThrow(STATION_LINES_COL))
                ));
            } catch (Exception e) {
                e.printStackTrace();
            }
            result.moveToNext();
        }
        result.close();

        return stations;
    }
}