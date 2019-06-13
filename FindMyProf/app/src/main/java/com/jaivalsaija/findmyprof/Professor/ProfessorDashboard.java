package com.jaivalsaija.findmyprof.Professor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jaivalsaija.findmyprof.R;
import com.jaivalsaija.findmyprof.helper.SharedPref;
import com.jaivalsaija.findmyprof.utils.ViewAnimation;

import java.util.HashMap;
import java.util.Map;


public class ProfessorDashboard extends AppCompatActivity {

    protected static final String TAG = "Done";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private boolean rotate = false;
    String documentId;
    private FloatingActionButton open, home, notify, history, send;
    private Intent intentNotify, intentHistory;
    private EditText timeStart, timeEnd;
    private EditText timeStart1, timeEnd1;
    private EditText timeStart2, timeEnd2;
    private String employeeId;
    private String start, end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_dashboard);

        employeeId = SharedPref.getInstance(getApplicationContext()).getProfessorEnrollment();
        open = findViewById(R.id.fabOpen);
        home = findViewById(R.id.fabHome);
        history = findViewById(R.id.fabHistory);
        notify = findViewById(R.id.fabMore);
        ViewAnimation.initShowOut(home);
        ViewAnimation.initShowOut(history);
        ViewAnimation.initShowOut(notify);

        timeStart = findViewById(R.id.editTimeStart);
        timeStart1 = findViewById(R.id.editTimeStart2);
        timeStart2 = findViewById(R.id.editTimeStart3);

        timeEnd = findViewById(R.id.editTimeEnd);
        timeEnd1 = findViewById(R.id.editTimeEnd2);
        timeEnd2 = findViewById(R.id.editTimeEnd3);

        send = findViewById(R.id.Done);

        intentHistory = new Intent(this, HistoryProfessor.class);
        intentNotify = new Intent(this, NotifyProfessor.class);

        setTime();

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

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start = timeStart.getText().toString();
                end = timeEnd.getText().toString();
//                if((timeStart.getText() != null) & (timeEnd.getText() != null)) {
//                    Log.e("Error","Update Time");
////                    updateTime(timeStart.getText().toString(),timeEnd.getText().toString());
//                }
//                else {
//                    Log.e("Error","Add Time");
////                    addTime(timeStart.getText().toString(),timeEnd.getText().toString());
//                }
                Map<String, Object> dataToSave = new HashMap<>();
                dataToSave.put("timeStart", start);
                dataToSave.put("timeEnd", end);
                dataToSave.put("timeStart1", timeStart1.getText().toString());
                dataToSave.put("timeEnd1", timeEnd1.getText().toString());
                dataToSave.put("timeStart2", timeStart2.getText().toString());
                dataToSave.put("timeEnd2", timeEnd2.getText().toString());

                db.collection("Professor").document("" + employeeId.toString() + "").set(dataToSave)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "Data Fetched");

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error", e);
                    }
                });
            }
        });
//    private void addTime(final String startTime, final String endTime) {
//
//        StringRequest stringAddTime = new StringRequest(
//                RequestProf.Method.POST,
//                ConstantValue.Url_add_Time,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            Toast.makeText(ProfessorDashboard.this,
//                                    jsonObject.getString("message"),
//                                    Toast.LENGTH_LONG).show();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(ProfessorDashboard.this,
//                                error.getMessage(),
//                                Toast.LENGTH_LONG).show();
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//
//                params.put("profId", employeeId);
//                params.put("timeStart", startTime);
//                params.put("timeEnd", endTime);
//
//                return params;
//            }
//        };
//        RequestHandler.getInstance(this).addToRequestQueue(stringAddTime);
//    }
//
//    private void updateTime(final String startTime, final String endTime) {
//
//        StringRequest stringTimeUpdate = new StringRequest(
//                RequestProf.Method.POST,
//                ConstantValue.Url_update_Time,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            Toast.makeText(ProfessorDashboard.this,
//                                    jsonObject.getString("message"),
//                                    Toast.LENGTH_LONG).show();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(ProfessorDashboard.this,
//                                error.getMessage(),
//                                Toast.LENGTH_LONG).show();
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//
//                params.put("profId", employeeId);
//                params.put("timeStart", startTime);
//                params.put("timeEnd", endTime);
//
//                return params;
//            }
//        };
//        RequestHandler.getInstance(this).addToRequestQueue(stringTimeUpdate);
//    }
    }

    public void setTime() {
        documentId = SharedPref.getInstance(getApplicationContext()).getProfessorEnrollment();

        Toast.makeText(getApplicationContext(), documentId, Toast.LENGTH_LONG).show();

        DocumentReference documentReference = db.collection("Professor").document(documentId);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    assert doc != null;

                    String ts = doc.get("timeStart").toString();
                    String ts1 = doc.get("timeStart1").toString();
                    String ts2 = doc.get("timeStart2").toString();
                    String te = doc.get("timeEnd").toString();
                    String te1 = doc.get("timeEnd1").toString();
                    String te2 = doc.get("timeEnd2").toString();

                    timeStart.setText(ts);
                    timeStart1.setText(ts1);
                    timeStart2.setText(ts2);
                    timeEnd.setText(te);
                    timeEnd1.setText(te1);
                    timeEnd2.setText(te2);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error", e);
            }
        });

    }
}
