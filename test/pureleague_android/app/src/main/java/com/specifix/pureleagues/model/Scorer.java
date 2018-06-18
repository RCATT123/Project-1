package com.specifix.pureleagues.model;

public class Scorer {
    private String playerName;
    private long divisionId;
    private long teamId;
    private long position;
    private String goals;

    public Scorer(String playerName, long divisionId, long teamId, long position, String goals) {
        this.divisionId = divisionId;
        this.teamId = teamId;
        this.position = position;
        this.goals = goals;

        if (playerName == null) {
            playerName = "";
        }

        String[] strArray = playerName.toLowerCase().split(" ");
        StringBuilder builder = new StringBuilder();
        for (String s : strArray) {
            if (s.length() > 0) {
                String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
                builder.append(cap + " ");
            }
        }
        this.playerName = builder.toString();
    }


    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public long getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(long divisionId) {
        this.divisionId = divisionId;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }
}
