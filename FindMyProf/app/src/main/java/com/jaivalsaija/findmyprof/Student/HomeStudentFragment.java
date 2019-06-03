package com.jaivalsaija.findmyprof.Student;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jaivalsaija.findmyprof.ListAdapter;
import com.jaivalsaija.findmyprof.ListData;
import com.jaivalsaija.findmyprof.R;

public class HomeStudentFragment extends Fragment {

    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_student, container, false);

        ListData[] listData = new ListData[]{
                new ListData("A", "aa"),
                new ListData("B", "bb"),
                new ListData("C", "cc"),
                new ListData("D", "dd"),
                new ListData("E", "ee"),
        };

        recyclerView = view.findViewById(R.id.recycler);
        ListAdapter adapter = new ListAdapter(listData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return view;
    }
}