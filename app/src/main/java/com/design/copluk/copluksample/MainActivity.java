package com.design.copluk.copluksample;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.design.copluk.copluksample.adapter.ItemClickListener;
import com.design.copluk.copluksample.adapter.MainAdapter;
import com.design.copluk.copluksample.controller.ChartActivity;
import com.design.copluk.copluksample.controller.DemoLocalNotification;
import com.design.copluk.copluksample.controller.HariChartActivity;
import com.design.copluk.copluksample.controller.ScrollViewHeightActivity;
import com.design.copluk.copluksample.receiver.DemoAlarmReceiver;
import com.design.copluk.copluksample.view.DesktopItemDecoration;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final String TAG = getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(getApplicationContext());
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.e("Token","Token : " + token);


//        sendBroadcast(new Intent(timerReceiver.RECEIVER_START));

        RecyclerView rcvMain = (RecyclerView) findViewById(R.id.mainRecyclerView);

        final MainAdapter adapter = new MainAdapter(this);
        rcvMain.setAdapter(adapter);

        List<String> strings = new ArrayList<>();
        strings.add("SetAlarm");
        strings.add(1 , "Notification");
        strings.add("Scroll to Chane View Height");
        strings.add("Chart Test");
        strings.add("HariChart Test");
        strings.add("GoogleMap Test");
        for (int i = 0; i < 5; i++) {

            strings.add(String.valueOf(strings.size()) +
                    " : " +
                    String.valueOf(strings.size()));
        }

        View view = new View(this);
        view.setBackgroundColor(ContextCompat.getColor(this, R.color.main_text));
        view.setMinimumHeight(50);
        view.setMinimumWidth(50);
        adapter.setHander(view);

        adapter.setItemClick(setItemClickListener());

        adapter.setData(strings);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter.isHasHander() && position == 0 || adapter.isHasFooter() && position == adapter.getItemCount())
                    return 2;

                return 1;
            }
        });
        RecyclerView.ItemDecoration mItemDecoration = new DesktopItemDecoration(Color.GRAY, 3, this);

        rcvMain.setLayoutManager(layoutManager);
        rcvMain.addItemDecoration(mItemDecoration);

    }

    private ItemClickListener setItemClickListener(){
        return new ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {

                switch (position) {
                    case 0:
                        Log.e(TAG, "alarm start");
                        Intent intentAlarm = new Intent(DemoAlarmReceiver.START_RECEIVER);
                        PendingIntent pending = PendingIntent.getBroadcast(MainActivity.this, 0, intentAlarm, 0);
                        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        alarm.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 10 * 1000, 5 * 1000, pending);
                        break;

                    case 1:

                        startActivity(new Intent(MainActivity.this , DemoLocalNotification.class));

                        break;

                    case 2:

                        startActivity(new Intent(MainActivity.this , ScrollViewHeightActivity.class));

                        break;

                    case 3:

                        startActivity(new Intent(MainActivity.this , ChartActivity.class));

                        break;

                    case 4:

                        startActivity(new Intent(MainActivity.this , HariChartActivity.class));

                        break;

                    case 5: //google map

                        startActivity(new Intent(MainActivity.this , HariChartActivity.class));

                        break;


                    default:

                        Toast.makeText(MainActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();

                        break;
                }

            }
        };
    }
}
