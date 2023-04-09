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

import com.google.android.material.textfield.TextInputLayout;
import com.gsoft.wallet.R;
import com.gsoft.wallet.model.database.DatabaseHelper;
import com.gsoft.wallet.model.models.Project;
import com.gsoft.wallet.utils.Utils;
import com.gsoft.wallet.viewmodel.DialogEditTransactionViewModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EditTransactionDialog extends SweetDialog
{
    public Dialog dialog;
    public String title, amount, type;
    public EditText editTitle, editAmount, editType, editIsDepot;
    public TextInputLayout layoutTitle;
    public Spinner editProject;
    public Button buttonOk;
    public DialogEditTransactionViewModel viewModel;
    public Context context;
    public int position = -1;
    private DatabaseHelper database;
    public int idProject;
    public RelativeLayout layoutProject;
    private Utils utils;
    public int[] projectIds = null;

    public EditTransactionDialog(Context ctx) {
        super(ctx, R.layout.dialog_new_balance_layout);
        this.context = ctx;
        this.database = new DatabaseHelper(context);
        this.initView();
        this.setConfigSpinner();
        this.viewModel = new DialogEditTransactionViewModel(this);
        this.utils = new Utils(context);
        this.idProject = 0;
        super.onCancel(R.id.dialog_nb_btn_cancel);
    }

    public void setPosition(int pos) {
        this.position = pos;
    }
    
    private void initView() {
        editTitle = (EditText) super.setView(R.id.edt_title, "","Regular");
        editType = (EditText) super.setView(R.id.edt_type, "","Regular");
        editAmount = (EditText) super.setView(R.id.edt_amount, "", "Regular");
        editProject = (Spinner) super.setView(R.id.edt_projet, "", "Regular");
        editIsDepot = (EditText) super.setView(R.id.edt_is_depot, "", "Regular");
        layoutProject = (RelativeLayout) super.findViewById(R.id.layout_project);
        layoutTitle = (TextInputLayout) super.findViewById(R.id.layout_title);
        buttonOk = (Button) super.setView(R.id.dialog_nb_btn_ok, "Ok", "Regular");
    }

    private void setConfigSpinner() {
        ArrayList<Project> listProject = database.listProject();
        String[] allProject = new String[listProject.size()];
        projectIds = new int[listProject.size()];

        if (projectIds.length > 0) {
            idProject = projectIds[0];
            for (int i = 0; i < listProject.size(); i++) {
                allProject[i] = listProject.get(i).getTitle();
                projectIds[i] = listProject.get(i).getId();
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, allProject);
        editProject.setAdapter(adapter);
        editProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idProject = projectIds[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
