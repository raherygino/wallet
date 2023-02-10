package com.gsoft.wallet.view.activities;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.gsoft.wallet.R;
import android.widget.TextView;
import android.widget.Button;
import com.gsoft.wallet.utils.Utils;
import com.gsoft.wallet.model.database.DatabaseHelper;
import android.widget.ImageView;
import java.util.ArrayList;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.gsoft.wallet.view.recyclers.AdapterRecycler;
import com.gsoft.wallet.view.recyclers.AdapterRecyclerProject;
import com.gsoft.wallet.model.models.Balance;
import android.widget.TableLayout;
import android.widget.LinearLayout;
import android.widget.TableRow;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gsoft.wallet.viewmodel.MainActivityViewModel;
import com.gsoft.wallet.view.navbar.BottomNav;
import android.content.Intent;
import android.widget.Toast;
import android.net.Uri;
import android.app.Activity;
import android.util.Log;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity
{

    public TextView home_title, home_username, home_title_balance, home_balance,  home_title_activity;
    //home_title_project,
    public ImageView home_btn_settings;
    public Button btn_income, btn_outcome,  home_btn_all_activities;
    //home_btn_all_pro,
    public FloatingActionButton btn_fab_add;
    private Utils utils;
    private DatabaseHelper db;
    public BottomNav bottomNav;
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
        this.viewModel = new MainActivityViewModel(this);
        this.bottomNav = new BottomNav(this);
    }

    private void init() {
        utils = new Utils(MainActivity.this);
        db = new DatabaseHelper(MainActivity.this);
    }

    private void initView() {
        btn_fab_add = findViewById(R.id.fab_add);
        btn_income = findViewById(R.id.btn_outcome);
        btn_outcome = findViewById(R.id.btn_income);
        home_balance = findViewById(R.id.home_balance);
        home_btn_all_activities = findViewById(R.id.btn_all_activities);
     //   home_btn_all_pro = findViewById(R.id.btn_all_projects);
        home_btn_settings = findViewById(R.id.home_btn_settings);
        home_title = findViewById(R.id.home_title);
        home_title_activity = findViewById(R.id.home_title_activity);
        home_title_balance = findViewById(R.id.home_title_balance);
      //  home_title_project = findViewById(R.id.home_title_project);
        home_username = findViewById(R.id.home_username);   
    }

    private void configView() {

        utils.setFont(btn_income, "Medium");
        utils.setFont(btn_outcome, "Medium");
        utils.setFont(home_balance, "SemiBold");
        utils.setFont(home_btn_all_activities, "Light");
        //utils.setFont(home_btn_all_pro, "Light");
        utils.setFont(home_title, "Light");
        utils.setFont(home_title_activity, "Light");
        utils.setFont(home_title_balance, "Light");
        //utils.setFont(home_title_project, "Light");
        utils.setFont(home_username, "SemiBold");
    }

    public void showFileChooser() {

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                Log.i("FileChooser", "Uri: " + uri.toString());
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
                    db.openFile(stringBuilder.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    

}
