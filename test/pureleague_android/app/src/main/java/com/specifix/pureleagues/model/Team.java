package com.specifix.pureleagues.model;

public class Team {
    private long mTeamId;
    private long mDivisionId;
    private String mDivision;
    private String mClub;
    private String mColor;
    private boolean mAllowNotifications;

    public Team(long teamId, long divisionId, String division, String mClub, String mColor) {
        this.mTeamId = teamId;
        this.mDivisionId = divisionId;
        this.mDivision = division;
        this.mClub = mClub;
        this.mColor = mColor;
        this.mAllowNotifications = true;
    }

    public long getTeamId() {
        return mTeamId;
    }

    public void setTeamId(long teamId) {
        this.mTeamId =teamId;
    }

    public long getDivisionId() {
        return mDivisionId;
    }

    public void setDivisionId(long divisionId) {
        this.mDivisionId = divisionId;
    }

    public String getDivision() {
        return mDivision;
    }

    public void setDivision(String division) {
        this.mDivision = division;
    }

    public String getClub() {
        return mClub;
    }

    public void setClub(String club) {
        this.mClub = club;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        this.mColor = color;
    }

    public boolean isAllowNotifications() {
        return mAllowNotifications;
    }

    public void setAllowNotifications(boolean allowNotifications) {
        this.mAllowNotifications = allowNotifications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        return mTeamId == team.mTeamId;

    }

    @Override
    public int hashCode() {
        return (int) (mTeamId ^ (mTeamId >>> 32));
    }

    @Override
    public String toString() {
        return "Team{" +
                "mTeamId=" + mTeamId +
                ", mDivisionId=" + mDivisionId +
                ", mDivision='" + mDivision + '\'' +
                ", mClub='" + mClub + '\'' +
                ", mColor='" + mColor + '\'' +
                ", mAllowNotifications=" + mAllowNotifications +
                '}';
    }
}
