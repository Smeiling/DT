package com.dt.sml.duitang.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dt.sml.duitang.R;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingViewActivity extends Activity {

    private Timer timer;
    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_view);
        timer = new Timer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        timer.schedule(timerTask, 1000, 1000);
    }

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if (count == 0) {
                finish();
            } else {
                count--;
            }
        }
    };
}
