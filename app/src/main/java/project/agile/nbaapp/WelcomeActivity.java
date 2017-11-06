package project.agile.nbaapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initWindow();
        setContentView(R.layout.activity_welcome);
        MyTimer timer = new MyTimer();
        timer.start();//启动线程
    }

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                    Intent intent = new Intent();
                    intent.setClass(WelcomeActivity.this, TabActivity.class);
                    getWindow().setFlags(0, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.in_alpha,
                            R.anim.out_alpha);
                    break;
                default:
                    break;
            }
        };
    };

    @TargetApi(19)
    private void initWindow(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    public class MyTimer extends Thread {
        public MyTimer() {
            // TODO Auto-generated constructor stub
        }
        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {
                Thread.sleep(2000);
                mHandler.sendEmptyMessage(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
