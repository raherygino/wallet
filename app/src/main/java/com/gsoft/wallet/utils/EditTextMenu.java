package com.gsoft.wallet.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import com.gsoft.wallet.R;

public class EditTextMenu {
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

                switch (id) {
                    case R.id.yes:
                        layoutProject.setVisibility(View.VISIBLE);
                        break;

                    case R.id.no:
                        layoutProject.setVisibility(View.GONE);
                        break;

                    case R.id.outcome:
                        isDepot.setEnabled(true);
                        isDepot.setText(activity.getString(R.string.yes));
                        break;

                    case R.id.income:
                        isDepot.setEnabled(false);
                        layoutProject.setVisibility(View.GONE);
                        isDepot.setText(activity.getString(R.string.no));
                        break;
                }
                edt.setText(item.getTitle().toString());
                return true;
            }
        });
        popupMenu.show();
    }

    public void setProjetLayout(RelativeLayout layoutProject) {
        this.layoutProject = layoutProject;
    }
    public void setEditTextDepot(EditText isDepot) {
        this.isDepot = isDepot;
    }
}
