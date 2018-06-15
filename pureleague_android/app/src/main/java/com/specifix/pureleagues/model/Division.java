package com.specifix.pureleagues.model;

public class Division {
    private long id;
    private long associationId;
    private long seasonId;
    private String name;

    public Division(long id, long associationId, long seasonId, String name) {
        this.id = id;
        this.associationId = associationId;
        this.seasonId = seasonId;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAssociationId() {
        return associationId;
    }

    public void setAssociationId(long associationId) {
        this.associationId = associationId;
    }

    public long getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(long seasonId) {
        this.seasonId = seasonId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
