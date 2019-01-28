package com.design.copluk.copluksample.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by copluk on 2017/5/22.
 */

public class timerReceiver extends BroadcastReceiver {
    public static final String RECEIVER_START = "start.timerReceiver";
    public static final String RECEIVER_DO_ONCE = "once.timerReceiver";
    //    private int reTryTime = 15*60*1000;
    private int reTryTime = 30 * 1000;
    private final Timer timer = new Timer();
    private TimerTask task;
    private Handler handler;

    @Override
    public void onReceive(final Context context, final Intent intent) {
        Log.e(RECEIVER_START, "Start");

        if (RECEIVER_START.equals(intent.getAction())) {
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // 要做的事情
                    super.handleMessage(msg);

                    if (msg.what == 1) {
                        context.sendBroadcast(intent);
                        Log.e(RECEIVER_START, "test Timer");
                    }

                }
            };

            setTimer();

        } else if (RECEIVER_DO_ONCE.equals(intent.getAction())) {
            Log.e(RECEIVER_DO_ONCE, RECEIVER_DO_ONCE);
        }


    }

    private void setTimer() {

        task = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };

        timer.schedule(task, reTryTime);
    }


}
