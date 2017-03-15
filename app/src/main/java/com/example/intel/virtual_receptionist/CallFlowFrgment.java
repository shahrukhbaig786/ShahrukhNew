package com.example.intel.virtual_receptionist;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.intel.virtual_receptionist.DBManager.DBHandler;
import com.example.intel.virtual_receptionist.Model.NumberAllocate;
import com.example.intel.virtual_receptionist.RulesFragmentBF.AfterFragment;
import com.example.intel.virtual_receptionist.RulesFragmentBF.BeforeFragment;
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
 * Created by intel on 2/21/2017.
 */
public class CallFlowFrgment extends Fragment implements View.OnClickListener {

    ViewPager viewPager;
    TabLayout tabLayout;
    ViewPagerAdapter viewPagerAdapter;
    ProgressBar dialog;
    AppBarLayout appbar;
    View myView;
    Spinner _number_Spinner;
    ArrayList<NumberAllocate> number_List;
    ArrayList number_number_POJO;
    ArrayAdapter<String> number_adapter;
    String userUID = null;
    DBHandler storage;
    String NUMBER_ID;
    FloatingActionButton myFab;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        load_number_from_Server();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.call_flow_fragment, container, false);
        initailizetheValue();
        number_number_POJO = new ArrayList<>();
        myFab = (FloatingActionButton) myView.findViewById(R.id.fab_call_flow);


        myFab.setOnClickListener(this);
        return myView;
    }

    private boolean load_number_from_Server() {
        //Here what iam trying to do is to load the Data from the Server and place it in the Spinner Item
        storage = new DBHandler(getActivity());
        userUID = storage.getUserId();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.setTimeout(10 * 1000);
        client.post(Urls.allocated_number + userUID, null, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
               /* System.out.println(Urls.allocated_number + userUID);
                System.out.println(response);*/
                if (response == null) {
                    return;
                }
                JSONObject parentJson, childJSON = null;
                JSONArray parentArray = null;
                try {
                    parentJson = response.getJSONObject("msg");
                    parentArray = parentJson.getJSONArray("Allocated_no_list");
                    for (int i = 0; i < parentArray.length(); i++) {
                        childJSON = parentArray.getJSONObject(i);
                        String id = childJSON.getString("id");
                        String uid = childJSON.getString("uid");
                        String num = childJSON.getString("num");
                        String date_added = childJSON.getString("date_added");
                        NumberAllocate number = new NumberAllocate(id, uid, num, date_added);
                        number_List.add(number);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (NumberAllocate numberAllocate : number_List) {
                    String number = numberAllocate.getNum();
                    number_number_POJO.add(number);
                    numberAllocate.setId(numberAllocate.getId());
                }
                number_adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item1, number_number_POJO);
                number_adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                _number_Spinner.setAdapter(number_adapter);
                _number_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    int count = 0;

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        String number = adapterView.getItemAtPosition(position).toString();
                        Bundle BUNDLE = new Bundle();
                        for (NumberAllocate numberId : number_List) {
                            String number_we_got = numberId.getNum();
                            if (number_we_got.equalsIgnoreCase(number)) {
                                NUMBER_ID = numberId.getId();
                                BUNDLE.putString("NUMBER", NUMBER_ID);
                            }
                        }
                        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), BUNDLE);
                        viewPagerAdapter.addFragments(new BeforeFragment(), "BEFORE RULE");//Done
                        viewPagerAdapter.addFragments(new AfterFragment(), " AFTER RULE");
                        viewPagerAdapter.notifyDataSetChanged();
                        viewPager.setAdapter(viewPagerAdapter);
                        //viewPager.setCurrentItem(1, false);
                        tabLayout.setupWithViewPager(viewPager);
                        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFD1CCCC"));

                    }


                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                super.onFailure(statusCode, headers, responseString, throwable);

            }
        });
        return true;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout_callflow);
        viewPager = (ViewPager) view.findViewById(R.id.pager_graph);
        appbar = (AppBarLayout) view.findViewById(R.id.appbar_callFlow);
    }

    private void initailizetheValue() {
        _number_Spinner = (Spinner) myView.findViewById(R.id._number_Spinner);
        number_List = new ArrayList<>();
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {//Start of Switch Case
            case R.id._number_Spinner: {
                Toast.makeText(getContext(), "you click on sPINNER fROM ONcLICK", Toast.LENGTH_SHORT).show();
            }
            case R.id.fab_call_flow: {


                if (viewPager.getCurrentItem() == 0) {
                    Intent intent = new Intent(getActivity(), AddRuleBefore.class);
                    for (NumberAllocate number : number_List) {
                        String number_from_spinner = _number_Spinner.getSelectedItem().toString();
                        String number2 = number.getNum();
                        if (number_from_spinner.equals(number2)) {//Get the Id of the Number from the List
                            NUMBER_ID = number.getId();
                        }

                    }
                    Toast.makeText(getActivity(), "  Current Item is  " + viewPager.getCurrentItem() + "\n" + "NUMBER ID IS " + NUMBER_ID, Toast.LENGTH_SHORT).show();


                    intent.putExtra("NUMBER", NUMBER_ID);
                    intent.putExtra("BEFORE", viewPager.getCurrentItem());
                    startActivity(intent);
                }
                if (viewPager.getCurrentItem() == 1) {
                    for (NumberAllocate number : number_List) {
                        String number_from_spinner = _number_Spinner.getSelectedItem().toString();
                        String number2 = number.getNum();
                        if (number_from_spinner.equals(number2)) {//Get the Id of the Number from the List
                            NUMBER_ID = number.getId();
                        }

                    }
                    Toast.makeText(getActivity(), "  Current Item is  " + viewPager.getCurrentItem() + "\n" + "NUMBER ID IS " + NUMBER_ID, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getActivity(), AddRuleBefore.class);
                    intent.putExtra("NUMBER", NUMBER_ID);
                    intent.putExtra("BEFORE", viewPager.getCurrentItem());
                    startActivity(intent);
                }


            }
        }//End of Switch Case
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if ((number_List != null)) {
            number_List.clear();
        }

        storage.close();
        number_List.clear();
        number_number_POJO.clear();
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> tabTttles = new ArrayList<>();
        private final Bundle fragmentBundle;

        public void addFragments(Fragment fragments, String titles) {

            this.fragments.add(fragments);
            this.tabTttles.add(titles);
        }

        public ViewPagerAdapter(FragmentManager fm, Bundle data) {
            super(fm);
            fragmentBundle = data;
        }

        /*
            @Override
            public void destroyItem(View container, int position, Object object) {
                super.destroyItem(container, position, object);
            }*/
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    BeforeFragment p = new BeforeFragment();
                    p.setArguments(this.fragmentBundle);
                    return p;
                case 1:
                    AfterFragment u = new AfterFragment();
                    u.setArguments(this.fragmentBundle);
                    return u;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {


            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            super.destroyItem(container, position, object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
        /*return tabTttles.get(position);*/
            switch (position) {
                case 0:
                    return "BEFORE";

                case 1:
                    return "AFTER";


            }
            return null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        number_List.clear();
        number_number_POJO.clear();
        number_adapter.clear();

        storage.close();

    }
}