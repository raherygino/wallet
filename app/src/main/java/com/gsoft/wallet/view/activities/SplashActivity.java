package com.gsoft.wallet.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import com.gsoft.wallet.R;
import com.gsoft.wallet.utils.Utils;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Utils utils = new Utils(this);
        Thread splash=new Thread() {
            public void run() {
                try{
                    sleep(2*1000);
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }catch (Exception e){
                    utils.toast(e.getMessage());
                }
            }
        };
        splash.start();
    }
}