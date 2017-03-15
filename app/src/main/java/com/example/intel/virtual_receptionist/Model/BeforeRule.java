package com.example.intel.virtual_receptionist.Model;

/**
 * Created by intel on 2/22/2017.
 */
public class BeforeRule {

    public String id;
    public String uid;
    public String caller;
    public String action;
    public String dtmf_rules;
    public String voicemail_rules;
    public String phone_rules;
    public String ivrid;
    public String description;
    public String notifications;
    public String dateadded;
    public String ip;
    public String nid;

    public BeforeRule(String id, String uid, String caller, String action, String dtmf_rules, String voicemail_rules, String phone_rules
            , String ivrid, String description, String notifications, String dateadded, String ip, String nid) {
        this.id=id;
        this.uid=uid;
        this.caller=caller;
        this.action=action;
        this.dtmf_rules=dtmf_rules;
        this.voicemail_rules=voicemail_rules;
        this.phone_rules=phone_rules;
        this.ivrid=ivrid;
        this.description=description;
        this.notifications=notifications;
        this.dateadded=dateadded;
        this.ip=ip;
        this.nid=nid;
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

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDtmf_rules() {
        return dtmf_rules;
    }

    public void setDtmf_rules(String dtmf_rules) {
        this.dtmf_rules = dtmf_rules;
    }

    public String getVoicemail_rules() {
        return voicemail_rules;
    }

    public void setVoicemail_rules(String voicemail_rules) {
        this.voicemail_rules = voicemail_rules;
    }

    public String getPhone_rules() {
        return phone_rules;
    }

    public void setPhone_rules(String phone_rules) {
        this.phone_rules = phone_rules;
    }

    public String getIvrid() {
        return ivrid;
    }

    public void setIvrid(String ivrid) {
        this.ivrid = ivrid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotifications() {
        return notifications;
    }

    public void setNotifications(String notifications) {
        this.notifications = notifications;
    }

    public String getDateadded() {
        return dateadded;
    }

    public void setDateadded(String dateadded) {
        this.dateadded = dateadded;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }


}
