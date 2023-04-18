package com.gsoft.wallet.view.tab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gsoft.wallet.R;
import com.gsoft.wallet.model.database.DatabaseHelper;
import com.gsoft.wallet.model.models.Transaction;
import com.gsoft.wallet.utils.Utils;
import com.gsoft.wallet.view.activities.HomeActivity;
import com.gsoft.wallet.view.dialog.DatePickerDialog;
import com.gsoft.wallet.view.recyclers.AdapterRecyclerTransaction;

import java.util.ArrayList;

public class TabTransaction extends Fragment {
    private final DatabaseHelper database;
    private final Utils utils;
    private final Context context;
    private ArrayList<Transaction> listTransaction;
    private TextView totalIncome, totalOutcome, currentBalance;
    private AdapterRecyclerTransaction adapterRecyclerTransaction;
    public TabTransaction(Context context) {
        this.database = new DatabaseHelper(context);
        this.utils = new Utils(context);
        this.context = context;
    }

    @Nullable
    @Override
    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.tab_transaction, container, false);
        this.initView(view);
        this.initRecycleView(view);
        this.refresh();
        this.export();
        return view;
    }

    private void initView(View view) {

        totalIncome = view.findViewById(R.id.total_income_amount);
        totalOutcome = view.findViewById(R.id.total_outcome_amount);
        currentBalance = view.findViewById(R.id.current_balance);
        EditText startDate = view.findViewById(R.id.edit_start_date);
        EditText endDate = view.findViewById(R.id.edit_end_date);

        startDate.setOnClickListener(new DatePickerDialog(context));
        endDate.setOnClickListener(new DatePickerDialog(context));
    }

    private void initRecycleView(View view) {

        RecyclerView recyclerViewTransaction = view.findViewById(R.id.recyclerview_transaction);
        listTransaction =  database.listTransaction();
        recyclerViewTransaction.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerTransaction = new LinearLayoutManager(this.context);
        recyclerViewTransaction.setLayoutManager(layoutManagerTransaction);
        adapterRecyclerTransaction = new AdapterRecyclerTransaction(this.context, listTransaction, recyclerViewTransaction);
        recyclerViewTransaction.setAdapter(adapterRecyclerTransaction);

    }

    public TabTransaction tabTransaction() {
        return this;
    }

    public void refresh() {

        int total = database.total(utils.getString(R.string.in)) - database.total(utils.getString(R.string.out));
        totalIncome.setText(utils.numberFormat(String.valueOf(database.total(utils.getString(R.string.in)))));
        totalOutcome.setText(utils.numberFormat(String.valueOf(database.total(utils.getString(R.string.out)))));
        currentBalance.setText(utils.numberFormat(String.valueOf(total)));

        listTransaction.clear();
        listTransaction.addAll(database.listTransaction());
        adapterRecyclerTransaction.notifyDataSetChanged();
    }

    private void export() {
        ((HomeActivity) context).getTransactionClass(this);
    }
}
