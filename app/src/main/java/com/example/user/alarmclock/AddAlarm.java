package com.example.user.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AddAlarm extends AppCompatActivity {

    public TextView time;
    public PendingIntent pendingIntent;
    public AlarmManager alarm_manager;
    public String myURI = "NONE";
    public Uri aa;
    final TimePicker pickTime = (TimePicker)findViewById(R.id.timePicker1);



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
                    case R.id.action_snooze:
                        Toast.makeText(AddAlarm.this, "Action Snooze Clicked", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        Button saveBtn = (Button)findViewById(R.id.save_alarm);
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        final Calendar calendar = Calendar.getInstance();

        if(!(loadRingtone().equals("NONE"))){
            myURI = loadRingtone();
        }


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getString.valueOf(pickTime.getHour());
                final String minute = String.valueOf(pickTime.getMinute());

                calendar.set(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                        pickTime.getHour(), pickTime.getMinute(), 0);

                Log.e(String.valueOf(calendar.getTimeInMillis()), " time in milis");

                Intent intent = new Intent(this, AlarmReceiver.class);
                intent.putExtra("ON", true);
                intent.putExtra("URI", myURI);
                intent.putExtra("DYNAMIC", false);
                intent.putExtra("TIME", 1);
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

                alarm_manager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),  pendingIntent);



                Toast.makeText(this, Hour + " " + minute, Toast.LENGTH_LONG).show();
            }
        });

        saveBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent()
                        .setType("*/*")
                        .setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select a file:"), 123);
                return true;
            }
        });

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==123 && resultCode==RESULT_OK) {
                Uri selectedfile = data.getData();
                aa = selectedfile;

                Toast.makeText(getApplicationContext(), String.valueOf("Ringtone Set Successfully"), Toast.LENGTH_SHORT).show();

                myURI = String.valueOf(selectedfile);

                saveRingtone(myURI);
            }
        }

    public void saveRingtone(String ringtoneURI){
        SharedPreferences sharedPreferences = getSharedPreferences("myData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("RINGTONE", ringtoneURI);
        editor.commit();
    }

    public String loadRingtone(){
        SharedPreferences sharedPreferences = getSharedPreferences("myData", MODE_PRIVATE);
        String str = sharedPreferences.getString("RINGTONE", "NONE");
        return (str);
    }


//        TimePicker timePicker1 = findViewById(R.id.timePicker1);
//        time = findViewById(R.id.withText);
////        Calendar calendar = Calendar.getInstance();
//        int hour = timePicker1.getCurrentHour();
//        int min = timePicker1.getCurrentMinute();
//        showTime(hour, min);
    }

    public void save_alarm(View view) {
        TimePicker timePicker1 = findViewById(R.id.timePicker1);

        time = findViewById(R.id.withText);
        int hour = timePicker1.getCurrentHour();
        int min = timePicker1.getCurrentMinute();

        Toast.makeText(AddAlarm.this, String.format("%s:%s", hour, min), Toast.LENGTH_SHORT).show();

        Alarm alarm = new Alarm(hour, min);
        MainActivity.alarms.add(String.valueOf(alarm));

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


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

//    }
}