package com.design.copluk.copluksample;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.design.copluk.copluksample.adapter.ItemClickListener;
import com.design.copluk.copluksample.adapter.MainAdapter;
import com.design.copluk.copluksample.controller.ChartActivity;
import com.design.copluk.copluksample.controller.DemoLocalNotification;
import com.design.copluk.copluksample.controller.GoogleMapActivity;
import com.design.copluk.copluksample.controller.HariChartActivity;
import com.design.copluk.copluksample.controller.ScrollViewChangeHeightActivity;
import com.design.copluk.copluksample.model.ClickItem;
import com.design.copluk.copluksample.receiver.DemoAlarmReceiver;
import com.design.copluk.copluksample.view.DesktopItemDecoration;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final String TAG = getClass().getName();

    private List<ClickItem> clickItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(getApplicationContext());
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.e("Token","Token : " + token);


        clickItems.add(new ClickItem("Scroll to Chane View Height" , ScrollViewChangeHeightActivity.class));
        clickItems.add(new ClickItem("Chart Test" , ChartActivity.class));
        clickItems.add(new ClickItem("HariChart Test" , HariChartActivity.class));
        clickItems.add(new ClickItem("GoogleMap Test" , GoogleMapActivity.class));
//        sendBroadcast(new Intent(timerReceiver.RECEIVER_START));

        RecyclerView rcvMain = (RecyclerView) findViewById(R.id.mainRecyclerView);

        final MainAdapter adapter = new MainAdapter(this);
        rcvMain.setAdapter(adapter);

//        List<String> strings = new ArrayList<>();
//        strings.add("SetAlarm");
//        strings.add(1 , "Notification");
//        strings.add("Scroll to Chane View Height");
//        strings.add("Chart Test");
//        strings.add("HariChart Test");
//        strings.add("GoogleMap Test");


        View view = new View(this);
        view.setBackgroundColor(ContextCompat.getColor(this, R.color.main_text));
        view.setMinimumHeight(50);
        view.setMinimumWidth(50);
        adapter.setHander(view);

        adapter.setItemClick(setItemClickListener());

        adapter.setData(clickItems);

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

                startActivity(new Intent(MainActivity.this , clickItems.get(position).activity));

            }
        };
    }
}
