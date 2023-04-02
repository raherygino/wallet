package com.gsoft.wallet.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.gsoft.wallet.R;
import com.gsoft.wallet.model.database.DatabaseHelper;
import com.gsoft.wallet.model.models.Deposit;
import com.gsoft.wallet.utils.Utils;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
    Utils utils = new Utils(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        DatabaseHelper database = new DatabaseHelper(this);
        TableLayout tableDeposit = findViewById(R.id.table_deposit);
        TableLayout tableProject = findViewById(R.id.table_project);
        TableLayout tableTransaction = findViewById(R.id.table_transaction);
        createTableFromDatabase(database.getAllDeposit(), tableDeposit);
        createTableFromDatabase(database.getAllProject(), tableProject);
        createTableFromDatabase(database.getAllTransaction(), tableTransaction);
    }

    private void createTableFromDatabase(Cursor cursor, TableLayout tableLayout) {
        int columnCount = cursor.getColumnCount();
        String[] allColumn = cursor.getColumnNames();
        createRow(tableLayout, allColumn);

        while (cursor.moveToNext()) {
            String[] columns = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                columns[i] = cursor.getString(i);
            }
            createRow(tableLayout, columns);
        }
    }

    public void createRow(TableLayout tableLayout, String[] col) {
        TableRow tableRow = new TableRow(this);
        for (String text : col) {
            TextView column = new TextView(this);
            column.setText(text);
            column.setTextSize(8);
            column.setBackgroundResource(R.drawable.back_column);
            column.setPadding(4,4,4,4);
            utils.setFont(column, Utils.REGULAR);
            tableRow.addView(column);
        }
        tableLayout.addView(tableRow);
    }
}