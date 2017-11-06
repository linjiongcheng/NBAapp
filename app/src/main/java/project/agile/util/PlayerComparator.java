package project.agile.util;

import java.util.Comparator;

import project.agile.Object.Player;

/**
 * Created by John on 2017/5/14.
 */

public class PlayerComparator implements Comparator<Player>{
    @Override
    public int compare(Player player, Player t1) {

        return player.getName().compareTo(t1.getName());
    }
}
