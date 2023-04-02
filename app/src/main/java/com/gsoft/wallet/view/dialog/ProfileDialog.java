package com.gsoft.wallet.view.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;

import com.gsoft.wallet.R;

public class ProfileDialog extends SweetDialog{

    public ProfileDialog(Context context) {
        super(context, R.layout.dialog_profile);
        onCancelListener((Activity) context);
    }

    public void onCancelListener(Activity activity) {
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                /*Do something*/
            }
        });
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
