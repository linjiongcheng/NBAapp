package project.agile.Object;

/**
 * Created by John on 2017/6/8.
 */

public class PlayerInATeam {
    private String teamName;
    private String season;
    private String teamAbbr;
    private String league;
    private String playerName;
    private int birthYear;
    private int games;
    private int points;
    private double ppg;

    public PlayerInATeam(String teamName, String season, String teamAbbr, String league, String playerName, int birthYear, int games, int points, double ppg) {
        this.teamName = teamName;
        this.season = season;
        this.teamAbbr = teamAbbr;
        this.league = league;
        this.playerName = playerName;
        this.birthYear = birthYear;
        this.games = games;
        this.points = points;
        this.ppg = ppg;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getTeamAbbr() {
        return teamAbbr;
    }

    public void setTeamAbbr(String teamAbbr) {
        this.teamAbbr = teamAbbr;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }


    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }


    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
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
