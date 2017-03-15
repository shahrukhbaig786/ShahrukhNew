package com.example.intel.virtual_receptionist;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intel.virtual_receptionist.DBManager.DBHandler;
import com.example.intel.virtual_receptionist.Model.Agent;
import com.example.intel.virtual_receptionist.URL.Urls;
import com.example.intel.virtual_receptionist.adapter.CustomAgentAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by intel on 2/7/2017.
 */
public class CustomFragment extends Fragment implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    ArrayList<Agent> myagent;
    private AlertDialog.Builder dialogBuilder;
    private CustomAgentAdapter CustomAgentAdapter;
    private GridLayoutManager gridLayoutManager;
    public EditText agent_name, agent_sip_name, agent_password, agent_mobile;
    public Spinner active_or_inactive;
    AlertDialog dialog;
    Button add_agent_button;
    public String agent_Name, agent_sip_Name, agent_Password, agent_mobo_Number;
    public String active_or_inactiveID = "0";
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.dummy_layout, container, false);
        recyclerView = (RecyclerView) myView.findViewById(R.id.directory_recycler_view);
        start();


        setHasOptionsMenu(true);

        final FloatingActionButton myFab = (FloatingActionButton) myView.findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Show dialog to add the Item in the ArrayList
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adddItem();
                    }
                });
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && myFab.isShown())
                    myFab.hide();


            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    myFab.show();
                }
                super.onScrollStateChanged(recyclerView, newState);


            }
        });


        return myView;
    }


    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem menu_item = (MenuItem) menu.findItem(R.id.update_btn_setting);
        menu_item.setVisible(true);//Here what im tryin gto do is ---- visibling the search item in the ActionBar
        SearchView search_item = (SearchView) MenuItemCompat.getActionView(menu_item);
        search_item.setOnQueryTextListener(CustomFragment.this);
    }

    @Override
    public void onResume() {
        super.onResume();
        start();
        loadDataFromServer();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myagent.clear();


    }

    private void adddItem() {

        dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.content_agent_profile, null);
        dialogBuilder.setView(dialogView);
        dialog = dialogBuilder.create();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);

        // dialog.show();
