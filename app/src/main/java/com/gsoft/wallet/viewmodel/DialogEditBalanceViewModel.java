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

public class DialogEditBalanceViewModel
{
    private final Context context;
    private final Utils utils;
    private final EditTransactionDialog dialog;
    private final DatabaseHelper database;
    private final MainActivity mainActivity;
    private int balance_id = 0;
    
    public DialogEditBalanceViewModel(EditTransactionDialog dialog) {
        this.context = dialog.context;
        this.dialog = dialog;
        this.utils = new Utils(context);
        this.database = new DatabaseHelper(context);
        this.mainActivity = (MainActivity) context;
        dialog.edt_type.setText("Entrée");
        dialog.edt_type.setOnClickListener(new Onclick());
        dialog.edt_is_depot.setOnClickListener(new Onclick());
        dialog.BTN_OK.setOnClickListener(new Onclick());
    }
    
    public void setData() {
        
        if (dialog.position != -1) {
            Transaction blc =  mainActivity.viewModel.list.get(dialog.position);
            this.balance_id = blc.getId();
            blc = database.show(balance_id);
            this.dialog.edt_title.setText(blc.getTitle());
            this.dialog.edt_amount.setText(blc.getAmout());
            this.dialog.edt_type.setText(blc.getType());
        }
    }
    
    class Onclick implements OnClickListener
    {

        @SuppressLint({"NonConstantResourceId", "NotifyDataSetChanged"})
        @Override
        public void onClick(View v)
        {
            utils.btnClick(v);
            int id = v.getId();
            EditText editText = null;
            if (v instanceof EditText) {
                editText = (EditText) v;
            }
            
            switch(id) {
                case R.id.dialog_nb_btn_ok:
                    String amount = dialog.edt_amount.getText().toString();
                    String type = dialog.edt_type.getText().toString();
                    String title = dialog.edt_title.getText().toString();
                    Transaction blc = new Transaction(title, amount, type, utils.DateSQLFormatNow());
                    if (amount.isEmpty()) { utils.msg("Montant invalide"); }
                    else {
                        if (title.isEmpty()) { utils.msg("Titre invalide"); }
                        else {

                            if (dialog.position != -1) {
                                Transaction blc_update = new Transaction(title, amount, type, utils.DateSQLFormatNow());
                                database.updateData(balance_id, blc_update);
                                mainActivity.viewModel.list.clear();
                                mainActivity.viewModel.list.addAll(database.listData());
                                mainActivity.viewModel.adapterRecyclerTransaction.notifyDataSetChanged();
                            }
                            else {
                                database.insertData(blc);
                                mainActivity.viewModel.list.add(0, blc);
                                mainActivity.viewModel.adapterRecyclerTransaction.notifyItemInserted(0);
                                if (dialog.edt_is_depot.getText().toString().equals("Oui")) {
                                    database.insertDeposit(new Deposit(0, dialog.idProject, database.maxId()));
                                    utils.msg("ok"+ database.maxId());
                                }
                                utils.msg(""+database.listDeposit().size());
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
                    editTextMenu.setProjet(dialog.edt_projet, dialog.layout_project);
                    editTextMenu.setEditTextDepot(dialog.edt_is_depot);
                    editTextMenu.setEditTextDepot(dialog.edt_is_depot);
                    break;
            }
        }

        
    }
}
