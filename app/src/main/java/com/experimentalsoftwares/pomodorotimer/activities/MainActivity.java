package com.experimentalsoftwares.pomodorotimer.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;


import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;


import com.experimentalsoftwares.pomodorotimer.R;
import com.experimentalsoftwares.pomodorotimer.fragments.AboutFragment;
import com.experimentalsoftwares.pomodorotimer.fragments.PomodoroFragment;
import com.experimentalsoftwares.pomodorotimer.fragments.SettingsFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private FragmentManager fragmentManager;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer = findViewById(R.id.drawer_view);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation_view);
        setSupportActionBar(toolbar);

        initDrawer();

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_activity_container,new PomodoroFragment()).commit();

    }

    @Override
    public void onBackPressed() {

        // close drawer if opened
        if (drawer.isDrawerOpen(GravityCompat.START)) drawer.closeDrawer(GravityCompat.START);

        else super.onBackPressed();

    }

    private void initDrawer(){
        navigationView.setNavigationItemSelectedListener(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            toggle = new ActionBarDrawerToggle(
                    this, // Activity
                    drawer, // DrawerLayout
                    toolbar,
                    R.string.navigation_draw_open, // Drawer opened string
                    R.string.navigation_draw_close // Drawer closed string
            );
            toggle.syncState();
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.drawer_pomodoro_start:
                startActivity(new Intent(this,PomodoroActivity.class));
                break;

            case R.id.drawer_app_settings:
                fragmentManager.beginTransaction().replace(R.id.main_activity_container,new SettingsFragment()).commit();
                break;

            case R.id.drawer_about:
                fragmentManager.beginTransaction().replace(R.id.main_activity_container,new AboutFragment()).commit();
                break;


        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}