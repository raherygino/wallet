package com.gsoft.wallet.view.dialog;
import android.app.Dialog;
import android.content.Context;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.appcompat.widget.AppCompatSpinner;

import com.gsoft.wallet.R;
import com.gsoft.wallet.model.database.DatabaseHelper;
import com.gsoft.wallet.model.models.Project;
import com.gsoft.wallet.utils.Utils;
import com.gsoft.wallet.viewmodel.DialogEditBalanceViewModel;

import java.util.ArrayList;

public class DialogEditBalance extends SweetDialog
{
    public Dialog dialog;
    public String TITLE, AMOUNT, TYPE;
    public EditText edt_title, edt_amount, edt_type, edt_is_depot;
    public Spinner edt_projet;
    public Button BTN_OK;
    public DialogEditBalanceViewModel viewModel;
    public Context context;
    public int position = -1;
    private DatabaseHelper database;
    public int idProject;
    public RelativeLayout layout_project;
    private Utils utils;

    public DialogEditBalance(Context ctx) {
        super(ctx, R.layout.dialog_new_balance_layout);
        this.context = ctx;
        this.database = new DatabaseHelper(context);
        this.initView();
        this.viewModel = new DialogEditBalanceViewModel(this);
        this.utils = new Utils(context);
        this.idProject = 0;
        super.onCancel(R.id.dialog_nb_btn_cancel);
    }

    public void setPosition(int pos) {
        this.position = pos;
    }
    
    private void initView() {
        edt_title = (EditText) super.setView(R.id.edt_title, "","Regular");
        edt_type = (EditText) super.setView(R.id.edt_type, "","Regular");
        edt_amount = (EditText) super.setView(R.id.edt_amount, "", "Regular");
        edt_projet = (Spinner) super.setView(R.id.edt_projet, "", "Regular");
        edt_is_depot = (EditText) super.setView(R.id.edt_is_depot, "", "Regular");
        layout_project = (RelativeLayout) super.findViewById(R.id.layout_project);

        ArrayList<Project> listProject = database.listProject();
        String[] allProject = new String[listProject.size()];
        int[] projectIds = new int[listProject.size()];
        for (int i = 0; i < listProject.size(); i++) {
            allProject[i] = listProject.get(i).getTitle();
            projectIds[i] = listProject.get(i).getId();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, allProject);
        edt_projet.setAdapter(adapter);
        edt_projet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idProject = projectIds[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        BTN_OK = (Button) super.setView(R.id.dialog_nb_btn_ok, "Ok", "Regular");
    }
    
    public void getData() {
        TITLE = edt_title.getText().toString();
        AMOUNT = edt_amount.getText().toString();
        TYPE = edt_type.getText().toString();
    }

    @Override
    public void show()
    {
        super.show();
    }

    @Override
    public void dismiss()
    {
        super.dismiss();
    }
}
