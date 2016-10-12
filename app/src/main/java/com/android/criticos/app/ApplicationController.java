package com.android.criticos.app;

import android.app.Application;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Daniel on 28/09/2016.
 */

public class ApplicationController extends Application {


    private RequestQueue requestQueue;
    private static ApplicationController ourInstance;

    @Override
    public void onCreate()
    {
        super.onCreate();
        ourInstance = this;
    }

    public static synchronized ApplicationController getInstance()
    {
        return ourInstance;
    }

    public RequestQueue getRequestQueue()
    {
        if (requestQueue == null)
        {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req)
    {
        getRequestQueue().add(req);
    }
}