package com.example.subway;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MetroMapModel {

    public MetroMapModel(Context context){
        ArrayList<MetroStationModel> ss = getStationsDataModel(context);
        Log.e("-*-*-*-*", ss.get(0).getMetroStationName());
    }

    /****METRO LINE 1****/
//    public final static class MetroLineM1Model extends MetroLineDataModel {
//        public MetroLineM1Model(){
//            super.setMetroLineId("M1");
//            super.setMetroLineLength(35);
//            super.setMetroLineAverageMovementSpeed(3.14285);
//            /*
//             * Metro Line 1 Stations
//             * */
//        }
//    }

    private ArrayList<MetroStationModel> getStationsDataModel(Context context){
        ArrayList<MetroStationModel> stations = new ArrayList<MetroStationModel>();

        XmlResourceParser parser = context.getResources().getXml(R.xml.stations);
        int eventType = -1;
        int myIndex = -1;
        String tagName = null;

        while (eventType != XmlResourceParser.END_DOCUMENT) {
            if(eventType == XmlResourceParser.START_TAG){
                tagName = parser.getName();
                switch (tagName){
                    case "item":
                        stations.add(new MetroStationModel());
                        myIndex++;
                        break;
                    case "id":
                        try {
                            parser.next();
                            stations.get(myIndex).setMetroStationId(Integer.parseInt(parser.getText()));
                            parser.next();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case "arrange":
                        try {
                            parser.next();
                            stations.get(myIndex).setMetroStationLineRelationship(Integer.parseInt(parser.getText()));
                            parser.next();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case "name":
                        try {
                            parser.next();
                            stations.get(myIndex).setMetroStationName(parser.getText());
                            parser.next();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case "intersection":
                        try {
                            parser.next();
                            stations.get(myIndex).setMetroStationIntersection(parser.getText());
                            parser.next();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
            try {
                eventType = parser.next();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return stations;
    }

}
