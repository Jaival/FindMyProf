package com.jaivalsaija.findmyprof.Student;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jaivalsaija.findmyprof.R;
import com.jaivalsaija.findmyprof.data.ConstantValue;
import com.jaivalsaija.findmyprof.data.NotifyStudentData;
import com.jaivalsaija.findmyprof.helper.RequestHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class NotifyStudent extends AppCompatActivity {

    RecyclerView recyclerView;
    List<NotifyStudentData> listData;
    String response;
    String accept, reject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notify_student);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Notification");

        accept = "Accept";
        reject = "Reject";

        recyclerView = findViewById(R.id.notify_student);
        ListAdapter adapter = new ListAdapter(listData, getApplication());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private class ListAdapter extends RecyclerView.Adapter<ViewHolder> {

        ViewHolder viewHolder;
        Context context;
        private List<NotifyStudentData> listData;

        ListAdapter(List<NotifyStudentData> listData, Context context) {

            this.listData = listData;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            View listItem = layoutInflater.inflate(R.layout.recycler_student_notification, viewGroup, false);
            Toast.makeText(getApplicationContext(), "Data Binding", Toast.LENGTH_LONG).show();
            viewHolder = new ViewHolder(listItem) {
            };

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            final NotifyStudentData listItem = listData.get(position);
            Toast.makeText(getApplicationContext(), "Data Binding Holder", Toast.LENGTH_LONG).show();
            viewHolder.textName.setText(listData.get(position).getstudentName());
            viewHolder.textTimeStart.setText(listData.get(position).getTimeStart());
            viewHolder.textTimeEnd.setText(listData.get(position).getTimeEnd());
            response = viewHolder.response.getText().toString();
            viewHolder.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String message = accept + "\n" + response;
                    sendSinglePush(message, listItem.getstudentName());
                    acceptRequest(listItem.getId(), response);
                }
            });

            viewHolder.reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String message = reject + "\n" + response;
                    sendSinglePush(message, listItem.getstudentName());
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
        TextView textName, textTimeStart, textTimeEnd, textResponse;
        Button accept, reject;
        EditText response;

        ViewHolder(View itemView) {
            super(itemView);
            this.textName = itemView.findViewById(R.id.textName);
            this.textTimeStart = itemView.findViewById(R.id.textTimeStart);
            this.textTimeEnd = itemView.findViewById(R.id.textTimeEnd);
            this.textResponse = itemView.findViewById(R.id.textProfResponse);
            this.response = itemView.findViewById(R.id.editStudResponse);
            this.accept = itemView.findViewById(R.id.btnsnAccept);
            this.reject = itemView.findViewById(R.id.btnsnReject);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }

    private void acceptRequest(final String Id, final String Response) {
        StringRequest stringAcceptRequest = new StringRequest(
                Request.Method.POST,
                ConstantValue.Url_Accept_Reject,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
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
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", Id);
                params.put("accept_reject ", "Accept");
                params.put("responseMessage", Response);

                return params;
            }
        };
        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringAcceptRequest);
    }

    private void sendSinglePush(final String message, final String enrollment_no) {

        StringRequest stringPushNotify = new StringRequest(
                Request.Method.POST,
                ConstantValue.Url_Stud_Send_Notify,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("title", "Request:");
                params.put("message", message);
                params.put("employee_id", enrollment_no);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringPushNotify);
    }

}
