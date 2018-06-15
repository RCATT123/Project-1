package com.specifix.pureleagues.api.model;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {

    @SerializedName("Key")
    private String key;
    @SerializedName("EnglishName")
    private String englishName;
    @SerializedName("PrimaryPostalCode")
    private String primaryPostalCode;

    public WeatherResponse(String key, String englishName, String primaryPostalCode) {
        this.key = key;
        this.englishName = englishName;
        this.primaryPostalCode = primaryPostalCode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getPrimaryPostalCode() {
        return primaryPostalCode;
    }

    public void setPrimaryPostalCode(String primaryPostalCode) {
        this.primaryPostalCode = primaryPostalCode;
    }

    @Override
    public String toString() {
        return "WeatherResponse{" +
                "key='" + key + '\'' +
                ", englishName='" + englishName + '\'' +
                ", primaryPostalCode='" + primaryPostalCode + '\'' +
                '}';
    }
}
