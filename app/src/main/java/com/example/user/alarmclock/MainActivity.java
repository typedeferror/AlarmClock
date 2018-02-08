package com.example.user.alarmclock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void activity_add_alarm(View v) {
        Intent intent = new Intent(this, AddAlarm.class);
        startActivity(intent);
    }

    //    public void addAlarmClick(View v) {
//        AddAlarm.broadcastCode++;
//        startActivity(new Intent(this, AddAlarm.class));
//    }
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(android.content.Context);
}
