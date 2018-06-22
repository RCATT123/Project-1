package com.specifix.pureleagues.api.model;

import com.google.gson.annotations.SerializedName;

public class Fixture {
    @SerializedName("id")
    private String id;

    @SerializedName("date")
    private String date;

    @SerializedName("time")
    private String time;

    @SerializedName("round")
    private String round;

    @SerializedName("home_name")
    private String home_name;

    @SerializedName("away_name")
    private String away_name;

    @SerializedName("location")
    private String location;

    @SerializedName("league_id")
    private String league_id;

    @SerializedName("home_id")
    private String home_id;

    @SerializedName("away_id")
    private String away_id;

    public Fixture(String id, String date, String time, String round, String home_name, String away_name, String location,
                           String league_id, String home_id, String away_id) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.round = round;
        this.home_name = home_name;
        this.away_name = away_name;
        this.location = location;
        this.league_id = league_id;
        this.home_id = home_id;
        this.away_id = away_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getHome_name() {
        return home_name;
    }

    public void setHome_name(String home_name) {
        this.home_name = home_name;
    }

    public String getAway_name() {
        return away_name;
    }

    public void setAway_name(String away_name) {
        this.away_name = away_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLeague_id() {
        return league_id;
    }

    public void setLeague_id(String league_id) {
        this.league_id = league_id;
    }

    public String getHome_id() {
        return home_id;
    }

    public void setHome_id(String home_id) {
        this.home_id = home_id;
    }

    public String getAway_id() {
        return away_id;
    }

    public void setAway_id(String away_id) {
        this.away_id = away_id;
    }

    @Override
    public String toString() {
        return "FixtureResponse{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
