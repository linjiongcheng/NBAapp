package project.agile.nbaapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import project.agile.Adapter.PlayerDetailAdapter;
import project.agile.Object.PlayerInOneSeason;
import project.agile.util.DensityUtil;
import project.agile.util.SQLdm;

public class Detail_PlayerActivity extends AppCompatActivity {
    private TextView nameTextView;
    private TextView birthTextView;
    private String name;
    private int birthYear;
    private List<PlayerInOneSeason> list = new ArrayList<>();
    private ListView mListView;

//    private Button goToChartButton;

    private LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__player);

//        goToChartButton = (Button) findViewById(R.id.goToChart);

        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("Name");
        birthYear = bundle.getInt("BirthYear");

        nameTextView = (TextView)findViewById(R.id.detailPlayerName);
        birthTextView = (TextView)findViewById(R.id.detailPlayerBirth);
        nameTextView.setText(name);
        birthTextView.setText(birthYear+"");

        List<Entry> entries = new ArrayList<Entry>();

        //初始化playerList
        SQLdm s = new SQLdm();
        final SQLiteDatabase db = s.openDatabase(getApplicationContext());
        Cursor cursor = db.rawQuery("select * from Player where Player = ? and Birth = ?", new String[] {name, birthYear+""});
        if(cursor.moveToFirst()){
            do {
                String season = cursor.getString(cursor.getColumnIndex("Season"));
                String league = cursor.getString(cursor.getColumnIndex("Lg"));
                String teamAbbr = cursor.getString(cursor.getColumnIndex("TeamAbbr"));
                int gameNum = cursor.getInt(cursor.getColumnIndex("G"));
                int points = cursor.getInt(cursor.getColumnIndex("PTS"));
                DecimalFormat df = new DecimalFormat("#.0");
                double ppg = Double.valueOf(df.format((double)points/gameNum));
                PlayerInOneSeason playerInOneSeason = new PlayerInOneSeason(season,league,
                        teamAbbr,gameNum,points,ppg);
                list.add(playerInOneSeason);

                entries.add(new Entry(Float.parseFloat(season), (float) ppg));
                Log.d("Guu1", Float.parseFloat(season) + "");
                Log.d("Guu2", (float) ppg + "");

            }while(cursor.moveToNext());
            cursor.close();
        }
        mListView = (ListView)findViewById(R.id.playerDetailList);
        PlayerDetailAdapter playerDetailAdapter = new PlayerDetailAdapter(this,R.layout.player_detail_item,
                list);
        mListView.setAdapter(playerDetailAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Detail_PlayerActivity.this, PlayersOfOneSeason.class);
                String teamAbbr = list.get(i).getTeamAbbr();
                String season = list.get(i).getSeason();

                Bundle bundle = new Bundle();
                bundle.putSerializable("TeamAbbr",teamAbbr);
                bundle.putSerializable("Season",season);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        setListViewHeightBasedOnChildren(mListView);

        initChart(entries, name);

//        goToChartButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Detail_PlayerActivity.this, Chart_PlayerActivity.class);
//                startActivity(intent);
//            }
//        });

    }

    void initChart(List<Entry> entries, String name) {
        Log.d("Guu", "init");
        chart = (LineChart) findViewById(R.id.chart);

        LineDataSet dataSet = new LineDataSet(entries, "场均得分");
        dataSet.setValueTextColor(Color.BLUE);
        dataSet.setColor(Color.WHITE);
        dataSet.setDrawFilled(true);
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        Description d = new Description();
        d.setText(name + "'s PPG Per Season");
        chart.setBackgroundColor(Color.LTGRAY);
        chart.setDescription(d);
        chart.invalidate();

    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

}
