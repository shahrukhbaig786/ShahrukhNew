package com.example.intel.virtual_receptionist;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.intel.virtual_receptionist.URL.Urls;

import org.json.JSONObject;

/**
 * Created by intel on 12/15/2016.
 */
public class OperationsaveToNetwork {
    ProgressDialog progressDialog;
    Context ctx;
    RequestQueue mRequestQueue;
    int socketTimeout;
    RetryPolicy policy;
    JsonObjectRequest jsonObjectRequest;


    public OperationsaveToNetwork(Context ctx) {
        this.ctx = ctx;
    }


    public void register(String email, String password, String fullname, String username, final VolleyCallback volleyCallback) {
       // final int[] number = new int[1];
        String url = Urls.registerUrl + "?email=" + email + "&password=" + password + "&name=" + fullname + "&username=" + username;
        mRequestQueue = Volley.newRequestQueue(ctx);
        policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        socketTimeout = getRequestTimeout();
        //http://82.145.38.202/sachin/pannel/api/register.php?
        // email=s@gmail.com&password=123&name=sachin&username=sachinagarwal
        progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage("Creating new MemberShip...");
        progressDialog.show();

        jsonObjectRequest= new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("msg").equals("added")) {
                       // number[0] = 1;
                        Log.e(" RESPONSE  ----", "ADDED" + "");
                        volleyCallback.onSuccessResponse("0");
                        progressDialog.dismiss();
                    } else if (jsonObject.getString("msg").equals("already")) {

                        progressDialog.dismiss();
                        volleyCallback.onSuccessResponse("1");
                        //number[0] = 2;
                        Log.e(" RESPONSE  ----", "ADDED added" + "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(" RESPONSE  ----", "Catch Block Exe---" + "");

                    progressDialog.dismiss();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(" RESPONSE  ----", "Error Override Method");

                AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
                volleyCallback.onSuccessResponse(Urls.AlertMsgs.errorMsg);
                dialog.setTitle("Alert");
                dialog.setMessage(Urls.AlertMsgs.errorMsg);
                dialog.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         dialog.dismiss();

                    }
                });
                dialog.create().show();
            }
        });

        jsonObjectRequest.setRetryPolicy(policy);
        mRequestQueue.add(jsonObjectRequest);
        Log.e(" RESPONSE  ----", "Return Value" + "");



    }

    public int getRequestTimeout() {
        int timeout = 0;
        if (HttpConnect.isOnline(ctx)) {
            timeout = 60000;
        } else {
            timeout = 2000;
        }
        return timeout;
    }


    public interface VolleyCallback {
        void onSuccessResponse(String result);
    }
}
