package project.agile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import project.agile.Object.Arena;
import project.agile.nbaapp.R;

/**
 * Created by John on 2017/4/16.
 */
public class ArenaAdapter extends ArrayAdapter<Arena>{
    private int resourceId;
    private List<Arena> mBackData;

    public ArenaAdapter(Context context, int textViewResourceId,
                         List<Arena> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
        this.mBackData = new ArrayList<Arena>(objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Arena arena = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.arenaName = (TextView)view.findViewById(R.id.arenaName);
            viewHolder.arenaLocation = (TextView)view.findViewById(R.id.arenaLocation);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.arenaName.setText(arena.getArenaName());
        viewHolder.arenaLocation.setText(arena.getArenaLocation());
        return view;
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                constraint = constraint.toString().toLowerCase();
                FilterResults result = new FilterResults();
                List<Arena> founded = new ArrayList<Arena>();
                if (constraint != null && constraint.toString().length() > 0) {
                    for(Arena item: mBackData){
                        if(item.getArenaName().toString().toLowerCase().contains(constraint)){
                            founded.add(item);
                        }
                    }
                    result.values = founded;
                    result.count = founded.size();

                }else{
                    result.values = mBackData;
                    result.count = mBackData.size();
                }


                return result;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                for (Arena item : (List<Arena>) results.values) {
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
        TextView arenaName;
        TextView arenaLocation;
    }
}
