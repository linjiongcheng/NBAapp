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

import project.agile.Object.Coach;
import project.agile.nbaapp.R;

/**
 * Created by John on 2017/4/16.
 */
public class CoachAdapter extends ArrayAdapter<Coach> {
    private int resourceId;
    private List<Coach> mBackData;


    public CoachAdapter(Context context, int textViewResourceId,
                         List<Coach> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
        this.mBackData = new ArrayList<Coach>(objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Coach player = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.coachName = (TextView)view.findViewById(R.id.coachName);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.coachName.setText(player.getCoachName());
        return view;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                constraint = constraint.toString().toLowerCase();
                FilterResults result = new FilterResults();
                List<Coach> founded = new ArrayList<Coach>();
                if (constraint != null && constraint.toString().length() > 0) {
                    for(Coach item: mBackData){
                        if(item.getCoachName().toString().toLowerCase().contains(constraint)){
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
                for (Coach item : (List<Coach>) results.values) {
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
        TextView coachName;
    }
}
