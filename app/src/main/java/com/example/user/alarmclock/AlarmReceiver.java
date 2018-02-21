package com.example.user.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


public class AlarmReceiver extends BroadcastReceiver {

    private SharedPreferences sharedPreferences;
    private AlarmService alarmService;

    @Override
    public void onReceive(Context context, Intent intent) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        if (sharedPreferences.contains("alarm")) {
            alarmService = new AlarmService(context);
            activate(context);
        }
        String label = intent.getStringExtra("");
        int id = intent.getIntExtra("id", 100000000);
        Intent intent2 = new Intent(context, SingActivity.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent2.putExtra("", label);
        intent2.putExtra("id", id);
        context.startActivity(intent2);
    }

    private void activate(Context context) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Alarm>>() {
        }.getType();
        List<Alarm> alarmList = gson.fromJson(sharedPreferences.getString("alarm", ""), type);

        for (Alarm anAlarmList : alarmList) {
            if (anAlarmList.isActive()) {

                alarmService.onHandleIntent
                        (new Intent
                                (context, AlarmReceiver.class)
                                .setAction("CREATE")
                                .putExtra("time", anAlarmList.getTime())
                                .putExtra("repeat", anAlarmList.getRepeat())
                                .putExtra("id", anAlarmList.getId())
                                .putExtra("", anAlarmList.getLabel()));
            }
        }
    }
}
