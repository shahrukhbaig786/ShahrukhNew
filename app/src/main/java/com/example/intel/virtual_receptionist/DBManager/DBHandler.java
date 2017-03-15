package com.example.intel.virtual_receptionist.DBManager;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by angstle on 12/8/2015.
 */
public class DBHandler extends SQLiteOpenHelper {

	boolean sendBroadCast;
	public static final String DB_NAME = "marketing.db";
	public static final String preferencesName = "marketing";

	//Contacts Table
	public static final String CONTACTS_TABLE = "mapp_contacts";
	public static final String C_ID = "id";
	public static final String C_NAME = "name";
	public static final String C_EMAIL = "email";
	public static final String C_DOB = "dob";
	public static final String C_ADDRESS = "address";
	public static final String C_MOBILE = "mobile";
	public static final String C_CITY = "city";
	public static final String C_STATE = "state";
	public static final String C_COUNTRY = "country";
	public static final String C_ANIVERSARY = "aniversary";
	public static final String C_IMAGE = "encodedImage";
	public static final String C_COMPANY = "company";
	public static final String C_UID = "uid";
	public static final String C_REMOTE_ID = "remote_id";



	//group table
	public static final String GROUP_TABLE = "mapp_group";
	public static final String G_ID = "group_id";
	public static final String G_NAME = "group_name";
	public static final String G_UIDS = "group_user_ids";
	public static final String G_STATUS = "group_status";
	public static final String G_DES = "group_description";
	public static final String G_ICON = "group_icon";
	public static final String G_REMOTE_ID = "remote_id";


	public static final int DB_VERSION = 1;



	public static final String CREATE_TABLE_CONTACTS = "CREATE TABLE IF NOT EXISTS " + CONTACTS_TABLE + " ("
			+ C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ C_NAME + " TEXT,"
			+ C_EMAIL + " TEXT ,"
			+ C_DOB + " TEXT, "
			+ C_ADDRESS + " TEXT,"
			+ C_MOBILE + " TEXT ,"
			+ C_CITY + " TEXT, "
			+ C_STATE + " TEXT,"
			+ C_COUNTRY + " TEXT ,"
			+ C_ANIVERSARY + " TEXT, "
			+ C_IMAGE + " TEXT,"
			+ C_COMPANY + " TEXT,"
			+ C_REMOTE_ID + " TEXT,"
			+ C_UID + " TEXT);";

	public static final String CREATE_TABLE_GROUP = "CREATE TABLE IF NOT EXISTS " + GROUP_TABLE + " ("
			+ G_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ G_NAME + " TEXT,"
			+ G_STATUS + " TEXT,"
			+ G_DES + " TEXT,"
			+ G_ICON + " TEXT,"
			+ G_REMOTE_ID + " INTEGER,"
			+ G_UIDS + " TEXT);";


