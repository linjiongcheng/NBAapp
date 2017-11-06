package project.agile.Object;

import java.io.Serializable;

/**
 * Created by John on 2017/4/16.
 */
public class Arena implements Serializable{
    private String arenaName;
    private String arenaLocation;

    public Arena() {
        super();
    }

    public Arena(String arenaName, String arenaLocation) {
        this.arenaName = arenaName;
        this.arenaLocation = arenaLocation;
    }

    public void setArenaName(String arenaName) {
        this.arenaName = arenaName;
    }

    public void setArenaLocation(String arenaLocation) {
        this.arenaLocation = arenaLocation;
    }

    public String getArenaLocation() {
        return arenaLocation;
    }

    public String getArenaName() {
        return arenaName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Arena arena = (Arena) o;

        if (!arenaName.equals(arena.arenaName)) return false;
        return arenaLocation.equals(arena.arenaLocation);

    }

    @Override
    public int hashCode() {
        int result = arenaName.hashCode();
        result = 31 * result + arenaLocation.hashCode();
        return result;
    }
}
