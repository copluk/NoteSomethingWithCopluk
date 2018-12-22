package com.design.copluk.copluksample.service;

import android.app.IntentService;
import android.content.Intent;
import androidx.annotation.Nullable;
import android.util.Log;

import com.design.copluk.copluksample.receiver.timerReceiver;

/**
 * Created by copluk on 2017/6/20.
 */

public class DemoIntentService extends IntentService {

    private int testInt = 3;

    public DemoIntentService() {
        super("DemoIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.e("afasd" , testInt + "");
        if(testInt == 0){
            return;
        }else {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendBroadcast( new Intent(timerReceiver.RECEIVER_DO_ONCE ));
            testInt --;
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(getClass().getName(), "onDestroy()");
    }

}
