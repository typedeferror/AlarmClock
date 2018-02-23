package com.example.user.alarmclock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class AlarmsSettings {

    private Gson gson;
    private Type type;
    private List<Alarm> alarmList;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;
    private AlarmService alarmService;


    @SuppressLint("CommitPrefEdits")
    public AlarmsSettings(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
        alarmService = new AlarmService(context);
        gson = new Gson();
        type = new TypeToken<List<Alarm>>() {
        }.getType();
    }

    public void addAlarmsSettings(Alarm alarm, int id, boolean addNewAlarm) {
        alarmList = null;
        if (gson.fromJson(sharedPreferences.getString("alarm", ""), type) == null) {
            alarmList = new ArrayList<>();
        } else {
            alarmList = gson.fromJson(sharedPreferences.getString("alarm", ""), type);
        }
        if (addNewAlarm) {
            alarmList.add(alarm);
        } else {
            activate(false, id);
            alarmList.remove(id);
            alarmList.add(id, alarm);
        }
        String jsonAlarm = gson.toJson(alarmList);
        editor.putString("alarm", jsonAlarm).apply();
    }

    public List<Alarm> getAlarmsSettings() {
        return gson.fromJson(sharedPreferences.getString("alarm", ""), type);
    }

    public Alarm getChooseAlarmsSettings(int id) {
        alarmList = null;
        alarmList = gson.fromJson(sharedPreferences.getString("alarm", ""), type);
        return alarmList.get(id);
    }

    public void activate(boolean on, int id) {
        alarmList = null;
        alarmList = gson.fromJson(sharedPreferences.getString("alarm", ""), type);
        Alarm alarm = alarmList.get(id);
        alarm.setActive(on);
        int[] repeat = alarm.getRepeat();

        if (on) {
            alarmService.onHandleIntent(new Intent(context, AlarmReceiver.class)
                    .putExtra("time", alarm.getTime()).setAction("CREATE")
                    .putExtra("repeat", repeat).putExtra("id", id).putExtra("label", alarm.getLabel()));
        } else {
            alarmService.onHandleIntent(new Intent(context, AlarmReceiver.class)
                    .putExtra("time", alarm.getTime()).setAction("CANCEL")
                    .putExtra("repeat", repeat).putExtra("id", id).putExtra("label", alarm.getLabel()));
        }
        String jsonAlarm = gson.toJson(alarmList);
        editor.putString("alarm", jsonAlarm).apply();
    }
    public void removeAlarm(int id) {
        alarmList = null;
        alarmList = gson.fromJson(sharedPreferences.getString("alarm", ""), type);
        activate(false, id);
        alarmList.remove(id);
        String jsonAlarm = gson.toJson(alarmList);
        editor.putString("alarm", jsonAlarm).commit();
    }
}
