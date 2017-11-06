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

public class CoachMostSeasonsRequest implements IStatRequest {

    private String name = "常规赛执教生涯排行榜";

    private int position = 1;

    private ProgressDialog progressDialog;

    private Map<String, Integer> coachGames;

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
        for (Map.Entry<String, Integer> entry : coachGames.entrySet()) {
            if (x == 20f)    break;
            str[(int) x] = entry.getKey();
            entries.add(new BarEntry(x++, entry.getValue().intValue()));
        }

        BarDataSet set = new BarDataSet(entries, "常规赛执教赛季数量");
        set.setValueTextColor(Color.BLUE);
        set.setColor(Color.WHITE);

        BarData data = new BarData(set);
        Description d = new Description();
        d.setText("常规赛执教生涯排行榜");
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

        barChart.setScaleMinima(4f, 0f);
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
            coachGames = new LinkedHashMap<>();

            SQLdm s = new SQLdm();
            final SQLiteDatabase db = s.openDatabase(params[0]);

            Cursor cursor = db.rawQuery("select * from Coach", new String[]{});

            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex("TeamCoach"));

                    if (coachGames.get(name) == null) {
//                        Log.d("Guu", name + " " + birth + " " + season + " " + points);
                        coachGames.put(name, 1);
                    } else {
                        int totalSeasons = coachGames.get(name);
                        totalSeasons++;
                        coachGames.put(name, totalSeasons);
                    }

                } while (cursor.moveToNext());

                cursor.close();
            }

            coachGames = MapUtil.sortByValue(coachGames);

            Map<String, Integer> temp = new LinkedHashMap<>();

            int counter = 20;
            for (Map.Entry<String, Integer> entry : coachGames.entrySet()) {
                if (counter == 0)   break;
                temp.put(entry.getKey(), entry.getValue());
                counter--;
            }
            coachGames = temp;

            for (Map.Entry<String, Integer> entry : coachGames.entrySet()) {
                Log.d("Guu", entry.getKey() + " : " + entry.getValue());
            }

            return true;
        }
    }

}
