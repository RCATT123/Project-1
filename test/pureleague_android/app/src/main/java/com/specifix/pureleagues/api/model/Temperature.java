package com.specifix.pureleagues.api.model;

import com.google.gson.annotations.SerializedName;

public class Temperature {
    @SerializedName("Metric")
    private Metric metric;

    public Temperature(Metric metric) {
        this.metric = metric;
    }

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "metric=" + metric +
                '}';
    }
}
