package project.agile.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import project.agile.Object.Team;
import project.agile.nbaapp.R;

/**
 * Created by John on 2017/4/16.
 */
public class TeamAdapter extends ArrayAdapter<Team>{
    private int resourceId;
    private List<Team> mBackData;

    public TeamAdapter(Context context, int textViewResourceId,
                         List<Team> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
        this.mBackData = new ArrayList<Team>(objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Team team = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.teamName = (TextView)view.findViewById(R.id.teamName);
            viewHolder.teamFromTo = (TextView)view.findViewById(R.id.teamFromTo);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.teamName.setText(team.getName());
        viewHolder.teamFromTo.setText(team.getFrom()+"-"+team.getTo());
        return view;
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                constraint = constraint.toString().toLowerCase();
                FilterResults result = new FilterResults();
                List<Team> founded = new ArrayList<Team>();
                if (constraint != null && constraint.toString().length() > 0) {
                    for(Team item: mBackData){
                        if(item.getName().toString().toLowerCase().contains(constraint)){
                            founded.add(item);
                        }
                    }
                    result.values = founded;
                    result.count = founded.size();

                }else if (TextUtils.isEmpty(constraint)){
                    result.values = mBackData;
                    result.count = mBackData.size();
                }


                return result;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
//                mData = (List<Arena>) results.values;
                for (Team item : (List<Team>) results.values) {
                    add(item);
                }
                if (results.count > 0){
                    notifyDataSetChanged();
                }else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }

    class ViewHolder{
        TextView teamName;
        TextView teamFromTo;
    }
}
