package project.agile.Object;

/**
 * Created by John on 2017/5/14.
 */

public class CoachInOneSeason {
    private String season;
    private String league;
    private String teamAbbr;

    public CoachInOneSeason(String season, String league, String teamAbbr) {
        this.season = season;
        this.league = league;
        this.teamAbbr = teamAbbr;
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
}
