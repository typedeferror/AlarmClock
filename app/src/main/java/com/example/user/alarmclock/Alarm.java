package com.example.user.alarmclock;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

public class Alarm extends Service{

    private int hour;
    private int min;
    AlarmManager alarm_manager;
    Ringtone ringtone;
    PendingIntent pendingIntent;

    public Alarm(int hour, int min) {
        this.hour = hour;
        this.min = min;
    }

    public int getHour() {
        return hour;
    }

    public int getMin() {
        return min;
    }

    public String toString() {
        return String.format("%s:%s", this.hour, this.min);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        boolean Alarm_State = intent.getBooleanExtra("ON", false);
        boolean Alarm_Type = intent.getBooleanExtra("DYNAMIC", false);
        long Time_In_Millis = intent.getLongExtra("TIME", 0);
        String myURI = intent.getStringExtra("URI");

        if (Alarm_Type){
            alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent i = new Intent(getApplicationContext(), AlarmReceiver.class);
            i.putExtra("ON", true);
            i.putExtra("URI", myURI);
            i.putExtra("DYNAMIC", true);
            i.putExtra("TIME", System.currentTimeMillis());
            Log.e("Service_State: ", String.valueOf(Alarm_State));
            Log.e("Service_Dynamic: ", String.valueOf(Alarm_Type));
            Log.e("Service_Time: ", String.valueOf(Time_In_Millis));
            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, i, 0);
            alarm_manager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000*60*5,  pendingIntent);
            return START_NOT_STICKY;
        }

        Log.e("Alarm State:", String.valueOf(Alarm_State));

        Toast.makeText(getBaseContext(), "In service", Toast.LENGTH_SHORT).show();
        Log.e("In service","");
        //NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent_main = new Intent(this.getApplicationContext(), MainActivity.class);
        Uri alarmUri;
        //alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if(!(myURI.equals("NONE"))){
            alarmUri = Uri.parse(myURI);
        }
        else{
            alarmUri = Uri.parse("android.resource://" + "com.kumailn.alarmclock/" + "raw/fellow");
        }

        ringtone = RingtoneManager.getRingtone(getApplicationContext(), alarmUri);
        ringtone.setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build());

        ringtone.play();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent_main, PendingIntent.FLAG_CANCEL_CURRENT);
        Notification notificationPopup = new Notification.Builder(this).setContentTitle("Alarm is ON!").setContentText("Click here")
                .setContentIntent(pendingIntent).setAutoCancel(true).setSmallIcon(R.drawable.ic_launcher_foreground).setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL).build();
        notificationManager.notify(0, notificationPopup);


        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(ringtone != null){
            ringtone.stop();
            Log.e("Ringtone"," success fully stopped");
        }

        Log.e("Service","Now Shutting Down");
        this.stopSelf();
    }
}
