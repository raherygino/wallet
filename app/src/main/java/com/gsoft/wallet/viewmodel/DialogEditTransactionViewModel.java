package com.gsoft.wallet.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import com.gsoft.wallet.R;
import com.gsoft.wallet.model.database.DatabaseHelper;
import com.gsoft.wallet.model.models.Transaction;
import com.gsoft.wallet.model.models.Deposit;
import com.gsoft.wallet.utils.EditTextMenu;
import com.gsoft.wallet.utils.Utils;
import com.gsoft.wallet.view.activities.MainActivity;
import com.gsoft.wallet.view.dialog.EditTransactionDialog;

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
                    String amount = dialog.editAmount.getText().toString();
                    String type = dialog.editType.getText().toString();
                    String title = dialog.editTitle.getText().toString();
                    Transaction blc = new Transaction(title, amount, type, utils.DateSQLFormatNow());

                    if (amount.isEmpty()) {
                        utils.toast(utils.getString(R.string.message_amount_invalid));
                    } else {

                        if (title.isEmpty()) {
                            utils.toast(utils.getString(R.string.message_title_invalid));
                        } else {

                            if (dialog.position != -1) {
                                Transaction blc_update = new Transaction(title, amount, type, utils.DateSQLFormatNow());
                                database.updateData(transactionId, blc_update);
                                mainActivity.viewModel.listTransaction.clear();
                                mainActivity.viewModel.listTransaction.addAll(database.listTransaction());
                                mainActivity.viewModel.adapterRecyclerTransaction.notifyDataSetChanged();
                            }
                            else {
                                database.insertData(blc);
                                mainActivity.viewModel.listTransaction.add(0, blc);
                                mainActivity.viewModel.adapterRecyclerTransaction.notifyItemInserted(0);

                                if (dialog.editIsDepot.getText().toString().equals(utils.getString(R.string.ok))) {
                                    database.insertDeposit(new Deposit(0, dialog.idProject, database.getMaxIdTransaction()));
                                }
                            }
                            mainActivity.viewModel.refresh();
                            dialog.dismiss();
                        }
                    }
                   break;
                   
                case R.id.edt_is_depot:
                case R.id.edt_type:
                    EditTextMenu editTextMenu = null;
                    if (id == R.id.edt_is_depot) {
                        editTextMenu = new EditTextMenu(context, editText, R.menu.menu_yes_or_no);
                    } else {
                        editTextMenu = new EditTextMenu(context, editText, R.menu.type);
                    }

                    editTextMenu.setProjetLayout(dialog.layoutProject);
                    editTextMenu.setEditTextDepot(dialog.editIsDepot);
                    break;
            }
        }
    }
}