	//user table
	public static final String USER_TABLE = "mapp_users";
	public static final String MAIN_USER_ID_PRIMARY = "id";
	public static final String MAIN_USER_ID = "users_id";
	public static final String MAIN_USER_EMAIL = "email";
	public static final String MAIN_USER_CONTACT = "contact";
	public static final String MAIN_USER_NAME = "name";
	public static final String MAIN_USER_COMPANY = "company";
	public static final String MAIN_USER_ADDRESS = "address";
	public static final String MAIN_USER_CITY = "city";
	public static final String MAIN_USER_STATE = "state";
	public static final String MAIN_USER_COUNTRY = "country";
	public static final String MAIN_USER_CREDITS = "credits";
	public static final String MAIN_USER_TYPE = "type";
	public static final String MAIN_USER_PROFILE_PIC = "profile_pic";
	public static final String MAIN_USER_PASSWORD = "password";
	public static final String MAIN_USER_USERNAME = "username";
	public static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS " + USER_TABLE + "("

			+ MAIN_USER_ID_PRIMARY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ MAIN_USER_ID + " INTEGER, "
			+ MAIN_USER_USERNAME + " TEXT, "
			+ MAIN_USER_NAME + " TEXT, "
			+ MAIN_USER_EMAIL + " TEXT, "
			+ MAIN_USER_CONTACT + " TEXT, "
			+ MAIN_USER_COMPANY + " TEXT, "
			+ MAIN_USER_ADDRESS + " TEXT, "
			+ MAIN_USER_CITY + " TEXT , "
			+ MAIN_USER_STATE + " TEXT, "
			+ MAIN_USER_COUNTRY + " TEXT, "
			+ MAIN_USER_CREDITS + " TEXT, "
			+ MAIN_USER_PROFILE_PIC + " TEXT, "
			+ MAIN_USER_PASSWORD + " TEXT, "
			+ MAIN_USER_TYPE + " TEXT);";




	//email settings table
	public static final String EMAIL_SETTINGS_TABLE = "email_settings";
	public static final String EMAIL_PRIMARY_ID = "id";
	public static final String EMAIL_SERVER = "server";
	public static final String EMAIL_PORT = "port";
	public static final String EMAIL_USERNAME = "username";
	public static final String EMAIL_PASSWORD = "password";
	public static final String EMAIL_FROM_NAME = "from_name";
	public static final String EMAIL_FROM_EMAIL = "from_email";
	public static final String EMAIL_SMTP_AUTH = "smtp_auth";
	public static final String EMAIL_SMTP_SECURE = "smpt_secure";
	public static final String EMAIL_SMTP_REMOTE_ID = "remote_id";
	public static final String EMAIL_SMTP_TEST_EMAIL = "test_email";

	public static final String CREATE_TABLE_EMAIL_SETTINGS = "CREATE TABLE IF NOT EXISTS " + EMAIL_SETTINGS_TABLE +
			"("
			+ EMAIL_PRIMARY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ EMAIL_SERVER + " TEXT, "
			+ EMAIL_PORT + " INTEGER, "
			+ EMAIL_USERNAME + " TEXT, "
			+ EMAIL_PASSWORD + " TEXT, "
			+ EMAIL_FROM_NAME + " TEXT, "
			+ EMAIL_FROM_EMAIL + " TEXT, "
			+ EMAIL_SMTP_AUTH + " TEXT, "
			+ EMAIL_SMTP_SECURE + " TEXT, "
			+ EMAIL_SMTP_REMOTE_ID + " TEXT, "
			+ EMAIL_SMTP_TEST_EMAIL + " TEXT"
			+");";



//country table
	public static final String COUNTRY_TABLE = "mapp_country";


	public static final String COUNTRY_ID  = "id";
	public static final String COUNTRY_ISO2  = "iso2";
	public static final String COUNTRY_SHORT_NAME  = "short_name";
	public static final String COUNTRY_lONG_NAME  = "long_name";
	public static final String COUNTRY_ISO3  = "iso3";
	public static final String COUNTRY_NUM_CODE  = "numcode";
	public static final String COUNTRY_UN_MEMBER  = "un_member";
	public static final String COUNTRY_CALLING_CODE  = "calling_code";
	public static final String COUNTRY_CCTLD  = "cctld";

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


	//sms senderids table not used yet. to be used in next version
	public static final String SENDERIDS_TABLE = "sender_id";
	public static final String SMS_SENDER_ID_PKEY = "id";
	public static final String SMS_SENDER_ID_REMOTE_ID = "remote_id";
	public static final String SMS_SENDER_ID_REAL = "real_sender_id";
	public static final String SMS_SENDERID_APPROVED = "activate";
	public static final String SMS_SENDERID_DATE_ADDED = "date_added";



	public static final String CREATE_TABLE_SENDERIDS = "CREATE TABLE IF NOT EXISTS " + SENDERIDS_TABLE + "("

			+ SMS_SENDER_ID_PKEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ SMS_SENDER_ID_REMOTE_ID + " TEXT, "
			+ SMS_SENDER_ID_REAL + " TEXT, "
			+ SMS_SENDERID_APPROVED + " TEXT, "
			+ SMS_SENDERID_DATE_ADDED + " TEXT);";



	//greetings table
	public static final String GREETINGS_TABLE = "greetings";
	public static final String GREETING_ID = "id";
	public static final String GREETING_REMOTE_ID = "remote_id";
	public static final String GREETING_TYPE = "type";
	public static final String GREETING_TITLE = "name";

	public static final String CREATE_GREETINGS_TABLE = "CREATE TABLE IF NOT EXISTS " + GREETINGS_TABLE + "("
			+ GREETING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ GREETING_REMOTE_ID + " TEXT, "
			+ GREETING_TYPE + " TEXT, "
			+ GREETING_TITLE + " TEXT);";


	//notification table
	public static final String NOTIFICATION_TABLE = "notifications";
	public static final String NOTIFICATION_ID = "id";
	public static final String NOTIFICATION_DATA = "data";
	public static final String NOTIFICATION_TITLE = "type";
	public static final String NOTIFICATION_PRIORITY = "priority";
	public static final String NOTIFICATION_READ = "read";

	public static final String CREATE_NOTIFICATION_TABLE = "CREATE TABLE IF NOT EXISTS "+NOTIFICATION_TABLE + "("
			+ NOTIFICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ NOTIFICATION_DATA + " TEXT, "
			+ NOTIFICATION_TITLE + " TEXT, "
			+ NOTIFICATION_PRIORITY + " INTEGER, "
			+ NOTIFICATION_READ + " INTEGER);";


	Context con;

	public DBHandler(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		con = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_USER);
		db.execSQL(CREATE_TABLE_CONTACTS);
		db.execSQL(CREATE_TABLE_GROUP);
		db.execSQL(CREATE_TABLE_EMAIL_SETTINGS);
		db.execSQL(CREATE_TABLE_COUNTRY);
		db.execSQL(CREATE_TABLE_SENDERIDS);
		db.execSQL(CREATE_GREETINGS_TABLE);
		db.execSQL(CREATE_NOTIFICATION_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_USER);
		db.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + GROUP_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + EMAIL_SETTINGS_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + COUNTRY_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + SENDERIDS_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + GREETINGS_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + NOTIFICATION_TABLE);
		onCreate(db);
	}

	public void addContact(Bundle bd) {
		String name, email, dob, address, mobile, city, state, country, aniversary, pic = "0", uid;
		name = bd.getString("name");
		email = bd.getString("email");
		dob = bd.getString("dob");
		address = bd.getString("address");
		mobile = bd.getString("mobile");
		city = bd.getString("city");
		state = bd.getString("state");
		country = bd.getString("country");
		aniversary = bd.getString("aniversary");
		pic = bd.getString("pic");
		ContentValues values = new ContentValues();
		values.put(C_NAME, name);
		values.put(C_EMAIL, email);
		values.put(C_DOB, dob);
		values.put(C_ADDRESS, address);
		values.put(C_MOBILE, mobile);
		values.put(C_CITY, city);
		values.put(C_STATE, state);
		values.put(C_COUNTRY, country);
		values.put(C_ANIVERSARY, aniversary);
		values.put(C_IMAGE, pic);
		values.put(C_UID, getUserId());
		SQLiteDatabase db = this.getWritableDatabase();
		db.insert(CONTACTS_TABLE, null, values);
		db.close();
	}


	/**
	 *
	 * @param params
	 * @return insertedRowId
	 */
	public int updateProfile(String... params) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(MAIN_USER_NAME, params[0]);
		values.put(MAIN_USER_EMAIL, params[1]);
		values.put(MAIN_USER_CONTACT, params[2]);
		values.put(MAIN_USER_ADDRESS, params[3]);
		values.put(MAIN_USER_CITY, params[4]);
		values.put(MAIN_USER_STATE, params[5]);
		values.put(MAIN_USER_COUNTRY, params[6]);
		values.put(MAIN_USER_COMPANY, params[7]);
		values.put(MAIN_USER_TYPE, params[8]);
		int insertedRowCount =  db.update(USER_TABLE, values, MAIN_USER_ID + " = " + params[9], null);
		db.close();
		Log.e("inserted row", insertedRowCount+"");
		if(insertedRowCount != 0)
		{
			sendBroadCast = true;
		}
		else
		{
			sendBroadCast = false;
		}

		return insertedRowCount;
	}


	/**
	 *
	 * @param picPath
	 * @param id
	 */

	public void updateProfilePic(String picPath, String id) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(MAIN_USER_PROFILE_PIC, picPath);
		 db.update(USER_TABLE, values, MAIN_USER_ID + " = " + id, null);
		Log.e("profile pic", "updated");
		db.close();

	}


	/**
	 *
	 * @param id
	 */
	public void deleteContact(String id) {
		SQLiteDatabase database = this.getWritableDatabase();

			database.delete(CONTACTS_TABLE, C_REMOTE_ID + "=" + id, null);
		database.close();
	}

	/**
	 *
	 * @param id
	 */
	public void deleteSenderId(String id) {
		SQLiteDatabase database = this.getWritableDatabase();
		int deleted = database.delete(SENDERIDS_TABLE, SMS_SENDER_ID_REMOTE_ID + "=" + id, null);
		Log.e("deleted", deleted + "");
		database.close();
	}

	/**
	 *
	 * @param id
	 */
	public void deleteGroup(String id) {
		SQLiteDatabase database = this.getWritableDatabase();

			database.delete(GROUP_TABLE, G_REMOTE_ID + "=" + id, null);
		database.close();
	}

	/**
	 *
	 * @param jsonArray
	 * @throws JSONException
	 */
	public void addAllContacts(JSONArray jsonArray) throws JSONException {
		Log.e("starting db", "started 1");

		SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
		sqLiteDatabase.delete(CONTACTS_TABLE, "1", null);
		Log.e("starting db", "started 2");
		int length = jsonArray.length();
		Log.e("starting db", "started 3");
		String uid = getUserId();
		for (int i = 0; i < length; i++) {
			JSONObject item = jsonArray.getJSONObject(i);
			String name = item.getString("name");
			String email = item.getString("email");
			String dob = item.getString("dob");
			String address = item.getString("address");
			String mobile = item.getString("mobile");
			String city = item.getString("city");
			String state = item.getString("state");
			String country = item.getString("country");
			String aniversary = item.getString("anniversary");
			String encodedImage = item.getString("pic");
			String id = item.getString("id");
			String company = item.getString("company");
			ContentValues values = new ContentValues();
			values.put(C_NAME, name);
			values.put(C_EMAIL, email);
			values.put(C_DOB, dob);
			values.put(C_ADDRESS, address);
			values.put(C_MOBILE, mobile);
			values.put(C_CITY, city);
			values.put(C_STATE, state);
			values.put(C_COUNTRY, country);
			values.put(C_ANIVERSARY, aniversary);
			values.put(C_IMAGE, encodedImage);
			values.put(C_COMPANY, company);
			values.put(C_REMOTE_ID, id);
			values.put(C_UID, uid);
			sqLiteDatabase.insert(CONTACTS_TABLE, null, values);
			Log.e("starting db", "inserted");

		}
		sqLiteDatabase.close();
	}


	/**
	 *
	 * @param userId
	 * @param name
	 * @param email
	 * @param contact
	 * @param credit
	 * @param address
	 * @param city
	 * @param state
	 * @param country
	 * @param company
	 * @param type
	 * @param password
	 * @param username
	 * @param pic
	 */
	public void addUser(String userId, String name,
						String email, String contact, String credit,
						String address, String city, String state, String country,
						String company, String type, String password, String username, String pic) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(MAIN_USER_ID, userId);
		values.put(MAIN_USER_NAME, name);
		values.put(MAIN_USER_EMAIL, email);
		values.put(MAIN_USER_CONTACT, contact);
		values.put(MAIN_USER_CREDITS, credit);
		values.put(MAIN_USER_ADDRESS, address);
		values.put(MAIN_USER_CITY, city);
		values.put(MAIN_USER_STATE, state);
		values.put(MAIN_USER_COUNTRY, country);
		values.put(MAIN_USER_COMPANY, company);
		values.put(MAIN_USER_TYPE, type);
		values.put(MAIN_USER_PASSWORD, password);
		values.put(MAIN_USER_USERNAME, username);
		values.put(MAIN_USER_PROFILE_PIC, pic);
		long rowID = db.insert(USER_TABLE, null, values);
		db.close();
		if(rowID != -1)
		{
			sendBroadCast = true;
		}
		else
		{
			sendBroadCast = false;
		}

	}


	/**
	 *
	 * @return contactsArrayList
	 */
	public ArrayList<HashMap<String, String>> getAllContacts() {
		ArrayList<HashMap<String, String>> contactsArrayList = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + CONTACTS_TABLE, null);
		if (c.moveToFirst()) {

			do {
				HashMap<String, String> values = new HashMap<String, String>();
				values.put(C_NAME, c.getString(c.getColumnIndex(C_NAME)));
				values.put(C_EMAIL, c.getString(c.getColumnIndex(C_EMAIL)));
				values.put(C_MOBILE, c.getString(c.getColumnIndex(C_MOBILE)));
				values.put(C_IMAGE, c.getString(c.getColumnIndex(C_IMAGE)));
				values.put(C_UID, c.getString(c.getColumnIndex(C_UID)));
				values.put(C_REMOTE_ID, c.getString(c.getColumnIndex(C_REMOTE_ID)));
				Log.e("state", c.getString(c.getColumnIndex(C_STATE)));
				contactsArrayList.add(values);


			} while (c.moveToNext());
		}
		c.close();
		db.close();
		Log.e("contacts array list", "Size" + contactsArrayList.size());
		return contactsArrayList;
	}

	/**
	 *
	 * @return userProfileData
	 */
	public List<String> getProfile() {
		List<String> profileList = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + USER_TABLE, null);
		if (c.moveToFirst()) {
				String name = c.getString(c.getColumnIndex(MAIN_USER_NAME));
				String mobile = c.getString(c.getColumnIndex(MAIN_USER_CONTACT));
				String picUrl = c.getString(c.getColumnIndex(MAIN_USER_PROFILE_PIC));
				String type = c.getString(c.getColumnIndex(MAIN_USER_TYPE));
				String company = c.getString(c.getColumnIndex(MAIN_USER_COMPANY));
				String address = c.getString(c.getColumnIndex(MAIN_USER_ADDRESS));
				String credit = c.getString(c.getColumnIndex(MAIN_USER_CREDITS));
				String city = c.getString(c.getColumnIndex(MAIN_USER_CITY));
				String state = c.getString(c.getColumnIndex(MAIN_USER_STATE));
				String country = c.getString(c.getColumnIndex(MAIN_USER_COUNTRY));
				String id = c.getString(c.getColumnIndex(MAIN_USER_ID));
				String username =  c.getString(c.getColumnIndex(MAIN_USER_USERNAME));
				String email =  c.getString(c.getColumnIndex(MAIN_USER_EMAIL));
				profileList.add(name);  //0
				profileList.add(mobile); //1
				profileList.add(type);  //2
				profileList.add(company); //3
				profileList.add(address);  //4
				profileList.add(city); //5
				profileList.add(state); //6
				profileList.add(country); //7
				profileList.add(credit); //8
				profileList.add(picUrl); //9
				profileList.add(id); //10
				profileList.add(username);
				profileList.add(email);
		}
		c.close();
		db.close();
		return profileList;
	}

	/**
	 *
	 * @return loggedInUserID
	 */
	public String getUserId() {
		String userID = "";
		SharedPreferences preferences = con.getSharedPreferences(DBHandler.preferencesName, Context.MODE_PRIVATE);
		userID = preferences.getString("uid", "0");
		return userID;
	}
	/**
	 *
	 * @return totalGroups
	 */
	public int getTotalGroups() {
		int totalGroups = 0;
		SQLiteDatabase db = getReadableDatabase();
		String countQuery = "SELECT * FROM " + GROUP_TABLE;
		Cursor cursor = db.rawQuery(countQuery, null);
		totalGroups = cursor.getCount();
		Log.e("cursor count", totalGroups + "");
		cursor.close();
		db.close();
		return totalGroups;
	}

	/**
	 *
	 * @return totalContacts
	 */
	public int getTotalContacts() {
		int totalContacts = 0;
		SQLiteDatabase db = getReadableDatabase();
		String countQuery = "SELECT  * FROM " + CONTACTS_TABLE;
		Cursor cursor = db.rawQuery(countQuery, null);
		totalContacts = cursor.getCount();
		Log.e("cursor count", totalContacts + "");
		cursor.close();
		db.close();
		return totalContacts;
	}

	/**
	 *
	 * @param jsonArray
	 * @throws JSONException
	 */
	public void addAllGroups(JSONArray jsonArray) throws JSONException {
		SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
		sqLiteDatabase.delete(GROUP_TABLE, "1", null);

		int length = jsonArray.length();
		for (int i = 0; i < length; i++) {
			JSONObject item = jsonArray.getJSONObject(i);
			String name = item.getString("name");
			String status = item.getString("status");
			String des = item.getString("des");
			String icon = item.getString("icon");
			String gid = item.getString("gid");
			String clids = item.getString("clids");
			Log.e("inserting db", icon);
			ContentValues values = new ContentValues();
			values.put(G_NAME, name);
			values.put(G_STATUS, status);
			values.put(G_DES, des);
			values.put(G_ICON, icon);
			values.put(G_REMOTE_ID, Integer.valueOf(gid));
			values.put(G_UIDS, clids);
			sqLiteDatabase.insert(GROUP_TABLE, null, values);


		}
		sqLiteDatabase.close();
	}

	/**
	 *
	 * @return groupList
	 */
	public ArrayList<HashMap<String, String>> getAllGroups() {
		ArrayList<HashMap<String, String>> groupsList = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + GROUP_TABLE, null);
		if (c.moveToFirst()) {

			do {
				HashMap<String, String> values = new HashMap<String, String>();
				values.put(G_NAME, c.getString(c.getColumnIndex(G_NAME)));
				values.put(G_STATUS, c.getString(c.getColumnIndex(G_STATUS)));
				values.put(G_DES, c.getString(c.getColumnIndex(G_DES)));
				values.put(G_ICON, c.getString(c.getColumnIndex(G_ICON)));
				values.put(G_REMOTE_ID, c.getString(c.getColumnIndex(G_REMOTE_ID)));
				values.put(G_UIDS, c.getString(c.getColumnIndex(G_UIDS)));
				groupsList.add(values);
			} while (c.moveToNext());
		}
		c.close();
		db.close();

		return groupsList;
	}

	/**
	 *
	 * @return activeGroupList
	 */
	public ArrayList<HashMap<String, String>> getAllActiveGroups() {
		ArrayList<HashMap<String, String>> groupsList = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + GROUP_TABLE + " where "+ G_STATUS +" = ?", new String[]{"1"});
		if (c.moveToFirst()) {

			do {
				HashMap<String, String> values = new HashMap<String, String>();
				values.put(G_NAME, c.getString(c.getColumnIndex(G_NAME)));
				values.put(G_STATUS, c.getString(c.getColumnIndex(G_STATUS)));
				values.put(G_DES, c.getString(c.getColumnIndex(G_DES)));
				values.put(G_ICON, c.getString(c.getColumnIndex(G_ICON)));
				values.put(G_REMOTE_ID, c.getString(c.getColumnIndex(G_REMOTE_ID)));
				values.put(G_UIDS, c.getString(c.getColumnIndex(G_UIDS)));
				groupsList.add(values);
			} while (c.moveToNext());
		}
		c.close();
		db.close();

		return groupsList;
	}


	/**
	 *
 	 * @param remoteID
	 * @return groupById
	 */
	public ArrayList<String> getGroup(String remoteID) {
		ArrayList<String> groupsList = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + GROUP_TABLE + " where " + G_REMOTE_ID + "=?", new String[]{remoteID});
		if (c.moveToFirst()) {

			do {
				String name = c.getString(c.getColumnIndex(G_NAME));
				String status = c.getString(c.getColumnIndex(G_STATUS));
				String des =  c.getString(c.getColumnIndex(G_DES));
				String icon =  c.getString(c.getColumnIndex(G_ICON));
				String remote = c.getString(c.getColumnIndex(G_REMOTE_ID));
				String uids =  c.getString(c.getColumnIndex(G_UIDS));
				groupsList.add(name);
				groupsList.add(status);
				groupsList.add(des);
				groupsList.add(icon);
				groupsList.add(remote);
				groupsList.add(uids);
			} while (c.moveToNext());
		}
		c.close();
		db.close();

		return groupsList;
	}

	/**
	 *
	 * @param id
	 * @return contactById
	 */
	public String[] getContactDetail(String id) {
		String[] contacts = new String[13];
		Log.e("id", id);
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + CONTACTS_TABLE + " where " + C_REMOTE_ID + "=?", new String[]{id});
		if (c.moveToFirst()) {
			do {

				contacts[0] = c.getString(c.getColumnIndex(C_NAME));
				contacts[1] = c.getString(c.getColumnIndex(C_EMAIL));
				contacts[2] = c.getString(c.getColumnIndex(C_DOB));
				contacts[3] = c.getString(c.getColumnIndex(C_ADDRESS));
				contacts[4] = c.getString(c.getColumnIndex(C_MOBILE));
				contacts[5] = c.getString(c.getColumnIndex(C_CITY));
				contacts[6] = c.getString(c.getColumnIndex(C_STATE));
				contacts[7] = c.getString(c.getColumnIndex(C_COUNTRY));
				contacts[8] = c.getString(c.getColumnIndex(C_ANIVERSARY));
				contacts[9] = c.getString(c.getColumnIndex(C_IMAGE));
				contacts[10] = c.getString(c.getColumnIndex(C_UID));
				contacts[11] = c.getString(c.getColumnIndex(C_REMOTE_ID));
				contacts[12] = c.getString(c.getColumnIndex(C_COMPANY));
				Log.e("mobile", contacts[4]);
			}
			while (c.moveToNext());
		}
		c.close();
		db.close();

		return contacts;
	}


	/** Add countries from csv to database
	 *
	 */
	public void addCountries()
	{
		String mCSVfile = "mapp_country.csv";
		AssetManager manager = con.getAssets();
		InputStream inStream = null;
		try {
			inStream = manager.open(mCSVfile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(COUNTRY_TABLE, null, null);
		String line =  "";
		try {
			while ((line = buffer.readLine()) != null) {
				String[] colums = line.split(",");
				if (colums.length != 9) {
					Log.d("CSVParser", "Skipping Bad CSV Row");
					continue;
				}
				ContentValues cv = new ContentValues();
				cv.put(COUNTRY_ID, Integer.valueOf(colums[0].replace("\"","")));
				cv.put(COUNTRY_ISO2, colums[1].replace("\"", ""));
				cv.put(COUNTRY_SHORT_NAME, colums[2].replace("\"", ""));
				cv.put(COUNTRY_lONG_NAME, colums[3].replace("\"", ""));
				cv.put(COUNTRY_ISO3, colums[4].replace("\"", ""));
				cv.put(COUNTRY_NUM_CODE, colums[5].replace("\"", ""));
				cv.put(COUNTRY_UN_MEMBER, colums[6].replace("\"", ""));
				cv.put(COUNTRY_CALLING_CODE, colums[7].replace("\"", ""));
				cv.put(COUNTRY_CCTLD, colums[8].replace("\"",""));
				long insertedRowID = db.insert(COUNTRY_TABLE, null, cv);
				Log.e("inserted row", insertedRowID+"");

			}
			sendBroadCast = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		db.close();
	}

	public ArrayList<HashMap<String, String>> getAllCountries() {
		ArrayList<HashMap<String, String>> countryList = new ArrayList<>();
		HashMap<String, String> sel = new HashMap<String, String>();
		sel.put(COUNTRY_SHORT_NAME, "Select Country");
		sel.put(COUNTRY_ID, "0");
		countryList.add(sel);
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + COUNTRY_TABLE, null);
		if (c.moveToFirst()) {

			do {
				HashMap<String, String> values = new HashMap<String, String>();
				values.put(COUNTRY_ID, c.getString(c.getColumnIndex(COUNTRY_ID)));
				values.put(COUNTRY_SHORT_NAME, c.getString(c.getColumnIndex(COUNTRY_SHORT_NAME)));
				countryList.add(values);
			} while (c.moveToNext());
		}
		c.close();
		db.close();
		Log.e("contacts array list", "Size" + countryList.size());
		return countryList;
	}

	/**
	 *
	 * @return getAllCountriesList
	 */
	/*public ArrayList<Coutries> getAllCountriesForAutoComplete() {
		ArrayList<Coutries> countryList = new ArrayList<>();
		Coutries coutries = new Coutries("Select Country", "0");
		countryList.add(coutries);
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + COUNTRY_TABLE, null);
		if (c.moveToFirst()) {

			do {

				countryList.add(new Coutries(c.getString(c.getColumnIndex(COUNTRY_SHORT_NAME)), c.getString(c.getColumnIndex(COUNTRY_ID))));
			} while (c.moveToNext());
		}
		c.close();
		db.close();
		return countryList;
	}*/

	/**
	 *
	 * @param json
	 */
	public void addEmailsToDb(String json)
	{
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			db.delete(EMAIL_SETTINGS_TABLE, "1", null);

			JSONObject jsonObject = new JSONObject(json);
			JSONArray jsonArray = jsonObject.getJSONArray("email");
			int length = jsonArray.length();
			for(int i =0; i < length; i++)
			{
				JSONObject jsonObject1 = jsonArray.getJSONObject(i);
				ContentValues contentValues = new ContentValues();
				contentValues.put(DBHandler.EMAIL_SERVER, jsonObject1.getString("host"));
				contentValues.put(DBHandler.EMAIL_PORT, jsonObject1.getString("port"));
				contentValues.put(DBHandler.EMAIL_USERNAME, jsonObject1.getString("username"));
				contentValues.put(DBHandler.EMAIL_PASSWORD, jsonObject1.getString("password"));
				contentValues.put(DBHandler.EMAIL_FROM_NAME, jsonObject1.getString("name"));
				contentValues.put(DBHandler.EMAIL_FROM_EMAIL, jsonObject1.getString("email"));
				contentValues.put(DBHandler.EMAIL_SMTP_AUTH, jsonObject1.getString("smtpauth"));
				contentValues.put(DBHandler.EMAIL_SMTP_SECURE, jsonObject1.getString("smtpsecure"));
				contentValues.put(DBHandler.EMAIL_SMTP_REMOTE_ID, jsonObject1.getString("id"));
				contentValues.put(DBHandler.EMAIL_SMTP_TEST_EMAIL, jsonObject1.getString("testemail"));
				db.insert(EMAIL_SETTINGS_TABLE, null, contentValues);
			}
			db.close();
		} catch (Exception e) {
			Log.getStackTraceString(e);
		}
	}

	/**
	 *
	 * @return emailsList
	 */
	public ArrayList<HashMap<String, String>> getAllEmails() {
		ArrayList<HashMap<String, String>> emaiList = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + EMAIL_SETTINGS_TABLE, null);
		if (c.moveToFirst()) {

			do {
				HashMap<String, String> contentValues = new HashMap<String, String>();
				contentValues.put(EMAIL_SERVER, c.getString(c.getColumnIndex(EMAIL_SERVER)));
				contentValues.put(EMAIL_PORT, c.getString(c.getColumnIndex(EMAIL_PORT)));
				contentValues.put(EMAIL_USERNAME, c.getString(c.getColumnIndex(EMAIL_USERNAME)));
				contentValues.put(EMAIL_PASSWORD, c.getString(c.getColumnIndex(EMAIL_PASSWORD)));
				contentValues.put(EMAIL_FROM_NAME, c.getString(c.getColumnIndex(EMAIL_FROM_NAME)));
				contentValues.put(EMAIL_FROM_EMAIL, c.getString(c.getColumnIndex(EMAIL_FROM_EMAIL)));
				contentValues.put(EMAIL_SMTP_AUTH, c.getString(c.getColumnIndex(EMAIL_SMTP_AUTH)));
				contentValues.put(EMAIL_SMTP_SECURE, c.getString(c.getColumnIndex(EMAIL_SMTP_SECURE)));
				contentValues.put(EMAIL_SMTP_REMOTE_ID, c.getString(c.getColumnIndex(EMAIL_SMTP_REMOTE_ID)));
				contentValues.put(EMAIL_SMTP_TEST_EMAIL, c.getString(c.getColumnIndex(EMAIL_SMTP_TEST_EMAIL)));
				emaiList.add(contentValues);
			} while (c.moveToNext());
		}
		c.close();
		db.close();
		Log.e("contacts array list", "Size" + emaiList.size());
		return emaiList;
	}


	/**
	 *
	 * @param id
	 * @return emailById
	 */
	public HashMap<String, String> getEmailById(String id) {
		HashMap<String, String> contentValues = new HashMap<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + EMAIL_SETTINGS_TABLE +" where "+ EMAIL_SMTP_REMOTE_ID + "=?", new String[]{id});
		if (c.moveToFirst()) {


				contentValues.put(EMAIL_SERVER, c.getString(c.getColumnIndex(EMAIL_SERVER)));
				contentValues.put(EMAIL_PORT, c.getString(c.getColumnIndex(EMAIL_PORT)));
				contentValues.put(EMAIL_USERNAME, c.getString(c.getColumnIndex(EMAIL_USERNAME)));
				contentValues.put(EMAIL_PASSWORD, c.getString(c.getColumnIndex(EMAIL_PASSWORD)));
				contentValues.put(EMAIL_FROM_NAME, c.getString(c.getColumnIndex(EMAIL_FROM_NAME)));
				contentValues.put(EMAIL_FROM_EMAIL, c.getString(c.getColumnIndex(EMAIL_FROM_EMAIL)));
				contentValues.put(EMAIL_SMTP_AUTH, c.getString(c.getColumnIndex(EMAIL_SMTP_AUTH)));
				contentValues.put(EMAIL_SMTP_SECURE, c.getString(c.getColumnIndex(EMAIL_SMTP_SECURE)));
				contentValues.put(EMAIL_SMTP_REMOTE_ID, c.getString(c.getColumnIndex(EMAIL_SMTP_REMOTE_ID)));
				contentValues.put(EMAIL_SMTP_TEST_EMAIL, c.getString(c.getColumnIndex(EMAIL_SMTP_TEST_EMAIL)));

		}
		c.close();
		db.close();
		Log.e("contacts array list", "Size" + contentValues.size());
		return contentValues;
	}

	/**Get the contacts within the group by group id
	 *
	 * @param groupList
	 * @return contactsByGrouId
	 */
	public ArrayList<String> getContactsFromGroupId(ArrayList<String> groupList)
	{
		ArrayList<String> contactList = new ArrayList<>();
		SQLiteDatabase db = getReadableDatabase();
		int length = groupList.size();
		for(int i = 0; i < length; i++)
		{
			Cursor c = db.rawQuery("SELECT * FROM " + GROUP_TABLE + " where " + G_REMOTE_ID + "=?", new String[]{groupList.get(i).toString()});
			if(c.moveToFirst())
			{
				String gCIDS = c.getString(c.getColumnIndex(G_UIDS));
				Log.e("groups", gCIDS);
				contactList.add(gCIDS);
			}
			c.close();
		}

		db.close();
		return contactList;
	}


	public void deleteEmail(String id)
	{
		SQLiteDatabase database = this.getWritableDatabase();
		database.delete(EMAIL_SETTINGS_TABLE, C_REMOTE_ID + "=" + id, null);
		database.close();
	}

	/**
	 * This method is called when user logged out. It will wipe out databse and sharedpreferences
	 */
	public void reset()
	{

		SharedPreferences preferences = con.getSharedPreferences(DBHandler.preferencesName, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.clear();
		editor.apply();

		SQLiteDatabase db = getWritableDatabase();
		db.delete(USER_TABLE, null, null);
		db.delete(SENDERIDS_TABLE, null, null);
		db.delete(GROUP_TABLE, null, null);
		db.delete(CONTACTS_TABLE, null, null);
		db.delete(SENDERIDS_TABLE, null, null);
		db.delete(EMAIL_SETTINGS_TABLE, null, null);
		db.delete(GREETINGS_TABLE, null, null);
		db.delete(NOTIFICATION_TABLE, null, null);
		db.close();

	}

	/**
	 *
	 * @param json
	 * @throws Exception
	 */
	public void addSenderIds(String json) throws Exception {
		JSONObject jsonObject = new JSONObject(json);
		JSONArray jsonArray = jsonObject.getJSONArray("senderids");
		SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
		int length = jsonArray.length();
		for (int i = 0; i < length; i++) {
			JSONObject item = jsonArray.getJSONObject(i);
			String id = item.getString("id");
			String senderid = item.getString("senderid");
			String approved = item.getString("approved");
			String date = item.getString("date");
			ContentValues values = new ContentValues();
			values.put(SMS_SENDER_ID_REMOTE_ID, id);
			values.put(SMS_SENDER_ID_REAL, senderid);
			values.put(SMS_SENDERID_APPROVED, approved);
			values.put(SMS_SENDERID_DATE_ADDED, date);
			long inserted = sqLiteDatabase.insert(SENDERIDS_TABLE, null, values);
			Log.e("inserted ", inserted+"");

		}
		sqLiteDatabase.close();
	}

	/**
	 *
	 * @return senderIdsList
	 */
	public ArrayList<HashMap<String, String>> getAllSenderIds() {
		ArrayList<HashMap<String, String>> senderIdsList = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Log.i("GEtting From Server","1 " );
		Cursor c = db.rawQuery("SELECT * FROM " + SENDERIDS_TABLE, null);
		Log.i("GEtting From Server","2 " );
		if (c.moveToFirst()) {

			do {
				HashMap<String, String> contentValues = new HashMap<String, String>();
				contentValues.put(SMS_SENDER_ID_REMOTE_ID, c.getString(c.getColumnIndex(SMS_SENDER_ID_REMOTE_ID)));
				contentValues.put(SMS_SENDER_ID_REAL, c.getString(c.getColumnIndex(SMS_SENDER_ID_REAL)));
				contentValues.put(SMS_SENDERID_DATE_ADDED, c.getString(c.getColumnIndex(SMS_SENDERID_DATE_ADDED)));
				senderIdsList.add(contentValues);
			} while (c.moveToNext());
		}
		c.close();
		db.close();
		Log.e("contacts array list", "Size" + senderIdsList.size());
		return senderIdsList;
	}

	/**
	 *
	 * @param jsonObject
	 * @throws JSONException
	 */
	public void addGreetings(JSONObject jsonObject) throws JSONException
	{
		JSONArray jsonArray = jsonObject.getJSONArray("greeting");
		SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
		sqLiteDatabase.delete(GREETINGS_TABLE, null, null);
		int length = jsonArray.length();
		for (int i = 0; i < length; i++) {
			JSONObject item = jsonArray.getJSONObject(i);
			String id = item.getString("id");
			String type = item.getString("type");
			String title = item.getString("title");
			ContentValues values = new ContentValues();
			values.put(GREETING_REMOTE_ID, id);
			values.put(GREETING_TYPE, type);
			values.put(GREETING_TITLE, title);
			sqLiteDatabase.insert(GREETINGS_TABLE, null, values);

		}
		sqLiteDatabase.close();

	}

	/**
	 *
	 * @return allGreetingsList
	 */
	public ArrayList<HashMap<String, String>> getAllGreetings()
	{
		ArrayList<HashMap<String, String>> greetings = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + GREETINGS_TABLE, null);
		if (c.moveToFirst()) {

			do {
				HashMap<String, String> contentValues = new HashMap<String, String>();
				contentValues.put(GREETING_REMOTE_ID, c.getString(c.getColumnIndex(GREETING_REMOTE_ID)));
				contentValues.put(GREETING_TYPE, c.getString(c.getColumnIndex(GREETING_TYPE)));
				contentValues.put(GREETING_TITLE, c.getString(c.getColumnIndex(GREETING_TITLE)));
				greetings.add(contentValues);
			} while (c.moveToNext());
		}
		c.close();
		db.close();

		return greetings;

	}

	/**
	 *
	 * @param title
	 * @param data
	 * @param priority
	 */
	public void addNotifications(String title, String data, int priority)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(NOTIFICATION_DATA, data);
		values.put(NOTIFICATION_TITLE,title);
		values.put(NOTIFICATION_PRIORITY, priority);
		values.put(NOTIFICATION_READ, 0);
		long row = db.insert(NOTIFICATION_TABLE, null, values);
		Log.e("row", row+"");
		db.close();

	}

	/**
	 *
	 * @return notificationsList
	 */
	public ArrayList<HashMap<String, String>> getNotifications()
	{
		ArrayList<HashMap<String, String>> notifications = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + NOTIFICATION_TABLE + " order by "+NOTIFICATION_ID +" DESC", null);
		if (c.moveToFirst()) {

			do {
				HashMap<String, String> contentValues = new HashMap<String, String>();
				contentValues.put(NOTIFICATION_TITLE, c.getString(c.getColumnIndex(NOTIFICATION_TITLE)));
				contentValues.put(NOTIFICATION_DATA, c.getString(c.getColumnIndex(NOTIFICATION_DATA)));
				contentValues.put(NOTIFICATION_PRIORITY, c.getString(c.getColumnIndex(NOTIFICATION_PRIORITY)));
				contentValues.put(NOTIFICATION_READ, c.getString(c.getColumnIndex(NOTIFICATION_READ)));
				contentValues.put(NOTIFICATION_ID, c.getString(c.getColumnIndex(NOTIFICATION_ID)));
				notifications.add(contentValues);
			} while (c.moveToNext());
		}
		c.close();
		db.close();
		return notifications;
	}

	/**
	 *
	 * @return
	 */
	public ArrayList<HashMap<String, String>> getLatestNotification()
	{
		ArrayList<HashMap<String, String>> notifications = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + NOTIFICATION_TABLE + " order by "+NOTIFICATION_ID +" DESC LIMIT 1", null);
		if (c.moveToFirst()) {

			do {
				HashMap<String, String> contentValues = new HashMap<String, String>();
				contentValues.put(NOTIFICATION_TITLE, c.getString(c.getColumnIndex(NOTIFICATION_TITLE)));
				contentValues.put(NOTIFICATION_DATA, c.getString(c.getColumnIndex(NOTIFICATION_DATA)));
				contentValues.put(NOTIFICATION_PRIORITY, c.getString(c.getColumnIndex(NOTIFICATION_PRIORITY)));
				contentValues.put(NOTIFICATION_READ, c.getString(c.getColumnIndex(NOTIFICATION_READ)));
				contentValues.put(NOTIFICATION_ID, c.getString(c.getColumnIndex(NOTIFICATION_ID)));
				notifications.add(contentValues);
			} while (c.moveToNext());
		}
		c.close();
		db.close();
		return notifications;
	}

	/**
	 *
	 * @param ids
	 */
	public void deleteNotification(ArrayList<String> ids)
	{
		int loopEnd = ids.size();
		SQLiteDatabase db = getWritableDatabase();
		for(int i = 0; i < loopEnd; i++)
		{
			db.delete(NOTIFICATION_TABLE, NOTIFICATION_ID+ "=" + ids.get(i), null);
		}
		db.close();



	}
}
