package com.jaivalsaija.findmyprof.Student;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jaivalsaija.findmyprof.ListData;
import com.jaivalsaija.findmyprof.R;
import com.jaivalsaija.findmyprof.utils.ViewAnimation;


public class StudentDashboard extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton open, home, notify, history;
    private boolean rotate = false;
    Intent intentNotify, intentHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        ListData[] listData = new ListData[]{
                new ListData("A", "aa"),
                new ListData("B", "bb"),
                new ListData("C", "cc"),
                new ListData("D", "dd"),
                new ListData("E", "ee"),
                new ListData("E", "ee"),
                new ListData("E", "ee"),
        };

        recyclerView = findViewById(R.id.recycler);
        ListAdapter adapter = new ListAdapter(listData, getApplication());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        open = findViewById(R.id.fabOpen);
        home = findViewById(R.id.fabHome);
        history = findViewById(R.id.fabHistory);
        notify = findViewById(R.id.fabMore);
        ViewAnimation.initShowOut(home);
        ViewAnimation.initShowOut(history);
        ViewAnimation.initShowOut(notify);

        intentHistory = new Intent(this, HistoryStudent.class);
        intentNotify = new Intent(this, NotifyStudent.class);

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

    private class ListAdapter extends RecyclerView.Adapter<ViewHolder> {

        private ListData[] listData;
        private Context context;
        Intent intent;
        ViewHolder viewHolder;

        ListAdapter(ListData[] listData, Context context) {

            this.listData = listData;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            View listItem = layoutInflater.inflate(R.layout.recycler_student_dashboard, viewGroup, false);
            viewHolder = new ViewHolder(listItem) {
            };

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            final ListData myListData = listData[position];
            viewHolder.textProf.setText(listData[position].getProfName());
            viewHolder.textAvailable.setText(listData[position].getDesc());
            viewHolder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent = new Intent(context.getApplicationContext(), Request.class);
                    intent.putExtra("professor", myListData.getProfName());
                    intent.putExtra("Desc", myListData.getDesc());
                    startActivity(intent);
                    Toast.makeText(view.getContext(), "Click on item: " + myListData.getProfName(), Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return listData.length;
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textProf;
        TextView textAvailable;
        Button btn;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textProf = itemView.findViewById(R.id.textProfessor);
            this.textAvailable = itemView.findViewById(R.id.textAvailable);
            this.btn = itemView.findViewById(R.id.btnRequest);
        }
    }
}
