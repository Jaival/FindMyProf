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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jaivalsaija.findmyprof.R;
import com.jaivalsaija.findmyprof.data.ConstantValue;
import com.jaivalsaija.findmyprof.data.ProfessorData;
import com.jaivalsaija.findmyprof.helper.RequestHandler;
import com.jaivalsaija.findmyprof.helper.SharedPref;
import com.jaivalsaija.findmyprof.utils.ViewAnimation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class StudentDashboard extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton open, home, notify, history;
    Intent intentNotify, intentHistory, intent;
    List<ProfessorData> listData;
    private boolean rotate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        listData = new ArrayList<>();

        StringRequest stringProfdata = new StringRequest(
                Request.Method.POST,
                ConstantValue.Url_get_Prof,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("professor");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject prof = array.getJSONObject(i);
                                ProfessorData data = new ProfessorData(
                                        prof.getString("name"),
                                        prof.getString("available"),
                                        prof.getString("employee_id"));
                                listData.add(data);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringProfdata);

        recyclerView = findViewById(R.id.recycler);
        ListAdapter adapter = new ListAdapter(listData, getApplication());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //loadRecyclerViewData();

        open = findViewById(R.id.fabOpen);
        home = findViewById(R.id.fabHome);
        history = findViewById(R.id.fabHistory);
        notify = findViewById(R.id.fabMore);
        ViewAnimation.initShowOut(home);
        ViewAnimation.initShowOut(history);
        ViewAnimation.initShowOut(notify);

        intentHistory = new Intent(this, HistoryStudent.class);
        intentNotify = new Intent(this, NotifyStudent.class);
        intent = new Intent(getApplicationContext(), RequestProf.class);

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

//    private void loadRecyclerViewData() {
//            }

    private class ListAdapter extends RecyclerView.Adapter<ViewHolder> {

        ViewHolder viewHolder;
        private List<ProfessorData> listData;
        private Context context;

        public ListAdapter(List<ProfessorData> listData, Context context) {
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
        public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
            final ProfessorData listItem = listData.get(position);
//            final LauncherActivity.ListItem myListData = (List) listData.get(position);
            viewHolder.textProf.setText(listData.get(position).getName());
            viewHolder.textAvailable.setText(listData.get(position).getAvailable());
            viewHolder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPref.getInstance(view.getContext()).setProfessor(listItem.getEmployeeId());
//                    intent.putExtra("employeeId", listItem.getEmployeeId());
                    startActivity(intent);
//                    Toast.makeText(view.getContext(), "Click on item: " + listItem.getEmployeeId(), Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return listData.size();
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
