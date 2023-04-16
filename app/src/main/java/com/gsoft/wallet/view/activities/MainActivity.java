package com.gsoft.wallet.view.activities;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gsoft.wallet.R;
import com.gsoft.wallet.model.database.DatabaseHelper;
import com.gsoft.wallet.utils.Utils;
import com.gsoft.wallet.view.navbar.BottomNav;
import com.gsoft.wallet.viewmodel.MainActivityViewModel;

import android.content.Intent;
import android.net.Uri;
import android.app.Activity;
import android.util.Log;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class MainActivity extends AppCompatActivity
{
    public TextView home_title, home_username, home_title_balance, home_balance,  home_title_activity, home_title_project, home_income, home_outcome;
    public ImageView home_btn_settings;
    public FloatingActionButton btn_fab_add;
    private Utils utils;
    private DatabaseHelper database;
    public MainActivityViewModel viewModel;
    private static final int READ_REQUEST_CODE = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.init();
        this.initView();
        this.configView();
        this.viewModel = new MainActivityViewModel(MainActivity.this);
    }

    private void init() {
        utils = new Utils(MainActivity.this);
        database = new DatabaseHelper(MainActivity.this);
    }

    private void initView() {
        btn_fab_add = findViewById(R.id.fab_add);
        home_balance = findViewById(R.id.home_balance);
        home_btn_settings = findViewById(R.id.home_btn_settings);
        home_title = findViewById(R.id.home_title);
        home_title_activity = findViewById(R.id.home_title_activity);
        home_title_balance = findViewById(R.id.home_title_balance);
        home_title_project = findViewById(R.id.home_title_project);
        home_username = findViewById(R.id.home_username);
        home_income = findViewById(R.id.value_income);
        home_outcome = findViewById(R.id.value_outcome);
    }

    private void configView() {
        utils.setFont(home_income, "SemiBold");
        utils.setFont(home_outcome, "SemiBold");
        utils.setFont(home_balance, "SemiBold");
        utils.setFont(home_title, "Light");
        utils.setFont(home_title_activity, "Light");
        utils.setFont(home_title_balance, "Light");
        utils.setFont(home_title_project, "Light");
        utils.setFont(home_username, "SemiBold");
    }

    public void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }


    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(
                                                                   inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    inputStream.close();
                    database.openFile(stringBuilder.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
