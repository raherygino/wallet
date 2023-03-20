package com.gsoft.wallet.view.dialog;

import static com.gsoft.wallet.utils.Utils.LIGHT;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;

import com.gsoft.wallet.R;
import com.gsoft.wallet.viewmodel.DialogEditProjectViewModel;


public class DialogEditProject extends SweetDialog{

    public EditText edt_title_project, edt_type_project, edt_priority_project, edt_diposit_project, edt_target_project;
    public Button dialog_np_btn_ok;
    public Context context;
    public DialogEditProjectViewModel viewModel;

    public DialogEditProject(Context ctx) {
        super(ctx, R.layout.dialog_new_project_layout);
        super.onCancel(R.id.dialog_np_btn_cancel);
        this.context = ctx;
        this.initView();
        this.viewModel = new DialogEditProjectViewModel(this);
    }

    private void initView() {
        edt_title_project = (EditText) setView(R.id.edt_title_project, null, LIGHT);
        edt_type_project = (EditText) setView(R.id.edt_type_project, null, LIGHT);
        edt_priority_project = (EditText) setView(R.id.edt_priority_project, null, LIGHT);
        edt_diposit_project = (EditText) setView(R.id.edt_diposit_project, null, LIGHT);
        edt_target_project = (EditText) setView(R.id.edt_target_project, null, LIGHT);
        dialog_np_btn_ok = (Button) setView(R.id.dialog_np_btn_ok, "Ok", LIGHT);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
