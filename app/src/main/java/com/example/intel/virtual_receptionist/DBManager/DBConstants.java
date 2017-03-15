package com.example.intel.virtual_receptionist.DBManager;

/**
 * Created by angstle on 7/27/2015.
 */
public class DBConstants {

    public static final String preferencesName = "virtual_receptionist";

    public static final String DB_NAME = "virtual_receptionist.db";
    public static final String TABLE_USER = "user";
    public static final String TABLE_USER_DATA = "user_data";
    public static final String TABLE_USER_DATA_PROFILE = "user_data_profile";
    public static final String COL_USER_DATE_ADDED = "date_added";
    public static final String COL_PRIMARY_KEY = "  id";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_USER_USERNAME = "user_username";
    public static final String COL_USER_EMAIL = "user_email";
    public static final String COL_USER_FULLNAME = "user_fullname";
    public static final String COL_USER_CREDITS = "user_credits";
    public static final String COL_USER_PLAN_ID = "user_plan_id";
    public static final String COL_PASSWORD = "password";

    //TABLE COMPANY HAVE BEEN DECLARED HERE
    public static final String TABLE_COMPANY_TYPE = "company_type";
    public static final String TABLE_COMPANY_TYPE_DATA = "company_type_data";
    public static final String COL_COMPANY_ID = "company_id";
    public static final String COL_COMPANY_TYPE = "company_type_name";

//TABLE INDUSTRY TYPE HAVE BEEN DECLARE HERE

    public static final String TABLE_INDUSTRY_TYPE = "industry_type";
    public static final String TABLE_INDUSTRY_TYPE_DATA = "industry_type_data";
    public static final String COL_INDUSTRY_ID = "industry_id";
    public static final String COL_INDUSTRY_TYPE = "industry_type_name";


    public static final String TAG_COMPANY_TYPE = "type";
    public static final String TAG_COUNTRY_NAME_LIST = "short_name";
    public static final String TAG_COMPANY_ID = "id";
    public static final String TAG_INDUSTRY_ID = "id";
    public static final String TAG_CONTRY_ID = "id";


    public static final String TAG_INDUSTRY_NAME = "industry";


    public static final String TAG_NAME = "name";
    public static final String TAG_COURSE = "course";
    public static final String TAG_SESSION = "session";


    public static final String COUNTRY_TABLE = "mapp_country";
    public static final String COUNTRY_ID = "id";
    public static final String COUNTRY_ISO2 = "iso2";
    public static final String COUNTRY_SHORT_NAME = "short_name";
    public static final String COUNTRY_lONG_NAME = "long_name";
    public static final String COUNTRY_ISO3 = "iso3";
    public static final String COUNTRY_NUM_CODE = "numcode";
    public static final String COUNTRY_UN_MEMBER = "un_member";
    public static final String COUNTRY_CALLING_CODE = "calling_code";
    public static final String COUNTRY_CCTLD = "cctld";


    public static final String USER_DATA_PROFILE_NAME = "Cp_Name";
    public static final String USER_DATA_PROFILE_DESIGNATION = "Cp_Designation";
    public static final String USER_DATA_PROFILE_EMAIL = "Cp_Email";
    public static final String USER_DATA_PROFILE_MOBILE = "Cp_Mobile";
    public static final String USER_DATA_PROFILE_COMPANY_NAME = "Company";
    public static final String USER_DATA_PROFILE_COMPANY_TYPE = "Company_Type";
    public static final String USER_DATA_PROFILE_PAN_NUMBER = "PAN_Number";
    public static final String USER_DATA_PROFILE_INDUSTRY_TYPE = "Industry";
    public static final String USER_DATA_PROFILE_STAFF_TYPE = "Staff";
    public static final String USER_DATA_PROFILE_REVENUE_TYPE = "Revenue";
    public static final String USER_DATA_PROFILE_WEBSITE = "Website";
    public static final String USER_DATA_PROFILE_ADDRESS_LINE1 = "Address_Line1";
    public static final String USER_DATA_PROFILE_ADDRESS_LINE2 = "Address_Line2";
    public static final String USER_DATA_PROFILE_CITY = "City";
    public static final String USER_DATA_PROFILE_STATE = "State";
    public static final String USER_DATA_PROFILE_ZIP = "Zip";
    public static final String USER_DATA_PROFILE_COUNTRY = "Country";
    public static final String USER_DATA_PROFILE_ADD_PROFF_IMAGE ="add_proof" ;
    public static final String USER_DATA_PROFILE_ADD_PROFF_NAME="add_proof_name";

