package com.jaivalsaija.findmyprof.Student;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.jaivalsaija.findmyprof.MainActivity;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StudentDashboard extends AppCompatActivity {

    protected static final String TAG = "Done";
    RecyclerView recyclerView;
    FloatingActionButton open, home, notify, history;
    Intent intentNotify, intentHistory, intent;
    List<ProfessorData> listData;
    String studentToken, username;
    private boolean rotate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        username = SharedPref.getInstance(getApplicationContext()).getStudentEnrollment();

        listData = new ArrayList<>();

        if (SharedPref.getInstance(getApplicationContext()).getStudentToken().equals("Empty")) {
            generateStudentToken();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Token Already Generated",
                    Toast.LENGTH_LONG).show();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Dashboard");

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

        intentHistory = new Intent(getApplicationContext(), HistoryStudent.class);
        intentNotify = new Intent(getApplicationContext(), NotifyStudent.class);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            SharedPref.getInstance(this).logOut();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        return true;
    }

    public void generateStudentToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        studentToken = Objects.requireNonNull(task.getResult()).getToken();
                        // Log and toast
//                        msg = getString(R.string.msg_token_fmt, token);
//                        Log.d(TAG, msg);
                        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        StringRequest stringRegStuToken = new StringRequest(
                Request.Method.POST,
                ConstantValue.Url_Reg_Stud_Token,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.getBoolean("error")) {
                                SharedPref.getInstance(getApplicationContext()).setStudentToken(studentToken);
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        jsonObject.getString("message"),
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("enrollment_no", username);
                params.put("token", studentToken);

                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRegStuToken);
    }


    private class ListAdapter extends RecyclerView.Adapter<ViewHolder> {

        ViewHolder viewHolder;
        private List<ProfessorData> listData;
        private Context context;

        ListAdapter(List<ProfessorData> listData, Context context) {
            this.listData = listData;
            this.context = context;
        }

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            View listItem = layoutInflater.inflate(R.layout.recycler_student_dashboard, viewGroup, false);
            Toast.makeText(getApplicationContext(), "Data Binding", Toast.LENGTH_LONG).show();
            viewHolder = new ViewHolder(listItem) {
            };
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
            final ProfessorData listItem = listData.get(position);
            Toast.makeText(getApplicationContext(), "Data Binding Holder", Toast.LENGTH_LONG).show();

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
