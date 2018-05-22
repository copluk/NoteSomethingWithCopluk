package com.design.copluk.copluksample.model.googleMap;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by copluk on 2018/5/21.
 */

public class DirectionResults {
    @SerializedName("routes")
    private List<Route> routes;
    @SerializedName("status")
    private String status;
    @SerializedName("geocoded_waypoints")
    private Geo[] geocoded_waypoints;
    @SerializedName("error_message")
    private String error_message;


    public List<Route> getRoutes() {
        return routes;
    }

    public String getStatus() {
        return status;
    }

    public Geo[] getGeocoded_waypoints() {
        return geocoded_waypoints;
    }

    public String getErrorMessage() {
        return error_message;
    }


    public boolean isStatusOk() {
        return "OK".equals(getStatus());
    }
}

