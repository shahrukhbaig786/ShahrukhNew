package com.example.intel.virtual_receptionist.DBManager;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.SparseArray;

import com.example.intel.virtual_receptionist.URL.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by angstle on 7/27/2015.
 */
public class MainStorage {
    SQLiteDatabase db = null;
    Context context;
    JSONObject jsonObject, jsonObjectnew;
    JSONObject parentjsonobject;
    JSONArray jsonArray;

    String result;


    public MainStorage(Context context, JSONObject result) throws JSONException {
        this.context = context;


        db = context.openOrCreateDatabase(
                DBConstants.DB_NAME
                , SQLiteDatabase.CREATE_IF_NECESSARY
                , null
        );

        jsonObject = result;


        // parentjsonobject = result.getJSONObject("body");


    }


    public void CloseDB()
    {
        db.close();

    }

    public MainStorage(Context context) {
        this.context = context;
        db = context.openOrCreateDatabase(
                DBConstants.DB_NAME
                , SQLiteDatabase.CREATE_IF_NECESSARY
                , null
        );
    }

    /* public void addUserProfileData(String Company1, String Company_Type1, String PAN_Number1,
                                    String Industry1, String Staff1, String Revenue1, String Website1,
                                    String Address_Line11, String Address_Line21
             , String City1, String State1, String Country1, String Zip1,
                                    String Cp_Name1, String Cp_Designation1, String Cp_Email1, String Cp_Mobile1) {*/
    public long addUserProfileData() throws Exception {
        long isaffected = 0;
        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.TABLE_USER_DATA_PROFILE);
        db.execSQL(DBConstants.CREATE_TABLE_USER_PROFILE_DATA);
        jsonObjectnew = jsonObject.getJSONObject("msg");
        String Company = jsonObjectnew.getString("Company");
        String Company_Type = jsonObjectnew.getString("Company Type");
        String PAN_Number = jsonObjectnew.getString("PAN Number");
        String Industry = jsonObjectnew.getString("Industry");
        String Staff = jsonObjectnew.getString("Staff");
        String Revenue = jsonObjectnew.getString("Revenue");
        String Website = jsonObjectnew.getString("Website");
        String Address_Line1 = jsonObjectnew.getString("Address Line1");
        String Address_Line2 = jsonObjectnew.getString("Address Line2");
        String City = jsonObjectnew.getString("City");
        String State = jsonObjectnew.getString("State");
        String Country = jsonObjectnew.getString("Country");
        String Zip = jsonObjectnew.getString("Zip");
        String Cp_Name = jsonObjectnew.getString("Cp Name");
        String Cp_Designation = jsonObjectnew.getString("Cp Designation");
        String Cp_Email = jsonObjectnew.getString("Cp Email");
        String Cp_Mobile = jsonObjectnew.getString("Cp Mobile");
        String Id_uploaded = jsonObjectnew.getString("id_proof");
        String Id_proofname = jsonObjectnew.getString("id_proof_name");
        String Address_uploaaded = jsonObjectnew.getString("add_proof");
        String Address_proofname = jsonObjectnew.getString("add_proof_name");
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBConstants.USER_DATA_PROFILE_NAME, Cp_Name);
        contentValues.put(DBConstants.USER_DATA_PROFILE_DESIGNATION, Cp_Designation);
        contentValues.put(DBConstants.USER_DATA_PROFILE_EMAIL, Cp_Email);
        contentValues.put(DBConstants.USER_DATA_PROFILE_MOBILE, Cp_Mobile);
        contentValues.put(DBConstants.USER_DATA_PROFILE_COMPANY_NAME, Company);
        contentValues.put(DBConstants.USER_DATA_PROFILE_COMPANY_TYPE, Company_Type);
        contentValues.put(DBConstants.USER_DATA_PROFILE_PAN_NUMBER, PAN_Number);
        contentValues.put(DBConstants.USER_DATA_PROFILE_INDUSTRY_TYPE, Industry);
        contentValues.put(DBConstants.USER_DATA_PROFILE_STAFF_TYPE, Staff);
        contentValues.put(DBConstants.USER_DATA_PROFILE_REVENUE_TYPE, Revenue);
        contentValues.put(DBConstants.USER_DATA_PROFILE_WEBSITE, Website);
        contentValues.put(DBConstants.USER_DATA_PROFILE_ADDRESS_LINE1, Address_Line1);
        contentValues.put(DBConstants.USER_DATA_PROFILE_ADDRESS_LINE2, Address_Line2);
        contentValues.put(DBConstants.USER_DATA_PROFILE_CITY, City);
        contentValues.put(DBConstants.USER_DATA_PROFILE_STATE, State);
        contentValues.put(DBConstants.USER_DATA_PROFILE_ZIP, Zip);
        contentValues.put(DBConstants.USER_DATA_PROFILE_COUNTRY, Country);
        contentValues.put(DBConstants.USER_DATA_PROFILE_ID_PROFF_IMAGE, Id_uploaded);
        contentValues.put(DBConstants.USER_DATA_PROFILE_ID_PROFF_NAME, Id_proofname);
        contentValues.put(DBConstants.USER_DATA_PROFILE_ADD_PROFF_IMAGE, Address_uploaaded);
        contentValues.put(DBConstants.USER_DATA_PROFILE_ADD_PROFF_NAME, Address_proofname);


        //Yes Addtion of Data was done Successfully  now just add the data in Data
        isaffected = db.insert(DBConstants.TABLE_USER_DATA_PROFILE, null, contentValues);

        db.close();//2Feb modification
        return isaffected;
    }

    public long storecomapnyIndustId() throws Exception {
        return 0;
    }

    public long storeUserData() throws Exception {
        long affected = 0;
        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.TABLE_USER_DATA);
        db.execSQL(DBConstants.CREATE_TABLE_USER_DATA);
        String user_id = jsonObject.getString("userid");
        String user_username = jsonObject.getString("uername");
        String user_email = jsonObject.getString("email");
        String user_password = jsonObject.getString("password");
        String user_fullname = jsonObject.getString("name");
        String user_credits = jsonObject.getString("credits");
        String user_planId = jsonObject.getString("plan_id");
        String date_added = jsonObject.getString("date_added");

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBConstants.COL_USER_ID, user_id);
        contentValues.put(DBConstants.COL_USER_USERNAME, user_username);
        contentValues.put(DBConstants.COL_USER_EMAIL, user_email);
        contentValues.put(DBConstants.COL_USER_FULLNAME, user_fullname);
        contentValues.put(DBConstants.COL_USER_CREDITS, user_credits);
        contentValues.put(DBConstants.COL_USER_PLAN_ID, user_planId);
        contentValues.put(DBConstants.COL_USER_DATE_ADDED, date_added);
        contentValues.put(DBConstants.COL_USER_PASSWORD, user_password);
        affected = db.insert(DBConstants.TABLE_USER_DATA, null, contentValues);
        return affected;
    }

    public SparseArray<String> getUsernamePassword() {
        Cursor c = db.rawQuery("SELECT " + DBConstants.COL_USER_EMAIL + ", " + DBConstants.COL_PASSWORD + " FROM " + DBConstants.TABLE_USER, null);
        SparseArray<String> stringSparseArray = new SparseArray<>();
        if (c.moveToFirst()) {
            do {
                //assing values

                String column1 = c.getString(0);
                String column2 = c.getString(1);
                stringSparseArray.put(0, column1);
                stringSparseArray.put(1, column2);

            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return stringSparseArray;
    }

    public long storeindustry(String username, String password) {
        long rowId;

        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.COL_COMPANY_TYPE);
        db.execSQL(DBConstants.TABLE_COMPANY_TYPE_DATA);
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBConstants.COL_COMPANY_ID, username);
        contentValues.put(DBConstants.COL_COMPANY_TYPE, password);
        rowId = db.insert(DBConstants.COL_COMPANY_TYPE, null, contentValues);
        return rowId;
    }


    public long storecompany(String username, String password) {
        long rowId;

        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.COL_COMPANY_TYPE);
        db.execSQL(DBConstants.TABLE_COMPANY_TYPE_DATA);
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBConstants.COL_COMPANY_ID, username);
        contentValues.put(DBConstants.COL_COMPANY_TYPE, password);
        rowId = db.insert(DBConstants.COL_COMPANY_TYPE, null, contentValues);
        return rowId;
    }

    public long storeCredentials(String username, String password) {
        long rowId;

        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.TABLE_USER);
        db.execSQL(DBConstants.CREATE_TABLE_USER_CREDENTIALS);
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBConstants.COL_USER_EMAIL, username);
        contentValues.put(DBConstants.COL_PASSWORD, password);
        rowId = db.insert(DBConstants.TABLE_USER, null, contentValues);
        return rowId;
    }


    public void setFirstTime() {
        SharedPreferences preferences = context.getSharedPreferences(Urls.sharedPreferencesName, Context.MODE_PRIVATE);
        Editor edit = preferences.edit();
        edit.putBoolean("first_time", false);

        edit.commit();
    }


    public List<String> getProfileData() {
        List<String> userData = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM " + DBConstants.TABLE_USER_DATA_PROFILE, null);
        if (c.moveToFirst()) {
            do {
                String Cp_Name = c.getString(c.getColumnIndex(DBConstants.USER_DATA_PROFILE_NAME));
                String Cp_Designation = c.getString(c.getColumnIndex(DBConstants.USER_DATA_PROFILE_DESIGNATION));
                String Cp_Email = c.getString(c.getColumnIndex(DBConstants.USER_DATA_PROFILE_EMAIL));
                String Cp_Mobile = c.getString(c.getColumnIndex(DBConstants.USER_DATA_PROFILE_MOBILE));
                String Company = c.getString(c.getColumnIndex(DBConstants.USER_DATA_PROFILE_COMPANY_NAME));
                String Company_Type = c.getString(c.getColumnIndex(DBConstants.USER_DATA_PROFILE_COMPANY_TYPE));
                String PAN_Number = c.getString(c.getColumnIndex(DBConstants.USER_DATA_PROFILE_PAN_NUMBER));
                String Industry = c.getString(c.getColumnIndex(DBConstants.USER_DATA_PROFILE_INDUSTRY_TYPE));
                String Staff = c.getString(c.getColumnIndex(DBConstants.USER_DATA_PROFILE_STAFF_TYPE));
                String Revenue = c.getString(c.getColumnIndex(DBConstants.USER_DATA_PROFILE_REVENUE_TYPE));
                String Website = c.getString(c.getColumnIndex(DBConstants.USER_DATA_PROFILE_WEBSITE));
                String Address_Line1 = c.getString(c.getColumnIndex(DBConstants.USER_DATA_PROFILE_ADDRESS_LINE1));
                String Address_Line2 = c.getString(c.getColumnIndex(DBConstants.USER_DATA_PROFILE_ADDRESS_LINE2));
                String City = c.getString(c.getColumnIndex(DBConstants.USER_DATA_PROFILE_CITY));
                String State = c.getString(c.getColumnIndex(DBConstants.USER_DATA_PROFILE_STATE));
                String Zip = c.getString(c.getColumnIndex(DBConstants.USER_DATA_PROFILE_ZIP));
                String Country = c.getString(c.getColumnIndex(DBConstants.USER_DATA_PROFILE_COUNTRY));
                String id_ImageUplpaded = c.getString(c.getColumnIndex(DBConstants.USER_DATA_PROFILE_ID_PROFF_IMAGE));
                String id_proffName = c.getString(c.getColumnIndex(DBConstants.USER_DATA_PROFILE_ID_PROFF_NAME));
                String add_ImageUplpaded = c.getString(c.getColumnIndex(DBConstants.USER_DATA_PROFILE_ADD_PROFF_IMAGE));
                String add_proffName = c.getString(c.getColumnIndex(DBConstants.USER_DATA_PROFILE_ADD_PROFF_NAME));
                userData.add(Cp_Name);
                userData.add(Cp_Designation);
                userData.add(Cp_Email);
                userData.add(Cp_Mobile);
                userData.add(Company);
                userData.add(Company_Type);
                userData.add(PAN_Number);
                userData.add(Industry);
                userData.add(Staff);
                userData.add(Revenue);
                userData.add(Website);
                userData.add(Address_Line1);
                userData.add(Address_Line2);
                userData.add(City);
                userData.add(State);
                userData.add(Zip);
                userData.add(Country);
                userData.add(id_ImageUplpaded);
                userData.add(id_proffName);
                userData.add(add_ImageUplpaded);
                userData.add(add_proffName);
            }
            while (c.moveToNext());
        }
        c.close();
        db.close();
        return userData;
    }

    public List<String> getData() {
        {
            List<String> userData = new ArrayList<String>();
            Cursor c = db.rawQuery("SELECT * FROM " + DBConstants.TABLE_USER_DATA, null);
            if (c.moveToFirst()) {
                do {
                    String user_id = c.getString(c.getColumnIndex(DBConstants.COL_USER_ID));
                    String user_name = c.getString(c.getColumnIndex(DBConstants.COL_USER_USERNAME));
                    String user_email = c.getString(c.getColumnIndex(DBConstants.COL_USER_EMAIL));
                    String user_fullname = c.getString(c.getColumnIndex(DBConstants.COL_USER_FULLNAME));
                    String user_credits = c.getString(c.getColumnIndex(DBConstants.COL_USER_CREDITS));
                    String user_plan_id = c.getString(c.getColumnIndex(DBConstants.COL_USER_PLAN_ID));
                    String date_added = c.getString(c.getColumnIndex(DBConstants.COL_USER_DATE_ADDED));
                    String password = c.getString(c.getColumnIndex(DBConstants.COL_USER_PASSWORD));
                    userData.add(user_id);
                    userData.add(user_name);
                    userData.add(user_fullname);
                    userData.add(user_email);
                    userData.add(password);
                    userData.add(user_credits);

                    userData.add(user_plan_id);
                    userData.add(date_added);


                } while (c.moveToNext());
            }
            c.close();
            db.close();
            return userData;
        }
    }


    /*  public long storeVehicleData() throws Exception {
          long newRow = 0;
          db.execSQL("DROP TABLE IF EXISTS " + DBConstants.TABLE_VEHICLE);
          db.execSQL(DBConstants.CREATE_TABLE_VEHICLE);
          int length = jsonArray.length();
          for (int i = 0; i < length; i++) {
              JSONObject jsonObject = jsonArray.getJSONObject(i);
              String imei = jsonObject.getString("imei");
              String sim_num = jsonObject.getString("sim_num");
              String devactive = jsonObject.getString("devactive");
              String devname = jsonObject.getString("devname");
              String vnumber = jsonObject.getString("vnumber");
              String vmanufacturer = jsonObject.getString("vmanufacturer");
              String vmodel = jsonObject.getString("vmodel");
              String vtype = jsonObject.getString("vtype");
              String lat = jsonObject.getString("lat");
              String lon = jsonObject.getString("lon");
              String speed = jsonObject.getString("speed");
              String ignition = jsonObject.getString("ignition");
              String door = jsonObject.getString("door");
              String ac = jsonObject.getString("ac");
              String temperature = jsonObject.getString("temperature");
              String fuel = jsonObject.getString("fuel");
              String angle = jsonObject.getString("angle");
              String datatime = jsonObject.getString("datatime");
              ContentValues contentValues = new ContentValues();
              contentValues.put(DBConstants.COL_VEHICLE_IMEI, imei);
              contentValues.put(DBConstants.COL_VEHICLE_SIM, sim_num);
              contentValues.put(DBConstants.COL_VEHICLE_ACTIVATE, devactive);
              contentValues.put(DBConstants.COL_VEHICLE_NAME, devname);
              contentValues.put(DBConstants.COL_VEHICLE_NUMBER, vnumber);
              contentValues.put(DBConstants.COL_VEHICLE_MANUFACTURER, vmanufacturer);
              contentValues.put(DBConstants.COL_VEHICLE_MODEL, vmodel);
              contentValues.put(DBConstants.COL_VEHICLE_TYPE, vtype);
              contentValues.put(DBConstants.COL_VEHICLE_LAST_LATTITUDE, lat);
              contentValues.put(DBConstants.COL_VEHICLE_LAST_LONGITUDE, lon);
              contentValues.put(DBConstants.COL_VEHICLE_LAST_SPEED, speed);
              contentValues.put(DBConstants.COL_VEHICLE_LAST_IGNITION, ignition);
              contentValues.put(DBConstants.COL_VEHICLE_LAST_DOOR, door);
              contentValues.put(DBConstants.COL_VEHICLE_LAST_AC, ac);
              contentValues.put(DBConstants.COL_VEHICLE_LAST_TEMPERATURE, temperature);
              contentValues.put(DBConstants.COL_VEHICLE_LAST_FUEL, fuel);
              contentValues.put(DBConstants.COL_VEHICLE_LAST_ANGLE, angle);
              contentValues.put(DBConstants.COL_VEHICLE_DATA_TIME, datatime);
              newRow = db.insert(DBConstants.TABLE_VEHICLE, null, contentValues);

          }
          db.close();
          return newRow;
      }
      */
    public boolean getProfileDatainServer() {
        boolean data_in_server;
        SharedPreferences preferences = context.getSharedPreferences(DBConstants.preferencesName, Context.MODE_PRIVATE);
        data_in_server = preferences.getBoolean("data_in_server", false);
        return data_in_server;
    }

   /* public ArrayList<HashMap<String, String>> getVehicleData() {
        ArrayList<HashMap<String, String>> vehicleData = new ArrayList<HashMap<String, String>>();
        Cursor c = db.rawQuery("SELECT * FROM " + DBConstants.TABLE_VEHICLE, null);
        if (c.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                String imei = c.getString(c.getColumnIndex(DBConstants.COL_VEHICLE_IMEI));
                String vehicleNum = c.getString(c.getColumnIndex(DBConstants.COL_VEHICLE_NUMBER));
                String dateTime = c.getString(c.getColumnIndex(DBConstants.COL_VEHICLE_DATA_TIME));
                String activate = c.getString(c.getColumnIndex(DBConstants.COL_VEHICLE_ACTIVATE));
                String speed = c.getString(c.getColumnIndex(DBConstants.COL_VEHICLE_LAST_SPEED));
                String door = c.getString(c.getColumnIndex(DBConstants.COL_VEHICLE_LAST_DOOR));
                String ac = c.getString(c.getColumnIndex(DBConstants.COL_VEHICLE_LAST_AC));
                String lat = c.getString(c.getColumnIndex(DBConstants.COL_VEHICLE_LAST_LATTITUDE));
                String lng = c.getString(c.getColumnIndex(DBConstants.COL_VEHICLE_LAST_LONGITUDE));
                map.put("imei", imei);
                map.put("vnum", vehicleNum);
                map.put("datatime", dateTime);
                map.put("activate", activate);
                map.put("speed", speed);
                map.put("door", door);
                map.put("ac", ac);
                map.put("lat", lat);
                map.put("lng", lng);
                vehicleData.add(map);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return vehicleData;
    }*/

    public List<String> getUIdAndCompanyId() {
        List<String> list = new ArrayList<String>();
        Cursor c = db.rawQuery("SELECT " + DBConstants.COL_USER_ID + ", " + DBConstants.COL_COMPANY_ID + " FROM " + DBConstants.TABLE_USER_DATA, null);

        if (c.moveToFirst()) {
            do {
                list.add(c.getString(c.getColumnIndex(DBConstants.COL_USER_ID)));
                list.add(c.getString(c.getColumnIndex(DBConstants.COL_COMPANY_ID)));
            }
            while (c.moveToNext());
        }
        c.close();
        db.close();
        return list;

    }


    public List<String> getInformation() {
        List<String> list = new ArrayList<String>();
        Cursor c = db.rawQuery("SELECT " + DBConstants.COL_USER_CREDITS + ", "
                + DBConstants.COL_USER_EMAIL + ","
                + DBConstants.COL_USER_FULLNAME + ","
                + DBConstants.COL_USER_PLAN_ID
                + " FROM "
                + DBConstants.TABLE_USER, null);

        if (c.moveToFirst()) {
            do {
                list.add(c.getString(c.getColumnIndex(DBConstants.COL_USER_CREDITS)));
                list.add(c.getString(c.getColumnIndex(DBConstants.COL_USER_EMAIL)));
                list.add(c.getString(c.getColumnIndex(DBConstants.COL_USER_FULLNAME)));
                list.add(c.getString(c.getColumnIndex(DBConstants.COL_USER_PLAN_ID)));
            }
            while (c.moveToNext());
        }
        c.close();
        db.close();
        return list;

    }


    public String getUid() {
        String uid = "";
        Cursor c = db.rawQuery("SELECT " + DBConstants.COL_USER_ID + " FROM " + DBConstants.TABLE_USER_DATA, null);

        if (c.moveToFirst()) {
            do {
                uid = c.getString(c.getColumnIndex(DBConstants.COL_USER_ID));

            }
            while (c.moveToNext());
        }
        c.close();
        db.close();
        return uid;

    }

    public void setDbStatus() {
        SharedPreferences preferences = context.getSharedPreferences(Urls.sharedPreferencesName, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putBoolean("login", true);
        editor.apply();

    }

    public boolean getDbStatus() {
        boolean login;
        SharedPreferences preferences = context.getSharedPreferences(Urls.sharedPreferencesName, Context.MODE_PRIVATE);
        login = preferences.getBoolean("login", false);

        return login;


    }

    public static boolean doesDatabaseExist(Context con) {
        File dbFile = con.getDatabasePath(DBConstants.DB_NAME);
        return dbFile.exists();
    }


}
