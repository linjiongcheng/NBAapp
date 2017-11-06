package project.agile.StatModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guure on 2017/6/9.
 */

public class StatsCoach {

    private static final StatsCoach ourInstance = new StatsCoach();

    public static StatsCoach getInstance() {
        return ourInstance;
    }

    private List<IStatRequest> coachRequests;

    private StatsCoach() {
        coachRequests = new ArrayList<>();
        coachRequests.add(new CoachMostSeasonsRequest());
    }

    public List<IStatRequest> getCoachRequests() {
        return coachRequests;
    }

}
