package com.example.subway.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import io.requery.android.database.sqlite.SQLiteDatabase;
import io.requery.android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
        ExecSQL.fetch(db);
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
        db.close();
        return true;
    }

    //TODO: Use this method to store all stations as objects in order to use in the dropdown list at home
    public ArrayList<MetroStationModel> querySelectorAll() {
        ArrayList<MetroStationModel> stations = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery( "select * from "+TABLE_NAME,null);
        result.moveToFirst();
        MetroStationModel station = null;
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
        db.close();
        return stations;
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
        db.close();
        return station;
    }

    public ArrayList<MetroStationModel> getStationInLine(int line) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MetroStationModel> stations = new ArrayList<>();
        try {
            Cursor result = db.rawQuery(
                    "select t.* from "+TABLE_NAME+" t"+
                    " JOIN json_each(t."+STATION_LINES_COL+") j"+
                    " WHERE json_extract(j.value, '$.line') = "+line, null );
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
            db.close();
        } catch (Exception e) {
            Log.e("++++++ ", e.getMessage());
        }
        return stations;
    }

    public ArrayList<MetroStationModel> getIntersectionStation(int firstLine, int secondLine) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MetroStationModel> stations = new ArrayList<>();
        try {
            Cursor result = db.rawQuery(
                    "select t.* from "+TABLE_NAME+" t"+
                            " JOIN json_each(t."+STATION_LINES_COL+") j"+
                            " WHERE json_extract(j.value, '$.line') = "+firstLine+
                            " OR json_extract(j.value, '$.line') = "+secondLine+
                            " GROUP BY t.id having count(*) > 1", null );
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
            db.close();
        } catch (Exception e) {
            Log.e("++++++ ", e.getMessage());
        }
        return stations;
    }
}