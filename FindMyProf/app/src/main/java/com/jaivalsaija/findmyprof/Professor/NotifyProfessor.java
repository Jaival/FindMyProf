package com.jaivalsaija.findmyprof.Professor;

import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jaivalsaija.findmyprof.MainActivity;
import com.jaivalsaija.findmyprof.R;
import com.jaivalsaija.findmyprof.data.ConstantValue;
import com.jaivalsaija.findmyprof.data.NotifyProfessorData;
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

public class NotifyProfessor extends AppCompatActivity {

    RecyclerView recyclerView;
    List<NotifyProfessorData> listData;
    String response, professorId;
    String accept, reject;

    private FloatingActionButton open, home, notify, history;
    private boolean rotate = false;
    private Intent intentNotify, intentHistory, intentHome;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notify_professor);

        Toast.makeText(getApplicationContext(), "Professor", Toast.LENGTH_LONG).show();
        accept = "Accept";
        reject = "Reject";

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Notification");

        open = findViewById(R.id.fabOpen);
        home = findViewById(R.id.fabHome);
        history = findViewById(R.id.fabHistory);
        notify = findViewById(R.id.fabMore);

        ViewAnimation.initShowOut(home);
        ViewAnimation.initShowOut(history);
        ViewAnimation.initShowOut(notify);

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
        listData = new ArrayList<>();
        loadNotifyData();

//        ListData[] listData = new ListData[]{
//                new ListData("A", "aa"),
//                new ListData("B", "bb"),
//                new ListData("C", "cc"),
//                new ListData("D", "dd"),
//                new ListData("E", "ee"),
//                new ListData("E", "ee"),
//                new ListData("E", "ee"),
//        };
        intentHome = new Intent(this, ProfessorDashboard.class);
        intentHistory = new Intent(this, HistoryProfessor.class);
        intentNotify = new Intent(this, NotifyProfessor.class);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentHome);
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

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...\nLoading Data");

        professorId = SharedPref.getInstance(getApplicationContext()).getProfessorEnrollment();
        Toast.makeText(getApplicationContext(), professorId, Toast.LENGTH_LONG).show();


        recyclerView = findViewById(R.id.notifyProfessor);
        ListAdapter adapter = new ListAdapter(listData, getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

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

    //  TODO : Change params
    private void sendSinglePush(final String message, final String enrollment_no) {

        StringRequest stringPushNotify = new StringRequest(
                Request.Method.POST,
                ConstantValue.Url_Stud_Send_Notify,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(NotifyProfessor.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("title", "Request:");
                params.put("message", message);
                params.put("employee_id", enrollment_no);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringPushNotify);
    }

    private void acceptRequest(final String Id, final String Response) {
        StringRequest stringAcceptRequest = new StringRequest(
                Request.Method.POST,
                ConstantValue.Url_Accept_Reject,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(NotifyProfessor.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", Id);
                params.put("accept_reject ", "Accept");
                params.put("responseMessage", Response);

                return params;
            }
        };
        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringAcceptRequest);
    }

    private void rejectRequest(final String Id, final String Response) {
        StringRequest stringAcceptRequest = new StringRequest(
                Request.Method.POST,
                ConstantValue.Url_Accept_Reject,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(NotifyProfessor.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", Id);
                params.put("accept_reject ", "Accept");
                params.put("responseMessage", Response);

                return params;
            }
        };
        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringAcceptRequest);
    }

    private void loadNotifyData() {
//        progressDialog.show();
        StringRequest stringNotifydata = new StringRequest(
                Request.Method.POST,
                ConstantValue.Url_Get_History,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        progressDialog.dismiss();
                        try {
                            Log.e("Error", "working");
                            Toast.makeText(getApplicationContext(), "Working", Toast.LENGTH_LONG).show();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("history");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject prof = array.getJSONObject(i);
                                NotifyProfessorData data = new NotifyProfessorData(
                                        prof.getString("id"),
                                        prof.getString("studentId"),
                                        prof.getString("timeStart"),
                                        prof.getString("timeEnd"));
                                listData.add(data);
                            }
                        } catch (JSONException e) {
                            Log.e("Error", "Json error");
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        progressDialog.hide();
                        Log.e("Error", "Response Error");
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("employee_id", professorId);
                return params;
            }
        };
        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringNotifydata);
    }

    private class ListAdapter extends RecyclerView.Adapter<ViewHolder> {

        ViewHolder viewHolder;
        Context context;
        private List<NotifyProfessorData> listData;

        ListAdapter(List<NotifyProfessorData> listData, Context context) {

            this.listData = listData;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            View listItem = layoutInflater.inflate(R.layout.recycler_notification, viewGroup, false);
            Toast.makeText(getApplicationContext(), "Data Binding", Toast.LENGTH_LONG).show();
            viewHolder = new ViewHolder(listItem) {
            };

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            final NotifyProfessorData listItem = listData.get(position);
            Toast.makeText(getApplicationContext(), "Data Binding Holder", Toast.LENGTH_LONG).show();
            viewHolder.textProf.setText(listData.get(position).getstudentId());
            viewHolder.textTimeStart.setText(new StringBuilder().append("Start Time:").append(listData.get(position).getTimeStart()).toString());
            viewHolder.textTimeEnd.setText(new StringBuilder().append("Start Time:").append(listData.get(position).getTimeEnd()).toString());
            response = viewHolder.response.getText().toString();
            viewHolder.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String message = accept + "\n" + response;
                    sendSinglePush(message, listItem.getstudentId());
                    acceptRequest(listItem.getId(), response);
                }
            });

            viewHolder.reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String message = reject + "\n" + response;
                    sendSinglePush(message, listItem.getstudentId());
                    rejectRequest(listItem.getId(), response);
                }
            });
        }

        @Override
        public int getItemCount() {
            return listData.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textProf, textTimeStart, textTimeEnd;
        Button accept, reject;
        EditText response;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textProf = itemView.findViewById(R.id.textStudentName);
            this.textTimeStart = itemView.findViewById(R.id.textTimeStart);
            this.textTimeEnd = itemView.findViewById(R.id.textTimeEnd);
            this.response = itemView.findViewById(R.id.editResponse);
            this.accept = itemView.findViewById(R.id.btnAccept);
            this.reject = itemView.findViewById(R.id.btnReject);
        }
    }