/*

        Dialog d = dialogBuilder.setView(new View(getActivity())).create();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(d.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        d.show();
        d.getWindow().setAttributes(lp);
*/


        TextView txt_heading = (TextView) dialogView.findViewById(R.id.txt_heading);
        txt_heading.setText("Add Agent");
        agent_name = (EditText) dialogView.findViewById(R.id.agent_name);
        agent_sip_name = (EditText) dialogView.findViewById(R.id.agent_sip_username);
        agent_password = (EditText) dialogView.findViewById(R.id.agent_password);
        agent_mobile = (EditText) dialogView.findViewById(R.id.agent_mobile);
        active_or_inactive = (Spinner) dialogView.findViewById(R.id.agent_active_or_Inactive);
        TextView acc_status1 = (TextView) dialogView.findViewById(R.id.acc_status1);
        acc_status1.setVisibility(View.GONE);
        ArrayList<String> active_orInactive = new ArrayList<>();
        active_orInactive.add("Active");
        active_orInactive.add("Inactive");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_item, active_orInactive);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        active_or_inactive.setAdapter(adapter);
        add_agent_button = (Button) dialogView.findViewById(R.id.add_agent);
        add_agent_button.setText("SAVE AGENT");

        add_agent_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHandler storage = new DBHandler(getActivity());
                String uid = storage.getUserId();
                boolean ISTrue = letsValidate();
                if (ISTrue) {
                    final ProgressDialog pDialog;
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();
                    pDialog = new ProgressDialog(getActivity());
                    pDialog.setMessage("Updating Information Please wait..");
                    pDialog.setCanceledOnTouchOutside(false);
                    pDialog.show();
                    params.add("agent_name", agent_Name);
                    params.add("sip_username", agent_sip_Name);
                    params.add("sip_password", agent_Password);
                    params.add("mobile", agent_mobo_Number);
                    params.add("account_status", active_or_inactiveID);
                    client.setTimeout(10 * 1000);
                    Log.d(" SSSS", params.toString());
                    System.out.println(" SSSS" + params.toString());
                    int nsmr = 0;
                    client.post(Urls.agent_add + uid, params, new JsonHttpResponseHandler() {
                                @Override
                                public void onStart() {
                                    super.onStart();
                                    pDialog.show();
                                }

                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                                    try {

                                        if (response.getString("msg").equals("200")) {
                                            pDialog.dismiss();
                                          /*  Urls.createDialogSuccess(getActivity(), Urls.AlertMsgs.success);*/
                                            dialog.dismiss();
                                           /* Toast.makeText(getActivity(), "Added Successfully ", Toast.LENGTH_SHORT).show();
                                          */
                                            Urls.createDialogSuccess(getActivity(), Urls.AlertMsgs.success);
                                            myagent.clear();
                                            loadDataFromServer();


                                        }
                                        if (response.getString("msg").equals("202")) {
                                            pDialog.dismiss();
                                            Urls.createDialog(getActivity(), Urls.AlertMsgs.alreadyaddded);
                                        }
                                        if (response.getString("msg").equals("404")) {
                                            pDialog.dismiss();
                                            Urls.createDialog(getActivity(), Urls.AlertMsgs.server);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                    super.onFailure(statusCode, headers, throwable, errorResponse);

                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                    super.onFailure(statusCode, headers, responseString, throwable);
                                }
                            }
                    );
                } else {
                    Urls.createDialog(getContext(), Urls.AlertMsgs.allFieldsMsg);
                }
                //http://82.145.38.202/sachin/pannel/api/agent_details.php?
                // uid=27&action=add&agent_name=srkemo&sip_username=125672&sip_password=1212012&mobile=9876543210&account_status=1


            }

            private boolean letsValidate() {
                String activeOR_Inactive = active_or_inactive.getSelectedItem().toString();
                if (activeOR_Inactive.equals("Active")) {
                    active_or_inactiveID = "1";
                }
                boolean isTrue;
                agent_Name = agent_name.getText().toString().trim();
                agent_sip_Name = agent_sip_name.getText().toString().trim();
                agent_Password = agent_password.getText().toString().trim();
                agent_mobo_Number = agent_mobile.getText().toString().trim();
                if (!(agent_Name.length() > 0)) {
                    agent_name.requestFocus();
                    return false;
                }
                if (!(agent_sip_Name.length() > 0)) {
                    agent_sip_name.requestFocus();
                    agent_sip_name.animate();
                    return false;
                }
                if (!(agent_Password.length() > 0)) {
                    agent_password.requestFocus();
                    agent_password.animate();
                    return false;
                }
                if (!(agent_mobo_Number.length() > 0)) {
                    agent_mobile.requestFocus();
                    agent_mobile.animate();
                    return false;
                } else {
                    isTrue = true;
                }
                return isTrue;
            }
        });
    }

    private void start() {
        myagent = new ArrayList<>();
        CustomAgentAdapter = new CustomAgentAdapter(getActivity(), myagent);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(CustomAgentAdapter);
        recyclerView.setHasFixedSize(true);
        getActivity().registerForContextMenu(recyclerView);
    }

    private void loadDataFromServer() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        DBHandler storage = new DBHandler(getActivity());
        String uid = storage.getUserId();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.setTimeout(10 * 1000);
        client.post(Urls.agent_list + uid, params, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                progressDialog.show();
            }

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (response == null || response.length() == 0) {
                    Toast.makeText(getActivity(), " Failed Check Network", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    try {
                        progressDialog.dismiss();

                        JSONObject jsonObjectparent = null;
                        JSONArray parent_Array = null;
                        jsonObjectparent = response.getJSONObject("msg");
                        parent_Array = jsonObjectparent.getJSONArray("Agent_list");
                        for (int i = 0; i < parent_Array.length(); i++) {

                            JSONObject object = parent_Array.getJSONObject(i);
                            Agent data = new Agent(object.getString("id"),
                                    object.getString("agent_name"), object.getString("sip_username"),
                                    object.getString("sip_password"), object.getString("mobile"),
                                    object.getString("active"), object.getString("date_added")
                            );


                            myagent.add(data);//Save in list


                        }
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), "" + e.toString(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), " Error " + e, Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
                CustomAgentAdapter.notifyDataSetChanged();

            }


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getActivity(), " Error " + throwable, Toast.LENGTH_LONG).show();
                progressDialog.dismiss();

            }
        });


    }


    @Override
    public void onStop() {
        super.onStop();
        if (myagent != null) {
            myagent.clear();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;


    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // This will invoke when user write something in the ActionBar

        newText = newText.toLowerCase();
        ArrayList<Agent> new_List = new ArrayList<>();

        for (Agent aagent : myagent) {
            String name = aagent.getAgent_Name().toLowerCase();
            if (name.contains(newText)) {

                new_List.add(aagent);

            }
            CustomAgentAdapter.Filter_Item(new_List);

        }
        return true;
    }
}
