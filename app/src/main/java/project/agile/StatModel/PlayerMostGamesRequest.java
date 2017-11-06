package project.agile.StatModel;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import project.agile.Object.Player;
import project.agile.util.MapUtil;
import project.agile.util.SQLdm;

/**
 * Created by Guure on 2017/6/9.
 */

public class PlayerMostGamesRequest implements IStatRequest {

    private String name = "常规赛比赛次数排行榜";

    private int position = 3;

    private ProgressDialog progressDialog;

    private Map<Player, Integer> playerGames;

    private LinearLayout content;

    private Context context;

    private String[] str;

    @Override
    public void init(LinearLayout content, Context context) {
        this.content = content;
        this.context = context;
        requestData();
    }

    @Override
    public void addChart() {
        BarChart barChart = new BarChart(context);

        List<BarEntry> entries = new ArrayList<>();

        str = new String[20];

        float x = 0f;
        for (Map.Entry<Player, Integer> entry : playerGames.entrySet()) {
            if (x == 20f)    break;
            str[(int) x] = entry.getKey().getName();
            entries.add(new BarEntry(x++, entry.getValue().intValue()));
        }

        BarDataSet set = new BarDataSet(entries, "常规赛参加比赛次数");
        set.setValueTextColor(Color.BLUE);
        set.setColor(Color.WHITE);

        BarData data = new BarData(set);
        Description d = new Description();
        d.setText("常规赛比赛次数排行榜");
        barChart.setDescription(d);
        barChart.setData(data);
        barChart.setFitBars(true);

        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return str[(int) value];
            }
        };

        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(formatter);
        xAxis.setTextColor(Color.BLUE);

        barChart.setScaleMinima(6f, 0f);
        barChart.setBackgroundColor(Color.CYAN);
        barChart.invalidate();

        BarChart.LayoutParams p = new BarChart.LayoutParams(BarChart.LayoutParams.MATCH_PARENT, BarChart.LayoutParams.MATCH_PARENT);
        barChart.setLayoutParams(p);
        content.addView(barChart);
    }

    @Override
    public void requestData() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        new requestDataTask().execute(context);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    class requestDataTask extends AsyncTask<Context, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            progressDialog.dismiss();
            addChart();
        }

        @Override
        protected Boolean doInBackground(Context... params) {
            playerGames = new LinkedHashMap<>();

            SQLdm s = new SQLdm();
            final SQLiteDatabase db = s.openDatabase(params[0]);

            Cursor cursor = db.rawQuery("select * from Player", new String[]{});

            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex("Player"));
                    int birth = cursor.getInt(cursor.getColumnIndex("Birth"));
//                    String season = cursor.getString(cursor.getColumnIndex("Season"));
                    int games = cursor.getInt(cursor.getColumnIndex("G"));
                    Player player = new Player(name, birth);

                    if (playerGames.get(player) == null) {
//                        Log.d("Guu", name + " " + birth + " " + season + " " + points);
                        playerGames.put(player, games);
                    } else {
                        int totalGames = playerGames.get(player);
                        totalGames += games;
                        playerGames.put(player, totalGames);
                    }

                } while (cursor.moveToNext());

                cursor.close();
            }

            playerGames = MapUtil.sortByValue(playerGames);

            Map<Player, Integer> temp = new LinkedHashMap<>();

            int counter = 20;
            for (Map.Entry<Player, Integer> entry : playerGames.entrySet()) {
                if (counter == 0)   break;
                temp.put(entry.getKey(), entry.getValue());
                counter--;
            }
            playerGames = temp;

            for (Map.Entry<Player, Integer> entry : playerGames.entrySet()) {
                Log.d("Guu", entry.getKey().getName() + " " + entry.getKey().getBirthYear() + " : " + entry.getValue());
            }

            return true;
        }
    }

}
