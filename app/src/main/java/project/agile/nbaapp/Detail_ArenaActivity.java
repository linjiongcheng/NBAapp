package project.agile.nbaapp;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import project.agile.util.SQLdm;

public class Detail_ArenaActivity extends AppCompatActivity {
    public List<Map<String, String>> list = new ArrayList<>();
    String ArenaName;
    String ArenaLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__arena);

        ArenaName = getIntent().getStringExtra("ArenaName");
        ArenaLocation = getIntent().getStringExtra("ArenaLocation");
        Map<String, String> ArenaMap = readFromSQ();
        TextView detailArenaName = (TextView) findViewById(R.id.detailArenaName);
        TextView detailTeam = (TextView) findViewById(R.id.detailTeam);
        TextView detailTeamLeague = (TextView) findViewById(R.id.detailTeamLeague);
        TextView detailTeamDate = (TextView) findViewById(R.id.detailDate);
        TextView detailLocation = (TextView) findViewById(R.id.detailLocation);
        TextView detailCapacity = (TextView) findViewById(R.id.detailCapacity);
        detailArenaName.setText(ArenaName);
        detailLocation.setText(ArenaLocation);
        detailTeamDate.setText(ArenaMap.get("time"));
        detailTeam.setText(ArenaMap.get("teamAbbr"));
        detailTeamLeague.setText(ArenaMap.get("lg"));
        detailCapacity.setText(ArenaMap.get("capacity"));

    }

    private Map<String, String> readFromSQ() {
        SQLdm s = new SQLdm();
        final SQLiteDatabase db = s.openDatabase(getApplicationContext());
        Cursor cursor = db.rawQuery("select * from Arena where Arena = ? and ArenaLocation = ?", new String[]{ArenaName, ArenaLocation});
        Map<String, String> map = new HashMap<>();
        while (cursor.moveToNext()) {
            String ArenaStart = cursor.getString(cursor.getColumnIndex("ArenaStart"));
            String ArenaEnd = cursor.getString(cursor.getColumnIndex("ArenaEnd"));
            String league = cursor.getString(cursor.getColumnIndex("Lg"));
            String teamAbbr = cursor.getString(cursor.getColumnIndex("TeamAbbr"));
            String capacity = cursor.getString(cursor.getColumnIndex("ArenaCapacity"));
            map.put("time", ArenaStart + "-" + ArenaEnd);
            map.put("lg", league);
            map.put("teamAbbr", teamAbbr);
            map.put("capacity", capacity);
        } while (cursor.moveToNext());
        cursor.close();
        return map;
    }

}
