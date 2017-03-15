package com.example.intel.virtual_receptionist;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intel.virtual_receptionist.DBManager.DBHandler;
import com.example.intel.virtual_receptionist.DBManager.MainStorage;
import com.example.intel.virtual_receptionist.URL.Urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by intel on 12/22/2016.
 */
public class showProfileSettingFragment extends Fragment implements View.OnClickListener {

    String name, username, email, password;
    EditText name_tv, username_tv, email_tv, password_tv;
    TextView credit_available;
    List<String> info;
    Button update_btn;
    ProgressDialog pDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View myview = inflater.inflate(R.layout.tab_show_profile, container, false);

        name_tv = (EditText) myview.findViewById(R.id.profile_et_name);
        username_tv = (EditText) myview.findViewById(R.id.profile_et_username);
        email_tv = (EditText) myview.findViewById(R.id.profile_et_email);
        credit_available = (TextView) myview.findViewById(R.id.credit_available);
        password_tv = (EditText) myview.findViewById(R.id.profile_et_password);
        update_btn = (Button) myview.findViewById(R.id.update_btn_setting);


        return myview;


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        info = new ArrayList<String>();
        update_btn.setOnClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        MainStorage storage = new MainStorage(getActivity());
        info = storage.getData();
        if (!(info.isEmpty())) {

            info.get(0);//UserId
            info.get(1);//name
            info.get(2);//username
            info.get(3);//email
            info.get(4);//password
            username_tv.setText(info.get(1));
            name_tv.setText(info.get(2));
            email_tv.setText(info.get(3));
            password_tv.setText(info.get(4));
            credit_available.setText(" " + info.get(5));
        }
    }

    @Override
    public void onClick(View view) {
        final DBHandler handler = new DBHandler(getActivity());
        final String uid = handler.getUserId();
        if (view.getId() == R.id.update_btn_setting) {
            name = name_tv.getText().toString().trim();
            username = username_tv.getText().toString().trim();
            email = email_tv.getText().toString().trim();
            password = password_tv.getText().toString().trim();
            boolean isTrue = validate();
            if (isTrue) {


                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                pDialog = new ProgressDialog(getActivity());
                pDialog.setMessage("Updating Information Please wait..");
                pDialog.setCanceledOnTouchOutside(false);
                pDialog.show();
                params.add("name", name);
                params.add("username", username);
                params.add("email", email);
                params.add("newpassword", password);
                client.setTimeout(10 * 1000);
                client.post(Urls.settingUrlpassword + uid, params, new JsonHttpResponseHandler() {
                            @Override
                            public void onStart() {
                                super.onStart();
                                pDialog.show();
                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                pDialog.dismiss();
                                try {

                                    if (response.getString("msg").equals("200")) {
                                        Urls.createDialogSuccess(getActivity(), Urls.AlertMsgs.Updated);
                                        AsyncHttpClient client = new AsyncHttpClient();
                                        RequestParams params = new RequestParams();

                                        String url = Urls.loginUrl + "?email=" + email + "&password=" + password;
                                        client.post(url, params, new JsonHttpResponseHandler() {
                                            @Override
                                            public void onStart() {
                                                super.onStart();
                                            }

                                            @Override
                                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                                try {
                                                    //If response code is 404 means No data is Available
                                                    if (response.getString("msg").equals("201")) {


                                                        Urls.createDialog(getActivity(), Urls.AlertMsgs.EmailExistAlready);
                                                        //editor.putBoolean("data_in_server",false);
                                                    }
                                                    if (response.getString("msg").equals("202")) {
                                                        Urls.createDialog(getActivity(), Urls.AlertMsgs.WentWrong);
                                                        //editor.putBoolean("data_in_server",false);
                                                    } else {
                                                        MainStorage storage = new MainStorage(getActivity(), response);

                                                        storage.storeUserData();


                                                    }
                                                } catch (JSONException e) {
                                                    Toast.makeText(getActivity(), " Error in JSON " + e.toString(), Toast.LENGTH_SHORT).show();
                                                    e.printStackTrace();
                                                } catch (Exception e) {
                                                    Toast.makeText(getActivity(), " Error Main Exception " + e, Toast.LENGTH_LONG).show();
                                                    e.printStackTrace();
                                                }
                                            }

                                            @Override
                                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                                super.onFailure(statusCode, headers, responseString, throwable);
                                            }
                                        });

                                    } else {
                                        Urls.createDialog(getActivity(), Urls.AlertMsgs.Error);
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                                if (throwable.getCause() instanceof UnknownHostException) {
                                    Toast.makeText(getActivity(), "No internet connection...!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "Unable to connect, Please check your network connection...!", Toast.LENGTH_SHORT).show();

                                }

                                pDialog.dismiss();

                            }

                        }

                );


            } else {
                Urls.createDialog(getActivity(), Urls.AlertMsgs.allFieldsMsg);

            }


        }


    }

    @Override
    public void onPause() {
        super.onPause();


    }

    @Override
    public void onStop() {
        super.onStop();
        if (info != null) {
            info.clear();
        }
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    private boolean validate() {
        boolean isTrue = false;
        if (!(name.length() > 0)) {
            name_tv.requestFocus();
            name_tv.setHint(Html.fromHtml("<font color='#ae0101'>Enter new Name</font>"));
            isTrue = false;

        }
        if (!(username.length() > 0)) {
            username_tv.requestFocus();
            username_tv.setHint(Html.fromHtml("<font color='#ae0101'>Enter username Name</font>"));
            isTrue = false;
        }
        if (!(email.length() > 0)) {
            email_tv.requestFocus();
            email_tv.setHint(Html.fromHtml("<font color='#ae0101'>Enter new email</font>"));
            isTrue = false;
        }
        if (!(password.length() > 0)) {
            password_tv.requestFocus();
            password_tv.setHint(Html.fromHtml("<font color='#ae0101'>Enter new Password Name</font>"));
            isTrue = false;
        } else {
            isTrue = true;
        }

        return isTrue;
    }
}
