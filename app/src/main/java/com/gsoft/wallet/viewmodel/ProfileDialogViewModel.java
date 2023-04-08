package com.gsoft.wallet.viewmodel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.gsoft.wallet.R;
import com.gsoft.wallet.model.database.DatabaseHelper;
import com.gsoft.wallet.model.models.User;
import com.gsoft.wallet.utils.Utils;
import com.gsoft.wallet.view.activities.MainActivity;
import com.gsoft.wallet.view.dialog.ProfileDialog;

import java.util.Objects;

public class ProfileDialogViewModel {

    private ProfileDialog profileDialog;
    private Activity activity;
    private Utils utils;

    public ProfileDialogViewModel(ProfileDialog profileDialog) {
        this.profileDialog = profileDialog;
        this.activity = (Activity) profileDialog.context;
        this.utils = profileDialog.utils;
        this.setActionView();
        this.onCancelListener(activity);
    }

    public void setActionView() {
        profileDialog.buttonOk.setOnClickListener(new onClick());
    }

    class onClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String lastName = profileDialog.lastName.getText().toString();
            String firstName = profileDialog.firstName.getText().toString();
            String email = profileDialog.email.getText().toString();
            boolean lastNameIsValidate = !lastName.isEmpty();
            boolean firstNameIsValidate = !firstName.isEmpty();
            boolean emailIsValidate = !email.isEmpty();

            if (lastNameIsValidate && firstNameIsValidate && emailIsValidate) {
                User user = new User(firstName, lastName, email);
                DatabaseHelper database = new DatabaseHelper(activity);
                database.insertUser(user);
                profileDialog.dismiss();
                activity.startActivity(new Intent(activity, MainActivity.class));
                activity.finish();
            } else {
                if (!lastNameIsValidate) {
                    profileDialog.lastName.setError("Nom invalide");
                }
                if (!firstNameIsValidate) {
                    profileDialog.firstName.setError("Prénom invalide");
                }
                if (!emailIsValidate) {
                    profileDialog.email.setError("Email invalide");
                }
            }
        }
    }

    public void onCancelListener(Activity activity) {
        profileDialog.dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                finishActivity(activity);
            }
        });
    }
    public void finishActivity(Activity activity) {
        activity.finish();
    }
}
