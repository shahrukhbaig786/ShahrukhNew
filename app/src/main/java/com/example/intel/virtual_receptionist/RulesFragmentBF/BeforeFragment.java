package com.example.intel.virtual_receptionist.RulesFragmentBF;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intel.virtual_receptionist.DBManager.DBHandler;
import com.example.intel.virtual_receptionist.Model.BeforeRule;
import com.example.intel.virtual_receptionist.R;
import com.example.intel.virtual_receptionist.RuleAdapterMajor.RuleAdapter_Before;
import com.example.intel.virtual_receptionist.URL.Urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by intel on 2/28/2017.
 */
public class BeforeFragment extends Fragment {
    RecyclerView recycleListView;
    private ProgressDialog progressDialog;
    ArrayList<BeforeRule> rule_before_list;
    RuleAdapter_Before adapter;
    private GridLayoutManager gridLayoutManager;
    String spinner_number_ID;
    LinearLayout line1;
    TextView norecord;
    ImageView img_back;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list_view, container, false);
        recycleListView = (RecyclerView) view.findViewById(R.id._directory_list_view);
        line1 = (LinearLayout) view.findViewById(R.id.line1);
        norecord = (TextView) view.findViewById(R.id.norecord);
        img_back = (ImageView) view.findViewById(R.id.img_back);
        doBack_ground_tsk();
        start();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //here what im going to do is to take the Spinner value and Load the Server Value

        final Bundle bundle = getArguments();
        spinner_number_ID = bundle.getString("NUMBER");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rule_before_list = new ArrayList<BeforeRule>();
        //here what im going to do is to take the Spinner value and Load the Server Value


    }


    private void doBack_ground_tsk() {
        //Method Start from Here
        //http://82.145.38.202/sachin/pannel/api/rules_by_no.php?uid=27&nid=3
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading");
        progressDialog.setCanceledOnTouchOutside(false);
        DBHandler storage = new DBHandler(getActivity());
        String userUID = storage.getUserId();
        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
        //params.add("nid",numberId);
        client.setTimeout(10 * 1000);
        client.post(Urls.rule_by_number + userUID + "&nid=" + spinner_number_ID, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        progressDialog.show();
                    }
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        //super.onSuccess(statusCode, headers, response);
                        if (response == null || response == null || response.length() == 0) {
                            progressDialog.dismiss();
                            return;
                        } else {
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObjectparent = null;
                                JSONArray parent_Array_before_rule = null;
                                jsonObjectparent = response.getJSONObject("msg");
                                parent_Array_before_rule = jsonObjectparent.getJSONArray("before_rules");
                                if (parent_Array_before_rule.length() == 0 || parent_Array_before_rule == null) {// It menas no records are there try to add the rules
                                    line1.setVisibility(View.VISIBLE);
                                    img_back.setBackgroundResource(R.drawable.norecord);

                                    norecord.setText("No Rules are added ");
                                } else {
                                    for (int i = 0; i < parent_Array_before_rule.length(); i++) {
                                        JSONObject object = parent_Array_before_rule.getJSONObject(i);
                                        BeforeRule data_before_list = new BeforeRule(object.getString("id"),
                                                object.getString("uid"), object.getString("caller"),
                                                object.getString("action"), object.getString("dtmf_rules"),
                                                object.getString("voicemail_rules"), object.getString("phone_rules"),
                                                object.getString("ivrid"), object.getString("description"),
                                                object.getString("notifications"), object.getString("dateadded"),
                                                object.getString("ip"), object.getString("nid"));
                                        rule_before_list.add(data_before_list);//Save in list
                                    }
                                    adapter = new RuleAdapter_Before(getActivity(), rule_before_list);
                                    recycleListView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                System.out.println("ERRRORR1 " + statusCode + "" + "" + e);
                                Toast.makeText(getActivity(), "" + e.toString(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            } catch (Exception e) {
                                System.out.println("ERRRORR2 " + statusCode + "" + "" + e);
                                Toast.makeText(getActivity(), " Error " + e, Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                                System.out.println("ERRRORR3 " + statusCode + "" + "" + e);
                            }
                        }

                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        progressDialog.dismiss();
                        System.out.println("ERRRORR " + statusCode + "" + throwable + "" + responseString);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        progressDialog.dismiss();
                        System.out.println("ERRRORR " + statusCode + "" + throwable);
                    }
                }

        );
    }//Method End from Here

    private void start() {
        gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recycleListView.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        rule_before_list.clear();

    }
}
