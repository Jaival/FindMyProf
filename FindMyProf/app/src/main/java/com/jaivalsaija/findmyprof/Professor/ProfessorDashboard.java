package com.jaivalsaija.findmyprof.Professor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.jaivalsaija.findmyprof.R;

import java.util.Objects;


public class ProfessorDashboard extends AppCompatActivity {

    BottomNavigationView btmNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_dashboard);

        btmNavigation = findViewById(R.id.btm_nav);
        btmNavigation.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new HomeProfessorFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            Fragment selectedFragment = null;

            switch (menuItem.getItemId()) {
                case R.id.home:
                    selectedFragment = new HomeProfessorFragment();
                    break;
                case R.id.notify:
                    selectedFragment = new NotifyProfessorFragment();
                    break;
                case R.id.history:
                    selectedFragment = new HistoryProfessorFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, Objects.requireNonNull(selectedFragment)).commit();

            return true;
        }
    };

}
