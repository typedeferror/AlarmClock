package com.example.user.alarmclock;



public interface AddAlarmIface {

    void addNewAlarm(Alarm alarm, int id, boolean addNewAlarm);

    Alarm getChooseAlarmsSettings(int id);
    void closeFragment();
}
