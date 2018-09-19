package com.Technivision.sweden.navigationdrawer.Alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by Ma7MouD on 2/3/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("we are in on receive:" ," wow");

        Toast.makeText(context, new Date().toString(),
                Toast.LENGTH_SHORT).show();


        // get string in the intent ...
        int index = intent.getExtras().getInt("index");

        // create an intent to Alarm Playing service ...
        Intent service_intent = new Intent(context , AlarmPlayingService.class);

        // pass the intent string to service ...
        service_intent.putExtra("index" , index);

        // start service ..
        context.startService(service_intent);
    }
}
