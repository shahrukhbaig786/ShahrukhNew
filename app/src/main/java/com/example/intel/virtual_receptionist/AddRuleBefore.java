package com.example.intel.virtual_receptionist;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.intel.virtual_receptionist.DBManager.DBHandler;
import com.example.intel.virtual_receptionist.Model.AgentIdName;
import com.example.intel.virtual_receptionist.Model.Sound;
import com.example.intel.virtual_receptionist.CustomizeSpinnerOFAgent.MultiSelectionSpinner;
import com.example.intel.virtual_receptionist.URL.Urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by intel on 3/4/2017.
 */
public class AddRuleBefore extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, MultiSelectionSpinner.OnMultipleItemsSelectedListener {
    Spinner _rule_before_Spinner;
    String[] action = {"BLACK LIST", "DTMF", "MISSED CALL", "PHONE", "SOUND", "VOICE MAIL"};
    ArrayAdapter _rule_before_adpater, multiple;
    LinearLayout layout1_case1, layout2_case2, layout3_case3, layout4_case4, layout5_case5, layout6_case6;
    EditText case1_caller_edit_text, case1_description_edit_text, case2_caller_edit_text, case4_caller_edit_text, case4_description_edit_text,
            case6_caller_edit_text, case6_description_edit_text, case6_email_edit_text, case2_description_edit_text, case3_description_edit_text, case3_caller_edit_text, case5_description_edit_text, case5_caller_edit_text;
    Spinner case2_welcome_sound_spinner, case6_pre_sound_spinner, case6_post_sound_spinner, case5_sound_spinner, case2_no_input_sound_spinner, case2_wrong_input_sound_spinner, case4_pre_sound_spinner, case4_hold_sound_spinner, case4_error_sound_spinner, case4_call_mode_spinner, case4_total_agent_spinner;
    MultiSelectionSpinner agent_customoze_spinner;
    SwitchCompat switchButton;
    String CASE4_CALLMODE;
    ListView selected_agent_listView;
    Button add_rule_before_activity;
    String userUID;
    int CHECKED = 0;
    AsyncHttpClient client;
    RequestParams params = null;
    static ProgressDialog pDialog = null;
    String numberID, Rule_Before = null;
    public static ArrayList<Sound> case2_welcome_sound_list = null;
    public static ArrayList<AgentIdName> agent_list_API = null;
    public static ArrayList case2_sound_name_welcome_POJO = null;
    public static ArrayList agent_list_API_POJO = null;
    public static ArrayList case2_no_input_sound_POJO = null;
    ArrayAdapter case2_welcome_sound_adapter, case2_no_input_sound_adapter, case2_wrong_input_sound_adapter, case4_pre_sound_adapter, case4_error_sound_adapter,
            case4_hold_sound_adapter, case5_sound_adapter, case6_pre_sound_adapter,
            case6_post_sound_adapter;

