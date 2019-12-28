package com.jaivalsaija.findmyprof.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    private static final String Shared_Pref_Name = "Data";
    private static final String Student_Id = "StudentId";
    private static final String Student_Enrollment = "StudentEnrollment";
    private static final String Student_Email = "StudentEmail";
    private static final String Professor_Id = "ProfessorId";
    private static final String Professor_Enrollment = "ProfessorEnrollment";
    private static final String Professor_Email = "ProfessorEmail";
    private static final String Student_Token = "StudentToken";
    private static final String Professor_Token = "ProfessorToken";
    private static final String TYPE = "userType";
    private static final String Student = "Student";
    private static final String Professor = "Professor";

    @SuppressLint("StaticFieldLeak")
    private static SharedPref instance;
    private Context ctx;

    private SharedPref(Context context) {
        ctx = context;
    }

    public static synchronized SharedPref getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPref(context);
        }
        return instance;
    }

    public boolean setStudentid(String type) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(Shared_Pref_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(Student, type);

        editor.apply();
        return true;
    }

    public String getStudentid() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(Shared_Pref_Name, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Student, null);
    }

    public boolean setStudentToken(String type) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(Shared_Pref_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(Student_Enrollment, type);

        editor.apply();
        return true;
    }

    public boolean setProfessorToken(String type) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(Shared_Pref_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(Professor_Enrollment, type);

        editor.apply();
        return true;
    }

    public boolean setProfessorid(String type) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(Shared_Pref_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(Professor, type);

        editor.apply();
        return true;
    }

    public String getProfessorid() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(Shared_Pref_Name, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Professor, null);
    }

    public boolean userType(String type) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(Shared_Pref_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TYPE, type);

        editor.apply();
        return true;
    }

    public String getUserType() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(Shared_Pref_Name, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TYPE, null);
    }

    public boolean userStudent(int id, String enrollment, String email, String token) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(Shared_Pref_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(Student_Id, id);
        editor.putString(Student_Enrollment, enrollment);
        editor.putString(Student_Email, email);
        editor.putString(Student_Token, token);

        editor.apply();
        return true;
    }

    public boolean userProfessor(int id, String enrollment, String email, String token) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(Shared_Pref_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(Professor_Id, id);
        editor.putString(Professor_Enrollment, enrollment);
        editor.putString(Professor_Email, email);
        editor.putString(Professor_Token, token);

        editor.apply();
        return true;
    }

    public boolean isStudentLoggedIn() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(Shared_Pref_Name, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Student_Enrollment, null) != null;

    }

    public boolean isProfessorLoggedIn() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(Shared_Pref_Name, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Professor_Enrollment, null) != null;

    }

    public boolean logOut() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(Shared_Pref_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.apply();
//        StringRequest stringProfessor = new StringRequest(
//                RequestProf.Method.POST,
//                ConstantValue.Url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//
//                return params;
//            }
//        };
//        RequestHandler.getInstance(ctx.getApplicationContext()).addToRequestQueue(stringProfessor);
        return true;
    }

    public String getStudentEnrollment() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(Shared_Pref_Name, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Student_Enrollment, null);
    }

    public String getStudentEmail() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(Shared_Pref_Name, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Student_Email, null);
    }

    public String getProfessorEnrollment() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(Shared_Pref_Name, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Professor_Enrollment, null);
    }

    public String getProfessorEmail() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(Shared_Pref_Name, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Professor_Email, null);
    }

    public void setProfessor(String enrollment) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(Shared_Pref_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(Professor_Enrollment, enrollment);

        editor.apply();
    }

    public String getProfessorStudent() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(Shared_Pref_Name, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Professor_Enrollment, null);
    }

    public String getStudentToken() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(Shared_Pref_Name, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Student_Token, null);
    }

    public String getProfessorToken() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(Shared_Pref_Name, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Professor_Token, null);
    }
}
