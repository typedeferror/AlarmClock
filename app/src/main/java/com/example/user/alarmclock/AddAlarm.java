package com.example.user.alarmclock;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class AddAlarm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        BottomNavigationView bottomNavigationView =(BottomNavigationView) findViewById(R.id.bottoms_settings);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_repeat:
                        Toast.makeText(AddAlarm.this, "Action Repeat Clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_label:
                        Toast.makeText(AddAlarm.this, "Action Label Clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_snooze:
                        Toast.makeText(AddAlarm.this, "Action Snooze Clicked", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }
}
