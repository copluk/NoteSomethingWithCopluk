package com.design.copluk.copluksample.model.googleMap;

import java.util.List;

public class Legs {
    private List<Steps> steps;
    private Distance distance;
    private Duration duration;

    public List<Steps> getSteps() {
        return steps;
    }

    public Distance getDistance() {
        return distance;
    }

    public Duration getDuration() {
        return duration;
    }
}
