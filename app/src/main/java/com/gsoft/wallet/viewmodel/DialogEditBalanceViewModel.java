package com.gsoft.wallet.viewmodel;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupMenu;
import android.view.MenuItem;
import android.widget.EditText;

import com.gsoft.wallet.R;
import com.gsoft.wallet.model.database.DatabaseHelper;
import com.gsoft.wallet.model.models.Balance;
import com.gsoft.wallet.utils.EditTextMenu;
import com.gsoft.wallet.utils.Utils;
import com.gsoft.wallet.view.activities.MainActivity;
import com.gsoft.wallet.view.dialog.DialogEditBalance;

public class DialogEditBalanceViewModel
{
    private Context context;
    private Utils u;
    private DialogEditBalance dlg;
    private DatabaseHelper db;
    private MainActivity mActivity;
    private int balance_id = 0;
    
    public DialogEditBalanceViewModel(DialogEditBalance dialog) {
        this.context = dialog.context;
        this.dlg = dialog;
        this.u = new Utils(context);
        this.db = new DatabaseHelper(context);
        this.mActivity = (MainActivity) context;
        dialog.edt_type.setText("Entrée");
        dialog.edt_type.setOnClickListener(new Onclick());
        dialog.BTN_OK.setOnClickListener(new Onclick());
        
    }
    
    public void setData() {
        
        if (dlg.position != -1) {
            Balance blc =  mActivity.viewModel.list.get(dlg.position);
            this.balance_id = blc.getId();
            blc = db.show(balance_id);
            this.dlg.edt_title.setText(blc.getTitle());
            this.dlg.edt_amount.setText(blc.getAmout());
            this.dlg.edt_type.setText(blc.getType());
        }
    }
    
    class Onclick implements OnClickListener
    {

        @SuppressLint({"NonConstantResourceId", "NotifyDataSetChanged"})
        @Override
        public void onClick(View v)
        {
            u.btnClick(v);
            int id = v.getId();
            
            switch(id) {
                case R.id.dialog_nb_btn_ok:
                    String amount = dlg.edt_amount.getText().toString();
                    String type = dlg.edt_type.getText().toString();
                    String title = dlg.edt_title.getText().toString();
                    Balance blc = new Balance(title, amount, type, u.DateSQLFormatNow());
                    if (amount.isEmpty()) { u.msg("Montant invalide"); }
                    else {
                        if (title.isEmpty()) { u.msg("Titre invalide"); }
                        else {

                            if (dlg.position != -1) {
                                Balance blc_update = new Balance(title, amount, type, u.DateSQLFormatNow());
                                db.updateData(balance_id, blc_update);
                                mActivity.viewModel.list.clear();
                                mActivity.viewModel.list.addAll(db.listData());
                                mActivity.viewModel.adapterRecyclerTransaction.notifyDataSetChanged();
                            }
                            else {
                                db.insertData(blc);
                                mActivity.viewModel.list.add(0, blc);
                                mActivity.viewModel.adapterRecyclerTransaction.notifyItemInserted(0);
                            }
                            mActivity.viewModel.refresh();
                            dlg.dismiss();
                        }
                    }
                    
                   break;
                   
                case R.id.edt_type:
                    final EditText edt = (EditText) v;
                    new EditTextMenu(context, edt, R.menu.type);
                    break;
            }
        }

        
    }
}
