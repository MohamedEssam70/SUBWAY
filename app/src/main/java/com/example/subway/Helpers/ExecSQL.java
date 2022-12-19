package com.example.subway.Helpers;

import com.example.subway.LineModel;
import com.example.subway.MetroStationModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.requery.android.database.sqlite.SQLiteDatabase;

public class ExecSQL {
    public static void fetch(SQLiteDatabase db){
        insert(db, 1, "New El Marg", new LineModel(1, 1));
        insert(db, 2, "El Marg", new LineModel(1, 2));
        insert(db, 3, "Ezbet El Nakhl", new LineModel(1, 3));
        insert(db, 4, "Ain Shams", new LineModel(1, 4));
        insert(db, 5, "Mattareya", new LineModel(1, 5));
        insert(db, 6, "Helmeyet El Zaytoun", new LineModel(1, 6));
        insert(db, 7, "Hadayek El Zaytoun", new LineModel(1, 7));
        insert(db, 8, "Saray El Qubba", new LineModel(1, 8));
        insert(db, 9, "Hammamat El Qubba", new LineModel(1, 9));
        insert(db, 10, "Kobry El Qubba", new LineModel(1, 10));
        insert(db, 11, "Manshyet El Sadr", new LineModel(1, 11));
        insert(db, 12, "El Demerdash", new LineModel(1, 12));
        insert(db, 13, "Ghamra", new LineModel(1, 13));
        insert(db, 14, "Al Shohadaa", new LineModel(1, 14), new LineModel(2, 8));
        insert(db, 15, "Orabi", new LineModel(1, 15));
        insert(db, 16, "Nasser", new LineModel(1, 16), new LineModel(3, 19));
        insert(db, 17, "El Sadat", new LineModel(1, 17), new LineModel(2, 11));
        insert(db, 18, "Saad Zaghloul", new LineModel(1, 18));
        insert(db, 19, "Sayeda Zainab", new LineModel(1, 19));
        insert(db, 20, "El Malek El Saleh", new LineModel(1, 20));
        insert(db, 21, "Mar Girgis", new LineModel(1, 21));
        insert(db, 22, "El Zahraa", new LineModel(1, 22));
        insert(db, 23, "Dar El Salam", new LineModel(1, 23));
        insert(db, 24, "Hadayek El Maadi", new LineModel(1, 24));
        insert(db, 25, "El Maadi", new LineModel(1, 25));
        insert(db, 26, "Sakanat El Maadi", new LineModel(1, 26));
        insert(db, 27, "Tura El Balad", new LineModel(1, 27));
        insert(db, 28, "Kozzika", new LineModel(1, 28));
        insert(db, 29, "Tura El Asmant", new LineModel(1, 29));
        insert(db, 30, "El Maasara", new LineModel(1, 30));
        insert(db, 31, "Hadayek Helwan", new LineModel(1, 31));
        insert(db, 32, "Wadi Hof", new LineModel(1, 32));
        insert(db, 33, "Helwan University", new LineModel(1, 33));
        insert(db, 34, "Ain Helwan", new LineModel(1, 34));
        insert(db, 35, "Helwan", new LineModel(1, 35));
        insert(db, 36, "Shubra El Kheima", new LineModel(2, 1));
        insert(db, 37, "Koleyet El Zeraa", new LineModel(2, 2));
        insert(db, 38, "El Mezallat", new LineModel(2, 3));
        insert(db, 39, "El Khalafawy", new LineModel(2, 4));
        insert(db, 40, "Saint Theresa", new LineModel(2, 5));
        insert(db, 41, "Rod El Farag", new LineModel(2, 6));
        insert(db, 42, "Massara", new LineModel(2, 7));
        insert(db, 43, "Attaba", new LineModel(2, 9), new LineModel(3, 18));
        insert(db, 44, "Nageeb", new LineModel(2, 10));
        insert(db, 45, "Opera", new LineModel(2, 12));
        insert(db, 46, "Dokki", new LineModel(2, 13));
        insert(db, 47, "El Behoos", new LineModel(2, 14));
        insert(db, 48, "Cairo University", new LineModel(2, 15), new LineModel(3, 27));
        insert(db, 49, "Faysal", new LineModel(2, 16));
        insert(db, 50, "Giza", new LineModel(2, 17));
        insert(db, 51, "Om El Masryeen", new LineModel(2, 18));
        insert(db, 52, "Sakyet Mekky", new LineModel(2, 19));
        insert(db, 53, "El Moneeb", new LineModel(2, 20));
        insert(db, 54, "El Haykeestep", new LineModel(3, 1));
        insert(db, 55, "Omar Ibn El khattab", new LineModel(3, 2));
        insert(db, 56, "Qubaa", new LineModel(3, 3));
        insert(db, 57, "Hesham Barakat", new LineModel(3, 4));
        insert(db, 58, "El Nozha", new LineModel(3, 5));
        insert(db, 59, "El Shams Club", new LineModel(3, 6));
        insert(db, 60, "Alf Maskan", new LineModel(3, 7));
        insert(db, 61, "Heliopolis", new LineModel(3, 8));
        insert(db, 62, "Haroun", new LineModel(3, 9));
        insert(db, 63, "El Ahram", new LineModel(3, 10));
        insert(db, 64, "Kolleyet El Banat", new LineModel(3, 11));
        insert(db, 65, "Stadium", new LineModel(3, 12));
        insert(db, 66, "Fair Zone", new LineModel(3, 13));
        insert(db, 67, "El Abassiya", new LineModel(3, 14));
        insert(db, 68, "Abdou Pasha", new LineModel(3, 15));
        insert(db, 69, "El Geish", new LineModel(3, 16));
        insert(db, 70, "Bab El Shaariya", new LineModel(3, 17));
        insert(db, 71, "Maspero", new LineModel(3, 20));
        insert(db, 72, "Safaa Hegazy", new LineModel(3, 21));
        insert(db, 73, "Kit-Kat", new LineModel(3, 22));
        insert(db, 74, "Tawfikia", new LineModel(3, 23));
        insert(db, 75, "Wadi El Nile", new LineModel(3, 24));
        insert(db, 76, "Gamet El Dowel", new LineModel(3, 25));
        insert(db, 77, "Boulak El Dakrour", new LineModel(3, 26));
        insert(db, 78, "Sudan", new LineModel(3, 23));
        insert(db, 79, "Imbaba", new LineModel(3, 24));
        insert(db, 80, "El-Bohy ", new LineModel(3, 25));
        insert(db, 81, "El-Qawmia", new LineModel(3, 26));
        insert(db, 82, "Ring Road", new LineModel(3, 27));
        insert(db, 83, "Rod El Farag Corr.", new LineModel(3, 28));
    }

    private static void insert(SQLiteDatabase db, int id, String name, LineModel ... lines){
        MetroStationModel metroStationModel = new MetroStationModel(id, name, new ArrayList<>(Arrays.asList(lines)));
        db.execSQL("INSERT INTO " +DBHelper.TABLE_NAME+ "("+DBHelper.STATION_ID_COL+", "+DBHelper.STATION_NAME_COL+", "+DBHelper.STATION_LINES_COL+")" +
                " VALUES ('"+metroStationModel.getMetroStationId()+"', '"+metroStationModel.getMetroStationName()+"', '"+metroStationModel.getLinesJson()+"')");
    }
}
