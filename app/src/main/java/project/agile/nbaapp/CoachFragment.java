package project.agile.nbaapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import project.agile.Adapter.CoachAdapter;
import project.agile.Object.Coach;
import project.agile.util.SQLdm;

/**
 * Created by Oneplus on 2017/5/25.
 */

public class CoachFragment  extends Fragment {

    private List<Coach> coachList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common,container,false);
        final ListView coachListView = (ListView) view.findViewById(R.id.fragment_list);
        SearchView search = (SearchView) view.findViewById(R.id.search);

        coachList.clear();
        //初始化coachList
        SQLdm s = new SQLdm();
        final SQLiteDatabase db = s.openDatabase(getContext());
        Cursor cursor = db.rawQuery("select distinct TeamCoach from Coach order by TeamCoach", new String[] { });
        if(cursor.moveToFirst()){
            do {
                Coach coach = new Coach();
                coach.setCoachName(cursor.getString(cursor.getColumnIndex("TeamCoach")));
                coachList.add(coach);
            }while(cursor.moveToNext());
            cursor.close();
        }


        final CoachAdapter coachAdapter = new CoachAdapter(getActivity(),R.layout.coach_item,
                coachList);

        coachListView.setAdapter(coachAdapter);
        coachListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), Detail_CoachActivity.class);
                String coachName = coachList.get(position).getCoachName();

                Bundle bundle = new Bundle();
                bundle.putSerializable("CoachName",coachName);

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("diujiong", "submit");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("diujiong", newText);
                coachAdapter.notifyDataSetChanged();
                if (!TextUtils.isEmpty(newText)){
                    coachAdapter.getFilter().filter(newText.toString());

                }else{
                    coachAdapter.getFilter().filter("");
                    coachListView.clearTextFilter();

                }
                return true;
            }
        });
        return view;
    }

}
