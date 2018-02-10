package com.example.user.alarmclock;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static ArrayList<String> alarms = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ListView lv = findViewById(R.id.listView);
//        generateListContent();
//        lv.setAdapter(new MyListAdapter(this, R.layout.list_item, alarms));

//        TextView t = findViewById(R.id.save_alarm);
//        if (alarms.size() > 0)
//            t.setText(alarms.get(0).toString());
    }

//    private void generateListContent() {
//        for (int i = 0; i < 55; i++) {
//            alarms.add("This time" + i);
//        }
//    }


    public void activity_add_alarm(View v) {
        Intent intent = new Intent(this, AddAlarm.class);
        startActivity(intent);
    }

//    private class MyListAdapter extends ArrayAdapter<String> {
//        private int layout;
//
//        private MyListAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
//            super(context, resource, objects);
//            layout = resource;
//        }
//
//        @NonNull
//        @Override
//        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//            ViewHolder mainViewholder;
//            if (convertView == null) {
//                LayoutInflater inflater = LayoutInflater.from(getContext());
//                convertView = inflater.inflate(layout, parent, false);
//                ViewHolder viewHolder = new ViewHolder();
//                viewHolder.time = convertView.findViewById(R.id.list_item_time);
//                viewHolder.title = convertView.findViewById(R.id.list_item_label);
//                viewHolder.button = convertView.findViewById(R.id.list_item_switch);
//                convertView.setTag(viewHolder);
//                viewHolder.button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(getContext(), "Button was clicked" + position, Toast.LENGTH_SHORT).show();
//                    }
//                });
//            } else {
//                mainViewholder = (ViewHolder) convertView.getTag();
//                mainViewholder.title.setText(getItem(position));
//            }
//
//            return convertView;
//        }
//    }
//
//    public class ViewHolder {
//
//        TextClock time;
//        TextView title;
//        Switch button;
//    }
}
