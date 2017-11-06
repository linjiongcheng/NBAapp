package project.agile.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import project.agile.Object.Player;
import project.agile.Object.PlayerInOneSeason;
import project.agile.nbaapp.R;

/**
 * Created by John on 2017/5/21.
 */

public class PlayerDetailAdapter extends ArrayAdapter<PlayerInOneSeason> {
    private int resourceId;

    public PlayerDetailAdapter(Context context, int textViewResourceId,
                         List<PlayerInOneSeason> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PlayerInOneSeason playerInOneSeason = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.season = (TextView)view.findViewById(R.id.player_season);
            viewHolder.league = (TextView)view.findViewById(R.id.player_league);
            viewHolder.teamAbbr = (TextView)view.findViewById(R.id.player_team_abbr);
            viewHolder.gameNum = (TextView)view.findViewById(R.id.player_game_num);
            viewHolder.points = (TextView)view.findViewById(R.id.player_points);
            viewHolder.ppg = (TextView)view.findViewById(R.id.player_ppg);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.season.setText((Integer.parseInt(playerInOneSeason.getSeason())-1)
        +"-"+playerInOneSeason.getSeason());
        viewHolder.league.setText(playerInOneSeason.getLeague());
        viewHolder.teamAbbr.setText(playerInOneSeason.getTeamAbbr());
        viewHolder.gameNum.setText(playerInOneSeason.getGames()+"");
        viewHolder.points.setText(playerInOneSeason.getPoints()+"");
        viewHolder.ppg.setText(playerInOneSeason.getPpg()+"");
        return view;
    }

    class ViewHolder{
        TextView season;
        TextView league;
        TextView teamAbbr;
        TextView gameNum;
        TextView points;
        TextView ppg;
    }
}
