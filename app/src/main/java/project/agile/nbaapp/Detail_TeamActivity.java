package project.agile.nbaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListPopupWindow;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import project.agile.Object.Team;

public class Detail_TeamActivity extends AppCompatActivity {
    private TextView teamNameText;
    private TextView leagueText;
    private TextView fromToText;
    private TextView abbrText;
    private TextView championsText;
    private TextView gamesText;
    private TextView winsText;
    private TextView losesText;
    private Team team;
    private ArrayList<String> list = new ArrayList<>();
    Button choose;
    private ListPopupWindow popupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__team);

        Bundle bundle = getIntent().getExtras();
        team = (Team)bundle.getSerializable("team");

        leagueText = (TextView)findViewById(R.id.detailTeamLeague);
        teamNameText = (TextView)findViewById(R.id.detailTeamName);
        abbrText = (TextView)findViewById(R.id.detailTeamAbbr);
        fromToText = (TextView)findViewById(R.id.detailTeamFromTo);
        championsText = (TextView)findViewById(R.id.detailTeamChampions);
        gamesText = (TextView)findViewById(R.id.detailTeamGames);
        winsText = (TextView)findViewById(R.id.detailTeamWins);
        losesText = (TextView)findViewById(R.id.detailTeamLoses);
        leagueText.setText(team.getLeague());
        teamNameText.setText(team.getName());
        abbrText.setText(team.getAbbr());
        fromToText.setText(team.getFrom()+"-"+team.getTo());
        championsText.setText(team.getChampions()+"");
        gamesText.setText(team.getGames()+"");
        winsText.setText(team.getWins()+"");
        losesText.setText(team.getLoses()+"");

        choose= (Button) findViewById(R.id.choose_season);
        popupWindow = new ListPopupWindow(this);
        popupWindow.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getData()));
        popupWindow.setAnchorView(choose);
        popupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setModal(true);
        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                choose.setText(list.get(position));
                popupWindow.dismiss();
                Intent intent = new Intent(Detail_TeamActivity.this, PlayersOfOneSeason.class);
                String teamAbbr = team.getAbbr();
                String season = list.get(position).split("-")[1];
                Bundle bundle = new Bundle();
                bundle.putSerializable("TeamAbbr",teamAbbr);
                bundle.putSerializable("Season",season);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.show();

            }
        });
    }
    private ArrayList<String> getData()
    {
        int from = team.getFrom();
        int to = team.getTo();
        for(int i = from; i <= to; i++){
            int j = i-1;
            list.add(j+"-"+i);
        }
        return list;
    }
}
