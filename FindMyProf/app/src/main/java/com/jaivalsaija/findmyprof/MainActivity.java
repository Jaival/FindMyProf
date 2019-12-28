package com.jaivalsaija.findmyprof;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jaivalsaija.findmyprof.Professor.ProfessorDashboard;
import com.jaivalsaija.findmyprof.Student.StudentDashboard;
import com.jaivalsaija.findmyprof.data.ConstantValue;
import com.jaivalsaija.findmyprof.helper.RequestHandler;
import com.jaivalsaija.findmyprof.helper.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "Done";
    Intent intentStudent, intentProfessor, intentChangePassword;
    String[] value = {"Professor", "Student"};
    String sname;
    EditText userid, password;
    private ProgressDialog progressLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (SharedPref.getInstance(this).isStudentLoggedIn()) {
            finish();
            startActivity(new Intent(this, StudentDashboard.class));
            return;
        }
        if (SharedPref.getInstance(this).isProfessorLoggedIn()) {
            finish();
            startActivity(new Intent(this, ProfessorDashboard.class));
            return;
        }

        progressLogin = new ProgressDialog(this);
        progressLogin.setMessage("Logging In");

        intentStudent = new Intent(getApplicationContext(), StudentDashboard.class);
        intentProfessor = new Intent(getApplicationContext(), ProfessorDashboard.class);
        intentChangePassword = new Intent(getApplicationContext(), ChangePassword.class);

        FloatingActionButton fab = findViewById(R.id.fab);
        Spinner spinner = findViewById(R.id.spinner);

        userid = findViewById(R.id.editUserId);
        password = findViewById(R.id.editPassword);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, value);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(view, value[position], Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                sname = value[position];
                Log.d("Value", sname);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner.setAdapter(adapter);

        SharedPref.getInstance(getApplicationContext()).userType(sname);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = userid.getText().toString();
                final String pass = password.getText().toString();
                if (sname.equals("Professor")) {
                    if (username.equals(pass)) {
                        intentChangePassword.putExtra("type", sname);
                        intentChangePassword.putExtra("employee_id", username);
                        SharedPref.getInstance(getApplicationContext()).setProfessorid(username);
                        startActivity(intentChangePassword);
                    } else {
                        loginProfessor();
                    }
                } else if (sname.equals("Student")) {
                    if (username.equals(pass)) {
                        intentChangePassword.putExtra("type", sname);
                        intentChangePassword.putExtra("enrollment_no", username);
                        SharedPref.getInstance(getApplicationContext()).setProfessorid(username);
                        startActivity(intentChangePassword);
                    } else {
                        loginStudent();
                    }
                }
            }
        });
    }

    private void loginStudent() {
        progressLogin.show();

        final String username = userid.getText().toString();
        final String pass = password.getText().toString();

        StringRequest stringStudent = new StringRequest(
                Request.Method.POST,
                ConstantValue.Url_Log_Stu,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressLogin.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.getBoolean("error")) {
                                SharedPref.getInstance(getApplicationContext()).
                                        userStudent(
                                                jsonObject.getInt("id"),
                                                jsonObject.getString("enrollment_no"),
                                                jsonObject.getString("email"),
                                                jsonObject.getString("token"));
                                startActivity(intentStudent);
                                finish();
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
                        progressLogin.hide();
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("enrollment_no", username);
                params.put("password", pass);

                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringStudent);
//        generateStudentToken();
    }

    private void loginProfessor() {
        progressLogin.show();

        final String username = userid.getText().toString();
        final String pass = password.getText().toString();

        StringRequest stringProfessor = new StringRequest(
                Request.Method.POST,
                ConstantValue.Url_Log_Pro,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressLogin.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.getBoolean("error")) {
                                SharedPref.getInstance(getApplicationContext()).
                                        userProfessor(
                                                jsonObject.getInt("id"),
                                                jsonObject.getString("employee_id"),
                                                jsonObject.getString("email"),
                                                jsonObject.getString("token"));
                                startActivity(intentProfessor);
                                finish();
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
                        progressLogin.hide();
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("employee_id", username);
                params.put("password", pass);

                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringProfessor);
//        generateProfessorToken();
    }
//    public void generateProfessorToken() {
//        final String username = userid.getText().toString();
//
//        FirebaseInstanceId.getInstance().getInstanceId()
//                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "getInstanceId failed", task.getException());
//                            return;
//                        }
//                        // Get new Instance ID token
//                        professorToken = Objects.requireNonNull(task.getResult()).getToken();
//                        // Log and toast
////                        msg = getString("TOKEN", professorToken);
//                        Log.e(TAG, professorToken);
////                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//        StringRequest stringRegProToken = new StringRequest(
//                Request.Method.POST,
//                ConstantValue.Url_Reg_Prof_Token,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            if (!jsonObject.getBoolean("error")) {
//                            } else {
//                                Toast.makeText(getApplicationContext(),
//                                        jsonObject.getString("message"),
//                                        Toast.LENGTH_LONG).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
////                        progressDialog.dismiss();
//                        Toast.makeText(getApplicationContext(),
//                                error.getMessage(),
//                                Toast.LENGTH_LONG).show();
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//
//                params.put("employee_id", username);
//                params.put("token", professorToken);
//
//                return params;
//            }
//        };
//        RequestHandler.getInstance(this).addToRequestQueue(stringRegProToken);
//    }
//    public void generateStudentToken() {
//        final String username = userid.getText().toString();
//
//        FirebaseInstanceId.getInstance().getInstanceId()
//                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "getInstanceId failed", task.getException());
//                            return;
//                        }
//                        // Get new Instance ID token
//                        studentToken = Objects.requireNonNull(task.getResult()).getToken();
//                        // Log and toast
////                        msg = getString(R.string.msg_token_fmt, token);
////                        Log.d(TAG, msg);
//                        //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//        StringRequest stringRegStuToken = new StringRequest(
//                Request.Method.POST,
//                ConstantValue.Url_Reg_Stud_Token,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            if (!jsonObject.getBoolean("error")) {
//                            } else {
//                                Toast.makeText(getApplicationContext(),
//                                        jsonObject.getString("message"),
//                                        Toast.LENGTH_LONG).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
////                        progressDialog.dismiss();
//                        Toast.makeText(getApplicationContext(),
//                                error.getMessage(),
//                                Toast.LENGTH_LONG).show();
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//
//                params.put("enrollment_no", username);
//                params.put("token", studentToken);
//
//                return params;
//            }
//        };
//        RequestHandler.getInstance(this).addToRequestQueue(stringRegStuToken);
//    }
}
