package com.example.subway;

import android.content.Context;

import com.example.subway.Helpers.DBHelper;

public class MetroMapModel {
    private final Context context;
    DBHelper dbHelper;
    public MetroMapModel(Context context){
        this.context = context;
        dbHelper = new DBHelper(context);
    }
    public MetroLineDataModel M1(){
        return new MetroLineM1Model();
    }
    public MetroLineDataModel M2(){
        return new MetroLineM2Model();
    }
    public MetroLineDataModel M3(){
        return new MetroLineM3Model();
    }

    /****METRO LINE 1****/
     class MetroLineM1Model extends MetroLineDataModel {
        public MetroLineM1Model(){
            setMetroLineId("M1");
            setMetroLineLength(35);
            setMetroLineAverageMovementSpeed(3.14285);
            setStationsData(dbHelper.getStationInLine(1));
        }
    }

    /****METRO LINE 2****/
    class MetroLineM2Model extends MetroLineDataModel {
        public MetroLineM2Model(){
            super.setMetroLineId("M2");
            super.setMetroLineLength(20);
            super.setMetroLineAverageMovementSpeed(2.91123);
            super.setStationsData(dbHelper.getStationInLine(2));
        }
    }

    /****METRO LINE 3****/
    class MetroLineM3Model extends MetroLineDataModel {
        public MetroLineM3Model(){
            super.setMetroLineId("M3");
            super.setMetroLineLength(33);
            super.setMetroLineAverageMovementSpeed(2.14589);
            super.setStationsData(dbHelper.getStationInLine(3));
        }
    }
}