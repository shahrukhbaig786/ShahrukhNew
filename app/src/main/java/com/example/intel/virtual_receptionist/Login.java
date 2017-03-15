package com.example.intel.virtual_receptionist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.intel.virtual_receptionist.ApplicationController.ApplicationController;
import com.example.intel.virtual_receptionist.DBManager.DBHandler;
import com.example.intel.virtual_receptionist.DBManager.MainStorage;
import com.example.intel.virtual_receptionist.URL.Urls;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by intel on 12/7/2016.
 */
public class Login extends Activity implements OnClickListener {

    Button login;
    TextView sign_up;
    String username, password = null;
    TextView et_username, et_password;
    ProgressDialog progressDialog;
    boolean firstTime;
    boolean dbStatus;
    public static final String KEY_USERNAME = "email";
    public static final String KEY_PASSWORD = "password";
    //public static final String KEY_EMAIL = "email";

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    MainStorage storage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storage = new MainStorage(getApplicationContext());
        setContentView(R.layout.login);
        login = (Button) findViewById(R.id.login);
        sign_up = (TextView) findViewById(R.id.sign_up);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        login.setOnClickListener(this);
        sign_up.setOnClickListener(this);
        String uname, pwd = "";
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            firstTime = bundle.getBoolean("first_time");

            Log.e("First Time Running app ", " " + firstTime);
        }

        dbStatus = storage.getDbStatus();
        Log.e("DBStatus ", " " + dbStatus);

        if (firstTime) {

            storage.setFirstTime();


        } else {

            if (dbStatus == true) {

                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Signing in...");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                Log.e("DB status ", "returned");
                RequestQueue mRequestQueue = Volley.newRequestQueue(this);
                int socketTimeout = getRequestTimeout();
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                storage.setDbStatus();
                SparseArray<String> stringSparseArray = storage.getUsernamePassword();
                uname = stringSparseArray.get(0);
                pwd = stringSparseArray.get(1);
                username = uname;
                password = pwd;
                storage.CloseDB();
                String url = Urls.loginUrl + "?email=" + username + "&password=" + password;

                progressDialog.show();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {

                            if (jsonObject.getString("msg").equals("200")) {
                                SharedPreferences preferences;
                                SharedPreferences.Editor edit;
                                progressDialog.dismiss();
                                preferences = getSharedPreferences(DBHandler.preferencesName, Context.MODE_PRIVATE);
                                edit = preferences.edit();
                                MainStorage storeCredentials = new MainStorage(getApplicationContext(), jsonObject);
                                storeCredentials.setDbStatus();
                                storeCredentials.storeUserData();
                                String credits = jsonObject.getString("credits");
                                String fullname = jsonObject.getString("name");

                                edit.commit();
                                //long res = storeCredentials.storeVehicleData();
                                // Log.e(" ",storeCredentials.getUid());
                                Intent i = new Intent(Login.this, DashBoard.class);
                                //Log.e(" ",storeCredentials.getUid());
                                i.putExtra("emailname", username);
                                i.putExtra("credits", credits);
                                i.putExtra("fullname", fullname);
                                startActivity(i);


                            } else {
                                progressDialog.dismiss();
                                Urls.createDialog(Login.this, Urls.AlertMsgs.invalidCredentialMsg);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(Login.this);
                        dialog.setTitle("Alert");
                        dialog.setMessage(Urls.AlertMsgs.errorMsg);
                        dialog.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                finish();
                                moveTaskToBack(true);


                            }
                        });
                        dialog.setOnCancelListener(new OnCancelListener() {

                            @Override
                            public void onCancel(DialogInterface dialog) {
                                dialog.dismiss();
                                moveTaskToBack(true);
                                finish();

                            }
                        });
                        dialog.create().show();

                    }
                });


                jsonObjectRequest.setRetryPolicy(policy);
                mRequestQueue.add(jsonObjectRequest);


            } else {
                login = (Button) findViewById(R.id.login);
                sign_up = (TextView) findViewById(R.id.sign_up);
                et_username = (EditText) findViewById(R.id.et_username);
                et_password = (EditText) findViewById(R.id.et_password);
                login.setOnClickListener(this);
                sign_up.setOnClickListener(this);


            }


        }



