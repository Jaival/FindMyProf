package com.jaivalsaija.findmyprof.helper;

import android.annotation.SuppressLint;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestHandler {
    @SuppressLint("StaticFieldLeak")
    private static RequestHandler instance;
    @SuppressLint("StaticFieldLeak")
    private static Context ctx;
    private RequestQueue requestQueue;

    private RequestHandler(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized RequestHandler getInstance(Context context) {
        if (instance == null) {
            instance = new RequestHandler(context);
        }
        return instance;
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
