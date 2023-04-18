package com.gsoft.wallet.view.tab;

import static com.gsoft.wallet.utils.Utils.LIGHT;
import static com.gsoft.wallet.utils.Utils.SEMI_BOLD;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gsoft.wallet.R;
import com.gsoft.wallet.model.database.DatabaseHelper;
import com.gsoft.wallet.model.models.Project;
import com.gsoft.wallet.model.models.Transaction;
import com.gsoft.wallet.model.models.User;
import com.gsoft.wallet.utils.Utils;
import com.gsoft.wallet.view.activities.AdminActivity;
import com.gsoft.wallet.view.activities.HomeActivity;
import com.gsoft.wallet.view.activities.SplashActivity;
import com.gsoft.wallet.view.dialog.ConfirmDialog;
import com.gsoft.wallet.view.dialog.ExportDialog;
import com.gsoft.wallet.view.recyclers.AdapterRecyclerProject;
import com.gsoft.wallet.view.recyclers.AdapterRecyclerTransaction;

import java.util.ArrayList;

public class TabHome extends Fragment {

    public TextView home_title, home_username, home_title_balance, home_balance,  home_title_activity, home_title_project, home_income, home_outcome;

    private Utils utils;
    private DatabaseHelper database;
    private final Context context;
    ArrayList<Transaction> listTransaction;
    ArrayList<Project> listProject;
    RecyclerView recyclerViewTransaction;
    RecyclerView recyclerViewProject;
    AdapterRecyclerTransaction adapterRecyclerTransaction;
    AdapterRecyclerProject adapterRecycler;

    public TabHome(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.tab_home, container, false);
        this.init();
        this.initView(view);
        this.configView();
        this.refresh();
        this.export();
        return view;
    }

    private void export() {
        ((HomeActivity) context).getHomeClass(this);
    }

    private void init() {
        utils = new Utils(context);
        database = new DatabaseHelper(context);
    }

    private void initView(View view) {

        home_balance = view.findViewById(R.id.home_balance);
        home_title = view.findViewById(R.id.home_title);
        home_title_activity = view.findViewById(R.id.home_title_activity);
        home_title_balance = view.findViewById(R.id.home_title_balance);
        home_title_project = view.findViewById(R.id.home_title_project);
        home_username = view.findViewById(R.id.home_username);
        home_income = view.findViewById(R.id.value_income);
        home_outcome = view.findViewById(R.id.value_outcome);

        recyclerViewProject = view.findViewById(R.id.recyclerview_project);
        initRecyclerProject(recyclerViewProject);

        recyclerViewTransaction = view.findViewById(R.id.recyclerview_transaction_home);
        initRecyclerTransaction(recyclerViewTransaction);

    }

    private void initRecyclerProject(RecyclerView recyclerViewProject) {
        recyclerViewProject.setHasFixedSize(true);
        listProject = database.listProjectByStat(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewProject.setLayoutManager(layoutManager);
        adapterRecycler = new AdapterRecyclerProject(this.context, listProject, R.layout.item_project);
        recyclerViewProject.setAdapter(adapterRecycler);
    }

    private void initRecyclerTransaction(RecyclerView recyclerViewTransaction) {
        listTransaction =  database.listTransaction();
        recyclerViewTransaction.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerTransaction = new LinearLayoutManager(this.context);
        recyclerViewTransaction.setLayoutManager(layoutManagerTransaction);
        adapterRecyclerTransaction = new AdapterRecyclerTransaction(this.context, listTransaction, recyclerViewTransaction);
        recyclerViewTransaction.setAdapter(adapterRecyclerTransaction);
    }

    public TabHome exportClass() {
        return this;
    }

    private void configView() {
        utils.setFont(home_income, SEMI_BOLD);
        utils.setFont(home_outcome, SEMI_BOLD);
        utils.setFont(home_balance, SEMI_BOLD);
        utils.setFont(home_title, LIGHT);
        utils.setFont(home_title_activity, LIGHT);
        utils.setFont(home_title_balance, LIGHT);
        utils.setFont(home_title_project, LIGHT);
        utils.setFont(home_username, SEMI_BOLD);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void refresh() {
        int total = database.total(utils.getString(R.string.in)) - database.total(utils.getString(R.string.out));
        User user = database.getUser();
        home_username.setText(user.getLastName());
        home_income.setText(utils.numberFormat(String.valueOf(database.total(utils.getString(R.string.in)))));
        home_outcome.setText(utils.numberFormat(String.valueOf(database.total(utils.getString(R.string.out)))));
        home_balance.setText(utils.numberFormat(String.valueOf(total)));
        listTransaction.clear();
        listTransaction.addAll(database.listTransaction());
        adapterRecyclerTransaction.notifyDataSetChanged();

        if (database.listProjectByStat(false).size() == 0) {
            home_title_project.setVisibility(View.GONE);
            recyclerViewProject.setVisibility(View.GONE);
        } else {
            home_title_project.setVisibility(View.VISIBLE);
            recyclerViewProject.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void refreshProject() {
        listProject.clear();
        listProject.addAll(database.listProjectByStat(false));
        adapterRecycler.notifyDataSetChanged();
    }
}