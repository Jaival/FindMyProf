package com.jaivalsaija.findmyprof.Student;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jaivalsaija.findmyprof.R;
import com.jaivalsaija.findmyprof.data.ListData;

public class HistoryStudent extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_student);

        ListData[] listData = new ListData[]{
                new ListData("A", "aa"),
                new ListData("B", "bb"),
                new ListData("C", "cc"),
                new ListData("D", "dd"),
                new ListData("E", "ee"),
                new ListData("E", "ee"),
                new ListData("E", "ee"),
        };

        recyclerView = findViewById(R.id.historyStudent);
        ListAdapter adapter = new ListAdapter(listData, getApplication());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private class ListAdapter extends RecyclerView.Adapter<ViewHolder> {

        ViewHolder viewHolder;
        private ListData[] listData;
        private Context context;

        ListAdapter(ListData[] listData, Context context) {

            this.listData = listData;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            View listItem = layoutInflater.inflate(R.layout.recycler_history, viewGroup, false);
            viewHolder = new ViewHolder(listItem) {
            };

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            final ListData myListData = listData[position];
            viewHolder.textName.setText(listData[position].getProfName());
            viewHolder.textTime.setText(listData[position].getDesc());
            viewHolder.textResponse.setText(listData[position].getDesc());
            viewHolder.textAcRe.setText(listData[position].getDesc());
        }

        @Override
        public int getItemCount() {
            return listData.length;
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textTime, textResponse, textAcRe;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textName = itemView.findViewById(R.id.textName);
            this.textTime = itemView.findViewById(R.id.textTime);
            this.textResponse = itemView.findViewById(R.id.textResponse);
            this.textAcRe = itemView.findViewById(R.id.textAcRe);
        }
    }
}
