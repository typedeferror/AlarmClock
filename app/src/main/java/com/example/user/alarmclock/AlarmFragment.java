package com.example.user.alarmclock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class AlarmFragment extends android.app.Fragment {
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat ft = new SimpleDateFormat("h:mm");
    private AddAlarmIface useIface;
    private ToggleButton tgbMon;
    private ToggleButton tgbTue;
    private ToggleButton tgbWed;
    private ToggleButton tgbThu;
    private ToggleButton tgbFri;
    private ToggleButton tgbSat;
    private ToggleButton tgbSun;
    private TextView tvTime;
    private Calendar calendar;
    private TextView tvLabel;
    private int currentHour;
    private int currentMin;
    private boolean addNewAlarm = false;
    private ArrayList<Integer> days;
    private int currentId = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings_alarms, container, false);
    }


    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        initComponents(v);
        initEditAlarm();
    }

    private void initEditAlarm() {
        if (!addNewAlarm) {
            Alarm oldAlarm = useIface.getChooseAlarmsSettings(currentId);
            String oldTime = ft.format(oldAlarm.getTime());
            tvTime.setText(oldTime);
            String[] oldTimeFormat = oldTime.split(":");
            currentHour = Integer.parseInt(oldTimeFormat[0]);
            currentMin = Integer.parseInt(oldTimeFormat[1]);

            findDays(oldAlarm.getRepeat());
            tvLabel.setText(oldAlarm.getLabel());
        }
    }

    private String findDays(int[] days) {
        String newString = "";
        for (int day : days) {
            switch (day) {
                case 1:
                    tgbMon.setChecked(true);
                    break;
                case 2:
                    tgbTue.setChecked(true);
                    break;
                case 3:
                    tgbWed.setChecked(true);
                    break;
                case 4:
                    tgbThu.setChecked(true);
                    break;
                case 5:
                    tgbFri.setChecked(true);
                    break;
                case 6:
                    tgbSat.setChecked(true);
                    break;
                case 0:
                    tgbSun.setChecked(true);
                    break;
            }
        }
        return newString;
    }

    public void setAlarmId(int id) {
        currentId = id;
    }

    public void setAddNewAlarm(boolean addNewAlarm) {
        this.addNewAlarm = addNewAlarm;
    }

    private void onBackPressed() {
        if (tgbMon.isChecked())
            days.add(1);
        if (tgbTue.isChecked())
            days.add(2);
        if (tgbWed.isChecked())
            days.add(3);
        if (tgbThu.isChecked())
            days.add(4);
        if (tgbFri.isChecked())
            days.add(5);
        if (tgbSat.isChecked())
            days.add(6);
        if (tgbSun.isChecked())
            days.add(0);

        int[] finalDays = new int[days.size()];
        for (int i = 0; i < finalDays.length; i++) {
            finalDays[i] = days.get(i);
        }

        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, currentHour);
        calendar.set(Calendar.MINUTE, currentMin);
        calendar.set(Calendar.SECOND, 0);
        Date time = calendar.getTime();

        useIface.addNewAlarm(new Alarm(tvLabel.getText().toString(), time, false, finalDays, currentId), currentId, addNewAlarm);
        Toast.makeText(getActivity(), getString(R.string.alarmSaved), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        onBackPressed();
    }

    private void initComponents(View view) {

        days = new ArrayList<>(7);
        tvTime = view.findViewById(R.id.tvTime);
        tvTime.setOnClickListener(new SetTime(tvTime, getActivity()));
        tgbMon = view.findViewById(R.id.tgbMonday);
        tgbTue = view.findViewById(R.id.tgbTuesday);
        tgbWed = view.findViewById(R.id.tgbWednesday);
        tgbThu = view.findViewById(R.id.tgbThursday);
        tgbFri = view.findViewById(R.id.tgbFriday);
        tgbSat = view.findViewById(R.id.tgbSaturday);
        tgbSun = view.findViewById(R.id.tgbSunday);

        tvLabel = view.findViewById(R.id.tvLabel);

        ImageButton imgBtnLeft = view.findViewById(R.id.imgBtnLeft);
        ImageButton imgBtnRight = view.findViewById(R.id.imgBtnRight);

        imgBtnLeft.setOnClickListener(new imgBtnLeftListener());
        imgBtnRight.setOnClickListener(new imgBtnRightListener());

        tgbMon.setOnCheckedChangeListener(new DayAuditor());
        tgbTue.setOnCheckedChangeListener(new DayAuditor());
        tgbWed.setOnCheckedChangeListener(new DayAuditor());
        tgbThu.setOnCheckedChangeListener(new DayAuditor());
        tgbFri.setOnCheckedChangeListener(new DayAuditor());
        tgbSat.setOnCheckedChangeListener(new DayAuditor());
        tgbSun.setOnCheckedChangeListener(new DayAuditor());
        currentHour = 0;
        currentMin = 0;


    }

    private class imgBtnLeftListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            useIface.closeFragment();
        }
    }

    private class imgBtnRightListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            useIface.showAlarmsSettings();

        }
    }

    public void setCallBack(AddAlarmIface useIface) {
        this.useIface = useIface;
    }

    private class DayAuditor implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            TransitionDrawable transition = (TransitionDrawable) buttonView.getBackground();
            if (isChecked) {
                transition.startTransition(200);
            } else {
                transition.reverseTransition(200);
            }
        }
    }


    class SetTime implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

        private final TextView myView;
        private final Calendar myCalendar;


        SetTime(TextView textView, Context context) {
            this.myView = textView;
            this.myView.setOnClickListener(this);
            this.myCalendar = Calendar.getInstance();
        }


        @Override
        public void onClick(View view) {
            int min = myCalendar.get(Calendar.MINUTE);
            int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
            TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this, hour, min, false);
            timePickerDialog.setAccentColor(0xFF000000);
            timePickerDialog.show(getFragmentManager(), "TimePickerDialog");
        }


        @Override
        public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
            String hour;
            if (hourOfDay < 10) {
                hour = "0" + String.valueOf(hourOfDay);
            } else {
                hour = String.valueOf(hourOfDay);
            }

            String min;
            if (minute < 10) {
                min = "0" + String.valueOf(minute);
            } else {
                min = String.valueOf(minute);
            }
            String time = hour + ":" + min;
            currentHour = Integer.parseInt(hour);
            currentMin = Integer.parseInt(min);
            myView.setText(time);
            tvTime.setText(time);
        }
    }
}
