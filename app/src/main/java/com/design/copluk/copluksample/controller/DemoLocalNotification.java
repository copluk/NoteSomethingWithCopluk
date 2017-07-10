package com.design.copluk.copluksample.controller;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RemoteViews;

import com.design.copluk.copluksample.R;
import com.design.copluk.copluksample.adapter.ItemClickListener;
import com.design.copluk.copluksample.adapter.MainAdapter;
import com.design.copluk.copluksample.view.DesktopItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by copluk on 2017/6/23.
 */

public class DemoLocalNotification extends AppCompatActivity implements ItemClickListener {

    private int notifiID = 1;
    int replaceNotifiID = 0;
    int styleNotifiID = -1;
    int retoveViewNotifiID = -2;
    String notifiTitle = "Copluk";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        RecyclerView rcvMain = (RecyclerView) findViewById(R.id.mainRecyclerView);

        final MainAdapter adapter = new MainAdapter(this);
        rcvMain.setAdapter(adapter);

        List<String> strings = new ArrayList<>();
        strings.add(0, "單筆不重複通知");
        strings.add(1, "大欄通知");
        strings.add(2, "RemoteView, 自訂樣式通知");
        for (int i = 0; i < 5; i++) {
            strings.add("我會連發發發");
        }


        adapter.setData(strings);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter.isHasHander() && position == 0 || adapter.isHasFooter() && position == adapter.getItemCount())
                    return 2;

                return 2;
            }
        });
        RecyclerView.ItemDecoration mItemDecoration = new DesktopItemDecoration(Color.GRAY, 3, this);

        rcvMain.setLayoutManager(layoutManager);
        rcvMain.addItemDecoration(mItemDecoration);
        adapter.setItemClick(this);

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onItemClicked(View view, int position) {
        NotificationManager manager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);

        Notification notification;
        switch (position) {
            default:
                notificationBuilder
                        .setSmallIcon(R.drawable.btn_stat_notify_template)
                        .setContentTitle(notifiTitle)
                        .setContentText("Notification ID : " + String.valueOf(notifiID));

                notification = notificationBuilder.build();
                manager.notify(notifiID, notification);
                notifiID++;
                break;

            case 0:
                notificationBuilder
                        .setSmallIcon(R.drawable.btn_stat_notify_template_no_bg)
                        .setContentTitle(notifiTitle)
                        .setContentText("SingleNotification  ID : " + replaceNotifiID)
                        .setDefaults(Notification.DEFAULT_LIGHTS)
                        .setLights(0xff00ff00, 300, 1000);


                notification = notificationBuilder.build();
                manager.notify(replaceNotifiID, notification);
                break;

            case 1:
                NotificationCompat.InboxStyle inboxStyle =
                        new NotificationCompat.InboxStyle();

                inboxStyle
                        .addLine("測試測試測試測試測試測試測試測試測試測試測試測試測試測試測試測試測試測試測試測試測試測試測試測試測試測試測試測試測試測試")
                        .addLine("TextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextText")
                        .setBigContentTitle("BigContentTitle")
                        .setSummaryText("Hot");

                notificationBuilder
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(notifiTitle)
                        .setContentText("InboxStyle ID : " + "-1")
                        .setStyle(inboxStyle)
                        .setDefaults(Notification.DEFAULT_LIGHTS);

                notification = notificationBuilder.build();
                manager.notify(styleNotifiID, notification);
                break;

            case 2:
                RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_lauge_view);

                notificationBuilder
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(notifiTitle)
                        .setContentText("InboxStyle ID : " + String.valueOf(retoveViewNotifiID))
                        .setContent(remoteViews)
                        .setCustomBigContentView(remoteViews)
                        .setAutoCancel(false)
                        .setOngoing(true)
                        .setPriority(Notification.PRIORITY_MAX);

                notification = notificationBuilder.build();
                manager.notify(retoveViewNotifiID, notification);
                break;

        }


    }
}
