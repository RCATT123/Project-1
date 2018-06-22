package com.specifix.pureleagues.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FixtureResponse {

    private Fixtures fixtures;

    public Fixtures getFixtures() {
        return fixtures;
    }

    public class Fixtures
    {
        @SerializedName("fixtures")
        private ArrayList<Fixture> blouseLengths;

        public void setBlouseLengths(ArrayList<Fixture> blouseLengths) {
            this.blouseLengths = blouseLengths;
        }

        public ArrayList<Fixture> getBlouseLengths() {
            return blouseLengths;
        }
    }

}
