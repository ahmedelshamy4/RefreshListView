package com.example.ahmed.refreshlistview;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by ahmed on 4/4/18.
 */

public class MyApplication extends Application {

    public static final String TAG = MyApplication.class.getSimpleName();
    static MyApplication application;
    RequestQueue request;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static synchronized MyApplication getInstance() {
        return application;
    }

    public RequestQueue getRequest() {
        if (request == null) {
            request = Volley.newRequestQueue(this);
        }
        return request;
    }

    public void addToRequest(Request request, String tag) {
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequest().add(request);
    }

    public void addToRequest(Request request) {
        request.setTag(TAG);
        getRequest().add(request);
    }

    public void Cancel(Object tag) {
        if (request == null) {
            request.cancelAll(tag);
        }
    }

}
