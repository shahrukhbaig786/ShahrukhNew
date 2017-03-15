package com.example.intel.virtual_receptionist;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.intel.virtual_receptionist.ApplicationController.ApplicationController;
import com.example.intel.virtual_receptionist.DBManager.DBConstants;
import com.example.intel.virtual_receptionist.DBManager.DBHandler;
import com.example.intel.virtual_receptionist.DBManager.MainStorage;
import com.example.intel.virtual_receptionist.Model.ModelCountry;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class UpdateOrganizationFragment extends Fragment implements View.OnClickListener, Spinner.OnItemSelectedListener {

    private static final String TAG = "Shahrukh";
    TextView imageButton, imageButton1;
    LinearLayout comanydetails, comanydetails1;
    EditText name_et, designation_et, email_et, mobile_et, company_name_et, pan_number_et,
            website_et, address_line1_et, state_et, address_line2_et, city_et, zip_et;
    String name_string, deesignation_string, email_string, mobile_string, company_name_string, pan_number_string, state_string,
            website_string, address_line1_string, address_line2_string, city_string, zip_string;
    AppCompatAutoCompleteTextView countries;
    private Button submit;
    ArrayList<ModelCountry> modelCountries;
    private JSONArray jsonArray1, jsonArray2, jsonArray3;
    private String Company_id, Industry_id, Country_id;
    Spinner comapny_type_spinner, industry_spinner, staff_in_numbers, revenue_in_lacs;
    ArrayAdapter<String> dataAdapter1, dataAdapter2, dataAdapter3;
    ArrayAdapter<CharSequence> staffadapter, revenueadapter;
    ModelCountry model;
    DashBoard dashBoard;
    private static ArrayList<String> company_type_arrayList;
    private ProgressDialog pDialog;
    private static ArrayList<String> industry_type_arrayList;
    private static ArrayList<String> country_type_arrayList;
    List<String> ProfileData;
    LinearLayout ComapnyLinearLayout, IndustryLinearLayout, staffLayout, revenueLayout, countryLayout;
    String staff, revenue = "";
    boolean getProfileDatainServer;
    int selectedItem1, selectedItem2, selectedItem3 = -1;
    boolean isAAvalilable;
    public static final String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modelCountries = new ArrayList<ModelCountry>();
        //Initializing the ArrayList Here
        //Initializing the ArrayList Here
        company_type_arrayList = new ArrayList<String>();
        industry_type_arrayList = new ArrayList<String>();//Initializing the ArrayList Here
        country_type_arrayList = new ArrayList<String>();
        ProfileData = new ArrayList<String>();
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myVIew = inflater.inflate(R.layout.tab_update_organization_fragment, container, false);


        // Add the Items in the Spinner of Staff Programmatically


        return myVIew;
    }

    @Override
    public void onViewCreated(View myVIew, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(myVIew, savedInstanceState);
        imageButton = (TextView) myVIew.findViewById(R.id.imgbtn);
        imageButton1 = (TextView) myVIew.findViewById(R.id.imgbtnd);
        comanydetails = (LinearLayout) myVIew.findViewById(R.id.comanydetails);
        comanydetails1 = (LinearLayout) myVIew.findViewById(R.id.comanydetails1);
        comanydetails.setVisibility(View.GONE);
        comanydetails1.setVisibility(View.GONE);
        ComapnyLinearLayout = (LinearLayout) myVIew.findViewById(R.id.linearlayoutComp);
        IndustryLinearLayout = (LinearLayout) myVIew.findViewById(R.id.linearlayoutIndust);
        staffLayout = (LinearLayout) myVIew.findViewById(R.id.staffLayout);
        revenueLayout = (LinearLayout) myVIew.findViewById(R.id.revenueLayout);
        countryLayout = (LinearLayout) myVIew.findViewById(R.id.countryLayout);

        //Populating the id's of all the fields present in the layout xml file;
        name_et = (EditText) myVIew.findViewById(R.id.name_et);
        designation_et = (EditText) myVIew.findViewById(R.id.designation_et);
        email_et = (EditText) myVIew.findViewById(R.id.email_et);
        state_et = (EditText) myVIew.findViewById(R.id.state_et);
        mobile_et = (EditText) myVIew.findViewById(R.id.mobile_et);
        company_name_et = (EditText) myVIew.findViewById(R.id.company_name_et);
        pan_number_et = (EditText) myVIew.findViewById(R.id.pan_number_et);
        website_et = (EditText) myVIew.findViewById(R.id.website_et);
        address_line1_et = (EditText) myVIew.findViewById(R.id.address_line1_et);
        address_line2_et = (EditText) myVIew.findViewById(R.id.address_line2_et);
        city_et = (EditText) myVIew.findViewById(R.id.city_et);
        zip_et = (EditText) myVIew.findViewById(R.id.zip_et);
        submit = (Button) myVIew.findViewById(R.id.submit_button);
        //Populate the Spinner items from xml files
        comapny_type_spinner = (Spinner) myVIew.findViewById(R.id.comapny_type_spinner);
        industry_spinner = (Spinner) myVIew.findViewById(R.id.industry_spinner);
        staff_in_numbers = (Spinner) myVIew.findViewById(R.id.staff_spinner);
        revenue_in_lacs = (Spinner) myVIew.findViewById(R.id.revenue_spinner);
        countries = (AppCompatAutoCompleteTextView) myVIew.findViewById(R.id.country_spinner);

        comapny_type_spinner.setSelection(0, false);
        industry_spinner.setSelection(0, false);
        countries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Country_id = calculateID(position);
                Toast.makeText(
                        getActivity(),
                        " Countries Name " + dataAdapter3.getItem(position).toString()
                        , Toast.LENGTH_LONG).show();
            }

            private String calculateID(int position) {
                String id = null;
                ArrayList<String> name = model.getShort_name();
                ArrayList<String> ids = model.getId();
                String Short_name = dataAdapter3.getItem(position).toString();
                Log.e(TAG, "IN ONCLICK  " + Short_name);
                Log.e(TAG, "IN NAme  " + name.toString());
                Log.e(TAG, "IN ID  " + ids.toString());
                String nameArray[] = new String[name.size()];
                nameArray = name.toArray(nameArray);
                String countryid[] = new String[ids.size()];
                countryid = ids.toArray(countryid);
                Log.e(TAG, "You want To Search  " + Short_name);
                for (int i = 0; i < model.getShort_name().size(); i++) {
                    Log.e(TAG, "Country Name " + nameArray[i]);
                    Log.e(TAG, "Country Code  " + countryid[i]);
                    if (nameArray[i].equalsIgnoreCase(Short_name)) {
                        Log.e(TAG, "Country Name " + nameArray[i]);
                        Log.e(TAG, "Country Code  " + countryid[i]);
                        id = countryid[i];
                    }
                }

                Log.e(TAG, "IN FOR LOOP   NAme Id " + id);


                return id;

            }
        });
        staffadapter = ArrayAdapter.createFromResource(getActivity(), R.array.staff_in_numbers, android.R.layout.simple_spinner_item);
        staffadapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        staff_in_numbers.setAdapter(staffadapter);
        // Addd the items in spinner of the Revenue Programmatically
        revenueadapter = ArrayAdapter.createFromResource(getActivity(), R.array.revenue_in_crore, android.R.layout.simple_spinner_item);
        revenueadapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        revenue_in_lacs.setAdapter(revenueadapter);

        comapny_type_spinner.setOnItemSelectedListener(this);
        industry_spinner.setOnItemSelectedListener(this);
        imageButton.setOnClickListener(this);
        imageButton1.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        /*SharedPreferences preferences = getActivity().getSharedPreferences(DBConstants.preferencesName, Context.MODE_PRIVATE);

        isAAvalilable = preferences.getBoolean("data_in_server", false);*/


        MainStorage storage = new MainStorage(getActivity());
        getProfileDatainServer = storage.getProfileDatainServer();

        if (getProfileDatainServer) {
            new NetworkInAsync(this).execute();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
            ProfileData = storage.getProfileData();
            ProfileData.get(0);//Userprofilename
            ProfileData.get(1);//designation
            ProfileData.get(2);//Email
            ProfileData.get(3);//Mobile
            ProfileData.get(4);//Company name
            ProfileData.get(5);//company Type
            ProfileData.get(6);//Pan Number
            ProfileData.get(7);//IndustryType
            ProfileData.get(8);//Staff Type
            ProfileData.get(9);//Revenue type
            ProfileData.get(10);//Website
            ProfileData.get(11);//Address Line1
            ProfileData.get(12);//Address Line2
            ProfileData.get(13);//City
            ProfileData.get(14);//State
            ProfileData.get(15);//Zip
            ProfileData.get(16);//Country_Code
            name_et.setText(ProfileData.get(0));
            designation_et.setText(ProfileData.get(1));
            email_et.setText(ProfileData.get(2));
            mobile_et.setText(ProfileData.get(3));
            company_name_et.setText(ProfileData.get(4));

            //5 gett the id of the Company Type from here
            pan_number_et.setText(ProfileData.get(6));

            //7 gett the id of the Industry Type from here
            String compareStaff = ProfileData.get(8);
            if (!compareStaff.equals(null)) {
                int spinnerPosition = staffadapter.getPosition(compareStaff);
                staff_in_numbers.setSelection(spinnerPosition);
            }
            String compareRevenue = ProfileData.get(9);
            if (!compareRevenue.equals(null)) {
                int spinnerPosition = revenueadapter.getPosition(compareRevenue);
                revenue_in_lacs.setSelection(spinnerPosition);
            }
            website_et.setText(ProfileData.get(10));
            address_line1_et.setText(ProfileData.get(11));
            address_line2_et.setText(ProfileData.get(12));
            city_et.setText(ProfileData.get(13));
            state_et.setText(ProfileData.get(14));
            //country_spinner.setText(ProfileData.get(15));
            zip_et.setText(ProfileData.get(15));

        } else {
            new NetworkInAsync2(this).execute();


        }


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgbtn:
                if (comanydetails.getVisibility() == View.GONE) {
                    Animation slide_down = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_down);
                    comanydetails.setAnimation(slide_down);
                    comanydetails.setVisibility(View.VISIBLE);
                    imageButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.presence_busy, 0);
                } else {
                    comanydetails.setVisibility(View.GONE);
                    imageButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_input_add, 0);
                }
                break;
            case R.id.imgbtnd:
                if (comanydetails.getVisibility() == View.VISIBLE) {
                    comanydetails.setVisibility(View.GONE);
                    imageButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_input_add, 0);
                    imageButton1.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.presence_busy, 0);
                    comanydetails1.setVisibility(View.VISIBLE);
                    Animation slide_down = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_down);
                    comanydetails1.setAnimation(slide_down);
                } else if (comanydetails1.getVisibility() == View.GONE) {
                    imageButton1.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.presence_busy, 0);
                    comanydetails1.setVisibility(View.VISIBLE);
                    Animation slide_down = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_down);
                    comanydetails1.setAnimation(slide_down);
                } else {
                    comanydetails1.setVisibility(View.GONE);
                    imageButton1.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_input_add, 0);
                }
                break;
            case R.id.submit_button:
                name_string = name_et.getText().toString().trim();
                email_string = email_et.getText().toString().trim();
                deesignation_string = designation_et.getText().toString().trim();
                mobile_string = mobile_et.getText().toString().trim();
                company_name_string = company_name_et.getText().toString().trim();
                pan_number_string = pan_number_et.getText().toString().trim();
                website_string = website_et.getText().toString().trim();
                address_line1_string = address_line1_et.getText().toString().trim();
                address_line2_string = address_line2_et.getText().toString().trim();
                zip_string = zip_et.getText().toString().trim();
                city_string = city_et.getText().toString().trim();
                state_string = state_et.getText().toString().trim();
                String companyname = comapny_type_spinner.getSelectedItem().toString();
                String industryname = industry_spinner.getSelectedItem().toString();

                staff = staff_in_numbers.getSelectedItem().toString();
                revenue = revenue_in_lacs.getSelectedItem().toString();
                boolean status = Validate();
                if (status) {
                    if (companyname.equalsIgnoreCase("Select Company")) {
                        ComapnyLinearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
                    } else if (industryname.equalsIgnoreCase("Select Industry")) {
                        IndustryLinearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
                    } else {
                        Log.e(TAG, "We are In Else Block1");
                        ComapnyLinearLayout.setBackgroundColor(Color.parseColor("#fffcfc"));
                        IndustryLinearLayout.setBackgroundColor(Color.parseColor("#fffcfc"));
                        ComapnyLinearLayout.setBackgroundColor(Color.parseColor("#fffcfc"));

                        //Perform The Updation From Here
                        final DBHandler handler = new DBHandler(getActivity());
                        String uid = handler.getUserId();
                        AsyncHttpClient client = new AsyncHttpClient();
                        RequestParams params = new RequestParams();
                        pDialog = new ProgressDialog(getActivity());
                        pDialog.setMessage("Updating Information Please wait..");
                        pDialog.setCanceledOnTouchOutside(false);
                        pDialog.show();
                        Log.e(TAG, "We are In Else Block2");
                        params.add("add1", address_line1_string);
                        params.add("add2", address_line2_string);
                        params.add("city", city_string);
                        params.add("state", state_string);
                        params.add("country", Country_id);
                        params.add("zip", zip_string);
                        params.add("company", company_name_string);
                        params.add("c-type", Company_id);
                        params.add("pan", pan_number_string);
                        params.add("industry", Industry_id);
                        params.add("staff", staff);
                        params.add("revenue", revenue);
                        params.add("website", website_string);
                        params.add("cp-name", name_string);
                        params.add("cp-designation", deesignation_string);
                        params.add("cp-email", email_string);
                        params.add("cp-mobile", mobile_string);
                        params.add("action", "kyc");
                        client.setTimeout(10 * 1000);


                        Log.e(TAG, "We are In Else Block3" + Urls.profileUrl + uid);
                        client.post(Urls.profileUrl + uid, params, new JsonHttpResponseHandler() {
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


                                                client.post(Urls.profileUrl + handler.getUserId(), params, new JsonHttpResponseHandler() {
                                                    @Override
                                                    public void onStart() {
                                                        super.onStart();
                                                    }

                                                    @Override
                                                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                                        try {
                                                            SharedPreferences preferences = getActivity().getSharedPreferences(DBConstants.preferencesName, Context.MODE_PRIVATE);
                                                            SharedPreferences.Editor editor = preferences.edit();
                                                            //If response code is 404 means No data is Available
                                                            if (response.getString("msg").equals("404")) {
                                                                //editor.putBoolean("data_in_server",false);
                                                            } else {
                                                                MainStorage storage = new MainStorage(getActivity(), response);
                                                                storage.addUserProfileData();
                                                                editor.putBoolean("data_in_server", true);
                                                                editor.commit();


                                                            }
                                                        } catch (JSONException e) {
                                                            Toast.makeText(getActivity(), " Error " + e.toString(), Toast.LENGTH_SHORT).show();
                                                            e.printStackTrace();
                                                        } catch (Exception e) {
                                                            Toast.makeText(getActivity(), " Error " + e, Toast.LENGTH_LONG).show();
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


                    }
                }


                //If USer Miss Some Item Show Dialog Box Showing Please Fill all Items
                else {

                    Urls.createDialog(getActivity(), Urls.AlertMsgs.allFieldsMsg);

                }

                break;
        }

    }

    private boolean Validate() {

        String countryName = countries.getText().toString().trim();
        ArrayList<String> country_Name = model.getShort_name();
        String nameArray[] = new String[country_Name.size()];
        nameArray = country_Name.toArray(nameArray);
        Log.e(TAG, "You want To Search  " + countryName);
        boolean match = false;
        for (int i = 0; i < model.getShort_name().size(); i++) {

            if (nameArray[i].equalsIgnoreCase(countryName)) {
                Log.e(TAG, "Country Match at Position  " + nameArray[i]);
                Log.e(TAG, "country Match ");
                match = true;
            }
        }
        if (match == false) {
            countryLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
            return false;
        }
        boolean validate = false;
        boolean s = true;
        //Validating  the Entire Form From here to Asure that no Null data being saved in Server

        if (!(company_name_string.length() > 0)) {


            company_name_et.requestFocus();
            company_name_et.setHint(Html.fromHtml("<font color='#ae0101'>Enter Company Name</font>"));
            return false;
        }
        if ((revenue.equalsIgnoreCase("Select Revenue"))) {
            IndustryLinearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
            return false;
        }
        if (staff.equalsIgnoreCase("Select Staff")) {
            staffLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
            return false;
        }

        if (!(pan_number_string.length() > 0)) {
            pan_number_et.requestFocus();
            pan_number_et.setHint(Html.fromHtml("<font color='#ae0101'>Enter Pan</font>"));
            return false;
        }
        if (!(website_string.length() > 0)) {
            website_et.requestFocus();
            website_et.setHint(Html.fromHtml("<font color='#ae0101'>Enter Website</font>"));
            return false;
        }
        if (!(address_line1_string.length() > 0)) {
            address_line1_et.requestFocus();
            address_line1_et.setHint(Html.fromHtml("<font color='#ae0101'>Enter Address line1</font>"));
            return false;
        }
        if (!(city_string.length() > 0)) {
            city_et.requestFocus();
            city_et.setHint(Html.fromHtml("<font color='#ae0101'>Enter City</font>"));
            return false;
        }
        if (!(state_string.length() > 0)) {
            state_et.requestFocus();
            state_et.setHint(Html.fromHtml("<font color='#ae0101'>Enter State</font>"));
            return false;

        }
        if ((comapny_type_spinner.getSelectedItem().toString().equalsIgnoreCase("Select Company"))) {
            ComapnyLinearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
            return false;
        }

        if ((industry_spinner.getSelectedItem().toString().equalsIgnoreCase("Select Industry"))) {
            Log.e("LOGSERVICE", "Error in Industry");
            IndustryLinearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
            return false;
        }


        if (!(zip_string.length() > 0)) {
            Log.e("LOGSERVICE", "Submit Button Clicked6");
            zip_et.requestFocus();
            zip_et.setHint(Html.fromHtml("<font color='#ae0101'>Enter Zip</font>"));
            return false;
        }
        if (!(name_string.length() > 0)) {
            Log.e("LOGSERVICE", "Submit Button Clicked7");
            name_et.requestFocus();

            name_et.setHint(Html.fromHtml("<font color='#ae0101'>Enter Name</font>"));
            return false;
        }
        if (!(deesignation_string.length() > 0)) {
            Log.e("LOGSERVICE", "Submit Button Clicked8");
            designation_et.requestFocus();
            designation_et.setHint(Html.fromHtml("<font color='#ae0101'>Enter Designatin</font>"));
            return false;
        }
        if (!(mobile_string.length() > 0)) {
            Log.e("LOGSERVICE", "Submit Button Clicked9");
            mobile_et.requestFocus();
            mobile_et.setHint(Html.fromHtml("<font color='#ae0101'>Enter Mobile</font>"));
            return false;
        }
        Pattern emailPattern = Pattern.compile(emailRegex);
        Matcher emailMatcher = emailPattern.matcher(email_string);


        if (!emailMatcher.find()) {
            Log.e("LOGSERVICE", "Submit Button Clicked11");
            email_et.requestFocus();
            email_et.setHint(Html.fromHtml("<font color='#ae0101'>Enter Email</font>"));

        } else {


            validate = true;


        }

        return validate;
    }


    @Override
    public void onResume() {
        super.onResume();

        dashBoard = (DashBoard) getActivity();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void getAllData() {


        StringRequest stringRequest = new StringRequest(Urls.getallSpinnerData, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject json, parent_json = null;

                try {

                    json = new JSONObject(response);
                    parent_json = json.getJSONObject("body");
                    jsonArray1 = parent_json.getJSONArray("company_type");
                    getComapanyType(jsonArray1);
                    jsonArray2 = parent_json.getJSONArray("industry_type");
                    getIndustryType(jsonArray2);
                    jsonArray3 = parent_json.getJSONArray("country");
                    getCountryName(jsonArray3);
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), " Error Reading Value From Server " + e, Toast.LENGTH_LONG).show();
                }


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), " Error Reading Value From Server " + error, Toast.LENGTH_LONG).show();

            }
        });
        ApplicationController.getInstance().addToRequestQueue(stringRequest);
       /*  RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
           requestQueue.add(stringRequest);*/

    }

    private void getComapanyType(JSONArray jsonArray) {
        company_type_arrayList.add("Select Company");
        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject jsonObject = null;
            try {
                jsonObject = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                Toast.makeText(getActivity(), " Error Reading Value From Server Slow Internet  ", Toast.LENGTH_LONG).show();
            }
            try {
                company_type_arrayList.add(jsonObject.getString(DBConstants.TAG_COMPANY_TYPE));
                if (!ProfileData.isEmpty()) {
                    if (ProfileData.get(5).equals(jsonObject.getString(DBConstants.TAG_COMPANY_TYPE))) {
                        Company_id = jsonObject.getString((DBConstants.TAG_COMPANY_ID));
                    }
                }
            } catch (JSONException e) {
                Toast.makeText(getActivity(), "  Internet Connection is Slow  ", Toast.LENGTH_LONG).show();
            }
        } //Shahrukh made Comment here on 2/7/2017
       /* try {
            ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item, company_type_arrayList);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Some Error Have Occured ", Toast.LENGTH_SHORT).show();
        }*/
         dataAdapter1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, company_type_arrayList) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                View v = super.getDropDownView(position, null, parent);
                ;

                // If this is the selected item position
                if (position == selectedItem1) {
                    v.setBackgroundColor(Color.parseColor("#8ca7d4"));
                } else {
                    // for other views
                    v.setBackgroundColor(Color.WHITE);

                }
                return v;
            }


        };
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        comapny_type_spinner.setAdapter(dataAdapter1);
        String comparecompany = null;
        if (!ProfileData.isEmpty()) {
            comparecompany = ProfileData.get(5);
        }


        if (getProfileDatainServer) {
            if (!comparecompany.equals(null)) {
                int position = dataAdapter1.getPosition(comparecompany);
                selectedItem1 = position;
                comapny_type_spinner.setSelection(position);

            }
        }


    }

    private void getIndustryType(JSONArray result1) throws JSONException {
        industry_type_arrayList.add("Select Industry");
        for (int i = 0; i < result1.length(); i++) {

            JSONObject jsonObject = result1.getJSONObject(i);
            industry_type_arrayList.add(jsonObject.getString(DBConstants.TAG_INDUSTRY_NAME));
            if (!ProfileData.isEmpty()) {
                if (ProfileData.get(7).equals(jsonObject.getString(DBConstants.TAG_INDUSTRY_NAME))) {
                    Industry_id = jsonObject.getString((DBConstants.TAG_INDUSTRY_ID));
                }
            }


        }

        dataAdapter2 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, industry_type_arrayList) {
            @Override
            public View getDropDownView(int position2, View convertView, ViewGroup parent) {
              /*  Toast.makeText(getActivity(), Industry_id, Toast.LENGTH_LONG).show();
               */
                View v = null;
                v = super.getDropDownView(position2, null, parent);
                // If this is the selected item position
                if (position2 == selectedItem2) {
                    v.setBackgroundColor(Color.parseColor("#8ca7d4"));
                } else {
                    // for other views
                    v.setBackgroundColor(Color.WHITE);

                }
                return v;
            }
        };
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        String compareindustry = null;
        industry_spinner.setAdapter(dataAdapter2);
        if (!ProfileData.isEmpty()) {
            compareindustry = ProfileData.get(7);
        }

        if (getProfileDatainServer) {
            if (!compareindustry.equals(null)) {
                int position2 = dataAdapter2.getPosition(compareindustry);
                selectedItem2 = position2;
                industry_spinner.setSelection(position2);

            }
        }


    }

    ArrayList modelArrayList1 = new ArrayList();
    ArrayList modelArrayList2 = new ArrayList();

    private void getCountryName(JSONArray jsonArray3) throws JSONException {
        country_type_arrayList.add("Select Country");
        for (int i = 0; i < jsonArray3.length(); i++) {

            JSONObject jsonObject;
            jsonObject = jsonArray3.getJSONObject(i);

            country_type_arrayList.add(jsonObject.getString(DBConstants.TAG_COUNTRY_NAME_LIST));
            if (!ProfileData.isEmpty()) {
                if (ProfileData.get(16).equals(jsonObject.getString(DBConstants.TAG_COUNTRY_NAME_LIST))) {
                    Country_id = jsonObject.getString((DBConstants.TAG_CONTRY_ID));
                }
            }

            modelArrayList1.add(jsonObject.getString("short_name"));
            modelArrayList2.add(jsonObject.getString("id"));

        }
        model = new ModelCountry(modelArrayList1, modelArrayList2);

        dataAdapter3 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, country_type_arrayList) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = null;
                v = super.getDropDownView(position, null, parent);
                // If this is the selected item position
                if (position == selectedItem3) {
                    v.setBackgroundColor(Color.parseColor("#8ca7d4"));
                } else {
                    // for other views
                    v.setBackgroundColor(Color.WHITE);

                }
                return v;
            }
        };

        String compareCountry = null;
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        countries.setAdapter(dataAdapter3);
        Log.e(TAG, "   Total Countries  Are " + dataAdapter3.getCount());
        if (!ProfileData.isEmpty()) {
            compareCountry = ProfileData.get(16);
        }

        if (getProfileDatainServer) {

            if (!compareCountry.equals(null)) {
                int position = dataAdapter2.getPosition(compareCountry);
                selectedItem3 = position;
                countries.setText(compareCountry);

            }
        }


    }

    int inhibit_spinner, inhibit_spinner1 = 0;

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

        if (inhibit_spinner == 0) {
            ++inhibit_spinner;
        } else if (inhibit_spinner1 == 0) {
            ++inhibit_spinner1;
        } else {

            switch (adapterView.getId()) {
                case R.id.comapny_type_spinner:
                    Company_id = getCompanyID(position);
                   /* Toast.makeText(getActivity(), " Company Selected  ID "
                                    + adapterView.getSelectedItem().toString(),
                            Toast.LENGTH_SHORT).show();*/
                    ComapnyLinearLayout.setBackgroundColor(Color.parseColor("#fffcfc"));
                    break;
                case R.id.industry_spinner:
                    Industry_id = getIndustryID(position);
                    /*Toast.makeText(getActivity(), "Industry Selected " + adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                   */
                    IndustryLinearLayout.setBackgroundColor(Color.parseColor("#fffcfc"));
                    break;
                default:
                   /* Toast.makeText(getActivity(), "Nothing was  Selected ", Toast.LENGTH_SHORT).show();
                   */
                    break;
            }

        }
    }

    private String getIndustryID(int position) {
        String id = "";
        try {
            if (position == 0) {
                //Do nothing
            } else {
                JSONObject industryId = jsonArray2.getJSONObject(position - 1);
                Log.e(TAG, industryId.toString());
                id = industryId.getString(DBConstants.TAG_INDUSTRY_ID);
                Log.e(TAG, id);
            }
        } catch (JSONException e) {
            Toast.makeText(getActivity(), "" + e, Toast.LENGTH_LONG).show();
        }

        return id;
    }

    private String getCompanyID(int position) {
        String id = "";
        try {

            if (position == 0) {
                //Do nothing
            } else {
                JSONObject comapanyID = jsonArray1.getJSONObject(position - 1);
                id = comapanyID.getString(DBConstants.TAG_COMPANY_ID);
                Log.e(TAG, id);
            }
        } catch (JSONException e) {

            Toast.makeText(getContext(), "  Error " + e, Toast.LENGTH_LONG).show();
        }


        return id;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
       /* Toast.makeText(getActivity(), "Nothing Selected-- Selected " + adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
*/
    }

    private class NetworkInAsync extends AsyncTask<String, Void, Boolean> {
        private Context context;
        private Fragment fragment;

        NetworkInAsync(Fragment fragment) {
            this.context = getActivity().getApplicationContext();
            this.fragment = fragment;
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            getAllData();
            return false;
        }
    }


    private class NetworkInAsync2 extends AsyncTask<String, Void, Boolean> {
        private Context context;
        private Fragment fragment;

        NetworkInAsync2(Fragment fragment) {
            this.context = getActivity().getApplicationContext();
            this.fragment = fragment;
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            getAllData();
            return false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}


