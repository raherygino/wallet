package com.gsoft.wallet.viewmodel;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.widget.PopupMenu;
import android.view.MenuItem;

import com.gsoft.wallet.R;
import com.gsoft.wallet.model.database.DatabaseHelper;
import com.gsoft.wallet.model.models.Transaction;
import com.gsoft.wallet.model.models.Project;
import com.gsoft.wallet.utils.Utils;
import com.gsoft.wallet.view.activities.AdminActivity;
import com.gsoft.wallet.view.activities.MainActivity;
import com.gsoft.wallet.view.dialog.MenuDialog;
import com.gsoft.wallet.view.recyclers.AdapterRecyclerTransaction;
import com.gsoft.wallet.view.recyclers.AdapterRecyclerProject;

import java.util.ArrayList;

public class MainActivityViewModel
{
    private final Context context;
    private final MainActivity mActivity;
    private DatabaseHelper database;
    private Utils utils;
    public onClickEvent onClickListener;
    public AdapterRecyclerTransaction adapterRecyclerTransaction;
    public AdapterRecyclerProject adapterRecycler;
    public ArrayList<Transaction> listTransaction;
    public ArrayList<Project> listProject;

    public MainActivityViewModel(MainActivity mainActivity) {
        this.context = mainActivity;
        this.mActivity = mainActivity;
        this.utils = new Utils(context);
        this.database = new DatabaseHelper(context);
        this.onClickListener = new onClickEvent();
        this.setDataToView();
        this.setViewEvent();
        this.refresh();
    }

    private void setDataToView() {
        listTransaction =  database.listTransaction();
        
        RecyclerView recyclerView = mActivity.findViewById(R.id.recyclerview_project);
        recyclerView.setHasFixedSize(true);
        
        listProject = database.listProject();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager); 
        adapterRecycler = new AdapterRecyclerProject(mActivity, listProject);
        recyclerView.setAdapter(adapterRecycler);

        RecyclerView recyclerViewTransaction = mActivity.findViewById(R.id.recyclerview_transaction_home);
        recyclerViewTransaction.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerTransaction = new LinearLayoutManager(mActivity);

        recyclerViewTransaction.setLayoutManager(layoutManagerTransaction); 
        adapterRecyclerTransaction = new AdapterRecyclerTransaction(mActivity, listTransaction, recyclerViewTransaction);
        recyclerViewTransaction.setAdapter(adapterRecyclerTransaction); 
    } 
    
    private void setViewEvent() {
        mActivity.btn_fab_add.setOnClickListener(new onClickEvent());
        mActivity.home_btn_settings.setOnClickListener(new onClickEvent());
    }
    
    public void refresh() {
        int total = database.total(utils.getString(R.string.in)) - database.total(utils.getString(R.string.out));
        mActivity.home_income.setText(utils.numberFormat(String.valueOf(database.total(utils.getString(R.string.in)))));
        mActivity.home_outcome.setText(utils.numberFormat(String.valueOf(database.total(utils.getString(R.string.out)))));
        mActivity.home_balance.setText(utils.numberFormat(String.valueOf(total)));
        listTransaction.clear();
        listTransaction.addAll(database.listTransaction());
        adapterRecyclerTransaction.notifyDataSetChanged();
    }

    public void refreshProject() {
        listProject.clear();
        listProject.addAll(database.listProject());
        adapterRecycler.notifyDataSetChanged();
    }
    
    public void delete(int id) {
        database.deleteTransaction(id);
    }
    
    class onClickEvent implements OnClickListener
    {
        public onClickEvent() {}

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View p1)
        {
            utils.btnClick(p1);
            int id = p1.getId();
            
            switch(id) {
                
                case R.id.fab_add:
                    MenuDialog menu = new MenuDialog(context);
                    menu.show();
                    break;
                    
                case R.id.home_btn_settings:
                    PopupMenu popupMenu = new PopupMenu(context, p1);
                    popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new onItemClick());
                    popupMenu.show();
                    break;
                    
            }

        }
    }
    public class onItemClick implements PopupMenu.OnMenuItemClickListener
    {

        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onMenuItemClick(MenuItem p1)
        {
            switch (p1.getItemId()) {
                case R.id.import_data:
                    mActivity.showFileChooser();
                    break;
                case R.id.export_data:
                    database.saveFile();
                    break;
                case R.id.admin:
                    mActivity.startActivity(new Intent(mActivity, AdminActivity.class));
                    break;
            }
            return true;
        }
    }
}
