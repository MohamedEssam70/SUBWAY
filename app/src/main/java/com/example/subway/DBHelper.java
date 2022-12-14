package com.example.subway;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "stations.db";
    public static final String TABLE_NAME = "stations";
    public static final String STATION_NAME_COL = "name";

    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table "+TABLE_NAME+" " +
                        "(id integer primary key, name text,arrange integer,line integer, intersection integer)"
        );

        db.execSQL("INSERT INTO " +TABLE_NAME+ "(name, arrange, line, intersection)" +
                " VALUES ('New El Marg', 1, 1, 0)");
        db.execSQL("INSERT INTO " +TABLE_NAME+ "(name, arrange, line, intersection)" +
                " VALUES ('Al Shohadaa', 14, 2, 1)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(database);
    }

    public boolean insertStation (MetroStationModel metroStationModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", metroStationModel.getMetroStationName());
        contentValues.put("arrange", metroStationModel.getMetroStationLineRelationship());
        contentValues.put("line", metroStationModel.getMetroStationLine());
        contentValues.put("intersection", metroStationModel.getMetroStationIntersection());
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public MetroStationModel getStation(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery( "select * from "+TABLE_NAME+" where id="+id, null );
        result.moveToFirst();
        MetroStationModel station = null;
        if(!result.isAfterLast()) {
            station = new MetroStationModel(
                    result.getInt(result.getColumnIndexOrThrow("id")),
                    result.getString(result.getColumnIndexOrThrow(STATION_NAME_COL)),
                    result.getInt(result.getColumnIndexOrThrow("arrange")),
                    result.getInt(result.getColumnIndexOrThrow("line")),
                    result.getInt(result.getColumnIndexOrThrow("intersection"))
            );
        }
        result.close();
        return station;
    }

    public ArrayList<MetroStationModel> getStationInLine(int line) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery( "select * from "+TABLE_NAME+" where line="+line+" OR intersection="+line, null );
        ArrayList<MetroStationModel> stations = new ArrayList<>();
        result.moveToFirst();
        while(!result.isAfterLast()){
            stations.add(new MetroStationModel(
                    result.getInt(result.getColumnIndexOrThrow("id")),
                    result.getString(result.getColumnIndexOrThrow(STATION_NAME_COL)),
                    result.getInt(result.getColumnIndexOrThrow("arrange")),
                    result.getInt(result.getColumnIndexOrThrow("line")),
                    result.getInt(result.getColumnIndexOrThrow("intersection"))
            ));
            result.moveToNext();
        }
        result.close();
        return  stations;
    }
}