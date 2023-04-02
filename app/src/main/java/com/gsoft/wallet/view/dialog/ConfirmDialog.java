package com.gsoft.wallet.view.dialog;

import android.app.Activity;
import android.content.Context;
import android.widget.Button;
import com.gsoft.wallet.R;
import com.gsoft.wallet.utils.Utils;

public class ConfirmDialog extends SweetDialog
{
    private final String title;
    private final String description;
    public Button buttonCancel, buttonOk;
    private final Activity activity;
    
    public ConfirmDialog(Context context, String title, String message)
    {
        super(context, R.layout.dialog_layout);
        this.activity = (Activity) context;
        this.title = title;
        this.description = message;
        this.initView();
    }
    
    private void initView() 
    {
        super.setView(R.id.dialog_title, title, Utils.SEMI_BOLD);
        super.setView(R.id.dialog_desc, description,Utils.REGULAR);
        this.buttonCancel = (Button) super.setView(R.id.dialog_btn_cancel, activity.getString(R.string.cancel), Utils.LIGHT);
        this.buttonOk = (Button) super.setView(R.id.dialog_btn_ok, activity.getString(R.string.ok), Utils.LIGHT);
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
