package com.jaivalsaija.findmyprof.Professor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.jaivalsaija.findmyprof.MainActivity;
import com.jaivalsaija.findmyprof.R;
import com.jaivalsaija.findmyprof.data.ConstantValue;
import com.jaivalsaija.findmyprof.helper.RequestHandler;
import com.jaivalsaija.findmyprof.helper.SharedPref;
import com.jaivalsaija.findmyprof.utils.ViewAnimation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class ProfessorDashboard extends AppCompatActivity {

    protected static final String TAG = "Done";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String documentId;
    ProgressDialog progressToken;
    String professorToken, username;
    private boolean rotate = false;
    private FloatingActionButton open, home, notify, history, send;
    private Intent intentNotify, intentHistory;
    private EditText timeStart, timeEnd;
    private EditText timeStart1, timeEnd1;
    private EditText timeStart2, timeEnd2;
    private String employeeId, time;
    private String start, end;
    private Switch witch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_dashboard);

        progressToken = new ProgressDialog(this);
        progressToken.setMessage("Generating Token");

        if (SharedPref.getInstance(getApplicationContext()).getProfessorToken().equals("Empty")) {
            generateProfessorToken();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Token Already Generated",
                    Toast.LENGTH_LONG).show();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dashboard");

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

//        timeStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                calendar = Calendar.getInstance();
//                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
//                currentMinute = calendar.get(Calendar.MINUTE);
//
//                timePickerDialog = new TimePickerDialog(getApplicationContext(), new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
//                        if (hourOfDay >= 12) {
//                            amPm = "PM";
//                        } else {
//                            amPm = "AM";
//                        }
//                        time = String.format("%02d:%02d", hourOfDay, minutes) + amPm;
//                        timeStart.setText(time);
//                    }
//                }, currentHour, currentMinute, false);
//
//                timePickerDialog.show();
//            }
//        });

        witch = findViewById(R.id.switchPresent);
        witch.setChecked(false);

//        setTime();

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

        witch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    send.setEnabled(true);
                    timeStart.setEnabled(true);
                    timeStart1.setEnabled(true);
                    timeStart2.setEnabled(true);
                    timeEnd.setEnabled(true);
                    timeEnd1.setEnabled(true);
                    timeEnd2.setEnabled(true);
                    setTime();
                } else if (!isChecked) {
                    send.setEnabled(false);
                    timeStart.setEnabled(false);
                    timeStart1.setEnabled(false);
                    timeStart2.setEnabled(false);
                    timeEnd.setEnabled(false);
                    timeEnd1.setEnabled(false);
                    timeEnd2.setEnabled(false);
                }
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start = timeStart.getText().toString();
                end = timeEnd.getText().toString();

                Map<String, Object> dataToSave = new HashMap<>();
                dataToSave.put("timeStart", start);
                dataToSave.put("timeEnd", end);
                dataToSave.put("timeStart1", timeStart1.getText().toString());
                dataToSave.put("timeEnd1", timeEnd1.getText().toString());
                dataToSave.put("timeStart2", timeStart2.getText().toString());
                dataToSave.put("timeEnd2", timeEnd2.getText().toString());

                db.collection("Professor").document(employeeId).set(dataToSave)
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

    public void generateProfessorToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        professorToken = Objects.requireNonNull(task.getResult()).getToken();
                        // Log and toast
//                        msg = getString("TOKEN", professorToken);
                        Log.e(TAG, professorToken);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        StringRequest stringRegProToken = new StringRequest(
                Request.Method.POST,
                ConstantValue.Url_Reg_Prof_Token,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.getBoolean("error")) {
                                SharedPref.getInstance(getApplicationContext()).setProfessorToken(professorToken);
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

                params.put("employee_id", username);
                params.put("token", professorToken);

                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRegProToken);
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
