package com.example.intel.virtual_receptionist.Model;

/**
 * Created by intel on 2/21/2017.
 */
public class NumberAllocate {
    private String id;
    private String uid;
    private String num;
    private String date_added;

    public NumberAllocate() {

    }

    public NumberAllocate(String id, String uid, String num, String date_added) {
        this.id = id;
        this.uid = uid;
        this.num = num;
        this.date_added = date_added;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }
}
