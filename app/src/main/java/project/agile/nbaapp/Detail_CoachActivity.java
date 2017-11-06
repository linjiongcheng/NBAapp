package project.agile.nbaapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import project.agile.Adapter.CoachDetailAdapter;
import project.agile.Adapter.PlayerDetailAdapter;
import project.agile.Object.CoachInOneSeason;
import project.agile.Object.PlayerInOneSeason;
import project.agile.util.SQLdm;

public class Detail_CoachActivity extends AppCompatActivity {
    private TextView nameTextView;
    private String name;
    private List<CoachInOneSeason> list = new ArrayList<>();
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__coach);
        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("CoachName");

        nameTextView = (TextView)findViewById(R.id.detailCoachName);
        nameTextView.setText(name);

        //初始化playerList
        SQLdm s = new SQLdm();
        final SQLiteDatabase db = s.openDatabase(getApplicationContext());
        Cursor cursor = db.rawQuery("select distinct * from Coach where TeamCoach = ? order by TeamSeason", new String[] {name});
        if(cursor.moveToFirst()){
            do {
                String season = cursor.getString(cursor.getColumnIndex("TeamSeason"));
                String league = cursor.getString(cursor.getColumnIndex("Lg"));
                String teamAbbr = cursor.getString(cursor.getColumnIndex("TeamAbbr"));
                CoachInOneSeason coachInOneSeason = new CoachInOneSeason(season,league,
                        teamAbbr);
                list.add(coachInOneSeason);

            }while(cursor.moveToNext());
            cursor.close();
        }
        mListView = (ListView)findViewById(R.id.coachDetailList);
        CoachDetailAdapter coachDetailAdapter = new CoachDetailAdapter(this,R.layout.coach_detail_item,
                list);
        mListView.setAdapter(coachDetailAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Detail_CoachActivity.this, PlayersOfOneSeason.class);
                String teamAbbr = list.get(i).getTeamAbbr();
                String season = list.get(i).getSeason();

                Bundle bundle = new Bundle();
                bundle.putSerializable("TeamAbbr",teamAbbr);
                bundle.putSerializable("Season",season);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