/*

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            String username = getIntent().getExtras().getString("email");
            et_username.setText(username);
            et_password.setHint(Html.fromHtml("<font color='#ae0101'>Enter  Password</font>"));

        }
        username= et_username.getText().toString().trim();
*/


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.login:
                username = et_username.getText().toString().trim();
                password = et_password.getText().toString().trim();

                //RequestQueue mRequestQueue = Volley.newRequestQueue(this);
                int socketTimeout = getRequestTimeout();
                //RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                progressDialog = new ProgressDialog(Login.this);
                progressDialog.setMessage("Signing in...");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                String url = Urls.loginUrl + "?email=" + username + "&password=" + password;
                // String url = Urls.loginUrl;
                //http://82.145.38.202/sachin/pannel/api/login.php
                //http://82.145.38.202/sachin/pannel/api/login.php?email=sachinagrawal092@gmail.com&password=admin
                progressDialog.show();

                Map<String, String> params = new HashMap<String, String>();
                params.put("userID", "userid");
                params.put("email", "email");
                params.put("passwd", "password");

                if (username.length() > 0 && password.length() > 0) {


                    JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                if (jsonObject.getString("msg").equals("200")) {
                                    SharedPreferences preferences = getSharedPreferences(DBHandler.preferencesName, Context.MODE_PRIVATE);
                                    ;
                                    SharedPreferences.Editor edit = preferences.edit();
                                    ;
                                    progressDialog.dismiss();
                                    String name = jsonObject.getString("uername");
                                    String email = jsonObject.getString("email");
                                    MainStorage storage = new MainStorage(getApplicationContext(), jsonObject);
                                    storage.setDbStatus();
                                    storage.storeCredentials(username, password);
                                    edit.putString("uid", jsonObject.getString("userid"));
                                    edit.commit();
                                    storage.storeUserData();
                                    String credits = jsonObject.getString("credits");
                                    String fullname = jsonObject.getString("name");
                                    Intent intent = new Intent(Login.this, DashBoard.class);
                                    intent.putExtra("emailname", username);
                                    intent.putExtra("credits", credits);
                                    intent.putExtra("fullname", fullname);
                                    startActivity(intent);
                                    finish();
                                } else if (jsonObject.getString("msg").equals("201")) {
                                    progressDialog.dismiss();
                                    Urls.createDialog(Login.this, Urls.AlertMsgs.registerbutNotActivated);

                                } else if (jsonObject.getString("msg").equals("202")) {
                                    progressDialog.dismiss();
                                    Urls.createDialog(Login.this, Urls.AlertMsgs.invalidCredentialMsg);

                                } else {
                                    progressDialog.dismiss();
                                    Urls.createDialog(Login.this, Urls.AlertMsgs.noResultMsg);

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    progressDialog.dismiss();
                                    Toast.makeText(Login.this, error.toString(), Toast.LENGTH_LONG).show();
                                }
                            }) {


                    };
                    ApplicationController.getInstance().addToRequestQueue(stringRequest);
               /*  RequestQueue requestQueue = Volley.newRequestQueue(this);
                    requestQueue.add(stringRequest);*/

/*

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {

                                if (jsonObject.getString("msg").equals("200")) {
                                    progressDialog.dismiss();
                                    String name = jsonObject.getString("uername");
                                    String email = jsonObject.getString("email");
                                    MainStorage storage=new MainStorage(getApplicationContext(),jsonObject);
                                    storage.setDbStatus();
                                    storage.storeCredentials(username,password);
                                    storage.storeUserData();
                                    String credits=jsonObject.getString("credits");
                                    String  fullname=jsonObject.getString("name");
                                    Intent intent = new Intent(Login.this, DashBoard.class);
                                    intent.putExtra("emailname",username);
                                    intent.putExtra("credits",credits);
                                    intent.putExtra("fullname",fullname);
                                    startActivity(intent);
                                    finish();
                                } else if (jsonObject.getString("msg").equals("201")) {
                                    progressDialog.dismiss();
                                    Urls.createDialog(Login.this, Urls.AlertMsgs.registerbutNotActivated);

                                } else if (jsonObject.getString("msg").equals("202")) {
                                    progressDialog.dismiss();
                                    Urls.createDialog(Login.this, Urls.AlertMsgs.invalidCredentialMsg);

                                } else {
                                    progressDialog.dismiss();
                                    Urls.createDialog(Login.this, Urls.AlertMsgs.noResultMsg);

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(Login.this);
                            dialog.setTitle("Alert");
                            dialog.setMessage(Urls.AlertMsgs.errorMsg);
                            dialog.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                    moveTaskToBack(true);


                                }
                            });

                            dialog.create().show();

                        }
                    });


                    jsonObjectRequest.setRetryPolicy(policy);
                    mRequestQueue.add(jsonObjectRequest);

                */


                } else {
                    progressDialog.dismiss();
                    Urls.createDialog(Login.this, Urls.AlertMsgs.allFieldsMsg);
                }


                break;

            case R.id.sign_up:


                Intent intent1 = new Intent(Login.this, Register.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                break;


        }
    }


    public int getRequestTimeout() {
        int timeout = 0;
        if (HttpConnect.isOnline(this)) {
            timeout = 60000;
        } else {
            timeout = 2000;
        }
        return timeout;
    }
}