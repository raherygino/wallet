package com.gsoft.wallet.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;

import com.google.android.material.textfield.TextInputLayout;
import com.gsoft.wallet.R;
import com.gsoft.wallet.model.database.DatabaseHelper;
import com.gsoft.wallet.model.models.Project;

import java.util.ArrayList;

public class EditTextMenu {
    private TextInputLayout layoutTitle;
    private EditText isDepot;
    private RelativeLayout layoutProject;
    public EditTextMenu(Context context, EditText edt, int menu) {
        Activity activity = (Activity) context;

        PopupMenu popupMenu = new PopupMenu(context, edt);
        popupMenu.getMenuInflater().inflate(menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                int id = item.getItemId();
                edt.setText(item.getTitle().toString());

                switch (id) {
                    case R.id.yes:
                        ArrayList<Project> listProject = new DatabaseHelper(context).listProject();
                        int countProject = listProject.size();
                        if (countProject > 0) {
                            layoutProject.setVisibility(View.VISIBLE);
                            layoutTitle.setVisibility(View.GONE);
                        } else {
                            new Utils(context).toast(((Activity)context).getString(R.string.message_no_project));
                            layoutProject.setVisibility(View.GONE);
                            layoutTitle.setVisibility(View.VISIBLE);
                            edt.setText(activity.getString(R.string.no));
                        }
                        break;

                    case R.id.no:
                        layoutProject.setVisibility(View.GONE);
                        layoutTitle.setVisibility(View.VISIBLE);
                        break;

                    case R.id.outcome:
                        isDepot.setEnabled(true);
                        isDepot.setText(activity.getString(R.string.no));
                        layoutTitle.setVisibility(View.VISIBLE);
                        break;

                    case R.id.income:
                        isDepot.setEnabled(false);
                        layoutProject.setVisibility(View.GONE);
                        layoutTitle.setVisibility(View.VISIBLE);
                        isDepot.setText(activity.getString(R.string.no));
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    public void setLayoutTitle(TextInputLayout layoutTitle) {
        this.layoutTitle = layoutTitle;
    }

    public void setProjetLayout(RelativeLayout layoutProject) {
        this.layoutProject = layoutProject;
    }
    public void setEditTextDepot(EditText isDepot) {
        this.isDepot = isDepot;
    }
}
