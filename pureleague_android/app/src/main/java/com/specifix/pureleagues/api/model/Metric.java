package com.specifix.pureleagues.api.model;

import com.google.gson.annotations.SerializedName;

public class Metric {
    @SerializedName("Value")
    private String value;
    @SerializedName("Unit")
    private String unit;
    @SerializedName("UnitType")
    private String unitType;

    public Metric(String value, String unit, String unitType) {
        this.value = value;
        this.unit = unit;
        this.unitType = unitType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    @Override
    public String toString() {
        return "Metric{" +
                "value='" + value + '\'' +
                ", unit='" + unit + '\'' +
                ", unitType='" + unitType + '\'' +
                '}';
    }
}
