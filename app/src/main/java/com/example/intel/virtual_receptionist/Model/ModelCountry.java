package com.example.intel.virtual_receptionist.Model;

import java.util.ArrayList;

/**
 * Created by intel on 1/5/2017.
 */
public class ModelCountry {

    private ArrayList short_name;
    private ArrayList id;


    public ModelCountry(ArrayList short_name, ArrayList id) {
        this.short_name = short_name;
        this.id = id;

    }

    public ArrayList getShort_name() {
        return short_name;
    }

    public void setShort_name(ArrayList short_name) {
        this.short_name = short_name;
    }

    public ArrayList getId() {
        return id;
    }

    public void setId(ArrayList id) {
        this.id = id;
    }
}
















