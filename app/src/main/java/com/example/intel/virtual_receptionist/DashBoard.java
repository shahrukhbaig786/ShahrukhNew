package com.example.intel.virtual_receptionist;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intel.virtual_receptionist.DBManager.DBConstants;
import com.example.intel.virtual_receptionist.DBManager.DBHandler;
import com.example.intel.virtual_receptionist.DBManager.MainStorage;
import com.example.intel.virtual_receptionist.URL.Urls;
import com.example.intel.virtual_receptionist.adapter.ProfileFragment;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;


/**
 * Created by intel on 12/7/2016.
 */
public class DashBoard extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    Toolbar toolbar;
    TextView toolbarTitle;
    DrawerLayout dlayout;
    ListView navigationList;
    TextView txt_name, txt_email, money_in_bank;
    ImageView imageButton;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    UpdateOrganizationFragment mupdateOraganisation;
    public boolean userIntraction;
    String name;
    Fragment fragment;
    String email, credits, fullname;
    int a = 2;
    //MainStorage storeCredentials = new MainStorage(getApplicationContext());

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.dashboard_activity);
        fragmentManager = getSupportFragmentManager();


        checkUserDataPresentinServer();


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        //Typeface tf = Typeface.createFromAsset(getAssets(), "bol.ttf");
        toolbarTitle.setTextColor(getResources().getColor(R.color.white_bg));
        toolbarTitle.setText("Dashboard");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        dlayout = (DrawerLayout) findViewById(R.id.drawer_menu);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headerView = inflater.inflate(R.layout.header_view, null);
        txt_name = (TextView) headerView.findViewById(R.id.txt_name);
        txt_email = (TextView) headerView.findViewById(R.id.txt_email);
        money_in_bank = (TextView) headerView.findViewById(R.id.money_in_bank);


        Intent info = getIntent();
        if (info != null) {
            email = info.getStringExtra("emailname");
            credits = info.getStringExtra("credits");
            fullname = info.getStringExtra("fullname");
            txt_name.setText(fullname);
            txt_email.setText(email);
            money_in_bank.setText(credits);
        }


        navigationList = (ListView) findViewById(R.id.navigation_menu);
        int[] icons = {R.drawable.dashboard,
                android.R.drawable.stat_notify_chat,
                android.R.drawable.dialog_frame,
                android.R.drawable.ic_menu_today,
                android.R.drawable.ic_menu_camera,
                android.R.drawable.ic_menu_report_image,
                android.R.drawable.ic_menu_upload_you_tube,
                android.R.drawable.ic_menu_gallery,
                android.R.drawable.menu_frame,
                android.R.drawable.sym_action_call};
        String[] titles = {"DashBoard", "Agent", "Call Flow", "CRM ", " Call Flow", "Profile", "Recharge Account",
                "Info", "Invite", "Logout"};

        navigationList.addHeaderView(headerView);


        imageButton = (ImageView) findViewById(R.id.slider_minimize);
        imageButton.setOnClickListener(this);


        NavigationListAdapter navAdapter = new NavigationListAdapter(this, titles, icons);
        navAdapter.notifyDataSetChanged();
        navigationList.setAdapter(navAdapter);
        navigationList.setOnItemClickListener(this);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, dlayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        actionBarDrawerToggle.setHomeAsUpIndicator(R.drawable.virtual_log);
        dlayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

    }

    private void CallFlow() {

        Fragment callFlow = new CallFlowFrgment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, callFlow, "CALLFLOW");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }


    private void viewDashBoard() {
        Fragment settingFragment = new SettingFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, settingFragment, "DASHBOARD");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    private void updateProfile() {
        Fragment settingFragment = new ProfileFragment();
        //Shahrukh wants to know which fragment is active
        // fragmentManager.findFragmentById(R.id.fragment_container);


        fragmentTransaction = fragmentManager.beginTransaction();
        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        fragmentTransaction.replace(R.id.fragment_container, settingFragment, "PROFILE");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void XAngList() {
        Fragment custom = new CustomFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, custom, "Custon");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == navigationList.getId()) {
            switch (position) {
                case 1:

                    dlayout.closeDrawers();
                    toolbarTitle.setText("DashBoard");
                    XAngList();
                    navigationList.setItemChecked(0, true);

                    break;
                case 2:

                    toolbarTitle.setText("Agent");
                    XAngList();
                    dlayout.closeDrawers();
                    navigationList.setItemChecked(1, true);

                    break;
                case 3:
                    toolbarTitle.setText("Call Flow");
                    CallFlow();
                    dlayout.closeDrawers();
                    navigationList.setItemChecked(2, true);

                    break;
                case 4:

                    dlayout.closeDrawers();
                    navigationList.setItemChecked(3, true);
                    toolbarTitle.setText("CustomList4");
                    XAngList();
                    break;
                case 5:

                    dlayout.closeDrawers();
                    navigationList.setItemChecked(4, true);
                    toolbarTitle.setText("CustomList");
                    XAngList();
                    break;
                case 6:
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    updateProfile();
                    toolbarTitle.setText("Profile Information");
                    dlayout.closeDrawers();
                    navigationList.setItemChecked(5, true);

                    break;
                case 7:


                    //Log.i("calling Gretting Methods From DAshboard Classs", "01");
                    toolbarTitle.setText("Greetings");

                    XAngList();
                    dlayout.closeDrawers();
                    navigationList.setItemChecked(6, true);
                    break;
                case 8:
                    navigationList.setItemChecked(7, true);
                    dlayout.closeDrawers();
                    toolbarTitle.setText("Info");

                    XAngList();
                    break;
                case 9:
                    navigationList.setItemChecked(8, true);
                    dlayout.closeDrawers();

                    toolbarTitle.setText("CustomList5");
                    XAngList();
                    break;
                case 10:
                    navigationList.setItemChecked(7, true);

					/*DBHandler handler = new DBHandler(this);
                    handler.reset();

*/

                    SharedPreferences preferences = getSharedPreferences(Urls.sharedPreferencesName, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();


                    Intent logout = new Intent(DashBoard.this, Login.class);
                    logout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION);

                    logout.putExtra("first_time", true);

                    startActivity(logout);
                    break;
            }
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
       /* Toast.makeText(getApplicationContext(), fragment.getActivity()+" \n This is  "+fragment.toString()+" \n "+fragment, Toast.LENGTH_SHORT).show();
       */
        if (fragment instanceof ProfileFragment) {
            this.fragment = fragment;
            // Toast.makeText(getApplicationContext(), " This is ProfileFragment ", Toast.LENGTH_SHORT).show();

        }




       /* if (fragment instanceof SettingFragment) {
            this.fragment = fragment;
            Toast.makeText(getApplicationContext(), " This is SettingFragment ", Toast.LENGTH_SHORT).show();

        }
        if (fragment instanceof UpdateDocumentFragment) {
            this.fragment = fragment;
            Toast.makeText(this, " Fragment UpdateDocumentFragment called  ", Toast.LENGTH_SHORT).show();
        }
        if (fragment instanceof UpdateOrganizationFragment) {
            this.fragment = fragment;
            Toast.makeText(this, " Fragment UpdateOrganizationFragment called  ", Toast.LENGTH_SHORT).show();

        }
        if (fragment instanceof showProfileSettingFragment) {
            Toast.makeText(this, " Fragment showProfileSettingFragment called  ", Toast.LENGTH_SHORT).show();
        }*/

        /*if (fragment instanceof UpdateOrganizationFragment) {
            mupdateOraganisation = (UpdateOrganizationFragment) fragment;
            Toast.makeText(getApplicationContext(), String.valueOf(fragment.getId()), Toast.LENGTH_SHORT).show();
        }*/

    }

    public Fragment getVisibleFragment() {
        FragmentManager fragmentManager = DashBoard.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

    @Override
    public void onBackPressed() {


        /*if (fragmentManager.getBackStackEntryCount() > 0) {

            Fragment fragment = getVisibleFragment();
            if (fragment instanceof ProfileFragment) {

                Log.e("current", fragment.toString());
                fragment.onDetach();

                *//*createDialog();*//*
            } else if (fragment instanceof UpdateOrganizationFragment ||  fragment instanceof showProfileSettingFragment) {
                if (true) {
                    Log.e("back pressed", "2");
                    viewDashBoard();
                } else {
                    Log.e("back pressed", "3");
                    super.onBackPressed();
                }
            } else {
                Log.e("back pressed", "3");
                super.onBackPressed();
            }


        } else {
            Log.e("back pressed", "4");
            finish();
            moveTaskToBack(true);
        }*/
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("No", null).show();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.profile:

                Fragment profileFragment = new showProfileSettingFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, profileFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                return true;


            case R.id.logout:
                SharedPreferences preferences = getSharedPreferences(Urls.sharedPreferencesName, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Intent logout = new Intent(DashBoard.this, Login.class);
                logout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


                logout.putExtra("first_time", true);
                logout.putExtra("data_in_server", false);

                startActivity(logout);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.slider_minimize:
                dlayout.closeDrawers();
                break;

        }


    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        if (mupdateOraganisation != null) {
            userIntraction = true;
        }
    }

    public void checkUserDataPresentinServer() {


        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.setTimeout(10 * 1000);
        DBHandler handler = new DBHandler(this);
        String PrivateId = handler.getUserId();
        client.post(Urls.profileUrl + PrivateId, params, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {

                    SharedPreferences preferences = getSharedPreferences(DBConstants.preferencesName, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();

                    //If response code is 404 means No data is Available
                    if (response.getString("msg").equals("404")) {
                        editor.putBoolean("data_in_server", false);
                    } else {
                        MainStorage storage = new MainStorage(DashBoard.this, response);
                        //If We got the Dat a from The Sever Save to The local Db and then You can Proceed With Other data
                        long a = storage.addUserProfileData();
                        editor.putBoolean("data_in_server", true);
                        editor.commit();

                        if (a > 0) {
                            //Toast.makeText(DashBoard.this, "Profile data is there in Server ", Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (JSONException e) {
                    Toast.makeText(DashBoard.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (Exception e) {
                    Toast.makeText(DashBoard.this, " Error " + e, Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Toast.makeText(DashBoard.this, "OnDetached called", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (fragment instanceof ProfileFragment) {
            //Toast.makeText(this, "Save ProfileFragment  Data", Toast.LENGTH_SHORT).show();
            ProfileFragment fragmentDemo = (ProfileFragment)
                    getSupportFragmentManager().findFragmentByTag("PROFILE");


        }
        System.gc();

       /* if (fragment instanceof UpdateDocumentFragment) {
            Toast.makeText(this, "Save Update Organization Data", Toast.LENGTH_SHORT).show();
        }
        if (fragment instanceof showProfileSettingFragment) {
            Toast.makeText(this, "Save showProfileSettingFragment  Data", Toast.LENGTH_SHORT).show();

        }
        if (fragment instanceof UpdateOrganizationFragment) {

            Toast.makeText(this, "Save UpdateOrganizationFragment Data", Toast.LENGTH_SHORT).show();

        }*/

    /*
        if (fragment instanceof UpdateOrganizationFragment) {
            Toast.makeText(DashBoard.this, " SaveUpdate OrganizationFragment old values", Toast.LENGTH_SHORT).show();
        }
        if (fragment instanceof UpdateDocumentFragment) {
            Toast.makeText(DashBoard.this, " Save UpdateDocumentFragment old values", Toast.LENGTH_SHORT).show();
        }*/

    }

    @Override
    protected void onResume() {
        super.onResume();

       /* if (fragment instanceof UpdateDocumentFragment) {
            Toast.makeText(this, "Restored  Update Organization Data", Toast.LENGTH_SHORT).show();
        }
        if (fragment instanceof showProfileSettingFragment) {
            Toast.makeText(this, "Restore  showProfileSettingFragment  Data", Toast.LENGTH_SHORT).show();

        }
        if (fragment instanceof UpdateOrganizationFragment) {

            Toast.makeText(this, "Restore UpdateOrganizationFragment Data", Toast.LENGTH_SHORT).show();

        }*/
        if (fragment instanceof ProfileFragment) {

            //Toast.makeText(this, "Restore ProfileFragment Data", Toast.LENGTH_SHORT).show();

        }
        /*if (fragment instanceof UpdateOrganizationFragment) {
            Toast.makeText(DashBoard.this, " Restore SaveUpdate OrganizationFragment old values", Toast.LENGTH_SHORT).show();
        }
        if (fragment instanceof UpdateDocumentFragment) {
            Toast.makeText(DashBoard.this, " Restore  UpdateDocumentFragment old values", Toast.LENGTH_SHORT).show();
        }
        if (fragment instanceof SettingFragment) {
            Toast.makeText(DashBoard.this, " Restore  SettingFragment old values", Toast.LENGTH_SHORT).show();
        }*/
    }

    /*  @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(DashBoard.this, "You disturbed 1st spinner", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(DashBoard.this, "You disturbed 2nd spinner", Toast.LENGTH_SHORT).show();
    }*/
    boolean doubleBackToExitPressedOnce = false;


}
