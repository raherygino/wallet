package com.gsoft.wallet.view.dialog;
import android.content.Context;
import com.gsoft.wallet.R;
import android.widget.TextView;
import android.widget.Button;

public class ConfirmDialog extends SweetDialog
{
    private String n_title, n_desc;
    public Button btn_1, btn_2;
    
    public ConfirmDialog(Context ctx, String title, String message)
    {
        super(ctx, R.layout.dialog_layout);
        this.n_title = title;
        this.n_desc = message;
        this.initView();
    }
    
    private void initView() 
    {
        super.setView(R.id.dialog_title, n_title,"SemiBold");
        super.setView(R.id.dialog_desc, n_desc,"Regular");
        this.btn_1 = (Button) super.setView(R.id.dialog_btn_cancel, "Annuler", "Light");
        this.btn_2 = (Button) super.setView(R.id.dialog_btn_ok, "Ok", "Light");
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
