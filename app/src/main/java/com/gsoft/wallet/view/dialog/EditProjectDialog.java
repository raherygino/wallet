package com.gsoft.wallet.view.dialog;

import static com.gsoft.wallet.utils.Utils.LIGHT;
import android.app.Activity;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import com.gsoft.wallet.R;
import com.gsoft.wallet.viewmodel.DialogEditProjectViewModel;


public class EditProjectDialog extends SweetDialog{

    public EditText editTitleProject, editTypeProject, editPriorityProject, editTargetProject;
    public Button buttonOk;
    public Context context;
    private final Activity activity;
    public DialogEditProjectViewModel viewModel;

    public EditProjectDialog(Context context) {
        super(context, R.layout.dialog_new_project_layout);
        super.onCancel(R.id.dialog_np_btn_cancel);
        this.context = context;
        this.activity = (Activity) context;
        this.initView();
        this.viewModel = new DialogEditProjectViewModel(this);
    }

    private void initView() {
        editTitleProject = (EditText) setView(R.id.edt_title_project, null, LIGHT);
        editTypeProject = (EditText) setView(R.id.edt_type_project, null, LIGHT);
        editPriorityProject = (EditText) setView(R.id.edt_priority_project, null, LIGHT);
        editTargetProject = (EditText) setView(R.id.edt_target_project, null, LIGHT);
        buttonOk = (Button) setView(R.id.dialog_np_btn_ok, activity.getString(R.string.ok), LIGHT);
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
