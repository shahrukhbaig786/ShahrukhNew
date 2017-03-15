package com.example.intel.virtual_receptionist;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intel.virtual_receptionist.DBManager.DBConstants;
import com.example.intel.virtual_receptionist.DBManager.DBHandler;
import com.example.intel.virtual_receptionist.DBManager.MainStorage;
import com.example.intel.virtual_receptionist.URL.Urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by intel on 12/27/2016.
 */
public class UpdateDocumentFragment extends Fragment implements View.OnClickListener, Spinner.OnItemSelectedListener {


    String spinner_id_proff, spinner_address_proff = null;
    ImageButton upload_item1, upload_item2;
    Spinner document1, document2;
    boolean isAvailableOnServer;
    ImageButton action_send_server_data1, action_send_server_data2;

    static Context context;

    private static final int PICK_FILE_REQUEST1 = 1;
    private static final int PICK_FILE_REQUEST2 = 100;

    String content_type1, content_type2 = null;
    private String selectedFilePath1, selectedFilePath2;
    TextView tv_id_proff_name, tv_id_address_name;
    private ProgressDialog dialog;
    public static String id_proff, address_proff = null;
    List<String> ProfileData;
    ArrayAdapter<String> dataAdapter1, dataAdapter2;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.tab_upload_document_fragment, container, false);

        document1 = (Spinner) myView.findViewById(R.id.spinner_document1);
        document2 = (Spinner) myView.findViewById(R.id.spinner_document2);
        upload_item1 = (ImageButton) myView.findViewById(R.id.upload_id1);
        upload_item2 = (ImageButton) myView.findViewById(R.id.upload_id2);
        action_send_server_data1 = (ImageButton) myView.findViewById(R.id.img_document1);
        action_send_server_data2 = (ImageButton) myView.findViewById(R.id.img_document2);
        tv_id_proff_name = (TextView) myView.findViewById(R.id.status1);
        tv_id_address_name = (TextView) myView.findViewById(R.id.status2);
        return myView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Fetch the I'd related to the Xml layout Files ;
        initialize();
        document2.setSelection(0, false);
        document1.setSelection(0, false);
        upload_item1.setOnClickListener(this);
        upload_item2.setOnClickListener(this);
        document1.setOnItemSelectedListener(this);
        document2.setOnItemSelectedListener(this);
        action_send_server_data1.setOnClickListener(this);
        action_send_server_data2.setOnClickListener(this);

    }

    private void initialize() {
        ProfileData = new ArrayList<String>();
        List<String> documents_address = new ArrayList<>();
        documents_address.add("Select Id Proof");
        documents_address.add("PATNERSHIP_DEED");
        documents_address.add("AUTHORIZED_SIGNATURE_LIST");
        documents_address.add("COMPANY_PAN_CARD");
        documents_address.add("CERTIFICATE_OF_REGISTRATION_INC");
        ArrayList<String> documents_identity_proff = new ArrayList<String>();
        documents_identity_proff.add("Select Address Proof");
        documents_identity_proff.add("PATNERSHIP_DEED");
        documents_identity_proff.add("AUTHORIZED_SIGNATURE_LIST");
        documents_identity_proff.add("SELF_CERTIFICATION_LETTERPAD");
        dataAdapter1 = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_item, documents_address);

        dataAdapter1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        document1.setAdapter(dataAdapter1);
        dataAdapter2 = new ArrayAdapter<String>(getActivity(),
                R.layout.spinner_item, documents_identity_proff);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        document2.setAdapter(dataAdapter2);
    }

    @Override
    public void onStart() {
        super.onStart();
        MainStorage storage = new MainStorage(getActivity());
        isAvailableOnServer = storage.getProfileDatainServer();
        if (isAvailableOnServer) {

            ProfileData = storage.getProfileData();
            ProfileData.get(17);//ID_PROFF_IMAGE
            ProfileData.get(18);//ID_PROFF_NAME
            ProfileData.get(19);//ADD_PROFF_IMAGE
            ProfileData.get(20);//ADD_PROFF_NAME
            if (ProfileData.get(17) != null || ProfileData.get(18) != null) {
                tv_id_proff_name.setText(ProfileData.get(17));
                int position1 = dataAdapter1.getPosition(ProfileData.get(18));
                document1.setSelection(position1);

            }
            if (ProfileData.get(19) != null || ProfileData.get(20) != null) {
                tv_id_address_name.setText(ProfileData.get(19));
                int position2 = dataAdapter2.getPosition(ProfileData.get(20));
                document2.setSelection(position2);
            }

        }

    }

    @Override
    public void onClick(View view) {
        String content_img1 = "image/png", content_img2 = "image/jpg", content_img3 = "image/jpeg";
        String content_doc1 = "application/pdf", content_doc2 = "application/msword",
                content_doc3 = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";


        switch (view.getId()) {


            case R.id.img_document1:
                id_proff = document1.getSelectedItem().toString();
                boolean isValid_id_proof = validateAllFileds1(id_proff);

                if (isValid_id_proof && !(tv_id_proff_name.getText().toString().equals("Not Upload"))) {

                   /* if (((tv_id_proff_name.getText().toString().equalsIgnoreCase("Uploaded") || tv_id_proff_name.getText().toString().equalsIgnoreCase("Not Uploaded"))
                            && (content_type1 != null && !content_type1.isEmpty()))) {*/
                    if (content_type1 != null && (content_type1.equals(content_doc1)
                            || content_type1.equals(content_doc2)
                            || content_type1.equals(content_doc3)
                            || content_type1.equals(content_img1)
                            || content_type1.equals(content_img2)
                            || content_type1.equals(content_img3)
                    )) {

                        if (selectedFilePath1 != null) {
                            dialog = ProgressDialog.show(getActivity(), "", "Uploading File...", true);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    //creating new thread to handle Http Operations
                                    uploadFile(selectedFilePath1, "ID_proof");
                                }
                            }).start();
                        }

                    } else {
                        Urls.createDialog(getActivity(), Urls.AlertMsgs.selecFILE);
                    }
                } else {
                    Urls.createDialog(getActivity(), Urls.AlertMsgs.allFieldsMsg);
                }
                break;


            case R.id.img_document2:
                address_proff = document2.getSelectedItem().toString();
                boolean isValid_adress_proof = validateAllFileds2(address_proff);
                if (isValid_adress_proof && !(tv_id_address_name.getText().toString().equals("Not Upload"))) {
                   /* if ((tv_id_address_name.getText().toString().equals("Uploaded") && (content_type2 != null && !content_type2.isEmpty()))) {*/
                    if (content_type2 != null && (content_type2.equals(content_doc1)
                            || content_type2.equals(content_doc2)
                            || content_type2.equals(content_doc3)
                            || content_type2.equals(content_img1)
                            || content_type2.equals(content_img2)
                            || content_type2.equals(content_img3)
                    )) {
                        if (selectedFilePath2 != null) {
                            dialog = ProgressDialog.show(getActivity(), "", "Uploading File...", true);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    //creating new thread to handle Http Operations
                                    uploadFile(selectedFilePath2, "Address_proff");
                                }
                            }).start();

                        } else {
                            Urls.createDialog(getActivity(), Urls.AlertMsgs.ImageOrDOC);

                        }


                    } else {
                        Urls.createDialog(getActivity(), Urls.AlertMsgs.selecFILE);
                    }

                } else {
                    Urls.createDialog(getActivity(), Urls.AlertMsgs.allFieldsMsg);
                }
                break;
            case R.id.upload_id1:

                showFileChooser1();
                break;
            case R.id.upload_id2:

                showFileChooser2();
                break;
        }

    }

    public int uploadFile(final String selectedFilePath, final String ActionID) {

        int serverResponseCode = 0;
        HttpURLConnection connection;
        DataOutputStream dataOutputStream;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File selectedFile = new File(selectedFilePath);


        String[] parts = selectedFilePath.split("/");
        final String fileName = parts[parts.length - 1];
        final String fileName1 = fileName;
        if (!selectedFile.isFile()) {
            dialog.dismiss();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_id_proff_name.setText("Source File Doesn't Exist: " + selectedFilePath);
                }
            });
            return 0;
        } else {
            try {
                DBHandler handler = new DBHandler(getActivity());
                String userid = handler.getUserId();
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                URL url = null;
                if (ActionID.equals("ID_proof")) {
                    url = new URL(Urls.SERVER_UploadDoc1 + userid + "&id_proof=" + id_proff);


                }
                if (ActionID.equals("Address_proff")) {
                    url = new URL(Urls.SERVER_UploadDoc2 + userid + "&add_proof=" + address_proff);
                }


                //http://82.145.38.202/sachin/pannel/api/user_doc.php?uid=" + uid + "&id_proof=" + id_proof
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);//Allow Inputs
                connection.setDoOutput(true);//Allow Outputs
                connection.setUseCaches(false);//Don't use a cached Copy
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                connection.setRequestProperty("uploaded_file", selectedFilePath);

                //creating new dataoutputstream
                dataOutputStream = new DataOutputStream(connection.getOutputStream());

                //writing bytes to data outputstream
                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + selectedFilePath + "\"" + lineEnd);

                dataOutputStream.writeBytes(lineEnd);

                //returns no. of bytes present in fileInputStream
                bytesAvailable = fileInputStream.available();
                //selecting the buffer size as minimum of available bytes or 1 MB
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                //setting the buffer as byte array of size of bufferSize
                buffer = new byte[bufferSize];

                //reads bytes from FileInputStream(from 0th index of buffer to buffersize)
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                //loop repeats till bytesRead = -1, i.e., no bytes are left to read
                while (bytesRead > 0) {
                    //write the bytes read from inputstream
                    dataOutputStream.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                serverResponseCode = connection.getResponseCode();
                String serverResponseMessage = connection.getResponseMessage();
                final MainStorage storage = new MainStorage(getActivity());


                Log.i("Fragement1", "Server Response is: " + serverResponseMessage + ": " + serverResponseCode);

                //response code of 200 indicates the server status OK
                if (serverResponseCode == 200) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (ActionID.equals("ID_proof")) {


                                AsyncHttpClient client = new AsyncHttpClient();
                                RequestParams params = new RequestParams();
                                client.setTimeout(10 * 1000);
                                DBHandler handler = new DBHandler(getActivity());
                                String PrivateId = handler.getUserId();
                                client.post(Urls.profileUrl + PrivateId, params, new JsonHttpResponseHandler() {
                                    @Override
                                    public void onStart() {
                                        super.onStart();
                                    }

                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                        try {

                                            SharedPreferences preferences = getActivity().getSharedPreferences(DBConstants.preferencesName, Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = preferences.edit();

                                            //If response code is 404 means No data is Available
                                            if (response.getString("msg").equals("404")) {
                                                editor.putBoolean("data_in_server", false);
                                            } else {
                                                MainStorage storage = new MainStorage(getActivity(), response);
                                                //If We got the Dat a from The Sever Save to The local Db and then You can Proceed With Other data
                                                long a = storage.addUserProfileData();
                                                editor.putBoolean("data_in_server", true);
                                                editor.commit();

                                                if (a > 0) {
                                                    //Toast.makeText(DashBoard.this, "Profile data is there in Server ", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        } catch (JSONException e) {
                                            Toast.makeText(getActivity(), "" + e.toString(), Toast.LENGTH_SHORT).show();
                                            e.printStackTrace();
                                        } catch (Exception e) {
                                            Toast.makeText(getActivity(), " Error " + e, Toast.LENGTH_LONG).show();
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                        super.onFailure(statusCode, headers, responseString, throwable);
                                    }
                                });


                                tv_id_proff_name.setText("Upload SuccessFully." + fileName1);


                            }
                            if (ActionID.equals("Address_proff")) {


                                AsyncHttpClient client = new AsyncHttpClient();
                                RequestParams params = new RequestParams();
                                client.setTimeout(10 * 1000);
                                DBHandler handler = new DBHandler(getActivity());
                                String PrivateId = handler.getUserId();
                                client.post(Urls.profileUrl + PrivateId, params, new JsonHttpResponseHandler() {
                                    @Override
                                    public void onStart() {
                                        super.onStart();
                                    }

                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                        try {

                                            SharedPreferences preferences = getActivity().getSharedPreferences(DBConstants.preferencesName, Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = preferences.edit();

                                            //If response code is 404 means No data is Available
                                            if (response.getString("msg").equals("404")) {
                                                editor.putBoolean("data_in_server", false);
                                            } else {
                                                MainStorage storage = new MainStorage(getActivity(), response);
                                                //If We got the Dat a from The Sever Save to The local Db and then You can Proceed With Other data
                                                long a = storage.addUserProfileData();
                                                editor.putBoolean("data_in_server", true);
                                                editor.commit();

                                                if (a > 0) {
                                                    //Toast.makeText(DashBoard.this, "Profile data is there in Server ", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        } catch (JSONException e) {
                                            Toast.makeText(getActivity(), "" + e.toString(), Toast.LENGTH_SHORT).show();
                                            e.printStackTrace();
                                        } catch (Exception e) {
                                            Toast.makeText(getActivity(), " Error " + e, Toast.LENGTH_LONG).show();
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                        super.onFailure(statusCode, headers, responseString, throwable);
                                    }
                                });
                                tv_id_address_name.setText("Upload SuccessFully." + fileName1);

                            }

                        }
                    });
                }

                //closing the input and output streams
                fileInputStream.close();
                dataOutputStream.flush();
                dataOutputStream.close();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "File Not Found", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "URL error!", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Cannot Read/Write File!", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
            return serverResponseCode;
        }

    }

    private void showFileChooser1() {

        try {
            new MaterialFilePicker().withSupportFragment(UpdateDocumentFragment.this).
                    withRequestCode(PICK_FILE_REQUEST1)
                    .withFilterDirectories(true)
                    .start();

        } catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(getActivity(), "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }


    private void showFileChooser2() {

        try {
            new MaterialFilePicker().withSupportFragment(UpdateDocumentFragment.this).
                    withRequestCode(PICK_FILE_REQUEST2)
                    .withFilterDirectories(true)
                    .start();

        } catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(getActivity(), "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateAllFileds1(String spinnner_data1) {
        boolean status = false;
        if (!spinnner_data1.equals("Select Id Proof") || spinnner_data1.equals(null)) {
            status = true;
        }
        return status;
    }

    private boolean validateAllFileds2(String spinnner_data2) {
        boolean status = false;
        if (!spinnner_data2.equals("Select Address Proof") || spinnner_data2.equals(null)) {
            status = true;
        }
        return status;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_FILE_REQUEST1) {
                if (data == null) {
                    //no data present
                    return;
                }
                try {

                    File f = new File(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
                    String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
                    content_type1 = getMimiType(f.getPath());
                    //Both filepath and f will give same result
                    // Toast.makeText(this, "File Path 0 " +  f.getPath() +"/n /n File Path 122 "+filePath, Toast.LENGTH_LONG).show();
                    System.out.println("File Path is " + filePath);
                    selectedFilePath1 = filePath;
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), "File Path is " + selectedFilePath1, Toast.LENGTH_SHORT).show();
                        }
                    });
                    if (selectedFilePath1 != null && !selectedFilePath1.equals("")) {
                        tv_id_proff_name.setText(selectedFilePath1);
                    } else {
                        Toast.makeText(getActivity(), "Cannot upload file to server", Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


            if (requestCode == PICK_FILE_REQUEST2) {
                if (data == null) {
                    //no data present
                    return;
                }

                File f = new File(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
                String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
                content_type2 = getMimiType(f.getPath());
                //Both filepath and f will give same result
                // Toast.makeText(this, "File Path 0 " +  f.getPath() +"/n /n File Path 122 "+filePath, Toast.LENGTH_LONG).show();
                System.out.println("File Path is " + filePath);
                Toast.makeText(getActivity(), "File Path is " + filePath, Toast.LENGTH_SHORT).show();
                selectedFilePath2 = filePath;
                if (selectedFilePath2 != null && !selectedFilePath2.equals("")) {
                    tv_id_address_name.setText(selectedFilePath2);
                } else {
                    Toast.makeText(getActivity(), "Cannot upload file to server", Toast.LENGTH_SHORT).show();
                }

            }
        }


    }

    private String getMimiType(String path) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
        System.out.println("Extension is " + extension);

        String n = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        System.out.println("Finally Extension is  " + n);
        return n;

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (adapterView.getId()) {
            case R.id.upload_id1:
                spinner_id_proff = adapterView.getSelectedItem().toString();


                break;
            case R.id.upload_id2:
                spinner_address_proff = adapterView.getSelectedItem().toString();
                break;


        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(getActivity(), " Please Select Document", Toast.LENGTH_SHORT).show();


    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        System.gc();
    }


    @Override

    public void onDestroy() {
        super.onDestroy();
    }


}
