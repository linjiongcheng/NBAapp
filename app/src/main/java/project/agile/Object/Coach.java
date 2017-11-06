package project.agile.Object;

import java.io.Serializable;

/**
 * Created by John on 2017/4/16.
 */
public class Coach implements Serializable {
    private String coachName;

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }
}
