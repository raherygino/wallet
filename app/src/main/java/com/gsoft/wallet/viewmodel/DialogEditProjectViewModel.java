package com.gsoft.wallet.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.gsoft.wallet.model.database.DatabaseHelper;
import com.gsoft.wallet.model.models.Project;
import com.gsoft.wallet.utils.EditTextMenu;
import com.gsoft.wallet.utils.Utils;
import com.gsoft.wallet.view.activities.MainActivity;
import com.gsoft.wallet.view.dialog.EditProjectDialog;
import com.gsoft.wallet.R;

public class DialogEditProjectViewModel {

    @SuppressLint("StaticFieldLeak")
    private static EditProjectDialog dialog;
    private final DatabaseHelper db;
    private final Utils utils;
    private final MainActivity mainActivity;
    private int id_project;

    public DialogEditProjectViewModel(EditProjectDialog editProjectDialog) {
        Context context = editProjectDialog.context;
        utils = new Utils(context);
        db = new DatabaseHelper(context);
        mainActivity = (MainActivity) context;
        dialog = editProjectDialog;
        dialog.dialog_np_btn_ok.setOnClickListener(new onClickListner());
        dialog.edt_priority_project.setOnClickListener(new onClickListner());
    }

    public void setData(int id ) {
        if (id != 0) {
            id_project = id;
            Project project = db.getProjectById(id);
            dialog.edt_title_project.setText(project.getTitle());
            dialog.edt_type_project.setText(project.getType());
            dialog.edt_priority_project.setText(utils.getValueByPriority(project.getPriority()));
            dialog.edt_target_project.setText(String.valueOf(project.getTarget()));
            dialog.edt_diposit_project.setText(String.valueOf(project.getDeposit()));
            dialog.dialog_np_btn_ok.setText(mainActivity.getString(R.string.validate));
        }
    }
    class onClickListner implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            int id = view.getId();
            if (id == R.id.dialog_np_btn_ok) {
                String title = dialog.edt_title_project.getText().toString();
                String type = dialog.edt_type_project.getText().toString();
                int priority = utils.getPriorityByValue(dialog.edt_priority_project.getText().toString());
                int target = Integer.parseInt(dialog.edt_target_project.getText().toString());
                int deposit = Integer.parseInt(dialog.edt_diposit_project.getText().toString());
                Project project = new Project(title,type,priority,target,deposit);

                if (dialog.dialog_np_btn_ok.getText() == mainActivity.getString(R.string.validate)) {
                    project.setId(id_project);
                    db.updateProject(project);
                    mainActivity.viewModel.refreshProject();
                } else {
                    db.insertProject(project);
                    mainActivity.viewModel.list_project.add(0, project);
                    mainActivity.viewModel.adapterRecycler.notifyItemInserted(0);
                    mainActivity.viewModel.refreshProject();
                }
                dialog.dismiss();
            } else if (id == R.id.edt_priority_project) {
                EditText editText = (EditText) view;
                new EditTextMenu(dialog.context, editText, R.menu.priority_menu);
            }
        }
    }
}
