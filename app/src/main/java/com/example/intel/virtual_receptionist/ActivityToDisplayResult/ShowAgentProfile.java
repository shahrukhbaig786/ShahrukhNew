package com.example.intel.virtual_receptionist.ActivityToDisplayResult;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intel.virtual_receptionist.DBManager.DBConstants;
import com.example.intel.virtual_receptionist.DBManager.DBHandler;
import com.example.intel.virtual_receptionist.Model.Agent;
import com.example.intel.virtual_receptionist.R;
import com.example.intel.virtual_receptionist.URL.Urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class ShowAgentProfile extends AppCompatActivity implements View.OnClickListener {
    private EditText agenT_name, agenT_sip_username, agenT_password, agenT_mobile;
    private Spinner active_or_inactive;
    private TextView calander_date, txt_heading;
    private Button add_agent;
    public String idactive;
    public String string_agent_name, string_agent_sip_name, string_agent_password, string_agent_mobile, date_added;
    public String spinner_active_orInactive;
    ArrayAdapter<String> adapter;
    public String agent_uid;
    ArrayList<Agent> myagent = new ArrayList<>();
    TextView agent_active_or_not;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txt_heading = (TextView) findViewById(R.id.txt_heading);
        agenT_name = (EditText) findViewById(R.id.agent_name);
        agenT_sip_username = (EditText) findViewById(R.id.agent_sip_username);
        agent_active_or_not = (TextView) findViewById(R.id.agent_active_or_not);
        agent_active_or_not = (TextView) findViewById(R.id.agent_active_or_not);
        agenT_password = (EditText) findViewById(R.id.agent_password);
        agenT_mobile = (EditText) findViewById(R.id.agent_mobile);
        active_or_inactive = (Spinner) findViewById(R.id.agent_active_or_Inactive);
        add_agent = (Button) findViewById(R.id.add_agent);
        agent_uid = getIntent().getStringExtra("Agent_Id");
        agenT_name.setEnabled(false);
        agenT_sip_username.setEnabled(false);
        agenT_password.setEnabled(false);
        agenT_mobile.setEnabled(false);
        active_or_inactive.setEnabled(false);
        add_agent.setEnabled(false);

    }


    @Override
    protected void onStart() {
        super.onStart();
        List<String> active_orInactive = new ArrayList<>();
        active_orInactive.add("Inactive");
        active_orInactive.add("Active");
        adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, active_orInactive);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        active_or_inactive.setAdapter(adapter);
        calander_date = (TextView) findViewById(R.id.calendarView);
        agenT_name.setText(getIntent().getStringExtra("Agent_Name"));
        getSupportActionBar().setTitle(getIntent().getStringExtra("Agent_Name"));
        agenT_sip_username.setText(getIntent().getStringExtra("AgentSip_Name"));
        agenT_password.setText(getIntent().getStringExtra("Agent_Password"));
        date_added = getIntent().getStringExtra("date_added");
        agenT_mobile.setText(getIntent().getStringExtra("AgentMobile"));
        try {
            String nameof_item = getIntent().getStringExtra("active_or_Inactive");
            active_or_inactive.setSelection(Integer.valueOf(nameof_item));

        } catch (Exception e) {
            Toast.makeText(this, "Account activated Error ", Toast.LENGTH_SHORT).show();
        }




        /*int position = adapter.getPosition(getIntent().getStringExtra("active_or_Inactive"));
        System.out.println("Position is " + position);
        System.out.println("Item value we got is " + getIntent().getStringExtra("active_or_Inactive"));//Correct

        if (position == 0) {
            active_or_inactive.setSelection(position);
        } else {
            active_or_inactive.setSelection(1);
        }*/

        char[] date = getIntent().getStringExtra("date_added").toCharArray();
        String da = "";
        for (int i = 0; i < 10; i++) {
            //Just TimePass here i'm trying to get the month-date-year only from the server, i don't need time
            char data = date[i];
            da += data;
        }
        try {
            calander_date.setText(da);
        } catch (NumberFormatException e) {
            Toast.makeText(getBaseContext(), "Date  Exception" + e, Toast.LENGTH_SHORT).show();
        }

    }


    // This method will involed when user perform some operation on the MenuOption
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.update_btn_setting) {
            agenT_name.setEnabled(true);
            agenT_sip_username.setEnabled(true);
            agenT_password.setEnabled(true);
            agenT_mobile.setEnabled(true);
            active_or_inactive.setEnabled(true);
            txt_heading.setText("Update Agent");
            add_agent.setEnabled(true);
            agenT_name.setCursorVisible(true);
            agenT_sip_username.setCursorVisible(true);
            agenT_password.setCursorVisible(true);
            agenT_mobile.setCursorVisible(true);
            add_agent.setOnClickListener(ShowAgentProfile.this);


        }


        if (id == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    //This is responsible to construct the menu option file
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;

    }


    @Override
    public void onClick(View view) {
        //Get the id of the view
        int id = view.getId();
        if (id == R.id.add_agent) {
            DBHandler storage = new DBHandler(ShowAgentProfile.this);
            String uid = storage.getUserId();
            final ProgressDialog pDialog;


            //http://82.145.38.202/sachin/pannel/api/agent_details.php?
            // uid=27&action=edit&agent_name=shdemo&sip_username=121012&sip_password=1212012&mobile=9876543210&account_status=0&id=79


            final String activeOr_Inactive = active_or_inactive.getSelectedItem().toString();
            if (activeOr_Inactive.equals("Active")) {
                idactive = "1";

            } else {
                idactive = "0";
            }
            boolean isTrue = validateAll();
            if (isTrue) {
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                pDialog = new ProgressDialog(ShowAgentProfile.this);
                pDialog.setMessage("Updating Please wait..");
                pDialog.setCanceledOnTouchOutside(false);
                client.setTimeout(10 * 1000);
                //We enter here and want to perform the Updated in the DataTable
                pDialog.show();
                params.add("agent_name", string_agent_name);
                params.add("sip_username", string_agent_sip_name);
                params.add("sip_password", string_agent_password);
                params.add("mobile", string_agent_mobile);
                params.add("account_status", idactive);
                //
                http:
//82.145.38.202/sachin/pannel/api/agent_details.php?uid=27
                // &action=edit&agent_name=shdemo&sip_username=121012&sip_password=1212012&mobile=9876543210&account_status=1&id=79


                client.post(Urls.agent_update + uid + "&id=" + agent_uid, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        pDialog.show();
                        ;
                    }

                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        if (response == null || response.length() == 0) {
                            Toast.makeText(ShowAgentProfile.this, " Failed Check Network", Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            try {
                                if (response.getString("msg").equals("200")) {
                                    pDialog.dismiss();
                                    Urls.createDialogSuccess(ShowAgentProfile.this, Urls.AlertMsgs.Updated);
                                    //Here iam going to implement the code that will perform , update tne the list
                                    SharedPreferences preferences = getSharedPreferences(DBConstants.preferencesName, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putBoolean("agent_data_modified", true);
                                    editor.commit();


                                } else {
                                    pDialog.dismiss();
                                    Urls.createDialog(ShowAgentProfile.this, Urls.AlertMsgs.errorMsg);
                                }

                            } catch (Exception e) {
                                Toast.makeText(ShowAgentProfile.this, " Error " + e, Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }


                    }


                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Toast.makeText(ShowAgentProfile.this, " Error " + throwable, Toast.LENGTH_LONG).show();
                    }
                });


            } else {
                Urls.createDialog(ShowAgentProfile.this, Urls.AlertMsgs.allFieldsMsg);
            }

            //Add or Uodate the agent in the Server

         /*   params.add("agent_name", agent_Name);
            params.add("sip_username", agent_sip_Name);
            params.add("sip_password", agent_Password);
            params.add("mobile", agent_mobo_Number);
            params.add("account_status", active_or_inactiveID);*/


        }


    }

    private boolean validateAll() {
        boolean isTrue = false;
        string_agent_name = agenT_name.getText().toString().toString().trim();
        string_agent_sip_name = agenT_sip_username.getText().toString().trim();
        string_agent_password = agenT_password.getText().toString().trim();
        string_agent_mobile = agenT_mobile.getText().toString().trim();
        if (!(string_agent_name.length() > 0)) {

        }
        if (!(string_agent_sip_name.length() > 0)) {
            agenT_sip_username.requestFocus();
            return false;
        }
        if (!(string_agent_password.length() > 0)) {
            agenT_password.requestFocus();
            return false;
        }
        if (!(string_agent_mobile.length() > 0)) {
            agenT_mobile.requestFocus();
            return false;
        } else {
            isTrue = true;
        }
        return isTrue;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myagent.clear();
    }
}



