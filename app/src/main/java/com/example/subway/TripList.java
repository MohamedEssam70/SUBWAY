package com.example.subway;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TripList extends ArrayAdapter<Trip> {
    private Activity context;
    private List<Trip> tripList;

    public TripList(Activity context, List<Trip> tripList) {
        super(context, R.layout.trip_list, tripList);
        this.context = context;
        this.tripList = tripList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.trip_list,null, true);

        TextView dateTxt = (TextView) listViewItem.findViewById(R.id.tripDate);
        TextView startStationTxt = (TextView) listViewItem.findViewById(R.id.tripStartStation);
        TextView endStationTxt = (TextView) listViewItem.findViewById(R.id.tripEndStation);
        TextView costTxt = (TextView) listViewItem.findViewById(R.id.tripCost);
        Trip trip = tripList.get(position);
        dateTxt.setText("Date: "+trip.getDate());
        startStationTxt.setText(trip.getStartStation());
        endStationTxt.setText(trip.getEndStation());
        costTxt.setText("EGP "+trip.getCost());
        return listViewItem;
    }
}
