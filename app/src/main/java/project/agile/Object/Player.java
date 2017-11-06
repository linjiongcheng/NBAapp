package project.agile.Object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 2017/4/15.
 */
public class Player implements Serializable {
    private String name;
    private int birthYear;

    public Player(String name,int birthYear){
        this.name = name;
        this.birthYear = birthYear;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public int getBirthYear() {
        return birthYear;
    }
    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (birthYear != player.birthYear) return false;
        return name.equals(player.name);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + birthYear;
        return result;
    }

}
