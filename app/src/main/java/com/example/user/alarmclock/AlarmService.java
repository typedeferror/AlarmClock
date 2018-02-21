package com.example.user.alarmclock;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.Nullable;

import java.util.Calendar;
import java.util.Date;


public class AlarmService extends IntentService {


    private static final String CREATE = "CREATE";
    private static final String CANCEL = "CANCEL";
    private IntentFilter matcher;
    private Context context;


    public AlarmService(Context context) {
        super("AlarmClockService");
        this.context = context;
        matcher = new IntentFilter();
        matcher.addAction(CREATE);
        matcher.addAction(CANCEL);
    }

    @Override
    public void onHandleIntent(@Nullable Intent intent) {
        String action = null;
        if (intent != null) {
            action = intent.getAction();
        }
        int[] repeat = new int[0];
        if (intent != null) {
            repeat = intent.getIntArrayExtra("repeat");
        }
        Date time = null;
        if (intent != null) {
            time = (Date) intent.getSerializableExtra("time");
        }
        int id = 0;
        if (intent != null) {
            id = intent.getIntExtra("id", 0);
        }
        String label = null;
        if (intent != null) {
            label = intent.getStringExtra("label");
        }
        if (matcher.matchAction(action)) {
            execute(action, time, repeat, id, label);
        }
    }
    private void execute(String action, Date time, int[] repeat, int id, String label) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (CREATE.equals(action)) {
            if (repeat.length == 0) {
                Intent intent = new Intent(context, AlarmReceiver.class).putExtra("", label).putExtra("id", id);
                PendingIntent pi = PendingIntent.getBroadcast(context, id, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                if (time.getTime() < System.currentTimeMillis()) {
                    time.setTime(time.getTime() + 90000000);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (alarmManager != null) {
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time.getTime(), pi);
                    }
                } else {
                    if (alarmManager != null) {
                        alarmManager.set(AlarmManager.RTC_WAKEUP, time.getTime(), pi);
                    }
                }
            } else {
                for (int aRepeat : repeat) {
                    switch (aRepeat) {

                        case 1:
                            setAlarmSpecificDay(1, time, id, label);
                            break;
                        case 2:
                            setAlarmSpecificDay(2, time, id, label);
                            break;
                        case 3:
                            setAlarmSpecificDay(3, time, id, label);
                            break;
                        case 4:
                            setAlarmSpecificDay(4, time, id, label);
                            break;
                        case 5:
                            setAlarmSpecificDay(5, time, id, label);
                            break;
                        case 6:
                            setAlarmSpecificDay(6, time, id, label);
                            break;
                        case 0:
                            setAlarmSpecificDay(0, time, id, label);
                            break;
                    }
                }
            }
        } else if (CANCEL.equals(action)) {

            if (repeat.length == 0) {
                Intent intent = new Intent(context, AlarmReceiver.class).putExtra("", label);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                if (alarmManager != null) {
                    alarmManager.cancel(pendingIntent);
                }
            } else {
                for (int aRepeat : repeat) {
                    switch (aRepeat) {

                        case 1:
                            cancelRepeatAlarm(1, time, id);
                            break;
                        case 2:
                            cancelRepeatAlarm(2, time, id);
                            break;
                        case 3:
                            cancelRepeatAlarm(3, time, id);
                            break;
                        case 4:
                            cancelRepeatAlarm(4, time, id);
                            break;
                        case 5:
                            cancelRepeatAlarm(5, time, id);
                            break;
                        case 6:
                            cancelRepeatAlarm(6, time, id);
                            break;
                        case 0:
                            cancelRepeatAlarm(0, time, id);
                            break;
                    }
                }
            }
        }
    }
    private void cancelRepeatAlarm(int dayOfWeek, Date time, int id) {
        dayOfWeek++;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent
                .getBroadcast(context, id + (int) time.getTime() + dayOfWeek, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    public void setAlarmSpecificDay(int dayOfWeek, Date time, int id, String label) {
        dayOfWeek++;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class).putExtra("", label);
        PendingIntent pendingIntent = PendingIntent
                .getBroadcast(context, id + (int) time.getTime() + dayOfWeek, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 7);
        }
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager
                    .RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);
        }
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }
}
