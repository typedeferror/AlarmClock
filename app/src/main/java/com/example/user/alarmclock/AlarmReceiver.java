package com.example.user.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "In Receiver", Toast.LENGTH_SHORT).show();
        Log.e("In the receiver", "");

        boolean Alarm_State = intent.getBooleanExtra("ON", false);
        boolean Alarm_Type = intent.getBooleanExtra("DYNAMIC", false);
        int Time_In_Millis = intent.getIntExtra("TIME", 0);
        String myURI = intent.getStringExtra("URI");

        Intent serviceIntent = new Intent(context, Alarm.class);
        serviceIntent.putExtra("URI", myURI).putExtra("DYNAMIC", true).putExtra("TIME", Time_In_Millis).putExtra("ON", Alarm_State);

        Log.e("Dynamic?: ", String.valueOf(Alarm_Type));
        Log.e("On?: ", String.valueOf(Alarm_State));
        Log.e("Time: ", String.valueOf(Time_In_Millis));

        context.startService(serviceIntent);
    }
}
