package com.gsoft.wallet.view.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gsoft.wallet.R;
import com.gsoft.wallet.model.database.DatabaseHelper;
import com.gsoft.wallet.utils.Utils;
import com.gsoft.wallet.view.dialog.DetailTransDialog;
import com.gsoft.wallet.view.dialog.MenuDialog;
import com.gsoft.wallet.view.navbar.BottomNav;
import com.gsoft.wallet.view.recyclers.AdapterViewPager;
import com.gsoft.wallet.view.tab.TabHome;
import com.gsoft.wallet.view.tab.TabProject;
import com.gsoft.wallet.view.tab.TabTransaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HomeActivity extends AppCompatActivity {

    public Utils utils;
    private BottomNav bottomNav;
    private static final int READ_REQUEST_CODE = 42;
    private DatabaseHelper database;
    public TabHome tabHome;
    public TabTransaction tabTransaction;
    public TabProject tabProject;
    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.utils = new Utils(this);
        this.bottomNav = new BottomNav(this);
        this.database = new DatabaseHelper(this);
        this.fabAdd = findViewById(R.id.fab_add);
        this.setConfig();
        DetailTransDialog dialog = new DetailTransDialog(this);
        dialog.show();
    }

    private void setConfig() {
        fabAdd.setOnClickListener(new onClick());
        ViewPager viewPager = findViewById(R.id.viewPager);
        AdapterViewPager myAdapter = new AdapterViewPager(HomeActivity.this, getSupportFragmentManager());
        viewPager.setAdapter(myAdapter);
        viewPager.addOnPageChangeListener(new ViewPagerOnChanger());
        bottomNav.setupWithViewPager(viewPager);
    }

    class onClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            MenuDialog menu = new MenuDialog(HomeActivity.this);
            menu.show();
        }
    }

    public void refreshFragement() {
        tabHome.refresh();
        tabHome.refreshProject();
        tabTransaction.refresh();
        tabProject.refresh();
    }

    public void getHomeClass(TabHome tabHome) {
        this.tabHome = tabHome;
    }

    public void getTransactionClass(TabTransaction tabTransaction) {
        this.tabTransaction = tabTransaction;
    }

    public void getProjectClass(TabProject tabProject) {
        this.tabProject = tabProject;
    }

    class ViewPagerOnChanger implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            bottomNav.setItemActivate(position);
        }
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageScrollStateChanged(int state) {}
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
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    inputStream.close();
                    database.importAllData(stringBuilder.toString());
                    refreshFragement();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}