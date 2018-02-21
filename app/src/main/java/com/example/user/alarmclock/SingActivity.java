package com.example.user.alarmclock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class SingActivity extends AppCompatActivity {

    private final long[] pattern = {500, 500, 1000};
    private String label = "";
    private Vibrator vibrator;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private MediaPlayer mediaPlayer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (powerManager != null) {
            if (powerManager.isScreenOn()) getBaseContext();
            else {
                turnScreenOn();
            }
        }

        label = getIntent().getStringExtra("label");
        int id = getIntent().getIntExtra("id", 100000005);
        setContentView(R.layout.activity_sing);
        initComponents(id);
    }

    private void turnScreenOn() {
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
    }

    @SuppressLint("CommitPrefEdits")
    private void initComponents(int id) {
        TextView alarmText = findViewById(R.id.AlarmText);
        Button tempOff = findViewById(R.id.tempOff);
        alarmText.setText(label);
        PlayRingtone();
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(pattern, 0);
        }
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        editor = sharedPreferences.edit();
        editRingAlarm(id);
        tempOff.setOnClickListener(new OffListener());
    }


    private class OffListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            vibrator.cancel();
            mediaPlayer.stop();
            System.exit(0);
        }
    }

    private void setVolume(Uri song) {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        float vol = 0;
        if (audioManager != null) {
            vol = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
        }
        MediaPlayer mediaPlayer2 = MediaPlayer.create(getApplicationContext(), song);
        mediaPlayer2.setVolume(vol, vol);
        mediaPlayer2.start();
        mediaPlayer = mediaPlayer2;
    }

    private void editRingAlarm(int id) {

        if (id != 100000000) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Alarm>>() {
            }.getType();
            List<Alarm> alarmList = gson.fromJson(sharedPreferences.getString("alarm", ""), type);
            Alarm alarm = alarmList.get(id);
            alarmList.remove(id);
            alarm.setActive(false);
            alarmList.add(id, alarm);
            String jsonAlarm = gson.toJson(alarmList);
            editor.putString("alarm", jsonAlarm).apply();
        }
    }


    private void PlayRingtone() {
        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alert == null) {
            alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (alert == null) {
                alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }
        setVolume(alert);
    }


}
