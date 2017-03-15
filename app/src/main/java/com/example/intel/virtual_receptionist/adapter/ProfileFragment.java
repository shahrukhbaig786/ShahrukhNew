package com.example.intel.virtual_receptionist.adapter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.intel.virtual_receptionist.R;
import com.example.intel.virtual_receptionist.UpdateDocumentFragment;
import com.example.intel.virtual_receptionist.UpdateOrganizationFragment;
import com.example.intel.virtual_receptionist.showProfileSettingFragment;

import java.util.ArrayList;

/**
 * Created by intel on 12/22/2016.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {
    ViewPager viewPager;
    TabLayout tabLayout;
    ViewPagerAdapter viewPagerAdapter;
    ProgressBar dialog;
    AppBarLayout appbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_profle, container, false);
        dialog = (ProgressBar) myView.findViewById(R.id.progressBar);
        getActivity().setProgressBarIndeterminateVisibility(true);
        return myView;
    }


    public void Check_activePagerFragement(Fragment fragment_name) {
        // do something in fragment
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialog = new ProgressBar(getActivity());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {


                new AsybTaskMe().execute((Void[]) null);
            }
        });
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        appbar = (AppBarLayout) view.findViewById(R.id.appbar);
        appbar.setVisibility(View.INVISIBLE);
       // tabLayout.setBackgroundColor(Color.LTGRAY);
        tabLayout.setVisibility(View.INVISIBLE);
        int item = viewPager.getCurrentItem();
        viewPager.setCurrentItem(item);
        viewPager.getCurrentItem();
        viewPager.setOffscreenPageLimit(3);
        viewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);

       /* if (fragment instanceof UpdateDocumentFragment) {
            this.fragment = childFragment;
            Toast.makeText(getActivity(), " Fragment UpdateDocumentFragment called  ", Toast.LENGTH_SHORT).show();
        }
        if (fragment instanceof UpdateOrganizationFragment) {
            this.fragment = childFragment;
            Toast.makeText(getActivity(), " Fragment UpdateOrganizationFragment called  ", Toast.LENGTH_SHORT).show();

        }
        if (fragment instanceof showProfileSettingFragment) {
            Toast.makeText(getActivity(), " Fragment showProfileSettingFragment called  ", Toast.LENGTH_SHORT).show();
        }*/


    }


    @Override
    public void onPause() {
        super.onPause();
        System.gc();
        /*if (fragment instanceof UpdateDocumentFragment)
        {
            Toast.makeText(getActivity(), "Save Update Organization Data", Toast.LENGTH_SHORT).show();
        }
        if (fragment instanceof showProfileSettingFragment)
        {
            Toast.makeText(getActivity(), "Save showProfileSettingFragment  Data", Toast.LENGTH_SHORT).show();

        }
        if (fragment instanceof UpdateOrganizationFragment)
        {

            Toast.makeText(getActivity(), "Save UpdateOrganizationFragment Data", Toast.LENGTH_SHORT).show();

        }*/
    }

    @Override
    public void onResume() {
        super.onResume();

        /*if (fragment instanceof UpdateDocumentFragment)
        {
            Toast.makeText(getActivity(), "Restored  Update Organization Data", Toast.LENGTH_SHORT).show();
        }
        if (fragment instanceof showProfileSettingFragment)
        {
            Toast.makeText(getActivity(), "Restore  showProfileSettingFragment  Data", Toast.LENGTH_SHORT).show();

        }
        if (fragment instanceof UpdateOrganizationFragment)
        {

            Toast.makeText(getActivity(), "Restore UpdateOrganizationFragment Data", Toast.LENGTH_SHORT).show();

        }*/
    }

    @Override
    public void onClick(View view) {

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }


    @Override
    public void onPageSelected(int position) {


      /*  Toast.makeText(getActivity(),
                "onPageSelected : " + position, Toast.LENGTH_SHORT).show();
        posAnInt = position;*/
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private class AsybTaskMe extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            {
                for (int i = 0; i < 3; i++) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


                //dialog.setVisibility(View.GONE);
                return null;
            }

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            appbar.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.VISIBLE);
            viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
            viewPagerAdapter.addFragments(new UpdateOrganizationFragment(), "Organization");//Done
            viewPagerAdapter.addFragments(new UpdateDocumentFragment(), " Documents");
            viewPagerAdapter.addFragments(new showProfileSettingFragment(), " Setting");//Lets Do this
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    viewPager.setAdapter(viewPagerAdapter);
                    //viewPager.setCurrentItem(1, false);
                    tabLayout.setupWithViewPager(viewPager);
                    dialog.setVisibility(View.GONE);

                }
            });

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setScaleX(100);
            dialog.setAlpha(1);
            dialog.setScaleY(300);

            dialog.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}

class ViewPagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> tabTttles = new ArrayList<>();

    public void addFragments(Fragment fragments, String titles) {

        this.fragments.add(fragments);
        this.tabTttles.add(titles);
    }

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
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
                UpdateOrganizationFragment p = new UpdateOrganizationFragment();
                return p;

            case 1:
                UpdateDocumentFragment u = new UpdateDocumentFragment();
                return u;
            case 2:
                showProfileSettingFragment s = new showProfileSettingFragment();
                return s;
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
                return "KYC";

            case 1:
                return "Document";
            case 2:
                return "Password";

        }
        return null;
    }


}
