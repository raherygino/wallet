package com.gsoft.wallet.viewmodel;
import android.annotation.SuppressLint;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.widget.PopupMenu;
import android.view.MenuItem;

import com.gsoft.wallet.R;
import com.gsoft.wallet.model.database.DatabaseHelper;
import com.gsoft.wallet.model.models.Balance;
import com.gsoft.wallet.model.models.Project;
import com.gsoft.wallet.utils.Utils;
import com.gsoft.wallet.view.activities.MainActivity;
import com.gsoft.wallet.view.dialog.DialogMenu;
import com.gsoft.wallet.view.recyclers.AdapterRecycler;
import com.gsoft.wallet.view.recyclers.AdapterRecyclerProject;

import java.util.ArrayList;

public class MainActivityViewModel
{
    private Context context;
    private MainActivity mActivity;
    private DatabaseHelper db;
    private Utils u;
    public onClickEvent onClickListener;
    private RecyclerView recyclerViewTransaction;
    private RecyclerView.LayoutManager layoutManagerTransaction, layoutManager;
    public AdapterRecycler adapterRecyclerTransaction;
    public AdapterRecyclerProject adapterRecycler;
    public ArrayList<Balance> list;
    public ArrayList<Project> list_project;

    public MainActivityViewModel(MainActivity mainActivity) {
        this.context = mainActivity;
        this.mActivity = mainActivity;
        this.u = new Utils(context);
        this.db = new DatabaseHelper(context);
        this.onClickListener = new onClickEvent();
        this.refresh();
        this.setDataToView();
        this.setViewEvent();
    }


    private void setDataToView() {
        list =  db.listData();
        
        RecyclerView recyclerView = mActivity.findViewById(R.id.recyclerview_project);
        recyclerView.setHasFixedSize(true);
        
        list_project = db.listProject();
        layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager); 
        adapterRecycler = new AdapterRecyclerProject(mActivity, list_project);
        recyclerView.setAdapter(adapterRecycler);

        recyclerViewTransaction = mActivity.findViewById(R.id.recyclerview_transaction_home);
        recyclerViewTransaction.setHasFixedSize(true);
        layoutManagerTransaction = new LinearLayoutManager(mActivity);

        recyclerViewTransaction.setLayoutManager(layoutManagerTransaction); 
        adapterRecyclerTransaction = new AdapterRecycler(mActivity, list, recyclerViewTransaction);
        recyclerViewTransaction.setAdapter(adapterRecyclerTransaction); 
    } 
    
    private void setViewEvent() {
        mActivity.btn_fab_add.setOnClickListener(new onClickEvent());
        mActivity.home_btn_settings.setOnClickListener(new onClickEvent());
    }
    
    public void refresh() {
        int total = db.total("Entrée") - db.total("Sortie");
        mActivity.home_income.setText(u.numberFormat( String.valueOf(db.total("Entrée"))));
        mActivity.home_outcome.setText(u.numberFormat(String.valueOf(db.total("Sortie"))));
        mActivity.home_balance.setText(u.numberFormat(String.valueOf(total)));
    }

    public void refreshProject() {
        list_project.clear();
        list_project.addAll(db.listProject());
        adapterRecycler.notifyDataSetChanged();
    }
    
    public void delete(int id) {
        db.deleteData(id);
    }
    
    class onClickEvent implements OnClickListener
    {
        public onClickEvent() {}

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View p1)
        {
            u.btnClick(p1);
            int id = p1.getId();
            
            switch(id) {
                
                case R.id.fab_add:
                    DialogMenu menu = new DialogMenu(context);
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
                    db.saveFile();
                    break;
            }

            return true;
        }

   }
}
