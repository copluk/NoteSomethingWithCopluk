package com.design.copluk.copluksample.model.googleMap;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by copluk on 2018/5/21.
 */

public class DirectionResults {
    @SerializedName("routes")
    private List<Route> routes;

    public List<Route> getRoutes() {
        return routes;
    }
}

