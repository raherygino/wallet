package com.gsoft.wallet.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import com.gsoft.wallet.R;
import com.gsoft.wallet.model.database.DatabaseHelper;
import com.gsoft.wallet.model.models.Project;
import com.gsoft.wallet.model.models.Transaction;
import com.gsoft.wallet.model.models.Deposit;
import com.gsoft.wallet.utils.EditTextMenu;
import com.gsoft.wallet.utils.Utils;
import com.gsoft.wallet.view.activities.MainActivity;
import com.gsoft.wallet.view.dialog.EditTransactionDialog;
import java.util.Objects;

public class DialogEditTransactionViewModel
{
    private final Context context;
    private final Utils utils;
    private final EditTransactionDialog dialog;
    private final DatabaseHelper database;
    private final MainActivity mainActivity;
    private int transactionId = 0;
    
    public DialogEditTransactionViewModel(EditTransactionDialog dialog) {
        this.context = dialog.context;
        this.dialog = dialog;
        this.utils = new Utils(context);
        this.database = new DatabaseHelper(context);
        this.mainActivity = (MainActivity) context;
        dialog.editType.setText(utils.getString(R.string.in));
        dialog.editType.setOnClickListener(new Onclick());
        dialog.editIsDepot.setOnClickListener(new Onclick());
        dialog.buttonOk.setOnClickListener(new Onclick());
    }
    
    public void setData() {
        
        if (dialog.position != -1) {
            Transaction transaction =  mainActivity.viewModel.listTransaction.get(dialog.position);
            this.transactionId = transaction.getId();
            transaction = database.showTransaction(transactionId);
            this.dialog.editTitle.setText(transaction.getTitle());
            this.dialog.editAmount.setText(transaction.getAmout());
            this.dialog.editType.setText(transaction.getType());

            if (Objects.equals(transaction.getType(), mainActivity.getString(R.string.out)) && database.idTransactionIsDeposit(transactionId)) {
                this.dialog.editIsDepot.setText(mainActivity.getString(R.string.yes));
                this.dialog.layoutProject.setVisibility(View.VISIBLE);
                Project projectByTransaction = database.projectByTransaction(transactionId);
                if (projectByTransaction != null) {
                    this.dialog.idProject = projectByTransaction.getId();
                    for (int i = 0; i < dialog.projectIds.length; i++) {
                        if (dialog.projectIds[i] == projectByTransaction.getId()) {
                            this.dialog.editProject.setSelection(i);
                        }
                    }
                }
            }
        }
    }
    
    class Onclick implements OnClickListener
    {
        @SuppressLint({"NonConstantResourceId", "NotifyDataSetChanged"})
        @Override
        public void onClick(View view)
        {
            utils.btnClick(view);
            int id = view.getId();
            EditText editText = null;

            if (view instanceof EditText) {
                editText = (EditText) view;
            }
            
            switch(id) {
                case R.id.dialog_nb_btn_ok:
                    insertOrUpdate();
                    break;
                   
                case R.id.edt_is_depot:
                case R.id.edt_type:
                    EditTextMenu editTextMenu = null;
                    if (id == R.id.edt_is_depot) {
                        if (dialog.editType.getText().toString().equals(mainActivity.getString(R.string.out))) {
                            editTextMenu = new EditTextMenu(context, editText, R.menu.menu_yes_or_no);
                        } else {
                            assert editText != null;
                            editText.setText(null);
                        }
                        editTextMenu.setLayoutTitle(dialog.layoutTitle);
                    } else {
                        editTextMenu = new EditTextMenu(context, editText, R.menu.type);
                        editTextMenu.setLayoutTitle(dialog.layoutTitle);
                    }

                    if (editTextMenu != null) {
                        editTextMenu.setProjetLayout(dialog.layoutProject);
                        editTextMenu.setEditTextDepot(dialog.editIsDepot);
                    }
                    break;
            }
        }
    }

    public void insertOrUpdate() {
        boolean isDeposit = dialog.editIsDepot.getText().toString().equals(utils.getString(R.string.yes));
        String amount = dialog.editAmount.getText().toString();
        String type = dialog.editType.getText().toString();
        String title = dialog.editTitle.getText().toString();
        if (isDeposit) {
            title = "Depot "+database.getProjectById(dialog.idProject).getTitle();
        }

        if (amount.isEmpty()) {
            dialog.editAmount.setError(utils.getString(R.string.message_amount_invalid));
        } else {

            if (title.isEmpty() && !isDeposit) {
                dialog.editTitle.setError(utils.getString(R.string.message_title_invalid));
            } else {

                if (dialog.position != -1) {
                    Transaction blc_update = new Transaction(title, amount, type, utils.DateSQLFormatNow());
                    database.updateData(transactionId, blc_update);
                    database.deleteDepositByIdProjectTrans(dialog.idProject, transactionId);
                    mainActivity.viewModel.listTransaction.clear();
                    mainActivity.viewModel.listTransaction.addAll(database.listTransaction());
                    mainActivity.viewModel.adapterRecyclerTransaction.notifyDataSetChanged();
                }
                else {
                    Transaction transaction = new Transaction(title, amount, type, utils.DateSQLFormatNow());
                    database.insertData(transaction);
                    mainActivity.viewModel.listTransaction.add(0, transaction);
                    mainActivity.viewModel.adapterRecyclerTransaction.notifyItemInserted(0);
                }
                if (dialog.editIsDepot.getText().toString().equals(utils.getString(R.string.yes)) && type.equals(utils.getString(R.string.out))) {
                    int rest = database.getProjectById(dialog.idProject).getRest();
                    if (rest >= Integer.parseInt(amount)) {
                        database.insertDeposit(new Deposit(0, dialog.idProject, database.getMaxIdTransaction()));
                        dialog.dismiss();
                    } else {
                        dialog.editAmount.setError(utils.getString(R.string.message_rest_under_amount));
                    }
                } else {
                    dialog.dismiss();
                }
                mainActivity.viewModel.refresh();
            }
        }
    }
}
