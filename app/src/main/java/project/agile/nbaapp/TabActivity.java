package project.agile.nbaapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import project.agile.Adapter.MyFragmentPagerAdapter;
import project.agile.DataAnalysis.ReadCSV;
import project.agile.util.SQLdm;
import project.agile.util.ToastUtil;
import project.agile.util.WriteToSD;

public class TabActivity extends AppCompatActivity {

    public static final String TAG = "TestActivity";

    private MyFragmentPagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageView banner;
    private FloatingActionButton fab;
    private ProgressDialog progressDialog;
    private AppBarLayout appBarLayout;

    private int tagNum = 0;

    private Handler mHandler;
    private static final int SELECT_PLAYER = 0;
    private static final int SELECT_COACH = 1;
    private static final int SELECT_TEAM = 2;
    private static final int SELECT_ARENA = 3;
    private static final int UPDATE_SUCCESS = 4;
    private static final int UPDATE_FAILED = 5;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private ImageView stat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        stat = (ImageView) findViewById(R.id.stat);

        banner = (ImageView)findViewById(R.id.banner_image_view);

//        appBarLayout = (AppBarLayout)findViewById(R.id.appBar);
//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//
//            }
//        });

        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                progressDialog = new ProgressDialog(TabActivity.this);
//                progressDialog.setTitle(getResources().getString(R.string.loading_title));
//                progressDialog.setMessage(getResources().getString(R.string.loading_message));
//                progressDialog.setCancelable(false);
//                progressDialog.show();
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            SQLdm s = new SQLdm();
//                            final SQLiteDatabase db = s.openDatabase(getApplicationContext());
//                            db.execSQL("DELETE FROM Player;");
//                            db.execSQL("DELETE FROM Arena;");
//                            db.execSQL("DELETE FROM Coach;");
//                            db.execSQL("DELETE FROM Team;");
//                            Log.d(TAG, "Database Finish");
//                            WriteToSD.writeToSD(getApplicationContext(), getResources().getString(R.string.file_name));
//                            Log.d(TAG, "Copy File Finish");
//                            ReadCSV readCSV = new ReadCSV();
//                            readCSV.Insert(db, getResources().getString(R.string.file_name));
//                            Log.d(TAG, "Insert Finish");
//                            Message message = new Message();
//                            message.what = UPDATE_SUCCESS;
//                            mHandler.sendMessage(message);
//                        }catch (Exception e){
//                            e.printStackTrace();
//                            Message message = new Message();
//                            message.what = UPDATE_FAILED;
//                            mHandler.sendMessage(message);
//                        }
//                    }
//                }).start();
//                if(ContextCompat.checkSelfPermission(TabActivity.this, android.Manifest.
//                        permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(TabActivity.this,new
//                    String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
//                }else{
//                    writeSd();
//                }

