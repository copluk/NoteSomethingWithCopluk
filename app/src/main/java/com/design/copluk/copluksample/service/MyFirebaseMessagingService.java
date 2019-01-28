package com.design.copluk.copluksample.service;

import android.app.NotificationManager;
import android.content.Context;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.design.copluk.copluksample.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by copluk on 2017/7/6.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";



    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Message data payload: " + remoteMessage.getData());
            Map<String, String> messageData = remoteMessage.getData();


            if (true) {
                setNotification(messageData.get("title") , messageData.get("content"));
                Log.d(TAG, "payload: " + messageData);
            } else {
                handleNow();
            }

        }

        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

    }
    // [END receive_message]

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    private void setNotification(String title , String content) {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder
                .setSmallIcon(R.drawable.mabinogi_2_32)
                .setContentTitle(title)
                .setContentText(content);


        manager.notify(-99 , notificationBuilder.build());
    }

}
