package com.gsoft.wallet.view.dialog;
import android.app.Dialog;
import android.content.Context;

import android.widget.Button;
import android.widget.EditText;

import com.gsoft.wallet.R;
import com.gsoft.wallet.viewmodel.DialogEditBalanceViewModel;

public class DialogEditBalance extends SweetDialog
{
    public Dialog dialog;
    public String TITLE, AMOUNT, TYPE;
    public EditText edt_title, edt_amount, edt_type;
    public Button BTN_OK;
    public DialogEditBalanceViewModel viewModel;
    public Context context;
    public int position = -1;

    public DialogEditBalance(Context ctx) {
        super(ctx, R.layout.dialog_new_balance_layout);
        this.context = ctx;
        this.initView();
        this.viewModel = new DialogEditBalanceViewModel(this);
        super.onCancel(R.id.dialog_nb_btn_cancel);
    }

    public void setPosition(int pos) {
        this.position = pos;
    }
    
    private void initView() {
        edt_title = (EditText) super.setView(R.id.edt_title, "","Regular");
        edt_type = (EditText) super.setView(R.id.edt_type, "","Regular");
        edt_amount = (EditText) super.setView(R.id.edt_amount, "", "Regular");
        BTN_OK = (Button) super.setView(R.id.dialog_nb_btn_ok, "Ok", "Regular"); 
    }
    
    public void getData() {
        TITLE = edt_title.getText().toString();
        AMOUNT = edt_amount.getText().toString();
        TYPE = edt_type.getText().toString();
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
