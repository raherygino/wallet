package com.gsoft.wallet.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.gsoft.wallet.R;
import com.gsoft.wallet.model.database.DatabaseHelper;
import com.gsoft.wallet.view.dialog.ProfileDialog;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        DatabaseHelper database = new DatabaseHelper(this);
        Thread splash=new Thread() {
            public void run() {
                try{
                    sleep(2*1000);
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    finish();
                }catch (Exception e){
                    Log.d("Exception", e.getMessage());
                }
            }
        };

        if (database.getUser() == null) {
            ProfileDialog dialog = new ProfileDialog(SplashActivity.this);
            dialog.show();
        } else {
            splash.start();
        }
    }
}