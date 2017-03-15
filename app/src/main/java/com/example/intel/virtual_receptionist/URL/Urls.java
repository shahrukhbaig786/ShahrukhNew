package com.example.intel.virtual_receptionist.URL;

import android.content.Context;
import android.content.DialogInterface;

import com.example.intel.virtual_receptionist.R;

/**
 * Created by angstle on 6/23/2015.
 */
public class Urls {
    public static final String sharedPreferencesName = "virtual_receptionist";
    public static final String registerUrl = "http://82.145.38.202/sachin/pannel/api/register.php";
    public static final String loginUrl = "http://82.145.38.202/sachin/pannel/api/login.php";
    public static final String profileUrl = "http://82.145.38.202/sachin/pannel/api/profile.php?action=view&uid=";
    public static final String settingUrlpassword = "http://82.145.38.202/sachin/pannel/api/profile.php?action=setting&uid=";
    public static final String rule_by_number = "http://82.145.38.202/sachin/pannel/api/rules_by_no.php?uid=";

    public static final String getallSpinnerData = "http://82.145.38.202/sachin/pannel/api/company_type_info.php";

    public static final String SERVER_UploadDoc1 = "http://82.145.38.202/sachin/pannel/api/user_doc.php?uid=";
    public static final String SERVER_UploadDoc2 = "http://82.145.38.202/sachin/pannel/api/user_doc.php?uid=";
    public static final String agent_list = "http://82.145.38.202/sachin/pannel/api/agent_details.php?action=view&uid=";
    public static final String agent_add = "http://82.145.38.202/sachin/pannel/api/agent_details.php?action=add&uid=";
    public static final String agent_delete = "http://82.145.38.202/sachin/pannel/api/agent_details.php?action=delete";
    public static final String agent_update = "http://82.145.38.202/sachin/pannel/api/agent_details.php?action=edit&uid=";
    public static final String allocated_number = "http://82.145.38.202/sachin/pannel/api/allocated_no.php?uid=";
    public static final String delete_rule_before = "http://82.145.38.202/sachin/pannel/api/rules_operations.php?action=delete&rule=before&uid=";

    public static String delete_rule_after = "http://82.145.38.202/sachin/pannel/api/rules_operations.php?action=delete&rule=before&uid=";

    public static String add_rule_black_list = "http://82.145.38.202/sachin/pannel/api/add_rules.php?uid=";
    public static final String add_rule_MISSEDCALL = "http://82.145.38.202/sachin/pannel/api/add_rules.php?uid=";

    public static String soundLoad = "http://82.145.38.202/sachin/pannel/api/sound.php?uid=";
    public static String agentAPI = "http://82.145.38.202/sachin/pannel/api/agents_list.php?uid=";
    public static String ruleEditAPTBEFORE = "http://82.145.38.202/sachin/pannel/api/edit_rule.php?uid=";


    public static void createDialog(Context context, String msg) {
        android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(context);
        dialog.setTitle("Alert:");
        dialog.setMessage(msg);
        dialog.setIcon(R.drawable.alert);

        dialog.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                return;
            }
        });
        dialog.create().show();
    }

    public static void createDialogSuccess(Context context, String msg) {
        android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(context);
        dialog.setTitle("Success:");
        dialog.setMessage(msg);
        dialog.setIcon(R.drawable.correct);
        dialog.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                return;
            }
        });
        dialog.create().show();
    }

    public static final class AlertMsgs {
        public static final String noConncectionMsg = "No internet connection...";
        public static final String allFieldsMsg = "Please fill all fields...";
        public static final String noResultMsg = "No result found...";
        public static final String invalidCredentialMsg = "Invalid credentials...";
        public static final String registerbutNotActivated = " Account not Activated please Check Your Email ";
        public static final String SELECTAGENTS = " Can You Please Select the Agent";
        public static String Updated = "Updated Sucessfull";
        public static String Error = "Error";


        public static String EmailExistAlready = "Email  Already Exist ";
        public static String WentWrong = "Try After Some Time ";

        public static String errorMsg = "Try again & Check Conection";
        public static String ImageOrDOC = "Unsupported File \n Select DOC Or image  ";
        public static String selecFILE = "Select File to Upload";
        public static String alreadyaddded = "Agent already Exist";
        public static String success = "Congrats Agent Added Successfully ";
        public static String server = " Server is Down try after some time ";
    }


}
