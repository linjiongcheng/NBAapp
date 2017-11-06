package project.agile.Object;

import java.io.Serializable;

/**
 * Created by John on 2017/4/16.
 */
public class Team implements Serializable{

    private String league;
    private String abbr;

    private String name;
    private int from;
    private int to;
    private int champions;
    private int games;
    private int wins;
    private int loses;

    public Team() {
        super();
    }

    public Team(String league, String abbr) {
        this.league = league;
        this.abbr = abbr;
    }

    public String getLeague() {
        return league;
    }
    public void setLeague(String league) {
        this.league = league;
    }
    public String getAbbr() {
        return abbr;
    }
    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getFrom() {
        return from;
    }
    public void setFrom(int from) {
        this.from = from;
    }
    public int getTo() {
        return to;
    }
    public void setTo(int to) {
        this.to = to;
    }
    public int getChampions() {
        return champions;
    }
    public void setChampions(int champions) {
        this.champions = champions;
    }
    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLoses() {
        return loses;
    }
    public void setLoses(int loses) {
        this.loses = loses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        return abbr.equals(team.abbr);

    }

    @Override
    public int hashCode() {
        return abbr.hashCode();
    }

}
