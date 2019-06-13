package com.jaivalsaija.findmyprof;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jaivalsaija.findmyprof.data.ConstantValue;
import com.jaivalsaija.findmyprof.helper.RequestHandler;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {
    FloatingActionButton fab;
    EditText password, confirmpassword;
    String uname, type;
    Intent thisIntent, gotoMain;
    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        fab = findViewById(R.id.change);
        password = findViewById(R.id.edit_Password);
        confirmpassword = findViewById(R.id.edit_ConfirmPassword);

        thisIntent = getIntent();
        gotoMain = new Intent(getApplicationContext(), MainActivity.class);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().equals(confirmpassword.getText().toString())) {
                    type = thisIntent.getStringExtra("type");
                    if (type.equals("Professor")) {
                        uname = thisIntent.getStringExtra("employee_id");
                        pass = confirmpassword.getText().toString();
                        changeProfessorPassword(uname, pass);
                    } else if (type.equals("Student")) {
                        uname = thisIntent.getStringExtra("enrollment_no");
                        pass = confirmpassword.getText().toString();
                        changeStudentPassword(uname, pass);
                    }
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            "Password and Confirm Password are not same",
                            Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    private void changeStudentPassword(final String usrname, final String password) {
        StringRequest stringChangeStudentPassword = new StringRequest(
                Request.Method.POST,
                ConstantValue.Url_Change_Student_Password,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        startActivity(gotoMain);
                        finish();
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("enrollment_no", usrname);
                params.put("password", password);

                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringChangeStudentPassword);
    }

    private void changeProfessorPassword(final String usrname, final String password) {
        StringRequest stringChangeStudentPassword = new StringRequest(
                Request.Method.POST,
                ConstantValue.Url_Change_Professor_Password,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        startActivity(gotoMain);
                        finish();
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("enrollment_no", usrname);
                params.put("password", password);

                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringChangeStudentPassword);
    }

}