    StringBuffer ALLIDAGENT;
    String sound_ID_WELCOME, no_INPUT_sound_ID, wrong_INPUT_ID, sound_PRE_sound_ID, sound_POST_sound_ID, sound_ERROR_ID, sound_HOLDID = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbefore_rule);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Rule Before");
        numberID = getIntent().getStringExtra("NUMBER");
        _rule_before_Spinner = (Spinner) findViewById(R.id._rule_before_Spinner);
        add_rule_before_activity = (Button) findViewById(R.id.add_rule_before_activity);
        agent_customoze_spinner = (MultiSelectionSpinner) findViewById(R.id.case4_total_agent_spinner);
        _rule_before_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                int action_value = adapterView.getSelectedItemPosition();
                if (action_value == 0) {
                    layout1_case1.setVisibility(View.VISIBLE);
                    layout2_case2.setVisibility(View.GONE);
                    layout3_case3.setVisibility(View.GONE);
                    layout5_case5.setVisibility(View.GONE);
                    layout4_case4.setVisibility(View.GONE);
                    layout6_case6.setVisibility(View.GONE);
                }
                if (action_value == 1) {
                    layout2_case2.setVisibility(View.VISIBLE);
                    layout3_case3.setVisibility(View.GONE);
                    layout5_case5.setVisibility(View.GONE);
                    layout4_case4.setVisibility(View.GONE);
                    layout1_case1.setVisibility(View.GONE);
                    layout6_case6.setVisibility(View.GONE);
                }
                if (action_value == 2) {
                    layout3_case3.setVisibility(View.VISIBLE);
                    layout1_case1.setVisibility(View.GONE);
                    layout2_case2.setVisibility(View.GONE);
                    layout5_case5.setVisibility(View.GONE);
                    layout4_case4.setVisibility(View.GONE);
                    layout6_case6.setVisibility(View.GONE);
                }
                if (action_value == 3) {
                    layout4_case4.setVisibility(View.VISIBLE);
                    layout3_case3.setVisibility(View.GONE);
                    layout1_case1.setVisibility(View.GONE);
                    layout2_case2.setVisibility(View.GONE);
                    layout5_case5.setVisibility(View.GONE);
                    layout6_case6.setVisibility(View.GONE);
                }
                if (action_value == 4) {
                    layout5_case5.setVisibility(View.VISIBLE);
                    layout4_case4.setVisibility(View.GONE);
                    layout3_case3.setVisibility(View.GONE);
                    layout1_case1.setVisibility(View.GONE);
                    layout2_case2.setVisibility(View.GONE);
                    layout6_case6.setVisibility(View.GONE);
                }
                if (action_value == 5) {
                    layout5_case5.setVisibility(View.GONE);
                    layout4_case4.setVisibility(View.GONE);
                    layout3_case3.setVisibility(View.GONE);
                    layout1_case1.setVisibility(View.GONE);
                    layout2_case2.setVisibility(View.GONE);
                    layout6_case6.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        CodeReusability();
        initializeAllLayout();
        case2InitializeAdapter();
        add_rule_before_activity.setOnClickListener(this);
        loadSoundAPI();
        loadAgentAPI();
    }

    private void loadAgentAPI() {
        client.setTimeout(10 * 1000);
        client.post(Urls.agentAPI + userUID, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        if (response == null) return;
                        try {
                            if (response.getString("msg").equals("202")) {
                                Urls.createDialog(AddRuleBefore.this, Urls.AlertMsgs.noResultMsg);
                            } else {
                                JSONObject parentJson, childJSON = null;
                                JSONArray parentArray = null;
                                parentJson = response.getJSONObject("msg");
                                parentArray = parentJson.getJSONArray("Agent Details");
                                for (int i = 0; i < parentArray.length(); i++) {
                                    childJSON = parentArray.getJSONObject(i);
                                    String id = childJSON.getString("id");
                                    String ivr_name = childJSON.getString("agent_name");
                                    AgentIdName agent_data = new AgentIdName(id, ivr_name);
                                    agent_list_API.add(agent_data);
                                }
                                for (AgentIdName agentDATA : agent_list_API) {
                                    String agent_name = agentDATA.getAgent_name();
                                    agent_list_API_POJO.add(agent_name);
                                    // numberAllocate.setId(numberAllocate.getId());
                                }
                                agent_customoze_spinner.setItems(agent_list_API_POJO);
                                agent_customoze_spinner.setSelection(new int[]{1});
                                agent_customoze_spinner.setListener(AddRuleBefore.this);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        pDialog.dismiss();
                    }
                }
        );
    }

    private void case2InitializeAdapter() {
        case2_welcome_sound_list = new ArrayList<Sound>();
        agent_list_API = new ArrayList<AgentIdName>();
        case2_sound_name_welcome_POJO = new ArrayList();
        agent_list_API_POJO = new ArrayList();
        case2_no_input_sound_POJO = new ArrayList();

    }

    public void CodeReusability() {
        DBHandler storage = new DBHandler(AddRuleBefore.this);
        userUID = storage.getUserId();
        client = new AsyncHttpClient();
        pDialog = new ProgressDialog(AddRuleBefore.this);
        params = new RequestParams();
        actionAdapterSpinner();
    }

    public void actionAdapterSpinner() {
        _rule_before_adpater = new ArrayAdapter<String>(this, R.layout.spinner_item1, action);
        _rule_before_adpater.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        _rule_before_Spinner.setAdapter(_rule_before_adpater);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    private void insertBlackListRULE(String caller_number, String description) {
        pDialog.setMessage("Adding Rule Please wait..");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        params.put("db_action", "1");//DB ACTION AND ACTION ALWAYS BE 1
        params.put("rule_method", "before");
        params.put("call_bak", "undefined");
        params.put("numid", numberID);
        params.put("action", "1");
        params.put("caller", caller_number);
        params.put("description", description);
        params.put("moredata", "undefined");
        client.setTimeout(10 * 1000);
        client.post(Urls.add_rule_black_list + userUID + "&", params, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                pDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (response == null) {
                    System.out.println("No Response " + response);
                    return;
                }
                try {
                    if (response.getString("msg").equals("200")) {
                        pDialog.dismiss();
                        Toast.makeText(AddRuleBefore.this, "ADDED IN SERVER--- ", Toast.LENGTH_LONG).show();
                        Urls.createDialogSuccess(AddRuleBefore.this, Urls.AlertMsgs.success);


                    }
                    if (response.getString("msg").equals("202")) {
                        pDialog.dismiss();
                        Toast.makeText(AddRuleBefore.this, "Already ADDED IN SERVER--- ", Toast.LENGTH_LONG).show();
                    }
                    if (response.getString("msg").equals("404")) {
                        pDialog.dismiss();
                        Toast.makeText(AddRuleBefore.this, "Some ErrorIN SERVER--- ", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Error" + e);
                }
            }

            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                if (throwable.getCause() instanceof UnknownHostException) {
                    Toast.makeText(AddRuleBefore.this, "No internet connection...!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddRuleBefore.this, "Unable to connect, Please check your network connection...!", Toast.LENGTH_SHORT).show();
                }
                pDialog.dismiss();
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initializeLayout_5_fields() {
        case5_caller_edit_text = (EditText) findViewById(R.id.case5_caller_edit_text);
        case5_description_edit_text = (EditText) findViewById(R.id.case5_description_edit_text);
        case5_sound_spinner = (Spinner) findViewById(R.id.case5_sound_spinner);
    }

    private void initializeLayout_4_fields() {
        case4_caller_edit_text = (EditText) findViewById(R.id.case4_caller_edit_text);
        case4_description_edit_text = (EditText) findViewById(R.id.case4_description_edit_text);
        switchButton = (SwitchCompat) findViewById(R.id.switchButton);
        ;
        // case4_total_agent_spinner = (Spinner) findViewById(R.id.case4_total_agent_spinner);
        case4_call_mode_spinner = (Spinner) findViewById(R.id.case4_call_mode_spinner);
        case4_pre_sound_spinner = (Spinner) findViewById(R.id.case4_pre_sound_spinner);
        case4_error_sound_spinner = (Spinner) findViewById(R.id.case4_error_sound_spinner);
        case4_hold_sound_spinner = (Spinner) findViewById(R.id.case4_hold_sound_spinner);
        selected_agent_listView = (ListView) findViewById(R.id.selected_agent_listView);
        switchButton.setSwitchPadding(80);
        switchButton.setOnCheckedChangeListener(AddRuleBefore.this);


    }

    private void initializeLayout_3_fields() {
        case3_caller_edit_text = (EditText) findViewById(R.id.case3_caller_edit_text);
        case3_description_edit_text = (EditText) findViewById(R.id.case3_description_edit_text);
    }

    private void initializeLayout_2_fields() {
        case2_caller_edit_text = (EditText) findViewById(R.id.case2_caller_edit_text);
        case2_description_edit_text = (EditText) findViewById(R.id.case2_description_edit_text);
        case2_welcome_sound_spinner = (Spinner) findViewById(R.id.case2_welcome_sound_spinner);
        case2_no_input_sound_spinner = (Spinner) findViewById(R.id.case2_no_input_sound_spinner);
        case2_wrong_input_sound_spinner = (Spinner) findViewById(R.id.case2_wrong_input_sound_spinner);
    }

    private void initializeLayout_1_fields() {
        case1_caller_edit_text = (EditText) findViewById(R.id.case1_caller_edit_text);
        case1_description_edit_text = (EditText) findViewById(R.id.case1_description_edit_text);

    }

    private void initializeAllLayout() {
        layout1_case1 = (LinearLayout) findViewById(R.id.layout1_case1);
        layout2_case2 = (LinearLayout) findViewById(R.id.layout2_case2);
        layout3_case3 = (LinearLayout) findViewById(R.id.layout3_case3);
        layout4_case4 = (LinearLayout) findViewById(R.id.layout4_case4);
        layout5_case5 = (LinearLayout) findViewById(R.id.layout5_case5);
        layout6_case6 = (LinearLayout) findViewById(R.id.layout6_case6);
        initializeLayout_1_fields();
        initializeLayout_2_fields();
        initializeLayout_3_fields();
        initializeLayout_4_fields();
        initializeLayout_5_fields();
        initializeLayout_6_fields();
    }

    private void initializeLayout_6_fields() {
        case6_post_sound_spinner = (Spinner) findViewById(R.id.case6_post_sound_spinner);
        case6_pre_sound_spinner = (Spinner) findViewById(R.id.case6_pre_sound_spinner);
        case6_email_edit_text = (EditText) findViewById(R.id.case6_email_edit_text);
        case6_description_edit_text = (EditText) findViewById(R.id.case6_description_edit_text);
        case6_caller_edit_text = (EditText) findViewById(R.id.case6_caller_edit_text);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //loadSoundAPI();
    }

    private void loadSoundAPI() {
        client.setTimeout(10 * 1000);
        client.post(Urls.soundLoad + userUID, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        if (response == null) return;
                        try {
                            if (response.getString("msg").equals("202")) {
                                Urls.createDialog(AddRuleBefore.this, Urls.AlertMsgs.noResultMsg);
                            } else {
                                JSONObject parentJson, childJSON = null;
                                JSONArray parentArray = null;
                                parentJson = response.getJSONObject("msg");
                                parentArray = parentJson.getJSONArray("Ivr Details");
                                for (int i = 0; i < parentArray.length(); i++) {
                                    childJSON = parentArray.getJSONObject(i);
                                    String id = childJSON.getString("id");
                                    String ivr_name = childJSON.getString("ivr_name");
                                    Sound sound = new Sound(id, ivr_name);
                                    case2_welcome_sound_list.add(sound);
                                }
                                for (Sound numberAllocate : case2_welcome_sound_list) {
                                    String number = numberAllocate.getIvr_name();
                                    case2_sound_name_welcome_POJO.add(number);
                                    // numberAllocate.setId(numberAllocate.getId());
                                }

                                case2_welcome_sound_adapter = new ArrayAdapter(AddRuleBefore.this, R.layout.spinner_item, case2_sound_name_welcome_POJO);
                                case2_welcome_sound_adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                                case2_welcome_sound_spinner.setAdapter(case2_welcome_sound_adapter);

                            /*    ArrayAdapter case2_welcome_sound_adapter,case2_no_input_sound_adapter
                                        ,case2_wrong_input_sound_adapter,case4_pre_sound_adapter,case4_error_sound_adapter,
                                        case4_hold_sound_adapter,case5_sound_adapter,case6_pre_sound_adapter,case6_post_sound_adapter
                                case2_no_input_sound_adapter=case2_welcome_sound_adapter;
                                */

                                case2_no_input_sound_adapter = new ArrayAdapter(AddRuleBefore.this, R.layout.spinner_item, case2_sound_name_welcome_POJO);
                                case2_no_input_sound_adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                                case2_no_input_sound_spinner.setAdapter(case2_no_input_sound_adapter);

                                case2_wrong_input_sound_adapter = new ArrayAdapter(AddRuleBefore.this, R.layout.spinner_item, case2_sound_name_welcome_POJO);
                                case2_wrong_input_sound_adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                                case2_wrong_input_sound_spinner.setAdapter(case2_wrong_input_sound_adapter);

                                case4_pre_sound_adapter = new ArrayAdapter(AddRuleBefore.this, R.layout.spinner_item, case2_sound_name_welcome_POJO);
                                case4_pre_sound_adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                                case4_pre_sound_spinner.setAdapter(case4_pre_sound_adapter);

                                case4_error_sound_adapter = new ArrayAdapter(AddRuleBefore.this, R.layout.spinner_item, case2_sound_name_welcome_POJO);
                                case4_error_sound_adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                                case4_error_sound_spinner.setAdapter(case4_error_sound_adapter);

                                case4_hold_sound_adapter = new ArrayAdapter(AddRuleBefore.this, R.layout.spinner_item, case2_sound_name_welcome_POJO);
                                case4_hold_sound_adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                                case4_hold_sound_spinner.setAdapter(case4_hold_sound_adapter);

                                case5_sound_adapter = new ArrayAdapter(AddRuleBefore.this, R.layout.spinner_item, case2_sound_name_welcome_POJO);
                                case5_sound_adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                                case5_sound_spinner.setAdapter(case5_sound_adapter);

                                case6_pre_sound_adapter = new ArrayAdapter(AddRuleBefore.this, R.layout.spinner_item, case2_sound_name_welcome_POJO);
                                case6_pre_sound_adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                                case6_pre_sound_spinner.setAdapter(case6_pre_sound_adapter);

                                case6_post_sound_adapter = new ArrayAdapter(AddRuleBefore.this, R.layout.spinner_item, case2_sound_name_welcome_POJO);
                                case6_post_sound_adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                                case6_post_sound_spinner.setAdapter(case6_post_sound_adapter);


                                //GETTING THE ID OF THE SPINNER OF SOUND
                                GETALLIDOFSOUND();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        pDialog.dismiss();
                    }
                }
        );
    }

    private void GETALLIDOFSOUND() {
        case2_welcome_sound_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String welcome_sound_name = adapterView.getSelectedItem().toString();
                /*String no_input_sound = adapterView.getSelectedItem().toString();
                String wrong_input_sound = adapterView.getSelectedItem().toString();*/
                for (Sound sound : case2_welcome_sound_list) {
                    String sound_name = sound.getIvr_name();
                    if (sound_name.equals(welcome_sound_name)) {
                        //GET THE ID OF THE WELCOMESOUND
                        sound_ID_WELCOME = sound.getId();
                    }
                }//End of For Loop
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        case2_wrong_input_sound_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String no_input_sound = adapterView.getSelectedItem().toString();
                for (Sound sound : case2_welcome_sound_list) {
                    String sound_name = sound.getIvr_name();
                    if (sound_name.equals(no_input_sound)) {
                        //GET THE ID OF THE WELCOMESOUND
                        wrong_INPUT_ID = sound.getId();
                    }
                }//End of For Loop
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        case2_no_input_sound_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String no_input_sound = adapterView.getSelectedItem().toString();
                for (Sound sound : case2_welcome_sound_list) {
                    String sound_name = sound.getIvr_name();
                    if (sound_name.equals(no_input_sound)) {
                        //GET THE ID OF THE WELCOMESOUND
                        no_INPUT_sound_ID = sound.getId();
                    }
                }//End of For Loo
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        case6_pre_sound_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String no_input_sound = adapterView.getSelectedItem().toString();
                for (Sound sound : case2_welcome_sound_list) {
                    String sound_name = sound.getIvr_name();
                    if (sound_name.equals(no_input_sound)) {
                        //GET THE ID OF THE WELCOMESOUND
                        sound_PRE_sound_ID = sound.getId();
                    }
                }//End of For Loo
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        case6_post_sound_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String no_input_sound = adapterView.getSelectedItem().toString();
                for (Sound sound : case2_welcome_sound_list) {
                    String sound_name = sound.getIvr_name();
                    if (sound_name.equals(no_input_sound)) {
                        //GET THE ID OF THE WELCOMESOUND
                        sound_POST_sound_ID = sound.getId();
                    }
                }//End of For Loo
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        String CALL_MODE[] = {"Sequential", "Round_Robin", "Parallel_Ring"};
        ArrayAdapter agent_name_adapter = new ArrayAdapter(AddRuleBefore.this, R.layout.spinner_item, CALL_MODE);
        agent_name_adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        case4_call_mode_spinner.setAdapter(agent_name_adapter);
        case4_call_mode_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CASE4_CALLMODE = String.valueOf(adapterView.getItemIdAtPosition(i + 1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        case4_pre_sound_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String no_input_sound = adapterView.getSelectedItem().toString();
                for (Sound sound : case2_welcome_sound_list) {
                    String sound_name = sound.getIvr_name();
                    if (sound_name.equals(no_input_sound)) {
                        //GET THE ID OF THE WELCOMESOUND
                        sound_PRE_sound_ID = sound.getId();
                    }
                }//End of For Loo

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        case4_error_sound_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String no_input_sound = adapterView.getSelectedItem().toString();
                for (Sound sound : case2_welcome_sound_list) {
                    String sound_name = sound.getIvr_name();
                    if (sound_name.equals(no_input_sound)) {
                        //GET THE ID OF THE WELCOMESOUND
                        sound_ERROR_ID = sound.getId();
                    }
                }//End of For L
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        case4_hold_sound_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String no_input_sound = adapterView.getSelectedItem().toString();
                for (Sound sound : case2_welcome_sound_list) {
                    String sound_name = sound.getIvr_name();
                    if (sound_name.equals(no_input_sound)) {
                        //GET THE ID OF THE WELCOMESOUND
                        sound_HOLDID = sound.getId();
                    }
                }//End of For L
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {//Start of Switch Statement

            case R.id.add_rule_before_activity: {// Add rule here
                if (_rule_before_Spinner.getSelectedItem().toString().equals("BLACK LIST")) {
                    //Here iam Going to add the Value in the Sever of BlackList
                    String caller_number = case1_caller_edit_text.getText().toString();
                    String description = case1_description_edit_text.getText().toString();
                    if (caller_number.length() > 0 && description.length() > 0) {
                        insertBlackListRULE(caller_number, description);
                    } else {
                        Urls.createDialog(AddRuleBefore.this, Urls.AlertMsgs.allFieldsMsg);
                    }
                }
                if (_rule_before_Spinner.getSelectedItem().toString().equals("DTMF")) {
                    String case2caller = case2_caller_edit_text.getText().toString();
                    String case2desc = case2_description_edit_text.getText().toString();


                    if (case2caller.length() > 0 && case2desc.length() > 0) {
                        insertBlackListDTMF(case2caller, case2desc);
                    } else {
                        Urls.createDialog(AddRuleBefore.this, Urls.AlertMsgs.allFieldsMsg);
                    }
                    //http://82.145.38.202/sachin/pannel/api/add_rules.php?uid=27&db_action=2
                    // &rule_method=before&call_bak=undefined&numid=3&action=2&caller=91&description=hit
                    // &welcome_sound=39&noinput_sound=39&wrong_input=39&moredata=undefined
                }
                if (_rule_before_Spinner.getSelectedItem().toString().equals("MISSED CALL")) {
                    String case3_missed_call = case3_caller_edit_text.getText().toString().trim();
                    String case3_desc = case3_description_edit_text.getText().toString().trim();
                    if (case3_missed_call.length() > 0 && case3_desc.length() > 0) {
                        insertMISSEDCALL(case3_missed_call, case3_desc);
                    } else {
                        Urls.createDialog(AddRuleBefore.this, Urls.AlertMsgs.allFieldsMsg);
                    }
                    Toast.makeText(AddRuleBefore.this, " Operation  is " + "  ID IS  " + userUID + "" + _rule_before_Spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                }
                if (_rule_before_Spinner.getSelectedItem().toString().equals("PHONE")) {
                    // Iam the Greatest i said even Before i knew i was;;;;;;;;;;
                    String name_callerEditText = case4_caller_edit_text.getText().toString().trim();
                    String name_callerDescription = case4_description_edit_text.getText().toString().trim();
                    if (name_callerEditText.length() > 0 && name_callerDescription.length() > 0)
                        insertPHONE(name_callerEditText, name_callerDescription);
                    else Urls.createDialog(AddRuleBefore.this, Urls.AlertMsgs.allFieldsMsg);


                }
                if (_rule_before_Spinner.getSelectedItem().toString().equals("SOUND")) {
                    String caller_name = case5_caller_edit_text.getText().toString().trim();
                    String case_description = case5_description_edit_text.getText().toString().trim();
                    if (caller_name.length() > 0 && case_description.length() > 0) {
                        insertSOUND(caller_name, case_description);
                    } else {
                        Urls.createDialog(AddRuleBefore.this, Urls.AlertMsgs.allFieldsMsg);
                    }
                }
                if (_rule_before_Spinner.getSelectedItem().toString().equals("VOICE MAIL")) {
                    String caller_name = case6_caller_edit_text.getText().toString().trim();
                    String case_description = case6_description_edit_text.getText().toString().trim();
                    String case_email = case6_email_edit_text.getText().toString().trim();

                    if (caller_name.length() > 0 && case_email.length() > 0 && case_email.length() > 0) {
                        insertVOICEMAIL(caller_name, case_description, case_email);
                    } else {
                        Urls.createDialog(AddRuleBefore.this, Urls.AlertMsgs.allFieldsMsg);
                    }


                }
            }//End of Button here
        }//End of Switcj
    }

    private void insertPHONE(String name_callerEditText, String name_callerDescription) {
        pDialog.setMessage("Adding Rule Please wait..");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        params.put("db_action", "4");//DB ACTION AND ACTION ALWAYS BE 6
        params.put("rule_method", "before");
        params.put("call_bak", CHECKED);
        params.put("numid", numberID);
        params.put("action", "4");
        params.put("caller", name_callerEditText);
        params.put("description", name_callerDescription);
        params.put("agent", "undefined");
        params.put("call_mode", CASE4_CALLMODE);
        params.put("pre_sound", sound_PRE_sound_ID);
        params.put("error_sound", sound_ERROR_ID);
        params.put("hold_sound", sound_ERROR_ID);
        params.put("moredata", ALLIDAGENT);
        client.setTimeout(10 * 1000);
        fellTheHEAT();
    }

    private void insertVOICEMAIL(String caller_name, String case_description, String case_email) {
        pDialog.setMessage("Adding Rule Please wait..");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        params.put("db_action", "6");//DB ACTION AND ACTION ALWAYS BE 6
        params.put("rule_method", "before");
        params.put("call_bak", "undefined");
        params.put("numid", numberID);
        params.put("action", "6");
        params.put("caller", caller_name);
        params.put("description", case_description);
        params.put("email", case_email);
        params.put("pre_sound", sound_PRE_sound_ID);
        params.put("post_sound", sound_POST_sound_ID);
        params.put("moredata", "undefined");
        client.setTimeout(10 * 1000);
        //http://82.145.38.202/sachin/pannel/api/add_rules.php?uid=27&db_action=6&rule_method=before
        // &call_bak=undefined&numid=3
        // &action=6&caller=91&description=hit&email=sachinagrawal092@gmail.com
        // &pre_sound=39&post_sound=39&moredata=undefined
        fellTheHEAT();
    }

    private void insertSOUND(String caller_name, String case_description) {
        pDialog.setMessage("Adding Rule Please wait..");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        params.put("db_action", "5");//DB ACTION AND ACTION ALWAYS BE 2
        params.put("rule_method", "before");
        params.put("call_bak", "undefined");
        params.put("numid", numberID);
        params.put("action", "5");
        params.put("caller", caller_name);
        params.put("description", case_description);
        params.put("sound", sound_ID_WELCOME);
        params.put("moredata", "undefined");
        client.setTimeout(10 * 1000);
        //http://82.145.38.202/sachin/pannel/api/add_rules.php?uid=27&db_action=5&rule_method=before
        // &call_bak=undefined&numid=3&action=5&caller=91&description=hit&sound=39&moredata=undefined
        fellTheHEAT();
    }

    private void insertMISSEDCALL(String case3_missed_call, String case3_desc) {
        pDialog.setMessage("Adding Rule Please wait..");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        params.put("db_action", "3");//DB ACTION AND ACTION ALWAYS BE 2
        params.put("rule_method", "before");
        params.put("call_bak", "undefined");
        params.put("numid", numberID);
        params.put("action", "3");
        params.put("caller", case3_missed_call);
        params.put("description", case3_desc);
        params.put("moredata", "undefined");
        client.setTimeout(10 * 1000);
        fellTheHEAT();
    }

    private void fellTheHEAT() {
        client.post(Urls.add_rule_MISSEDCALL + userUID, params, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                pDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (response == null) return;
                {
                    if (response == null) {
                        System.out.println("No Response " + response);
                        pDialog.dismiss();
                        return;
                    }
                    try {
                        if (response.getString("msg").equals("200")) {
                            pDialog.dismiss();
                            Urls.createDialogSuccess(AddRuleBefore.this, Urls.AlertMsgs.success);
                        }
                        if (response.getString("msg").equals("202")) {
                            pDialog.dismiss();
                            Toast.makeText(AddRuleBefore.this, "Already ADDED IN SERVER--- ", Toast.LENGTH_LONG).show();
                        }
                        if (response.getString("msg").equals("404")) {
                            pDialog.dismiss();
                            Toast.makeText(AddRuleBefore.this, "Some ErrorIN SERVER--- ", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println("Error" + e);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

    }

    private void insertBlackListDTMF(String case2caller, String case2desc) {
        pDialog.setMessage("Adding Rule Please wait..");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        params.put("db_action", "2");//DB ACTION AND ACTION ALWAYS BE 2
        params.put("rule_method", "before");
        params.put("call_bak", "undefined");
        params.put("numid", numberID);
        params.put("action", "2");
        params.put("caller", case2caller);
        params.put("description", case2desc);
        params.put("welcome_sound", sound_ID_WELCOME);
        params.put("noinput_sound", no_INPUT_sound_ID);
        params.put("wrong_input", wrong_INPUT_ID);
        params.put("moredata", "undefined");
        client.setTimeout(10 * 1000);
        fellTheHEAT();

    }


    @Override
    public void selectedIndices(List<Integer> indices) {

    }

    /*
    http://82.145.38.202/sachin/pannel/api/add_rules.php?uid=27&db_action=4&rule_method=before
    &call_bak=1&numid=3&action=4&caller=91&description=hi&agent=%27%27&call_mode=1&pre_sound=39&
    error_sound=39&hold_sound=39&moredata=73,74
     */
    @Override
    public void selectedStrings(List<String> strings) {
        List<String> answer = new ArrayList<String>();
        answer = strings;
        StringBuffer stringBuffer = new StringBuffer();
        String[] names = new String[answer.size()];
        for (int i = 0; i < answer.size(); i++) {
            stringBuffer.append(answer.get(i));
            names[i] = answer.get(i);
        }
        ALLIDAGENT = new StringBuffer();
        for (String name : names) {
            for (AgentIdName nameFROm : agent_list_API) {
                if (nameFROm.getAgent_name().equals(name)) {
                    ALLIDAGENT.append(nameFROm.getId());
                    ALLIDAGENT.append(",");
                }
            }
        }
        int ALLID1 = ALLIDAGENT.length();
        ALLIDAGENT.deleteCharAt(ALLID1 - 1);
        Toast.makeText(AddRuleBefore.this, "ID IS " + ALLIDAGENT, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.switchButton:
                CHECKED = 1;
                Toast.makeText(AddRuleBefore.this, "VAlue i8e " + CHECKED, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        case2_welcome_sound_list.clear();
        agent_list_API.clear();
        case2_sound_name_welcome_POJO.clear();
        agent_list_API_POJO.clear();
        case2_no_input_sound_POJO.clear();

    }
}
