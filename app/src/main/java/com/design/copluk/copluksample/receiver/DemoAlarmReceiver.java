package com.design.copluk.copluksample.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by copluk on 2017/6/20.
 */

public class DemoAlarmReceiver extends BroadcastReceiver {
    public static final String START_RECEIVER = "DemoAlarmReceiver.start";
    final int REPEAT_TIME = 5 * 1000;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e(getClass().getName() , "QWERTYUIO" );

    }

}
