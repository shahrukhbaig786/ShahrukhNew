package com.example.intel.virtual_receptionist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by intel on 12/22/2016.
 */
public class SettingFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

       View  myView=inflater.inflate(R.layout.tab_update_organization_fragment,container,false);


        return myView;


    }

}