//private class ListAdapter extends RecyclerView.Adapter<ViewHolder> {
//
//    ViewHolder viewHolder;
//    private ListData[] listData;
//    private Context context;
//
//    ListAdapter(ListData[] listData, Context context) {
//
//        this.listData = listData;
//        this.context = context;
//    }
//    public Context getContext() {
//        return context;
//    }
//
//    public void setContext(Context context) {
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
//        View listItem = layoutInflater.inflate(R.layout.recycler_student_notification, viewGroup, false);
//        viewHolder = new ViewHolder(listItem) {
//        };
//
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
//        final ListData myListData = listData[position];
//        viewHolder.textName.setText(listData[position].getProfName());
//        viewHolder.textTime.setText(listData[position].getDesc());
//        viewHolder.textResponse.setText(listData[position].getDesc());
//        viewHolder.response.setText(listData[position].getDesc());
//        viewHolder.accept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        viewHolder.reject.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return listData.length;
//    }
//
//}
//
//    class ViewHolder extends RecyclerView.ViewHolder {
//        TextView textName, textTime, textResponse;
//        Button accept, reject;
//        EditText response;
//
//        ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            this.textName = itemView.findViewById(R.id.textName);
//            this.textTime = itemView.findViewById(R.id.textTime);
//            this.textResponse = itemView.findViewById(R.id.textProfResponse);
//            this.response = itemView.findViewById(R.id.editStudResponse);
//            this.accept = itemView.findViewById(R.id.btnsnAccept);
//            this.reject = itemView.findViewById(R.id.btnsnReject);
//        }
//    }
}
