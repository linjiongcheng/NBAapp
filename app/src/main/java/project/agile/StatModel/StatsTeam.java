package project.agile.StatModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guure on 2017/6/8.
 */

public class StatsTeam {

    private static final StatsTeam ourInstance = new StatsTeam();

    public static StatsTeam getInstance() {
        return ourInstance;
    }

    private List<IStatRequest> teamRequests;

    private StatsTeam() {
        teamRequests = new ArrayList<>();
        teamRequests.add(new TeamHighestWinningRateRequest());
        teamRequests.add(new TeamLongestHistoryRequest());
        teamRequests.add(new TeamMostChampionsRequest());
    }

    public List<IStatRequest> getTeamRequests() {
        return teamRequests;
    }

}
