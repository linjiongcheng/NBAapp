package project.agile.nbaapp;

import android.content.ClipData;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import project.agile.StatModel.IStatRequest;
import project.agile.StatModel.StatsArena;
import project.agile.StatModel.StatsCoach;
import project.agile.StatModel.StatsPlayer;
import project.agile.StatModel.StatsTeam;

public class Stat2_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int tagNum;
    private List<IStatRequest> statRequests;

    private LinearLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat2_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        content = (LinearLayout) findViewById(R.id.content);

        // get the tag number indicates where user from
        tagNum = getIntent().getIntExtra("tag", 0);

        // set item title based on the tag number
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        MenuItem titleItem = menu.findItem(R.id.titleItem);
        Menu menu2 = titleItem.getSubMenu();
//        MenuItem mi = menu2.add(0, 3, 3, "你好");
//        mi.setIcon(R.drawable.common_google_signin_btn_icon_dark_disabled);
        switch (tagNum) {
            case 0:
                titleItem.setTitle("球员百科");
                StatsPlayer statsPlayer = StatsPlayer.getInstance();
                statRequests = statsPlayer.getPlayerRequests();
                addItem(menu2);
                break;
            case 1:
                titleItem.setTitle("教练百科");
                StatsCoach statsCoach = StatsCoach.getInstance();
                statRequests = statsCoach.getCoachRequests();
                addItem(menu2);
                break;
            case 2:
                titleItem.setTitle("球队百科");
                StatsTeam statsTeam = StatsTeam.getInstance();
                statRequests = statsTeam.getTeamRequests();
                addItem(menu2);
                break;
            case 3:
                titleItem.setTitle("场馆百科");
                StatsArena statsArena = StatsArena.getInstance();
                statRequests = statsArena.getArenaRequests();
                addItem(menu2);
                break;
            default:
                break;
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        content.removeAllViews();

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        for (IStatRequest iStatRequest : statRequests) {
            if (iStatRequest.getPosition() == id)
                iStatRequest.init(content, Stat2_Activity.this);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addItem(Menu menu) {
        for (IStatRequest iStatRequest : statRequests) {
            String name = iStatRequest.getName();
            int position = iStatRequest.getPosition();
            MenuItem mi = menu.add(0, position, position, name);
            mi.setIcon(android.R.drawable.star_big_on);
        }
    }

}
