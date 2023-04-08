package com.gsoft.wallet.view.dialog;

import static com.gsoft.wallet.utils.Utils.LIGHT;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gsoft.wallet.R;
import com.gsoft.wallet.utils.Utils;
import com.gsoft.wallet.viewmodel.ProfileDialogViewModel;

public class ProfileDialog extends SweetDialog{

    public Button buttonOk, buttonCancel;
    public EditText firstName, lastName, email;
    public Utils utils;
    public Context context;
    public ProfileDialogViewModel viewModel;
    public ProfileDialog(Context context) {
        super(context, R.layout.dialog_profile);
        super.onCancel(R.id.profile_btn_cancel);
        this.utils = new Utils(context);
        this.context = context;
        this.initView();
        this.viewModel = new ProfileDialogViewModel(this);
    }

    public void initView() {
        firstName = (EditText) super.setView(R.id.edit_first_name, null, LIGHT);
        lastName = (EditText) super.setView(R.id.edit_last_name, null, LIGHT);
        email = (EditText) super.setView(R.id.edit_email, null, LIGHT);
        buttonOk = (Button) super.setView(R.id.profile_btn_ok, utils.getString(R.string.ok), LIGHT);
        buttonCancel = (Button) super.setView(R.id.profile_btn_cancel, utils.getString(R.string.cancel), LIGHT);
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
