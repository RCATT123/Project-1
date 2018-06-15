package com.specifix.pureleagues.api.model;

import com.google.gson.annotations.SerializedName;

public class TemperatureResponse {
    @SerializedName("Temperature")
    private Temperature temperature;
    @SerializedName("MobileLink")
    private String mobileLink;

    public TemperatureResponse(Temperature temperature, String mobileLink) {
        this.temperature = temperature;
        this.mobileLink = mobileLink;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public String getMobileLink() {
        return mobileLink;
    }

    public void setMobileLink(String mobileLink) {
        this.mobileLink = mobileLink;
    }
}
