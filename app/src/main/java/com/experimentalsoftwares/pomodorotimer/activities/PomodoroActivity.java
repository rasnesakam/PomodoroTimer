package com.experimentalsoftwares.pomodorotimer.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.ViewGroup;
import android.widget.TextView;

import com.experimentalsoftwares.pomodorotimer.R;
import com.experimentalsoftwares.pomodorotimer.tools.pomodoro.services.GeneralPomodoroService;
import com.experimentalsoftwares.pomodorotimer.tools.system.AppSettings;
import com.experimentalsoftwares.pomodorotimer.tools.utils.NotifySound;
import com.experimentalsoftwares.pomodorotimer.tools.utils.Vibrator;
import com.google.android.material.snackbar.Snackbar;

public class PomodoroActivity extends AppCompatActivity {

    AppSettings appSettings;

    @Override
    protected void onPause() {
        /**
         * push notification to show status of timer
         */
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ViewGroup vg = findViewById(R.id.content);
        TextView tv = findViewById(R.id.pomodoro_timer_tv);



        GeneralPomodoroService pomodoroService = new GeneralPomodoroService(this,new Handler(Looper.myLooper())) {
            @Override
            public void onWork() {
                vg.setBackgroundResource(R.drawable.bg_pomodoro_work);
                ActionBar bar = getSupportActionBar();
                if (bar != null) bar.setTitle(R.string.pomodoro_work_time);
                new NotifySound.Work(PomodoroActivity.this).play();
                Snackbar.make(vg,R.string.pomodoro_work_time_snackbar,Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onRest() {
                vg.setBackground(ContextCompat.getDrawable(PomodoroActivity.this,R.drawable.bg_pomodoro_rest));
                ActionBar bar = getSupportActionBar();
                if (bar != null) bar.setTitle(R.string.pomodoro_rest_time);
                new NotifySound.Rest(PomodoroActivity.this).play();
                Snackbar.make(vg,R.string.pomodoro_rest_time_snackbar,Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void pulse(int h, int m, int s) {

                String text;

                if (h>0) text = String.format("%d : %d : %d",h,m,s);

                else if (m>0) text = String.format("%d : %d",m,s);

                else  text = String.format("%d",s);

                tv.setText(text);

            }
        };
        pomodoroService.setOnTimerChange(()->{
            Vibrator.vibrate(PomodoroActivity.this);
        });
        pomodoroService.preparePomodoros(2);
        pomodoroService.start();


    }


}