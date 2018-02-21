package com.example.user.alarmclock;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {

    private Fragment currentAlarmFragment;
    private Fragment currentHelperAlarmFragment;
    private FragmentManager fragmentManager;
    private AlarmsSettings alarmsSettings;
    private ListView alarmList;
    private int currentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();

    }

    private void initComponents() {

        FloatingActionButton btnFab = findViewById(R.id.fabAlarmsSettings);
        ButtonListener buttonListener = new ButtonListener();
        btnFab.setOnClickListener(buttonListener);
        fragmentManager = getFragmentManager();
        alarmsSettings = new AlarmsSettings(getApplicationContext());
        alarmList = findViewById(R.id.alarmList);
        initAlarmList();
        alarmList.setOnItemClickListener(new AlarmListListener());
        alarmList.setOnItemLongClickListener(new AlarmListLongListener());
    }


    private void initAlarmList() {
        List<Alarm> alarms = alarmsSettings.getAlarmsSettings();
        ArrayList<String> extractedAlarms = new ArrayList<>();
        int i = 0;
        String newString;
        String[] finalAlarms = {};
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("h:mm");
        ArrayList<Boolean> enableOrNot = new ArrayList<>();

        if (alarms != null) {
            while (i < alarms.size()) {
                newString = format.format(alarms.get(i).getTime()) + " " +
                        findDays(alarms.get(i).getRepeat()) + " " + alarms.get(i).getLabel();
                extractedAlarms.add(newString);
                enableOrNot.add(alarms.get(i).isActive());
                i++;
            }
            finalAlarms = extractedAlarms.toArray(new String[extractedAlarms.size()]);
        }
        AtomicReference<ArrayList<Object>> alarmsLists = new AtomicReference<>(new ArrayList<>());
        Collections.addAll(alarmsLists.get(), finalAlarms);
        ArrayAdapter<String> arrAdapter = new CustomDialogAdapter(getApplicationContext(), finalAlarms, enableOrNot);
        alarmList.setAdapter(arrAdapter);
        currentId = arrAdapter.getCount();
    }

    private String findDays(int[] days) {
        StringBuilder newString = new StringBuilder();
        for (int day : days) {
            switch (day) {
                case 1:
                    newString.append(this.getString(R.string.mon));
                    break;
                case 2:
                    newString.append(this.getString(R.string.tue));
                    break;
                case 3:
                    newString.append(this.getString(R.string.wed));
                    break;
                case 4:
                    newString.append(this.getString(R.string.thu));
                    break;
                case 5:
                    newString.append(this.getString(R.string.fri));
                    break;
                case 6:
                    newString.append(this.getString(R.string.sat));
                    break;
                case 0:
                    newString.append(this.getString(R.string.sun));
                    break;
            }
        }
        return newString.toString();
    }


    private void changeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(android.R.id.content, fragment).commit();
        fragmentManager = getFragmentManager();
    }


    private void removeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.hide(fragment).commit();


    }

    public void onBackPressed() {

        if (currentHelperAlarmFragment != null) {
            removeFragment(currentHelperAlarmFragment);
            currentHelperAlarmFragment = null;

        } else if (currentAlarmFragment != null) {
            removeFragment(currentAlarmFragment);
            currentAlarmFragment = null;
        } else {
            super.onBackPressed();
        }

    }

    private void startAlarmFragment(int id, boolean addNewAlarm) {
        currentAlarmFragment = new AlarmFragment();
        ((AlarmFragment) currentAlarmFragment).setCallBack(new AlarmListener());
        ((AlarmFragment) currentAlarmFragment).setAlarmId(id);
        ((AlarmFragment) currentAlarmFragment).setAddNewAlarm(addNewAlarm);
        changeFragment(currentAlarmFragment);
    }

    private void startHelperAlarmFragment() {
        currentHelperAlarmFragment = new HelperAlarmFragment();
        ((HelperAlarmFragment) currentHelperAlarmFragment).setCallBack(new AlarmHelperListener());
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(android.R.id.content, currentHelperAlarmFragment).commit();
        fragmentManager = getFragmentManager();


    }


    private class AlarmListListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            startAlarmFragment(position, false);
        }
    }

    private class AlarmListLongListener implements AdapterView.OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            new AlertDialog.Builder(MainActivity.this, R.style.AppCompatAlertDialogStyle)
                    .setMessage(getString(R.string.dialogMessage))
                    .setPositiveButton(getString(R.string.dialogPositive), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            alarmsSettings.removeAlarm(position);
                            initAlarmList();
                        }
                    })
                    .setNegativeButton(getString(R.string.dialogNegative), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return true;
        }
    }

    private class AlarmHelperListener implements ClosingIface {

        @Override
        public void closeFragment() {
            removeFragment(currentHelperAlarmFragment);
        }
    }

    private class AlarmListener implements AddAlarmIface {
        @Override
        public void addNewAlarm(Alarm alarm, int id, boolean addNewAlarm) {
            alarmsSettings.addAlarmsSettings(alarm, id, addNewAlarm);
            initAlarmList();
        }

        @Override
        public Alarm getChooseAlarmsSettings(int id) {
            return alarmsSettings.getChooseAlarmsSettings(id);
        }

        @Override
        public void closeFragment() {
            removeFragment(currentAlarmFragment);
        }

        @Override
        public void showAlarmsSettings() {
            startHelperAlarmFragment();
        }
    }


    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.fabAlarmsSettings) {
                startAlarmFragment(currentId++, true);
            }
        }
    }


    private class CustomDialogAdapter extends ArrayAdapter<String> {
        ArrayList<Boolean> enableOrNo;

        CustomDialogAdapter(Context context, String[] items, ArrayList<Boolean> enableOrNot) {
            super(context, 0, items);
            this.enableOrNo = enableOrNot;
        }

        @SuppressLint({"ViewHolder", "InflateParams"})
        @NonNull
        @Override
        public View getView(final int position, View convertView,
                            @NonNull ViewGroup parent) {
            View view = null;
            final TextView arrayText;

            LayoutInflater view2 = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (view2 != null) {
                view = view2.inflate(R.layout.spinner_row_main, null);
            }
            assert view != null;
            arrayText = view.findViewById(R.id.spinnerText);
            arrayText.setText(getItem(position));
            Switch control = view.findViewById(R.id.switchActiveList);

            if (enableOrNo.get(position)) {
                control.toggle();
            }
            control.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        alarmsSettings.activate(true, position);
                        Toast.makeText(getApplicationContext(), getString(R.string.alarmSet), Toast.LENGTH_SHORT).show();
                    } else {
                        alarmsSettings.activate(false, position);
                        Toast.makeText(getApplicationContext(), getString(R.string.alarmCanceled), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            return view;
        }
    }
}
