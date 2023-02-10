package com.gsoft.wallet.viewmodel;
import android.content.Context;
import com.gsoft.wallet.view.dialog.DialogEditBalance;
import android.view.View;
import com.gsoft.wallet.utils.Utils;
import com.gsoft.wallet.model.models.Balance;
import com.gsoft.wallet.model.database.DatabaseHelper;
import com.gsoft.wallet.view.activities.MainActivity;
import android.view.View.OnClickListener;
import com.gsoft.wallet.R;
import android.widget.PopupMenu;
import android.view.MenuItem;
import android.widget.EditText;

public class DialogEditBalanceViewModel
{
    private Context context;
    private Utils u;
    private DialogEditBalance dlg;
    private DatabaseHelper db;
    public DialogEditBalanceViewModel(DialogEditBalance dialog) {
        this.context = dialog.context;
        this.dlg = dialog;
        this.u = new Utils(context);
        this.db = new DatabaseHelper(context);
        dialog.edt_type.setText("Entrée");
        dialog.edt_type.setOnClickListener(new Onclick());
        dialog.BTN_OK.setOnClickListener(new Onclick());
        
    }
    class Onclick implements View.OnClickListener
    {

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
                    if (amount.isEmpty()) {
                        u.msg("Montant invalide");
                    }
                    else {
                        if (title.isEmpty()) {
                            u.msg("Titre invalide");
                        }
                        else {
                            db.insertData(blc);
                            MainActivity mActivity = (MainActivity) context;
                            mActivity.viewModel.list.add(0, blc);
                            mActivity.viewModel.adapterRecyclerTransaction.notifyItemChanged(0);
                            mActivity.viewModel.refresh();
                            dlg.dismiss();
                        }
                    }
                    
                   break;
                   
                case R.id.edt_type:
                    final EditText edt = (EditText) v;
                    PopupMenu popupMenu = new PopupMenu(context, v);
                    popupMenu.getMenuInflater().inflate(R.menu.type, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem p1)
                        {
                            switch (p1.getItemId()) {
                                case R.id.income:
                                    edt.setText("Entrée");
                                    break;
                                case R.id.outcome:
                                    edt.setText("Sortie");
                                    break;
                            }

                            return true;
                        }
                    });
                    popupMenu.show();
                    break;
            }
        }

        
    }
}
