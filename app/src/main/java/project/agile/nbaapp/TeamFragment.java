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

import project.agile.Adapter.TeamAdapter;
import project.agile.Object.Team;
import project.agile.util.SQLdm;

/**
 * Created by Oneplus on 2017/5/25.
 */

public class TeamFragment  extends Fragment {

    private List<Team> teamList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("yijiashishabi", "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_common,container,false);
        final ListView teamListView = (ListView) view.findViewById(R.id.fragment_list);
        SearchView search = (SearchView) view.findViewById(R.id.search);

        teamList.clear();
        //初始化teamList
        SQLdm s = new SQLdm();
        final SQLiteDatabase db = s.openDatabase(getContext());
        Cursor cursor = db.rawQuery("select distinct * from Team where TeamName != \"\" order by TeamName", new String[] { });
        if(cursor.moveToFirst()){
            do {
                Team team = new Team();
                team.setLeague(cursor.getString(cursor.getColumnIndex("Lg")));
                team.setAbbr(cursor.getString(cursor.getColumnIndex("TeamAbbr")));
                team.setChampions(cursor.getInt(cursor.getColumnIndex("TeamChamp")));
                team.setName(cursor.getString(cursor.getColumnIndex("TeamName")));
                team.setFrom(cursor.getInt(cursor.getColumnIndex("TeamFrom")));
                team.setTo(cursor.getInt(cursor.getColumnIndex("TeamTo")));
                team.setGames(cursor.getInt(cursor.getColumnIndex("TeamG")));
                team.setWins(cursor.getInt(cursor.getColumnIndex("TeamW")));
                team.setLoses(cursor.getInt(cursor.getColumnIndex("TeamL"))) ;
                teamList.add(team);
            }while(cursor.moveToNext());
            cursor.close();
        }

        final TeamAdapter teamAdapter = new TeamAdapter(getActivity(),R.layout.team_item,
                teamList);

        teamListView.setAdapter(teamAdapter);
        teamListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), Detail_TeamActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("team",teamList.get(position));
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
                teamAdapter.notifyDataSetChanged();
                if (!TextUtils.isEmpty(newText)){
                    teamAdapter.getFilter().filter(newText.toString());

                }else{
                    teamAdapter.getFilter().filter("");
                    teamListView.clearTextFilter();

                }
                return true;
            }
        });
        return view;
    }

}
