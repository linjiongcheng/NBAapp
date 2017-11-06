package project.agile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import project.agile.Object.PlayerInATeam;
import project.agile.nbaapp.R;

/**
 * Created by John on 2017/6/8.
 */

public class PlayerInATeamAdapter extends ArrayAdapter<PlayerInATeam> {
    private int resourceId;

    public PlayerInATeamAdapter(Context context, int textViewResourceId,
                               List<PlayerInATeam> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PlayerInATeam playerInATeam = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.playerName = (TextView)view.findViewById(R.id.one_season_player);
            viewHolder.gameNum = (TextView)view.findViewById(R.id.one_season_game_num);
            viewHolder.points = (TextView)view.findViewById(R.id.one_season_points);
            viewHolder.ppg = (TextView)view.findViewById(R.id.one_season_ppg);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.playerName.setText(playerInATeam.getPlayerName());
        viewHolder.gameNum.setText(playerInATeam.getGames()+"");
        viewHolder.points.setText(playerInATeam.getPoints()+"");
        viewHolder.ppg.setText(playerInATeam.getPpg()+"");
        return view;
    }

    class ViewHolder{
        TextView playerName;
        TextView gameNum;
        TextView points;
        TextView ppg;
    }
}
