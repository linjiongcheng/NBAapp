package project.agile.nbaapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import project.agile.Adapter.PlayerInATeamAdapter;
import project.agile.Object.PlayerInATeam;
import project.agile.util.SQLdm;

public class PlayersOfOneSeason extends AppCompatActivity {
    private TextView teamTextView;
    private TextView seasonTextView;
    private String teamAbbr;
    private String teamName;
    private String season;
    private List<PlayerInATeam> list = new ArrayList<>();
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_of_one_season);
        Bundle bundle = getIntent().getExtras();
        teamAbbr = bundle.getString("TeamAbbr");
        season = bundle.getString("Season");

        //初始化playerList
        SQLdm s = new SQLdm();
        final SQLiteDatabase db = s.openDatabase(getApplicationContext());
        Cursor cursor = db.rawQuery("select * from Player,Team where " +
                "Player.TeamAbbr = Team.TeamAbbr and Player.Lg = Team.Lg" +
                " and Team.TeamAbbr = ? and Season = ? order by Player", new String[] {teamAbbr, season});
        if(cursor.moveToFirst()){
            do {
                teamName = cursor.getString(cursor.getColumnIndex("TeamName"));
                String playerName = cursor.getString(cursor.getColumnIndex("Player"));
                String league = cursor.getString(cursor.getColumnIndex("Lg"));
                int birthYear = cursor.getInt(cursor.getColumnIndex("Birth"));
                int gameNum = cursor.getInt(cursor.getColumnIndex("G"));
                int points = cursor.getInt(cursor.getColumnIndex("PTS"));
                DecimalFormat df = new DecimalFormat("#.0");
                double ppg = Double.valueOf(df.format((double)points/gameNum));
                PlayerInATeam playerInATeam = new PlayerInATeam(teamName,season,
                        teamAbbr,league,playerName,birthYear,gameNum,points,ppg);
                list.add(playerInATeam);

            }while(cursor.moveToNext());
            cursor.close();
        }
        teamTextView = (TextView)findViewById(R.id.teamNameOfOneYear);
        seasonTextView = (TextView)findViewById(R.id.seasonOfOneYear);
        teamTextView.setText(teamName);
        teamTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        seasonTextView.setText((int)(Double.parseDouble(season)-1)
                +"-"+season);
        mListView = (ListView)findViewById(R.id.teamOneSeasonList);
        PlayerInATeamAdapter playerInATeamAdapter = new PlayerInATeamAdapter(this,R.layout.players_of_one_season_item,
                list);
        mListView.setAdapter(playerInATeamAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(PlayersOfOneSeason.this, Detail_PlayerActivity.class);
                String name = list.get(i).getPlayerName();
                int birthYear = list.get(i).getBirthYear();

                Bundle bundle = new Bundle();
                bundle.putSerializable("Name",name);
                bundle.putSerializable("BirthYear",birthYear);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
