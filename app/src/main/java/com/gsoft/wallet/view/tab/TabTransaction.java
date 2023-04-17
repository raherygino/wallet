package com.gsoft.wallet.view.tab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.gsoft.wallet.R;
import com.gsoft.wallet.model.database.DatabaseHelper;
import com.gsoft.wallet.model.models.User;
import com.gsoft.wallet.utils.Utils;

public class TabTransaction extends Fragment {
    private final DatabaseHelper database;
    private final Utils utils;
    public TabTransaction(Context context) {
        this.database = new DatabaseHelper(context);
        this.utils = new Utils(context);
    }

    @Nullable
    @Override
    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.tab_transaction, container, false);
        TextView totalIncome = view.findViewById(R.id.total_income_amount);
        TextView totalOutcome = view.findViewById(R.id.total_outcome_amount);
        TextView currentBalance = view.findViewById(R.id.current_balance);

        int total = database.total(utils.getString(R.string.in)) - database.total(utils.getString(R.string.out));
        totalIncome.setText(utils.numberFormat(String.valueOf(database.total(utils.getString(R.string.in)))));
        totalOutcome.setText(utils.numberFormat(String.valueOf(database.total(utils.getString(R.string.out)))));
        currentBalance.setText(utils.numberFormat(String.valueOf(total)));

        return view;
    }

    public TabTransaction tabTransaction() {
        return this;
    }
}
