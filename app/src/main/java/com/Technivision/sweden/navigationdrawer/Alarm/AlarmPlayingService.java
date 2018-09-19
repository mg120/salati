package com.Technivision.sweden.navigationdrawer.Alarm;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.Technivision.sweden.navigationdrawer.R;

/**
 * Created by Ma7MouD on 2/4/2018.
 */

public class AlarmPlayingService extends Service {

    public static MediaPlayer media_song;
    boolean isRunning;
    int startId;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       // Log.e("in the service", " on start command");

        int index = intent.getExtras().getInt("index");
        String salah_txt = next_salahtxt(index);
        runAgain(salah_txt , index);

        return super.onStartCommand(intent, flags, startId);
    }

    public String next_salahtxt(int index){

        String salah_txt = null ;
        if (index == 0) {
            salah_txt = "FAJR IN" ;
        } else if (index == 1) {
            salah_txt ="ZUHR IN" ;
        } else if (index == 2) {
            salah_txt = "ASER IN" ;
        } else if (index == 3) {
            salah_txt = "MAGHRIB IN";
        } else if (index == 4) {
            salah_txt = "ISHA IN";
        }
        return salah_txt ;
    }

    public void runAgain(String salah_txt , int index) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setTicker("My notification")
                .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                .setContentText("SALAT " + salah_txt+" Now")
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, mBuilder.build());

//        if (index == 0) {
//            // AZAN EL FAJR ...
//            media_song = MediaPlayer.create(this, R.raw.fajr_azan);
//            media_song.start();
//
//        } else {
//            media_song = MediaPlayer.create(this, R.raw.azan1);
//            media_song.start();
//        }
    }

    @Override
    public void onDestroy() {
       // Log.e("in the service", "on Destroy called");
        super.onDestroy();
        this.isRunning = false;
    }
}
