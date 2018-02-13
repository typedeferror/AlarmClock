package com.example.user.alarmclock;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ListView listView1;
    private TimePicker timePicker1;
    private Fragment fragment;
    private ArrayList<Alarm> alarms = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Alarm weather_data[] = new Alarm[]
                {
                        new Alarm(10, 20, "Clock 1"),
                        new Alarm(10, 21, "Clock 2"),
                        new Alarm(10, 22, "Clock 3"),
                        new Alarm(10, 23, "Clock 4"),
                        new Alarm(10, 24, "Clock 5")
                };

        AlarmListAdapter adapter = new AlarmListAdapter(this,
                R.layout.alarm_row, alarms);

        listView1 = (ListView) findViewById(R.id.alarm_list);

        listView1.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onAdd(MenuItem item) {
        Toast.makeText(this, "redaktor", Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Class fragmentClass = null;
        switch (item.getItemId()) {
            case R.id.add:
                fragmentClass = AddAlarmFragment.class;
                break;
            case R.id.edit:
                fragmentClass = EditAlarmFragment.class;
                break;
            case R.id.remove:
                fragmentClass = RemoveAlarmFragment.class;
                break;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.main_container, fragment).commit();

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void save_alarm(View view) {
        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
        int hour = timePicker1.getCurrentHour();
        int min = timePicker1.getCurrentMinute();

        alarms.add(new Alarm(hour, min, String.format("%s:%s", hour, min)));

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().remove(fragment).commit();
    }
}