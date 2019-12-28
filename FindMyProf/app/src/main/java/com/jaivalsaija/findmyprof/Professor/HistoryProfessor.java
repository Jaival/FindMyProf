package com.jaivalsaija.findmyprof.Professor;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jaivalsaija.findmyprof.R;
import com.jaivalsaija.findmyprof.data.ConstantValue;
import com.jaivalsaija.findmyprof.data.HistoryData;
import com.jaivalsaija.findmyprof.helper.RequestHandler;
import com.jaivalsaija.findmyprof.helper.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryProfessor extends AppCompatActivity {
    RecyclerView recyclerView;
    List<HistoryData> listData;
    String professorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_professor);

        professorId = SharedPref.getInstance(getApplicationContext()).getProfessorEnrollment();

        loadNotifyData();

        recyclerView = findViewById(R.id.historyProfessor);
        ListAdapter adapter = new ListAdapter(listData, getApplication());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private class ListAdapter extends RecyclerView.Adapter<ViewHolder> {

        ViewHolder viewHolder;
        Context context;
        private List<HistoryData> listData;

        ListAdapter(List<HistoryData> listData, Context context) {

            this.listData = listData;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            View listItem = layoutInflater.inflate(R.layout.recycler_history, viewGroup, false);
            Toast.makeText(getApplicationContext(), "Data Binding", Toast.LENGTH_LONG).show();
            viewHolder = new ViewHolder(listItem) {
            };

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            final HistoryData listItem = listData.get(position);
            Toast.makeText(getApplicationContext(), "Data Binding Holder", Toast.LENGTH_LONG).show();
            viewHolder.textProf.setText(listData.get(position).getStudentName());
            viewHolder.textTimeStart.setText(new StringBuilder().append("Start Time:").append(listData.get(position).getTimeStart()).toString());
            viewHolder.textTimeEnd.setText(new StringBuilder().append("Start Time:").append(listData.get(position).getTimeEnd()).toString());
            viewHolder.response.setText(listData.get(position).getResponseMessage());
            viewHolder.accept.setText(listData.get(position).getAcceptReject());
        }

        @Override
        public int getItemCount() {
            return listData.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textProf, textTimeStart, textTimeEnd, accept, response;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textProf = itemView.findViewById(R.id.textName);
            this.textTimeStart = itemView.findViewById(R.id.textTimeStart);
            this.textTimeEnd = itemView.findViewById(R.id.textTimeEnd);
            this.response = itemView.findViewById(R.id.textResponse);
            this.accept = itemView.findViewById(R.id.textAcRe);
        }
    }

    private void loadNotifyData() {
//        progressDialog.show();
        StringRequest stringNotifydata = new StringRequest(
                Request.Method.POST,
                ConstantValue.Url_History,
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
                                HistoryData data = new HistoryData(
                                        prof.getString("studentId"),
                                        prof.getString("timeStart"),
                                        prof.getString("timeEnd"),
                                        prof.getString("accept_reject"),
                                        prof.getString("responseMessage"));
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("employee_id", professorId);
                return params;
            }
        };
        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringNotifydata);
    }
}
