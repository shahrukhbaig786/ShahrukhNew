package com.example.intel.virtual_receptionist.Model;

/**
 * Created by intel on 2/9/2017.
 */
public class Agent {

    public String agent_Name;
    public String agent_Sip_Name;
    public String agent_id;
    public String agent_mobile;
    public String active_or_inactive;

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    public String getAgent_mobile() {
        return agent_mobile;
    }

    public void setAgent_mobile(String agent_mobile) {
        this.agent_mobile = agent_mobile;
    }

    public String getActive_or_inactive() {
        return active_or_inactive;
    }

    public void setActive_or_inactive(String active_or_inactive) {
        this.active_or_inactive = active_or_inactive;
    }

    public String date_added;

    public String getAgent_Name() {
        return agent_Name;
    }

    public void setAgent_Name(String agent_Name) {
        this.agent_Name = agent_Name;
    }

    public String getAgent_Sip_Name() {
        return agent_Sip_Name;
    }

    public void setAgent_Sip_Name(String agent_Sip_Name) {
        this.agent_Sip_Name = agent_Sip_Name;
    }

    public String getAgent_Password() {
        return agent_Password;
    }

    public void setAgent_Password(String agent_Password) {
        this.agent_Password = agent_Password;
    }

    public String agent_Password;

    public String getDummyImg() {
        return dummyImg;
    }

    public void setDummyImg(String dummyImg) {
        this.dummyImg = dummyImg;
    }

    public String dummyImg;

    public Agent(String id, String agent_Name, String agent_Sip_Name,
                 String agent_Password, String agent_mobile, String active_or_inactive,
                 String date_added) {

        this.agent_id = id;
        this.agent_Name = agent_Name;
        this.agent_Sip_Name = agent_Sip_Name;
        this.agent_Password = agent_Password;
        this.agent_mobile = agent_mobile;
        this.date_added = date_added;
        this.active_or_inactive = active_or_inactive;

    }


}