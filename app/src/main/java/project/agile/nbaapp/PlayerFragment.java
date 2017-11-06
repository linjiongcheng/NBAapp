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

import project.agile.Adapter.PlayerAdapter;
import project.agile.Object.Player;
import project.agile.util.SQLdm;

public class PlayerFragment extends Fragment {

    private List<Player> playerList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common, container, false);
        final ListView playerListView = (ListView) view.findViewById(R.id.fragment_list);
        SearchView search = (SearchView) view.findViewById(R.id.search);

        playerList.clear();
        //初始化playerList
        SQLdm s = new SQLdm();
        final SQLiteDatabase db = s.openDatabase(getContext());
        Cursor cursor = db.rawQuery("select distinct Player,Birth from Player order by Player", new String[]{});

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("Player"));
                int birth = cursor.getInt(cursor.getColumnIndex("Birth"));
                Player player = new Player(name, birth);
                playerList.add(player);
            } while (cursor.moveToNext());

            cursor.close();
        }

        final PlayerAdapter playerAdapter = new PlayerAdapter(getContext(), R.layout.player_item, playerList);

        playerListView.setAdapter(playerAdapter);
        playerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), Detail_PlayerActivity.class);
                String name = playerList.get(position).getName();
                int birthYear = playerList.get(position).getBirthYear();

                Bundle bundle = new Bundle();
                bundle.putSerializable("Name", name);
                bundle.putSerializable("BirthYear", birthYear);

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
                playerAdapter.notifyDataSetChanged();
                if (!TextUtils.isEmpty(newText)){
                    playerAdapter.getFilter().filter(newText.toString());

                }else{
                    playerAdapter.getFilter().filter("");
                    playerListView.clearTextFilter();

                }
                return true;
            }
        });

        return view;
    }
}



