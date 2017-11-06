package project.agile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import project.agile.Object.CoachInOneSeason;
import project.agile.nbaapp.R;

/**
 * Created by John on 2017/6/4.
 */

public class CoachDetailAdapter extends ArrayAdapter<CoachInOneSeason> {
    private int resourceId;

    public CoachDetailAdapter(Context context, int textViewResourceId,
                               List<CoachInOneSeason> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CoachInOneSeason coachInOneSeason = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.season = (TextView) view.findViewById(R.id.coach_season);
            viewHolder.league = (TextView) view.findViewById(R.id.coach_league);
            viewHolder.teamAbbr = (TextView) view.findViewById(R.id.coach_team_abbr);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.season.setText((int)(Double.parseDouble(coachInOneSeason.getSeason()) - 1)
                + "-" + coachInOneSeason.getSeason());
        viewHolder.league.setText(coachInOneSeason.getLeague());
        viewHolder.teamAbbr.setText(coachInOneSeason.getTeamAbbr());
        return view;
    }

    class ViewHolder {
        TextView season;
        TextView league;
        TextView teamAbbr;
    }
}
