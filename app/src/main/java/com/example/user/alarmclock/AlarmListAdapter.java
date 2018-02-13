package com.example.user.alarmclock;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 13.02.2018.
 */

public class AlarmListAdapter extends ArrayAdapter<Alarm> {

    Context context;
    int layoutResourceId;
    ArrayList<Alarm> data = null;

    public AlarmListAdapter(Context context, int layoutResourceId, ArrayList<Alarm> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        AlarmHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new AlarmHolder();
            holder.time = (TextView)row.findViewById(R.id.time);
            holder.label = (TextView)row.findViewById(R.id.label);

            row.setTag(holder);
        }
        else
        {
            holder = (AlarmHolder)row.getTag();
        }

        Alarm alarm = data.get(position);
        holder.time.setText(alarm.time());
        holder.label.setText(alarm.getLabel());

        return row;
    }

    static class AlarmHolder
    {
        TextView time;
        TextView label;
    }
}
