package com.example.intel.virtual_receptionist.Model;

/**
 * Created by intel on 3/6/2017.
 */
public class Sound {
    private String id;
    private String ivr_name;

    public Sound(String id, String ivr_name) {
        this.id = id;
        this.ivr_name = ivr_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIvr_name() {
        return ivr_name;
    }

    public void setIvr_name(String ivr_name) {
        this.ivr_name = ivr_name;
    }
}
