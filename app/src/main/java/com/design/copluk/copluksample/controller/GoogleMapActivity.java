package com.design.copluk.copluksample.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.design.copluk.copluksample.R;
import com.design.copluk.copluksample.model.googleMap.DirectionResults;
import com.design.copluk.copluksample.model.googleMap.Route;
import com.design.copluk.copluksample.util.AlertUtil;
import com.design.copluk.copluksample.util.GoogleMapDirectionUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.design.copluk.copluksample.util.GoogleMapDirectionUtil.decodePolyLines;
import static com.design.copluk.copluksample.util.GoogleMapDirectionUtil.directionSetting;

/**
 * Created by copluk on 2018/5/21.
 */

public class GoogleMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private EditText edtWhereRUGo;
    private Button btnGo;
    private RequestQueue mQueue;
//    private DirectionResults mDirectionData;
//    private List<Polyline> mPolyline = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_google_map_demo);

        //create volleyQueue
        mQueue = Volley.newRequestQueue(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        edtWhereRUGo = findViewById(R.id.edtWhereRUGo);
        btnGo = findViewById(R.id.btnGo);

        edtWhereRUGo.setText("松山車站");

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(edtWhereRUGo.getText().toString())) {
                    try {
                        startDirections(edtWhereRUGo.getText().toString());
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng startLatLng = new LatLng(25.0470289, 121.515987);
        mMap.addMarker(new MarkerOptions().position(startLatLng).title("start"));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(14));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(startLatLng));

        mMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline polyline) {
                Route data = (Route) polyline.getTag();


                if (data != null) {
                    AlertDialog.Builder builder = null;
                    builder = new AlertDialog.Builder(GoogleMapActivity.this)
                            .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setMessage("Distance :  " + data.getLegs().get(0).getDistance().getText() +
                                    " \nDuration :  " + data.getLegs().get(0).getDuration().getText());
                    builder.create().show();
                }


            }
        });

    }

    private void startDirections(String whereRUGo) throws UnsupportedEncodingException {
        StringRequest getRequest = new StringRequest(
                directionSetting(this, "25.0470289,121.515987", whereRUGo,
                        GoogleMapDirectionUtil.MODE_DRIVING, true, GoogleMapDirectionUtil.LANGUAGE_TW),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        mMap.clear();

                        Gson gson = new Gson();
                        DirectionResults mDirectionData = gson.fromJson(s, DirectionResults.class);

                        if (mDirectionData.isStatusOk()) {

                            for (int i = 0; i < mDirectionData.getRoutes().size(); i++) {
                                List<LatLng> polyList = decodePolyLines(mDirectionData.getRoutes().get(i).getOverviewPolyLine().getPoints());


                                PolylineOptions polylineOptions = new PolylineOptions();
                                polylineOptions.addAll(polyList);
                                polylineOptions.clickable(true);
                                polylineOptions.width(15f);
                                polylineOptions.geodesic(true);

                                if (i == 0) {
                                    polylineOptions.color(Color.argb(255, 0, 0, 255));
                                    polylineOptions.zIndex(999f);
                                } else {
                                    polylineOptions.color(Color.argb(255, 150, 150, 150));
                                    polylineOptions.zIndex(i);
                                }

                                Polyline polyline = mMap.addPolyline(polylineOptions);
                                polyline.setTag(mDirectionData.getRoutes().get(i));

                            }
                        } else {
                            Toast.makeText(GoogleMapActivity.this, "Oh! There is something wrong!",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("onErrorResponse", "onErrorResponse : " + volleyError.getLocalizedMessage());
                    }
                });
        mQueue.add(getRequest);
    }

}

