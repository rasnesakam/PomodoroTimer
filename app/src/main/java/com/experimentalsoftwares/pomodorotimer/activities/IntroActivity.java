package com.experimentalsoftwares.pomodorotimer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.experimentalsoftwares.pomodorotimer.R;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        startActivity(new Intent(this,MainActivity.class));
    }
}