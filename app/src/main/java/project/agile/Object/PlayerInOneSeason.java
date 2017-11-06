package project.agile.Object;

/**
 * Created by John on 2017/5/14.
 */

public class PlayerInOneSeason {
    private String season;
    private String league;
    private String teamAbbr;
    private int games;
    private int points;
    private double ppg;

    public PlayerInOneSeason(String season, String league, String teamAbbr, int games, int points, double ppg) {
        this.season = season;
        this.league = league;
        this.teamAbbr = teamAbbr;
        this.games = games;
        this.points = points;
        this.ppg = ppg;
    }

    public String getSeason() {

        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getTeamAbbr() {
        return teamAbbr;
    }

    public void setTeamAbbr(String teamAbbr) {
        this.teamAbbr = teamAbbr;
    }

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public double getPpg() {
        return ppg;
    }

    public void setPpg(double ppg) {
        this.ppg = ppg;
    }
}
