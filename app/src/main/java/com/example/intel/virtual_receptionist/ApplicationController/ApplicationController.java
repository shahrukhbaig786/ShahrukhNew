package com.example.intel.virtual_receptionist.ApplicationController;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.example.intel.virtual_receptionist.Model.Agent;

/**
 * Created by intel on 1/4/2017.
 */
public class ApplicationController extends Application {

    java.util.ArrayList list = new java.util.ArrayList<Agent>();
    public static final String TAG = "VolleyPatterns";
    private RequestQueue mRequestQueue;
    private static ApplicationController sInstance;
    private static View toastRoot;
    ;

   /* public void storeUpdatedAgent(java.util.ArrayList<Agent> newList) {
        java.util.List list1 = new java.util.ArrayList<Agent>();

        java.util.ArrayList agent = new java.util.ArrayList(newList);


        list = agent;

    }*/


   /* public java.util.ArrayList<Agent> get_Updated_Agent_list() {

        list.get(0);
        list.get(1);
        list.get(2);
        list.get(3);
        list.get(4);
        list.get(5);
        list.get(6);


        return list;
    }*/

    public ApplicationController() {
        sInstance = this;
    }

    public static Context getContext() {
        if (sInstance == null) {
            sInstance = new ApplicationController();
        }
        return sInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static synchronized ApplicationController getInstance() {
        return sInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        VolleyLog.e("Adding request to queue: %s", req.getUrl());
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);

        getRequestQueue().add(req);
    }






    private Fragment mCurrentFragement = null;

    public Fragment getmCurrentFragement() {
        return mCurrentFragement;
    }

    public void setmCurrentFragement(Fragment mCurrentActivity) {
        this.mCurrentFragement = mCurrentFragement;
    }


}