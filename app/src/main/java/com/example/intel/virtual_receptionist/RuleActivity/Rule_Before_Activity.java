package com.example.intel.virtual_receptionist.RuleActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
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

import com.example.intel.virtual_receptionist.CustomizeSpinnerOFAgent.MultiSelectionSpinner;
import com.example.intel.virtual_receptionist.DBManager.DBHandler;
import com.example.intel.virtual_receptionist.Model.AgentIdName;
import com.example.intel.virtual_receptionist.Model.Sound;
import com.example.intel.virtual_receptionist.R;
import com.example.intel.virtual_receptionist.URL.Urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by intel on 3/1/2017.
 */
public class Rule_Before_Activity extends AppCompatActivity implements View.OnClickListener, MultiSelectionSpinner.OnMultipleItemsSelectedListener, CompoundButton.OnCheckedChangeListener {

    String[] action = {"BLACK LIST", "DTMF", "MISSED CALL", "PHONE", "SOUND", "VOICE MAIL"};
    MultiSelectionSpinner agent_customoze_spinner;
    LinearLayout layout1_case1, layout2_case2, layout3_case3, layout4_case4, layout5_case5, layout6_case6;
    String CASE4_CALLMODE, sound_PRE_sound_ID, sound_ERROR_ID, sound_HOLDID, wrong_INPUT_ID, sound_ID_WELCOME = null;
    public static ArrayList<Sound> case2_welcome_sound_list = null;
    public static ArrayList<AgentIdName> agent_list_API = null;
    public static ArrayList case2_sound_name_welcome_POJO = null;
    public static ArrayList agent_list_API_POJO = null;
    public static ArrayList case2_no_input_sound_POJO = null;
    StringBuffer ALLIDAGENT;
    EditText case1_caller_edit_text, case1_description_edit_text, case2_caller_edit_text, case4_caller_edit_text, case4_description_edit_text,
            case6_caller_edit_text, case6_description_edit_text, case6_email_edit_text, case2_description_edit_text, case3_description_edit_text, case3_caller_edit_text, case5_description_edit_text, case5_caller_edit_text;
    Spinner case2_welcome_sound_spinner, case6_pre_sound_spinner, case6_post_sound_spinner, case5_sound_spinner, case2_no_input_sound_spinner, case2_wrong_input_sound_spinner, case4_pre_sound_spinner, case4_hold_sound_spinner, case4_error_sound_spinner, case4_call_mode_spinner, case4_total_agent_spinner;
    SwitchCompat switchButton;
    ListView selected_agent_listView;
    ArrayAdapter _rule_before_adpater, case2_welcome_sound_adapter, case2_no_input_sound_adapter, case2_wrong_input_sound_adapter, case4_pre_sound_adapter, case4_error_sound_adapter,
            case4_hold_sound_adapter, case5_sound_adapter, case6_pre_sound_adapter,
            case6_post_sound_adapter;
    String no_INPUT_sound_ID, sound_POST_sound_ID, sound_SOUNDID = null;
    int CHECKED = 0;
    String userUID = null;
    AsyncHttpClient client;
    RequestParams params;
    Spinner _rule_before_Spinner;
    static ProgressDialog pDialog = null;
    Button add_rule_before_activity;

