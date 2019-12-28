package com.jaivalsaija.findmyprof.Student;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jaivalsaija.findmyprof.R;
import com.jaivalsaija.findmyprof.data.ConstantValue;
import com.jaivalsaija.findmyprof.helper.RequestHandler;
import com.jaivalsaija.findmyprof.helper.SharedPref;

import java.util.HashMap;
import java.util.Map;

public class RequestProf extends AppCompatActivity {

    protected static final String TAG = "Done";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView timeStart, timeEnd;
    TextView timeStart2, timeEnd2;
    TextView timeStart3, timeEnd3;
    String documentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        timeStart = findViewById(R.id.textTimeStart);
        timeStart2 = findViewById(R.id.textTimeStart1);
        timeStart3 = findViewById(R.id.textTimeStart2);

        timeEnd = findViewById(R.id.textTimeEnd);
        timeEnd2 = findViewById(R.id.textTimeEnd1);
        timeEnd3 = findViewById(R.id.textTimeEnd2);

        documentId = SharedPref.getInstance(getApplicationContext()).getProfessorStudent();

        Toast.makeText(getApplicationContext(), documentId, Toast.LENGTH_LONG).show();

        DocumentReference documentReference = db.collection("Professor").document(documentId);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    assert doc != null;

                    String ts = "Start Time:" + doc.get("timeStart");
                    String ts1 = "Start Time:" + doc.get("timeStart1");
                    String ts2 = "Start Time:" + doc.get("timeStart2");
                    String te = "End Time:" + doc.get("timeEnd");
                    String te1 = "End Time:" + doc.get("timeEnd1");
                    String te2 = "End Time:" + doc.get("timeEnd2");

                    timeStart.setText(ts);
                    timeStart2.setText(ts1);
                    timeStart3.setText(ts2);
                    timeEnd.setText(te);
                    timeEnd2.setText(te1);
                    timeEnd3.setText(te2);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error", e);
            }
        });

    }

    private void sendSinglePush(final String title, final String message, final String enrollment_no) {

        StringRequest stringPushNotify = new StringRequest(
                Request.Method.POST,
                ConstantValue.Url_Prof_Send_Notify,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(RequestProf.this, response, Toast.LENGTH_LONG).show();
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

                params.put("title", title);
                params.put("message", message);
                params.put("employee_id", enrollment_no);

                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringPushNotify);
    }
}
