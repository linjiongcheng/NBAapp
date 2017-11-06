package project.agile.StatModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guure on 2017/6/9.
 */

public class StatsArena {

    private static final StatsArena ourInstance = new StatsArena();

    public static StatsArena getInstance() {
        return ourInstance;
    }

    private List<IStatRequest> arenaRequests;

    private StatsArena() {
        arenaRequests = new ArrayList<>();
        arenaRequests.add(new ArenaBiggestCapacityRequest());
        arenaRequests.add(new ArenaLongestHistoryRequest());
    }

    public List<IStatRequest> getArenaRequests() {
        return arenaRequests;
    }

}
