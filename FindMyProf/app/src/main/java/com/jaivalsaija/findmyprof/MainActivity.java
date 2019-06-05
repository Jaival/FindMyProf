package com.jaivalsaija.findmyprof;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.jaivalsaija.findmyprof.Professor.ProfessorDashboard;
import com.jaivalsaija.findmyprof.Student.StudentDashboard;

public class MainActivity extends AppCompatActivity {

    Intent intentStudent;
    Intent intentProfessor;
    String[] value = {"Student", "Professor"};
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intentStudent = new Intent(this, StudentDashboard.class);
        intentProfessor = new Intent(this, ProfessorDashboard.class);

        FloatingActionButton fab = findViewById(R.id.fab);
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, value);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(view, value[position], Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                name = value[position];
                Log.d("Value", name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.equals("Student")) {
                    startActivity(intentStudent);
                } else {
                    startActivity(intentProfessor);
                }

            }
        });
    }
}
