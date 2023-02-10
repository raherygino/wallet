package com.gsoft.wallet.view.dialog;
import android.app.Dialog;
import android.content.Context;
import com.gsoft.wallet.R;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.view.View;
import com.gsoft.wallet.viewmodel.DialogEditBalanceViewModel;

public class DialogEditBalance extends SweetDialog
{
    public Dialog dialog;
    public String TITLE, AMOUNT, TYPE;
    public EditText edt_title, edt_amount, edt_type;
    public Button BTN_OK;
    private DialogEditBalanceViewModel viewModel;
    public Context context;

    public DialogEditBalance(Context ctx) {
        super(ctx, R.layout.dialog_new_balance_layout);
        this.context = ctx;
        this.initView();
        this.viewModel = new DialogEditBalanceViewModel(this);
        super.onCancel(R.id.dialog_nb_btn_cancel);
    }
    /*public DialogEditBalance(Context ctx){

        this.viewModel = new DialogEditBalanceViewModel(this);
        this.context = ctx;
       // super(ctx,R.layout.dialog_new_balance_layout);
        this.initView();
        super.onCancel(R.id.dialog_nb_btn_cancel);
    }*/
    
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
