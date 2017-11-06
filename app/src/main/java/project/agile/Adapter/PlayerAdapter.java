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

import project.agile.Object.Player;
import project.agile.nbaapp.R;

/**
 * Created by John on 2017/4/16.
 */
public class PlayerAdapter extends ArrayAdapter<Player> {
    private int resourceId;
    private List<Player> mBackData;

    public PlayerAdapter(Context context,int textViewResourceId,
                         List<Player> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
        this.mBackData = new ArrayList<Player>(objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Player player = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView)view.findViewById(R.id.playerName);
            viewHolder.birthYear = (TextView)view.findViewById(R.id.playerBirth);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.name.setText(player.getName());
        viewHolder.birthYear.setText(player.getBirthYear()+"");
        return view;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                constraint = constraint.toString().toLowerCase();
                FilterResults result = new FilterResults();
                List<Player> founded = new ArrayList<Player>();
                if (constraint != null && constraint.toString().length() > 0) {
                    for(Player item: mBackData){
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
                for (Player item : (List<Player>) results.values) {
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
        TextView name;
        TextView birthYear;
    }
}
