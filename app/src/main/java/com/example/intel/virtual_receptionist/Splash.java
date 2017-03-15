package com.example.intel.virtual_receptionist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.intel.virtual_receptionist.URL.Urls;

/**
 * Created by intel on 12/7/2016.
 */
public class Splash extends AppCompatActivity implements View.OnClickListener {


    public Button login;
    public static boolean first_time;
    ProgressBar _progress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //getSupportActionBar().hide();
        SharedPreferences sharedPreferences = getSharedPreferences(Urls.sharedPreferencesName, Context.MODE_PRIVATE);
        first_time = sharedPreferences.getBoolean("first_time", true);
        login = (Button) findViewById(R.id.btn_login_splash);
        _progress = (ProgressBar) findViewById(R.id._progress);
        login.setOnClickListener(this);

        if(first_time)
        {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (HttpConnect.isOnline(Splash.this)) {
                        Intent i = new Intent(Splash.this, Login.class);
                        i.putExtra("first_time",true);
                        startActivity(i);
                        finish();
                    } else {
                        Splash.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Urls.createDialog(Splash.this, Urls.AlertMsgs.noConncectionMsg);
                            }
                        });
                    }
                }
            }, 3000);

        }
        else
        {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (HttpConnect.isOnline(Splash.this)) {
                        Intent i = new Intent(Splash.this, Login.class);
                        i.putExtra("first_time",false);
                        startActivity(i);
                        finish();
                    } else {
                        Splash.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Urls.createDialog(Splash.this, Urls.AlertMsgs.noConncectionMsg);
                            }
                        });
                    }
                }
            }, 2000);
        }




    }


    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_login_splash:

                Intent senttoLoginPage = new Intent(Splash.this, Login.class);
                startActivity(senttoLoginPage);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();

        }


    }


}
