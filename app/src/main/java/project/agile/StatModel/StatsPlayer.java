package project.agile.StatModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guure on 2017/6/7.
 */

public class StatsPlayer {

    private static final StatsPlayer ourInstance = new StatsPlayer();

    public static StatsPlayer getInstance() {
        return ourInstance;
    }

    private List<IStatRequest> playerRequests;

    private StatsPlayer() {
        playerRequests = new ArrayList<>();
        playerRequests.add(new PlayerMostPointsRequest());
        playerRequests.add(new PlayerMostPPGRequest());
        playerRequests.add(new PlayerMostGamesRequest());
    }

    public List<IStatRequest> getPlayerRequests() {
        return playerRequests;
    }

}
