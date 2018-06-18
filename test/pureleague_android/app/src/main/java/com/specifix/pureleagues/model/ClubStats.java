package com.specifix.pureleagues.model;

public class ClubStats {
    private long clubId;
    private String club;
    private long games;
    private long wins;
    private long draws;
    private long lost;
    private long goalsFor;
    private long goalsAgainst;
    private long goalsDifference;
    private long points;

    public ClubStats(long clubId,
                     String club,
                     long games,
                     long wins,
                     long draws,
                     long lost,
                     long goalsFor,
                     long goalsAgainst,
                     long goalsDifference,
                     long points) {
        this.clubId = clubId;
        this.club = club;
        this.games = games;
        this.wins = wins;
        this.draws = draws;
        this.lost = lost;
        this.goalsFor = goalsFor;
        this.goalsAgainst = goalsAgainst;
        this.goalsDifference = goalsDifference;
        this.points = points;
    }

    public long getClubId() {
        return clubId;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public long getGames() {
        return games;
    }

    public void setGames(long games) {
        this.games = games;
    }

    public long getWins() {
        return wins;
    }

    public void setWins(long wins) {
        this.wins = wins;
    }

    public long getDraws() {
        return draws;
    }

    public void setDraws(long draws) {
        this.draws = draws;
    }

    public long getLost() {
        return lost;
    }

    public void setLost(long lost) {
        this.lost = lost;
    }

    public long getGoalsFor() {
        return goalsFor;
    }

    public void setGoalsFor(long goalsFor) {
        this.goalsFor = goalsFor;
    }

    public long getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(long goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public long getGoalsDifference() {
        return goalsDifference;
    }

    public void setGoalsDifference(long goalsDifference) {
        this.goalsDifference = goalsDifference;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "ClubStats{" +
                "clubId='" + clubId + '\'' +
                ", club='" + club + '\'' +
                ", games='" + games + '\'' +
                ", wins='" + wins + '\'' +
                ", draws='" + draws + '\'' +
                ", lost='" + lost + '\'' +
                ", goalsFor='" + goalsFor + '\'' +
                ", goalsAgainst='" + goalsAgainst + '\'' +
                ", goalsDifference='" + goalsDifference + '\'' +
                ", points='" + points + '\'' +
                '}';
    }
}
