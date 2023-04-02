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
    private final DatabaseHelper database;
    private final Utils utils;
    private final MainActivity mainActivity;
    private int id_project;

    public DialogEditProjectViewModel(EditProjectDialog editProjectDialog) {
        Context context = editProjectDialog.context;
        utils = new Utils(context);
        database = new DatabaseHelper(context);
        mainActivity = (MainActivity) context;
        dialog = editProjectDialog;
        dialog.buttonOk.setOnClickListener(new onClickListner());
        dialog.editPriorityProject.setOnClickListener(new onClickListner());
    }

    public void setData(int id ) {
        if (id != 0) {
            id_project = id;
            Project project = database.getProjectById(id);
            dialog.editTitleProject.setText(project.getTitle());
            dialog.editTypeProject.setText(project.getType());
            dialog.editPriorityProject.setText(utils.getValueByPriority(project.getPriority()));
            dialog.editTargetProject.setText(String.valueOf(project.getTarget()));
            dialog.editDepositProject.setText(String.valueOf(project.getDeposit()));
            dialog.buttonOk.setText(mainActivity.getString(R.string.validate));
        }
    }
    class onClickListner implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            int id = view.getId();
            if (id == R.id.dialog_np_btn_ok) {
                String title = dialog.editTitleProject.getText().toString();
                String type = dialog.editTypeProject.getText().toString();

                int priority = utils.getPriorityByValue(dialog.editPriorityProject.getText().toString());
                int target = Integer.parseInt(dialog.editTargetProject.getText().toString());
                int deposit = Integer.parseInt(dialog.editDepositProject.getText().toString());

                Project project = new Project(title,type,priority,target,deposit);

                if (dialog.buttonOk.getText() == mainActivity.getString(R.string.validate)) {
                    project.setId(id_project);
                    database.updateProject(project);
                    mainActivity.viewModel.refreshProject();
                } else {
                    database.insertProject(project);
                    mainActivity.viewModel.listProject.add(0, project);
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
