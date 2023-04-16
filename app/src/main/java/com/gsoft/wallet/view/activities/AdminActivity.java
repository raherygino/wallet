package com.gsoft.wallet.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.gsoft.wallet.R;
import com.gsoft.wallet.model.database.DatabaseHelper;
import com.gsoft.wallet.model.models.Person;
import com.gsoft.wallet.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class AdminActivity extends AppCompatActivity {
    Utils utils = new Utils(this);

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        DatabaseHelper database = new DatabaseHelper(this);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TableLayout tableUser = findViewById(R.id.table_user);
        TableLayout tableDeposit = findViewById(R.id.table_deposit);
        TableLayout tableProject = findViewById(R.id.table_project);
        TableLayout tableTransaction = findViewById(R.id.table_transaction);
        createTableFromDatabase(database.getAllUser(), tableUser);
        createTableFromDatabase(database.getAllDeposit(), tableDeposit);
        createTableFromDatabase(database.getAllProject(), tableProject);
        createTableFromDatabase(database.getAllTransaction(), tableTransaction);

        ArrayList<Person> list = new ArrayList<>();
        try {
            list.add(new Person("John", new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").parse("01-01-2010 12:00:00")));
            list.add(new Person("Thomas", new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").parse("01-01-2010 01:00:00")));
            list.add(new Person("Tom", new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").parse("01-02-1990 21:00:00")));
            list.add(new Person("Anderson", new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").parse("01-02-1990 10:00:00")));
            list.add(new Person("Smith", new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").parse("01-03-2000 00:59:59")));
            list.add(new Person("Doe", new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").parse("01-03-2000 01:00:00")));
            Collections.sort(list, new Comparator<Person>() {
                @Override
                public int compare(Person person, Person person2) {
                    return Long.valueOf(person.getBirthdate().getTime()).compareTo(person2.getBirthdate().getTime());
                }
            });

            String result = "";
            for (Person person : list) {
                result += "Nom: "+person.getName()+"\nDate: "+person.getBirthdate()+"\n";
            }
            TextView textViewResult =  findViewById(R.id.result);
            textViewResult.setText(result);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

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