                new AlertDialog.Builder(TabActivity.this)
                        .setTitle("提示")
                        .setMessage("你确定要更新数据吗?")
                        .setPositiveButton("确定",new onPositiveListener())
                        .setNegativeButton("取消",null)
                        .create()
                        .show();
            }
        });


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("NBA");
            // actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mHandler = new Handler() {
            public void handleMessage(Message msg) {

                switch (msg.what) {
                    case SELECT_PLAYER:
                        banner.setImageResource(R.drawable.player);
                        //getSupportActionBar().show();
                        break;
                    case SELECT_COACH:
                        banner.setImageResource(R.drawable.coach);
                        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById((R.id.collapsing_toolbar));
                        collapsingToolbarLayout.setCollapsedTitleGravity(Gravity.START);
                        break;
                    case SELECT_TEAM:
                        banner.setImageResource(R.drawable.team);
                        break;
                    case SELECT_ARENA:
                        banner.setImageResource(R.drawable.arena);
                        break;
                    case UPDATE_SUCCESS:
                        // 更新成功的操作
                        progressDialog.dismiss();
                        ToastUtil.showToast(TabActivity.this, R.string.update_success);
                        break;
                    case UPDATE_FAILED:
                        // 更新失败的操作
                        progressDialog.dismiss();
                        ToastUtil.showToast(TabActivity.this, R.string.update_failed);
                        break;
                    default:
                        break;
                }
            }
        };

        //Fragment+ViewPager+FragmentViewPager组合的使用
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        List<Fragment> list_fragment = new ArrayList<>();
        list_fragment.add(new PlayerFragment());
        list_fragment.add(new CoachFragment());
        list_fragment.add(new TeamFragment());
        list_fragment.add(new ArenaFragment());
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), list_fragment, this);
        viewPager.setAdapter(adapter);

        //TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            //标签选中之后执行的方法
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tagNum = tab.getPosition();
                for (int i = 0; i < 4; i++) {
                    if (tab.getPosition() == i) {
                        Message message = new Message();
                        message.what = i;
                        mHandler.sendMessage(message);
                        break;
                    }
                }
            }

            //标签没选中
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TabActivity.this, Stat2_Activity.class);
                // user go to stat from this tag
                intent.putExtra("tag", tagNum);
                startActivity(intent);
            }
        });

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Test Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }
    class onPositiveListener implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(ContextCompat.checkSelfPermission(TabActivity.this, android.Manifest.
                    permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(TabActivity.this,new
                        String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }else{
                writeSd();
            }

//            progressDialog = new ProgressDialog(TabActivity.this);
//            progressDialog.setTitle(getResources().getString(R.string.loading_title));
//            progressDialog.setMessage(getResources().getString(R.string.loading_message));
//            progressDialog.setCancelable(false);
//            progressDialog.show();
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        SQLdm s = new SQLdm();
//                        final SQLiteDatabase db = s.openDatabase(getApplicationContext());
//                        db.execSQL("DELETE FROM Player;");
//                        db.execSQL("DELETE FROM Arena;");
//                        db.execSQL("DELETE FROM Coach;");
//                        db.execSQL("DELETE FROM Team;");
//                        Log.d(TAG, "Database Finish");
//                        WriteToSD.writeToSD(getApplicationContext(), getResources().getString(R.string.file_name));
//                        Log.d(TAG, "Copy File Finish");
//                        ReadCSV readCSV = new ReadCSV();
//                        readCSV.Insert(db, getResources().getString(R.string.file_name));
//                        Log.d(TAG, "Insert Finish");
//                        Message message = new Message();
//                        message.what = UPDATE_SUCCESS;
//                        mHandler.sendMessage(message);
//                    }catch (Exception e){
//                        e.printStackTrace();
//                        Message message = new Message();
//                        message.what = UPDATE_FAILED;
//                        mHandler.sendMessage(message);
//                    }
//                }
//            }).start();
        }
    }
    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private void writeSd(){
        progressDialog = new ProgressDialog(TabActivity.this);
        progressDialog.setTitle(getResources().getString(R.string.loading_title));
        progressDialog.setMessage(getResources().getString(R.string.loading_message));
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SQLdm s = new SQLdm();
                    final SQLiteDatabase db = s.openDatabase(getApplicationContext());
                    db.execSQL("DELETE FROM Player;");
                    db.execSQL("DELETE FROM Arena;");
                    db.execSQL("DELETE FROM Coach;");
                    db.execSQL("DELETE FROM Team;");
                    Log.d(TAG, "Database Finish");
                    WriteToSD.writeToSD(getApplicationContext(), getResources().getString(R.string.file_name));
                    Log.d(TAG, "Copy File Finish");
                    ReadCSV readCSV = new ReadCSV();
                    readCSV.Insert(db, getResources().getString(R.string.file_name));
                    Log.d(TAG, "Insert Finish");
                    Message message = new Message();
                    message.what = UPDATE_SUCCESS;
                    mHandler.sendMessage(message);
                }catch (Exception e){
                    e.printStackTrace();
                    Message message = new Message();
                    message.what = UPDATE_FAILED;
                    mHandler.sendMessage(message);
                }
            }
        }).start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,
                                           int[] grantResults){
        switch(requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    writeSd();
                }else{

                }
                break;
            default:
        }
    }
}
