package com.example.intel.virtual_receptionist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by intel on 12/8/2016.
 */
public class Register extends Activity implements View.OnClickListener {
    Button btn_register_signUp;
    String username, fullname, email, password = null;
    EditText reg_et_username, reg_et_user_fullname, reg_et_user_email, reg_et_user_pass;
    TextView reg_sign_in;
    Context ctx;
    public static final String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    // ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        reg_et_username = (EditText) findViewById(R.id.reg_et_username);
        reg_et_user_fullname = (EditText) findViewById(R.id.reg_et_fullname);
        reg_et_user_email = (EditText) findViewById(R.id.reg_et_email);
        reg_et_user_pass = (EditText) findViewById(R.id.reg_et_password);
        btn_register_signUp = (Button) findViewById(R.id.reg_signUp);
        reg_sign_in = (TextView) findViewById(R.id.reg_sign_in);
        btn_register_signUp.setOnClickListener(this);
        reg_sign_in.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.reg_signUp:
                //When user Click Signup Button
                // This Given Code will execute to perform the
                // Network Operation and to save the Data in Out Url

            /*    RequestQueue mRequestQueue = Volley.newRequestQueue(this);
                int socketTimeout = getRequestTimeout();
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
*/
             /*   progressDialog = new ProgressDialog(Register.this);
                progressDialog.setMessage("Creating new MemberShip...");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);*/

                username = reg_et_username.getText().toString().trim();
                fullname = reg_et_user_fullname.getText().toString().trim();
                email = reg_et_user_email.getText().toString().trim();
                password = reg_et_user_pass.getText().toString().trim();


                if (!(username.length() > 0)) {
                    // progressDialog.dismiss();
                    reg_et_username.requestFocus();
                    reg_et_username.setHint(Html.fromHtml("<font color='#ae0101'>Enter User Name</font>"));

                    Log.e("Block ","1");
                }
                 if (!(password.length() > 0)) {
                    //  progressDialog.dismiss();
                    reg_et_user_pass.requestFocus();
                    reg_et_user_pass.setHint(Html.fromHtml("<font color='#ae0101'>Enter Password</font>"));

                    Log.e("Block ","2");
                }
                 if (!(fullname.length() > 0)) {
                    //   progressDialog.dismiss();
                    reg_et_user_fullname.requestFocus();
                    reg_et_user_fullname.setHint(Html.fromHtml("<font color='#ae0101'>Enter Full Name</font>"));

                    Log.e("Block ","3");
                }
                 if (!(email.length() > 0)) {
                    //   progressDialog.dismiss();
                    reg_et_user_email.requestFocus();
                    reg_et_user_email.setHint(Html.fromHtml("<font color='#ae0101'>Email Required </font>"));

                    Log.e("Block ","4");
                } else {
                    Log.e("Block ","5");

                     Pattern emailPattern = Pattern.compile(emailRegex);
                    Matcher emailMatcher = emailPattern.matcher(email);

                    if (!(emailMatcher.find())) {
                        Log.e("Block ","6");
                        //      progressDialog.dismiss();
                        reg_et_user_email.requestFocus();

                        reg_et_user_email.setHint(Html.fromHtml("<font color='#ae0101'>Enter Valid Email Id</font>"));
                    }
                    else

                    {
                        Log.e("Block ","7");


                        OperationsaveToNetwork otr = new OperationsaveToNetwork(Register.this);
                        otr.register(email, password, fullname, username, new OperationsaveToNetwork.VolleyCallback() {
                                    @Override
                                    public void onSuccessResponse(String result) {
                                        if(result.equals("0")){
                                            Log.e("Block ","8");
                                            Toast.makeText(Register.this, "Successfully Added", Toast.LENGTH_LONG).show();

                                            Intent i=new Intent(Register.this,Login.class);
                                            i.putExtra("email",email);

                                            startActivity(i);
                                            finish();

                                        }else if(result.equals("1")){
                                            Log.e("Block ","9");
                                            Toast.makeText(Register.this, "Check Network  Alreadty you are members here ", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(Register.this, result+"", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                       // int status=0;
                       // status=new OperationsaveToNetwork(Register.this).register(email, password, fullname, username);
                      /*  if(otr.register(email, password, fullname, username)==1){
                            Log.e("Block ","8");
                            Toast.makeText(Register.this, "Successfully Added", Toast.LENGTH_LONG).show();
                            Intent i=new Intent(Register.this,Login.class);
                            startActivity(i);
                            finish();

                        }else{
                            Log.e("Block ","9");
                            Toast.makeText(Register.this, "Check Network  Alreadty you are members here ", Toast.LENGTH_SHORT).show();
                        }*/
                    }


                }


                break;
            // End of the SignUp Button


            case R.id.reg_sign_in:

                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left_to_right,R.anim.right_to_left);

                break;

        }



    }



}
