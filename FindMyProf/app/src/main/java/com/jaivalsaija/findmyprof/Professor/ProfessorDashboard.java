package com.jaivalsaija.findmyprof.Professor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jaivalsaija.findmyprof.R;
import com.jaivalsaija.findmyprof.utils.ViewAnimation;


public class ProfessorDashboard extends AppCompatActivity {

    FloatingActionButton open, home, notify, history;
    private boolean rotate = false;
    Intent intentNotify, intentHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_dashboard);

        open = findViewById(R.id.fabOpen);
        home = findViewById(R.id.fabHome);
        history = findViewById(R.id.fabHistory);
        notify = findViewById(R.id.fabMore);
        ViewAnimation.initShowOut(home);
        ViewAnimation.initShowOut(history);
        ViewAnimation.initShowOut(notify);

        intentHistory = new Intent(this, HistoryProfessor.class);
        intentNotify = new Intent(this, NotifyProfessor.class);


        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotate = ViewAnimation.rotateFab(v, !rotate);
                if (rotate) {
                    ViewAnimation.showIn(home);
                    ViewAnimation.showIn(history);
                    ViewAnimation.showIn(notify);
                } else {
                    ViewAnimation.showOut(home);
                    ViewAnimation.showOut(history);
                    ViewAnimation.showOut(notify);
                }
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentHistory);
            }
        });

        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentNotify);
            }
        });
    }



}