    public static String USER_DATA_PROFILE_ID_PROFF_IMAGE="id_proof";
    public static String USER_DATA_PROFILE_ID_PROFF_NAME="id_proof_name";
    public static final String COL_USER_PASSWORD = "password";
    public static final String CREATE_TABLE_USER_DATA = "CREATE TABLE IF NOT EXISTS " + DBConstants.TABLE_USER_DATA + " ("
            + DBConstants.COL_PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DBConstants.COL_USER_ID + " INTEGER,"
            + DBConstants.COL_USER_EMAIL + " TEXT ,"
            + DBConstants.COL_USER_USERNAME + " TEXT,"
            + DBConstants.COL_USER_CREDITS + " TEXT, "
            + DBConstants.COL_USER_PLAN_ID + " TEXT, "
            + DBConstants.COL_USER_DATE_ADDED + " TEXT, "
            + DBConstants.COL_USER_PASSWORD + " TEXT, "
            + DBConstants.COL_USER_FULLNAME + " TEXT);";
    public static final String CREATE_TABLE_USER_CREDENTIALS =
            "CREATE TABLE IF NOT EXISTS " + DBConstants.TABLE_USER + " ("
                    + DBConstants.COL_PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + DBConstants.COL_USER_EMAIL + " TEXT ,"
                    + DBConstants.COL_PASSWORD + " TEXT);";

    public static final String CREATE_TABLE_USER_PROFILE_DATA = "CREATE TABLE IF NOT EXISTS " + DBConstants.TABLE_USER_DATA_PROFILE + " ("
            + DBConstants.COL_PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DBConstants.USER_DATA_PROFILE_NAME + " TEXT ,"
            + DBConstants.USER_DATA_PROFILE_DESIGNATION + " TEXT,"
            + DBConstants.USER_DATA_PROFILE_EMAIL + " TEXT, "
            + DBConstants.USER_DATA_PROFILE_MOBILE + " TEXT, "
            + DBConstants.USER_DATA_PROFILE_COMPANY_NAME + " TEXT, "
            + DBConstants.USER_DATA_PROFILE_COMPANY_TYPE + " TEXT, "
            + DBConstants.USER_DATA_PROFILE_PAN_NUMBER + " TEXT, "
            + DBConstants.USER_DATA_PROFILE_INDUSTRY_TYPE + " TEXT, "
            + DBConstants.USER_DATA_PROFILE_STAFF_TYPE + " TEXT, "
            + DBConstants.USER_DATA_PROFILE_REVENUE_TYPE + " TEXT, "
            + DBConstants.USER_DATA_PROFILE_WEBSITE + " TEXT,"
            + DBConstants.USER_DATA_PROFILE_ADDRESS_LINE1 + " TEXT,"
            + DBConstants.USER_DATA_PROFILE_ADDRESS_LINE2 + " TEXT,"
            + DBConstants.USER_DATA_PROFILE_CITY + " TEXT,"
            + DBConstants.USER_DATA_PROFILE_STATE + " TEXT,"
            + DBConstants.USER_DATA_PROFILE_COUNTRY + " TEXT,"
            + DBConstants.USER_DATA_PROFILE_ID_PROFF_IMAGE + " TEXT,"
            + DBConstants.USER_DATA_PROFILE_ID_PROFF_NAME + " TEXT,"
            + DBConstants.USER_DATA_PROFILE_ADD_PROFF_IMAGE+ " TEXT,"
            + DBConstants.USER_DATA_PROFILE_ADD_PROFF_NAME+ " TEXT,"
            + DBConstants.USER_DATA_PROFILE_ZIP + " TEXT);";

    public static final String CREATE_TABLE_COMPANY = "CREATE TABLE IF NOT EXISTS " + DBConstants.TABLE_COMPANY_TYPE + " ("
            + DBConstants.COL_PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DBConstants.COL_COMPANY_ID + " TEXT,"
            + DBConstants.COL_COMPANY_TYPE + " TEXT ,";


    public static final String CREATE_TABLE_INDUSTRY = "CREATE TABLE IF NOT EXISTS " + DBConstants.TABLE_INDUSTRY_TYPE + " ("
            + DBConstants.COL_PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DBConstants.COL_INDUSTRY_ID + " TEXT,"
            + DBConstants.COL_INDUSTRY_TYPE + " TEXT ,";
    /* public static final String CREATE_TABLE_COUNTRY = "CREATE TABLE IF NOT EXISTS " + DBConstants.TABLE_COUNTRY_TYPE + " ("
             + DBConstants.COL_PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT,"
             + DBConstants.COL_COUNTRY_ID + " TEXT,"
             + DBConstants.COL_COUNTRY_TYPE + " TEXT ,"
             ;*/
    public static final String CREATE_TABLE_COUNTRY = "CREATE TABLE IF NOT EXISTS " + COUNTRY_TABLE + "("

            + COUNTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COUNTRY_ISO2 + " TEXT, "
            + COUNTRY_SHORT_NAME + " TEXT, "
            + COUNTRY_lONG_NAME + " TEXT, "
            + COUNTRY_ISO3 + " TEXT, "
            + COUNTRY_NUM_CODE + " TEXT, "
            + COUNTRY_UN_MEMBER + " TEXT, "
            + COUNTRY_CALLING_CODE + " TEXT, "
            + COUNTRY_CCTLD + " TEXT);";





    /*public void addUser(String Company,String Company_Type,String PAN_Number,
                        String Industry,String Staff,String Revenue,String Website,
                        String Address_Line1,String Address_Line2
            ,String City,String State,String Country,String Zip,
                        String Cp_Name,String Cp_Designation,String Cp_Email,String Cp_Mobile) {*/
}
