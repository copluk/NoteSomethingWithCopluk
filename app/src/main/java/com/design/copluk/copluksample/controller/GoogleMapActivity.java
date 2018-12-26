package com.design.copluk.copluksample.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import dagger.android.AndroidInjection;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.design.copluk.copluksample.network.ApiRoute;
import com.design.copluk.copluksample.network.AppClientManager;
import com.design.copluk.copluksample.R;
import com.design.copluk.copluksample.model.googleMap.DirectionResults;
import com.design.copluk.copluksample.model.googleMap.Route;
import com.design.copluk.copluksample.util.GoogleMapDirectionUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.inject.Inject;

import static com.design.copluk.copluksample.util.GoogleMapDirectionUtil.decodePolyLines;

/**
 * Created by copluk on 2018/5/21.
 */

public class GoogleMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private EditText edtWhereRUGo;
    private Button btnGo;

    @Inject
    ApiRoute apiRoute;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_google_map_demo);

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

    private void successDirections(DirectionResults directionResults) {
        mMap.clear();

        if (directionResults.isStatusOk()) {

            for (int i = 0; i < directionResults.getRoutes().size(); i++) {
                List<LatLng> polyList = decodePolyLines(directionResults.getRoutes().get(i).getOverviewPolyLine().getPoints());


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
                polyline.setTag(directionResults.getRoutes().get(i));

            }
        } else {
            Toast.makeText(GoogleMapActivity.this, "Oh! There is something wrong!",
                    Toast.LENGTH_SHORT).show();
        }

    }

    private void startDirections(String whereRUGo) throws UnsupportedEncodingException {

        apiRoute.getDirection(
                new GoogleMapDirectionUtil(this,
                        GoogleMapDirectionUtil.Mode.DRIVING,
                        GoogleMapDirectionUtil.Language.TW,
                        true)
                        .OriginToEndUrl("25.0470289,121.515987", whereRUGo))
                .enqueue(new retrofit2.Callback<DirectionResults>() {
                    @Override
                    public void onResponse(retrofit2.Call<DirectionResults> call, final retrofit2.Response<DirectionResults> response) {
                        GoogleMapActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                successDirections(response.body());
                            }
                        });
                    }

                    @Override
                    public void onFailure(retrofit2.Call<DirectionResults> call, Throwable t) {
                        Log.e("onErrorResponse", "onErrorResponse : " + t.getMessage());
                    }
                });

    }

}


