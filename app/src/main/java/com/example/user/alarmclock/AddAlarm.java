package com.example.user.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;


public class AddAlarm extends AppCompatActivity {

    // private TextView time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottoms_settings);
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

    //        TimePicker timePicker1 = findViewById(R.id.timePicker1);
//        time = findViewById(R.id.withText);
//       // Calendar calendar = Calendar.getInstance();
//        int hour = timePicker1.getCurrentHour();
//        int min = timePicker1.getCurrentMinute();
//        showTime(hour, min);
//    }
//
//    public void showTime(int hour, int min) {
//        String format;
//        if (hour == 0) {
//            hour += 12;
//            format = "AM";
//        } else if (hour == 12) {
//            format = "PM";
//        } else if (hour > 12) {
//            hour -= 12;
//            format = "PM";
//        } else {
//            format = "AM";
//        }
//
//        time.setText(new StringBuilder().append(hour).append(" : ").append(min)
//                .append(" ").append(format));
//    public static int broadcastCode = 0;
//    AlarmManager mgrAlarm = (AlarmManager) this.getSystemService(ALARM_SERVICE);
//    ArrayList<PendingIntent> intentArray = new ArrayList<>();
//
//            for(int i = 0; i < 10; ++i)
//
//    {
//        Intent intent = new Intent(this, AlarmReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, i, intent, 0);
//        mgrAlarm.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                SystemClock.elapsedRealtime() + 60000 * i,
//                pendingIntent);
//
//        intentArray.add(pendingIntent);
//        intent = new Intent(AddAlarm.this, AlarmReceiver.class);
//        pendingIntent = PendingIntent.getBroadcast(AddAlarm.this, broadcastCode,intent, 0);
//    }

}