    String MNumberSelectedID, MNumberUniqueID, DTMFRULE, previous_VOICEMAIL_PRE_SOUND, previous_VOICEMAIL_POST_SOUND, VOICEMAILRULE, IVRD, PHONERULE, previous_ACTION, CALLER, DESCRIPTION = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule_before);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("View Added Rules ");


        previous_ACTION = getIntent().getStringExtra("ACTION");
        _rule_before_Spinner = (Spinner) findViewById(R.id._rule_before_Spinner);
        actionAdapterSpinner();

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
        getPreviousDaTA();
        try {
            loadSoundAPI();
            loadAgentAPI();
        } catch (Exception e) {
            Toast.makeText(Rule_Before_Activity.this, "Error in loading The Data", Toast.LENGTH_SHORT).show();
        }
    }

    String previous_DTMF_WELCOME_SOUNDID, previous_DTMF_NO_INPUT_SOUNDID_, Sound_Id_Previous, previous_DTMF_WRONG_INPUT_SOUNDID;

    private void getPreviousDaTA() {

        CALLER = getIntent().getStringExtra("CALLER");
        DESCRIPTION = getIntent().getStringExtra("DESCRIPTION");
        MNumberSelectedID = getIntent().getStringExtra("NID");
        MNumberUniqueID = getIntent().getStringExtra("ID");
        DTMFRULE = getIntent().getStringExtra("DTMFRULE");
        if (DTMFRULE.length() > 0) {
            String newDTMFUKE[] = DTMFRULE.split(",");
            previous_DTMF_WELCOME_SOUNDID = newDTMFUKE[0];
            previous_DTMF_NO_INPUT_SOUNDID_ = newDTMFUKE[1];
            previous_DTMF_WRONG_INPUT_SOUNDID = newDTMFUKE[2];
        }
        IVRD = getIntent().getStringExtra("IVRD");
        if (IVRD.length() > 0) {
            Sound_Id_Previous = IVRD;
        }
        PHONERULE = getIntent().getStringExtra("PHONERULE");
        if (PHONERULE.length() > 0) {
            String newPHONERULE[] = PHONERULE.split(",");
            String previous_PHONE_FALLBACKRULEID = newPHONERULE[0];
            String previous_PHONE_CALLMODEID = newPHONERULE[1];
            String previous_PHONE_PRE_SOUNDID = newPHONERULE[2];
            String previous_PHONE_ERROR_SOUNDID = newPHONERULE[3];
            String previous_PHONE_HOLD_SOUNDID = newPHONERULE[4];
            String previous_PHONE_ALL_AGENTSID = newPHONERULE[5];
            String newAGENT[] = previous_PHONE_ALL_AGENTSID.split("#");
            List AgentIds = new ArrayList<String>();
            for (int i = 0; i < newAGENT.length; i++) {
                AgentIds.add(i, newAGENT[i]);
            }
/*
            for (Object a : AgentIds) {
                System.out.println("IS's are " + a);

                for (AgentIdName name : agent_list_API) {
                    String id = name.getId();
                    if (id.equals(a)) {
                        String Name = name.getAgent_name();
                        System.out.println("ALl NAME " + Name);

                    }
                }

            }
*/

        }


        VOICEMAILRULE = getIntent().getStringExtra("VOICEEMAILRULE");
        if (VOICEMAILRULE.length() > 0) {
            String newVOICERULE[] = VOICEMAILRULE.split(",");
            previous_VOICEMAIL_PRE_SOUND = newVOICERULE[0];
            previous_VOICEMAIL_POST_SOUND = newVOICERULE[1];
            String previous_VOICEMAIL_EMAIl = newVOICERULE[2];
            case6_email_edit_text.setText(previous_VOICEMAIL_EMAIl);
        }


        //Here what iam trying to do is to Enter the Value in the
        insertDatainEdittext();
    }

    private void insertDatainEdittext() {
        case1_caller_edit_text.setText(CALLER);
        case1_description_edit_text.setText(DESCRIPTION);
        case2_caller_edit_text.setText(CALLER);
        case2_description_edit_text.setText(DESCRIPTION);
        case3_caller_edit_text.setText(CALLER);
        case3_description_edit_text.setText(DESCRIPTION);
        case4_caller_edit_text.setText(CALLER);
        case4_description_edit_text.setText(DESCRIPTION);
        case5_caller_edit_text.setText(CALLER);
        case5_description_edit_text.setText(DESCRIPTION);
        case6_caller_edit_text.setText(CALLER);
        case6_description_edit_text.setText(DESCRIPTION);
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
                                Urls.createDialog(Rule_Before_Activity.this, Urls.AlertMsgs.noResultMsg);
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
                                agent_customoze_spinner.setSelection(new int[]{});
                              /*  agent_customoze_spinner.setSelection(new int[]{1});*/
                                agent_customoze_spinner.setListener(Rule_Before_Activity.this);
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

    public void loadSoundAPI() {
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
                                Urls.createDialog(Rule_Before_Activity.this, Urls.AlertMsgs.noResultMsg);
                            } else {
                                String welcomesound = null;
                                String no_input_sound = null;
                                String wrong_input_sound = null;
                                String Prevoius_case6_pre_soundID = null;
                                String Prevoius_case6_post_soundID = null;

                                boolean welcomeBOOLEAN, no_input_BOOLEAN, wrong_input_BOOLEAN = false;
                                boolean case5_welcme_sound = false;
                                boolean previous_pre_case6 = false;
                                boolean previous_post_case6 = false;
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
                                    welcomeBOOLEAN = numberAllocate.getId().equals(previous_DTMF_WELCOME_SOUNDID);
                                    no_input_BOOLEAN = numberAllocate.getId().equals(previous_DTMF_NO_INPUT_SOUNDID_);
                                    wrong_input_BOOLEAN = numberAllocate.getId().equals(previous_DTMF_WRONG_INPUT_SOUNDID);
                                    case5_welcme_sound = numberAllocate.getId().equals(Sound_Id_Previous);
                                    previous_pre_case6 = numberAllocate.getId().equals(previous_VOICEMAIL_PRE_SOUND);
                                    previous_post_case6 = numberAllocate.getId().equals(previous_VOICEMAIL_POST_SOUND);

                                    if (welcomeBOOLEAN) {
                                        welcomesound = numberAllocate.getIvr_name();
                                    }
                                    if (no_input_BOOLEAN) {
                                        no_input_sound = numberAllocate.getIvr_name();
                                    }
                                    if (wrong_input_BOOLEAN) {
                                        wrong_input_sound = numberAllocate.getIvr_name();
                                    }
                                    if (case5_welcme_sound) {
                                        welcomesound = numberAllocate.getIvr_name();
                                    }

                                }

                                case2_welcome_sound_adapter = new ArrayAdapter(Rule_Before_Activity.this, R.layout.spinner_item, case2_sound_name_welcome_POJO);
                                case2_welcome_sound_adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                                int position = case2_welcome_sound_adapter.getPosition(welcomesound);
                                case2_welcome_sound_spinner.setAdapter(case2_welcome_sound_adapter);
                                case2_welcome_sound_spinner.setSelection(position);


                            /*    ArrayAdapter case2_welcome_sound_adapter,case2_no_input_sound_adapter
                                        ,case2_wrong_input_sound_adapter,case4_pre_sound_adapter,case4_error_sound_adapter,
                                        case4_hold_sound_adapter,case5_sound_adapter,case6_pre_sound_adapter,case6_post_sound_adapter
                                case2_no_input_sound_adapter=case2_welcome_sound_adapter;
                                */

                                case2_no_input_sound_adapter = new ArrayAdapter(Rule_Before_Activity.this, R.layout.spinner_item, case2_sound_name_welcome_POJO);
                                case2_no_input_sound_adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                                int postion2 = case2_no_input_sound_adapter.getPosition(no_input_sound);
                                case2_no_input_sound_spinner.setAdapter(case2_no_input_sound_adapter);
                                case2_no_input_sound_spinner.setSelection(postion2);

                                case2_wrong_input_sound_adapter = new ArrayAdapter(Rule_Before_Activity.this, R.layout.spinner_item, case2_sound_name_welcome_POJO);
                                case2_wrong_input_sound_adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                                int position3 = case2_wrong_input_sound_adapter.getPosition(wrong_input_sound);
                                case2_wrong_input_sound_spinner.setAdapter(case2_wrong_input_sound_adapter);
                                case2_wrong_input_sound_spinner.setSelection(position3);


                                case4_pre_sound_adapter = new ArrayAdapter(Rule_Before_Activity.this, R.layout.spinner_item, case2_sound_name_welcome_POJO);
                                case4_pre_sound_adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                                case4_pre_sound_spinner.setAdapter(case4_pre_sound_adapter);

                                case4_error_sound_adapter = new ArrayAdapter(Rule_Before_Activity.this, R.layout.spinner_item, case2_sound_name_welcome_POJO);
                                case4_error_sound_adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                                case4_error_sound_spinner.setAdapter(case4_error_sound_adapter);

                                case4_hold_sound_adapter = new ArrayAdapter(Rule_Before_Activity.this, R.layout.spinner_item, case2_sound_name_welcome_POJO);
                                case4_hold_sound_adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                                case4_hold_sound_spinner.setAdapter(case4_hold_sound_adapter);

                                case5_sound_adapter = new ArrayAdapter(Rule_Before_Activity.this, R.layout.spinner_item, case2_sound_name_welcome_POJO);
                                case5_sound_adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                                int position5 = case2_welcome_sound_adapter.getPosition(welcomesound);
                                case5_sound_spinner.setAdapter(case5_sound_adapter);
                                case5_sound_spinner.setSelection(position5);

                                case6_pre_sound_adapter = new ArrayAdapter(Rule_Before_Activity.this, R.layout.spinner_item, case2_sound_name_welcome_POJO);
                                case6_pre_sound_adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                                case6_pre_sound_spinner.setAdapter(case6_pre_sound_adapter);


                                case6_post_sound_adapter = new ArrayAdapter(Rule_Before_Activity.this, R.layout.spinner_item, case2_sound_name_welcome_POJO);
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
        ArrayAdapter agent_name_adapter = new ArrayAdapter(Rule_Before_Activity.this, R.layout.spinner_item, CALL_MODE);
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
        case5_sound_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String no_input_sound = adapterView.getSelectedItem().toString();
                for (Sound sound : case2_welcome_sound_list) {
                    String sound_name = sound.getIvr_name();
                    if (sound_name.equals(no_input_sound)) {
                        //GET THE ID OF THE WELCOMESOUND
                        sound_SOUNDID = sound.getId();
                    }
                }//End of For L
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public void CodeReusability() {
        DBHandler storage = new DBHandler(Rule_Before_Activity.this);
        userUID = storage.getUserId();
        client = new AsyncHttpClient();
        pDialog = new ProgressDialog(Rule_Before_Activity.this);
        params = new RequestParams();

        add_rule_before_activity = (Button) findViewById(R.id.add_rule_before_activity);
        add_rule_before_activity.setText("Update Rule");
        add_rule_before_activity.setOnClickListener(this);
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

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onResume() {
        super.onResume();
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

    private void initializeLayout_1_fields() {
        case1_caller_edit_text = (EditText) findViewById(R.id.case1_caller_edit_text);
        case1_description_edit_text = (EditText) findViewById(R.id.case1_description_edit_text);

    }

    private void initializeLayout_2_fields() {
        case2_caller_edit_text = (EditText) findViewById(R.id.case2_caller_edit_text);
        case2_description_edit_text = (EditText) findViewById(R.id.case2_description_edit_text);
        case2_welcome_sound_spinner = (Spinner) findViewById(R.id.case2_welcome_sound_spinner);
        case2_no_input_sound_spinner = (Spinner) findViewById(R.id.case2_no_input_sound_spinner);
        case2_wrong_input_sound_spinner = (Spinner) findViewById(R.id.case2_wrong_input_sound_spinner);
    }

    private void initializeLayout_3_fields() {
        case3_caller_edit_text = (EditText) findViewById(R.id.case3_caller_edit_text);
        case3_description_edit_text = (EditText) findViewById(R.id.case3_description_edit_text);
    }


    public void actionAdapterSpinner() {
        _rule_before_adpater = new ArrayAdapter<String>(this, R.layout.spinner_item1, action);
        _rule_before_adpater.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        _rule_before_Spinner.setAdapter(_rule_before_adpater);
        _rule_before_Spinner.setSelection(Integer.parseInt(previous_ACTION) - 1);

    }


    private void initializeLayout_4_fields() {
        case4_caller_edit_text = (EditText) findViewById(R.id.case4_caller_edit_text);
        case4_description_edit_text = (EditText) findViewById(R.id.case4_description_edit_text);
        switchButton = (SwitchCompat) findViewById(R.id.switchButton);
        ;
        agent_customoze_spinner = (MultiSelectionSpinner) findViewById(R.id.case4_total_agent_spinner);
        case4_call_mode_spinner = (Spinner) findViewById(R.id.case4_call_mode_spinner);
        case4_pre_sound_spinner = (Spinner) findViewById(R.id.case4_pre_sound_spinner);
        case4_error_sound_spinner = (Spinner) findViewById(R.id.case4_error_sound_spinner);
        case4_hold_sound_spinner = (Spinner) findViewById(R.id.case4_hold_sound_spinner);
        selected_agent_listView = (ListView) findViewById(R.id.selected_agent_listView);
        switchButton.setSwitchPadding(80);
        switchButton.setOnCheckedChangeListener(Rule_Before_Activity.this);
    }

    private void initializeLayout_5_fields() {
        case5_caller_edit_text = (EditText) findViewById(R.id.case5_caller_edit_text);
        case5_description_edit_text = (EditText) findViewById(R.id.case5_description_edit_text);
        case5_sound_spinner = (Spinner) findViewById(R.id.case5_sound_spinner);
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

    }


    @Override
    public void onClick(View view) {
        //Get the Id's for the Button Click or Handdle the Button Intent From here
        int id = view.getId();
        switch (id) {//Start of thr Switch methods
            case R.id.add_rule_before_activity:
                System.out.println(" ID ID" + id);
                System.out.println("SPINNER IREn ios " + _rule_before_Spinner.getSelectedItem().toString());
                if (_rule_before_Spinner.getSelectedItem().toString().equals("BLACK LIST")) {

                    String case1_editCaller = case1_caller_edit_text.getText().toString().trim();
                    String case1_editDescription = case1_description_edit_text.getText().toString().trim();
                    if (case1_editCaller.length() > 0 && case1_editDescription.length() > 0) {
                        updateBLACKLIST(case1_editCaller, case1_editDescription);
                    } else {
                        Urls.createDialog(Rule_Before_Activity.this, Urls.AlertMsgs.allFieldsMsg);
                    }
                }
                if (_rule_before_Spinner.getSelectedItem().toString().equals("DTMF")) {

                    String case2caller = case2_caller_edit_text.getText().toString();
                    String case2desc = case2_description_edit_text.getText().toString();
                    if (case2caller.length() > 0 && case2desc.length() > 0) {
                        updateDTMF(case2caller, case2desc);
                    } else {
                        Urls.createDialog(Rule_Before_Activity.this, Urls.AlertMsgs.allFieldsMsg);
                    }
                }
                if (_rule_before_Spinner.getSelectedItem().toString().equals("MISSED CALL")) {
                    String case3_missed_call = case3_caller_edit_text.getText().toString().trim();
                    String case3_desc = case3_description_edit_text.getText().toString().trim();
                    if (case3_missed_call.length() > 0 && case3_desc.length() > 0) {
                        updateMISSSEDCALL(case3_missed_call, case3_desc);
                    } else {
                        Urls.createDialog(Rule_Before_Activity.this, Urls.AlertMsgs.allFieldsMsg);
                    }
                }
                if (_rule_before_Spinner.getSelectedItem().toString().equals("PHONE")) {
                    // Iam the Greatest i said even Before i knew i was;;;;;;;;;;
                    String name_callerEditText = case4_caller_edit_text.getText().toString().trim();
                    String name_callerDescription = case4_description_edit_text.getText().toString().trim();
                    if (name_callerEditText.length() > 0 && name_callerDescription.length() > 0)
                        updatePHONE(name_callerEditText, name_callerDescription);
                    else {
                        Urls.createDialog(Rule_Before_Activity.this, Urls.AlertMsgs.allFieldsMsg);
                    }
                }
                if (_rule_before_Spinner.getSelectedItem().toString().equalsIgnoreCase("SOUND")) {
                    String caller_name = case5_caller_edit_text.getText().toString().trim();
                    String case_description = case5_description_edit_text.getText().toString().trim();
                    if (caller_name.length() > 0 && case_description.length() > 0) {
                        updateSOUND(caller_name, case_description);
                    } else {
                        Urls.createDialog(Rule_Before_Activity.this, Urls.AlertMsgs.allFieldsMsg);
                    }
                }
                if (_rule_before_Spinner.getSelectedItem().toString().equals("VOICE MAIL")) {
                    String caller_name = case6_caller_edit_text.getText().toString().trim();
                    String case_description = case6_description_edit_text.getText().toString().trim();
                    String case_email = case6_email_edit_text.getText().toString().trim();

                    if (caller_name.length() > 0 && case_email.length() > 0 && case_email.length() > 0) {
                        updateVOICEMAIL(caller_name, case_description, case_email);
                    } else {
                        Urls.createDialog(Rule_Before_Activity.this, Urls.AlertMsgs.allFieldsMsg);
                    }
                }


        }//End of the Switch methods


    }//End of theOnclick methods

    private void updateVOICEMAIL(String caller_name, String case_description, String case_email) {
        pDialog.setMessage("Adding Rule Please wait..");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        params.put("process", "before");
        params.put("call_bak", "undefined");
        params.put("action", "6");
        params.put("caller", caller_name);
        params.put("description", case_description);
        params.put("email", case_email);

        params.put("pre_sound", sound_PRE_sound_ID);
        params.put("post_sound", sound_POST_sound_ID);
        params.put("moredata", "undefined");
        params.put("id", MNumberUniqueID);
        client.setTimeout(10 * 1000);
        fellTheHEAT();


    }

    private void updateSOUND(String caller_name, String case_description) {
        pDialog.setMessage("Adding Rule Please wait..");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        params.put("process", "before");
        params.put("call_bak", "undefined");
        params.put("action", "5");
        params.put("caller", caller_name);
        params.put("description", case_description);
        params.put("sound", sound_SOUNDID);
        params.put("moredata", "undefined");
        params.put("id", MNumberUniqueID);
        client.setTimeout(10 * 1000);

        fellTheHEAT();

    }

    private void updatePHONE(String name_callerEditText, String name_callerDescription) {


//       ;
        pDialog.setMessage("Adding Rule Please wait..");
        pDialog.setCanceledOnTouchOutside(false);

        if (ALLIDAGENT == null) {
            Urls.createDialog(Rule_Before_Activity.this, Urls.AlertMsgs.SELECTAGENTS);

        } else {
            String IDS = ALLIDAGENT.toString();
            pDialog.show();
            params.put("process", "before");
            params.put("call_bak", CHECKED);
            params.put("action", "4");
            params.put("caller", name_callerEditText);
            params.put("description", name_callerDescription);
            params.put("agent", "undefined");
            params.put("call_mode", case4_call_mode_spinner.getSelectedItemPosition() + 1);
            params.put("pre_sound", sound_PRE_sound_ID);
            params.put("error_sound", sound_ERROR_ID);
            params.put("hold_sound", sound_HOLDID);
            params.put("moredata", IDS);
            params.put("id", MNumberUniqueID);
            client.setTimeout(10 * 1000);
            fellTheHEAT();

        }


    }

    private void updateMISSSEDCALL(String case3_missed_call, String case3_desc) {
        pDialog.setMessage("Adding Rule Please wait..");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        params.put("process", "before");
        params.put("call_bak", "undefined");
        params.put("action", "3");
        params.put("caller", case3_missed_call);
        params.put("description", case3_desc);
        params.put("moredata", "undefined");
        params.put("id", MNumberUniqueID);
        client.setTimeout(10 * 1000);
        fellTheHEAT();
    }

    private void updateDTMF(String case2caller, String case2desc) {
        pDialog.setMessage("Adding Rule Please wait..");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        params.put("process", "before");
        params.put("call_bak", "undefined");
        params.put("action", "2");
        params.put("caller", case2caller);
        params.put("description", case2desc);
        params.put("welcome_sound", sound_ID_WELCOME);
        params.put("noinput_sound", no_INPUT_sound_ID);
        params.put("wrong_input", wrong_INPUT_ID);
        params.put("moredata", "undefined");
        params.put("id", MNumberUniqueID);
        client.setTimeout(10 * 1000);
        fellTheHEAT();

    }

    private void updateBLACKLIST(String case1_editCaller, String case1_editDescription) {
        pDialog.setMessage("Adding Rule Please wait..");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        params.put("process", "before");
        params.put("call_bak", "undefined");
        params.put("action", "1");


        params.put("caller", case1_editCaller);
        params.put("description", case1_editDescription);
        params.put("moredata", "undefined");
        params.put("id", MNumberUniqueID);
        client.setTimeout(10 * 1000);
        fellTheHEAT();
    }

    private void fellTheHEAT() {
        client.post(Urls.ruleEditAPTBEFORE + userUID, params, new JsonHttpResponseHandler() {
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
                            Urls.createDialogSuccess(Rule_Before_Activity.this, Urls.AlertMsgs.success);
                        }
                        if (response.getString("msg").equals("202")) {
                            pDialog.dismiss();
                            Toast.makeText(Rule_Before_Activity.this, "Already ADDED IN SERVER--- ", Toast.LENGTH_LONG).show();
                        }
                        if (response.getString("msg").equals("404")) {
                            pDialog.dismiss();
                            Toast.makeText(Rule_Before_Activity.this, "Some ErrorIN SERVER--- ", Toast.LENGTH_LONG).show();
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


    // This method will involed when user perform some operation on the MenuOption
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.switchButton:
                CHECKED = 1;
                Toast.makeText(Rule_Before_Activity.this, "VAlue i8e " + CHECKED, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void selectedIndices(List<Integer> indices) {

    }

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
        if (ALLID1 != 0)
            ALLIDAGENT.deleteCharAt(ALLID1 - 1);
        Toast.makeText(Rule_Before_Activity.this, "ID IS " + ALLIDAGENT, Toast.LENGTH_SHORT).show();


    }

}
