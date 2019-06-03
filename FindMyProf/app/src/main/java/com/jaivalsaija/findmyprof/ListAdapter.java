package com.jaivalsaija.findmyprof;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private ListData[] listData;

    public ListAdapter(ListData[] listData) {
        this.listData = listData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.recycler_view_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem) {
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
                Toast.makeText(view.getContext(), "Click on item: " + myListData.getProfName(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return listData.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textProf;
        public TextView textAvailable;
        public Button btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textProf = itemView.findViewById(R.id.textProfessor);
            this.textAvailable = itemView.findViewById(R.id.textAvailable);
            this.btn = itemView.findViewById(R.id.btnRequest);
        }
    }
}