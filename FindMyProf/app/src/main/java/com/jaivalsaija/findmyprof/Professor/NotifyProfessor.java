package com.jaivalsaija.findmyprof.Professor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jaivalsaija.findmyprof.R;
import com.jaivalsaija.findmyprof.data.ListData;

public class NotifyProfessor extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notify_professor);

        ListData[] listData = new ListData[]{
                new ListData("A", "aa"),
                new ListData("B", "bb"),
                new ListData("C", "cc"),
                new ListData("D", "dd"),
                new ListData("E", "ee"),
                new ListData("E", "ee"),
                new ListData("E", "ee"),
        };

        recyclerView = findViewById(R.id.notifyRecycler);
        ListAdapter adapter = new ListAdapter(listData, getApplication());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

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
            View listItem = layoutInflater.inflate(R.layout.recycler_notification, viewGroup, false);
            viewHolder = new ViewHolder(listItem) {
            };

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            final ListData myListData = listData[position];
            viewHolder.textProf.setText(listData[position].getProfName());
            viewHolder.textAvailableTime.setText(listData[position].getDesc());
            viewHolder.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            viewHolder.reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

        @Override
        public int getItemCount() {
            return listData.length;
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textProf, textAvailableTime;
        Button accept, reject;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textProf = itemView.findViewById(R.id.textProfessor);
            this.textAvailableTime = itemView.findViewById(R.id.textTime);
            this.accept = itemView.findViewById(R.id.btnAccept);
            this.reject = itemView.findViewById(R.id.btnReject);
        }
    }
}
