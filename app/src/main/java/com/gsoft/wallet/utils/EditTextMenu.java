package com.gsoft.wallet.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.appcompat.widget.AppCompatSpinner;

import com.gsoft.wallet.R;


public class EditTextMenu {
    private Spinner projet;
    private EditText isDepot;
    private RelativeLayout layoutProject;
    public EditTextMenu(Context context, EditText edt, int menu) {
        PopupMenu popupMenu = new PopupMenu(context, edt);
        popupMenu.getMenuInflater().inflate(menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                edt.setText(item.getTitle().toString());
                int id = item.getItemId();
                if (id == R.id.yes) {
                    layoutProject.setVisibility(View.VISIBLE);
                } else if (id == R.id.no){
                    layoutProject.setVisibility(View.GONE);
                } else if (id == R.id.outcome) {
                    isDepot.setEnabled(true);
                    isDepot.setText("Non");
                } else if (id == R.id.income) {
                    isDepot.setEnabled(false);
                    layoutProject.setVisibility(View.GONE);
                    isDepot.setText("Non");
                }
                return true;
            }
        });
        popupMenu.show();
    }

    public void setProjet(Spinner projet, RelativeLayout layoutProject) {
        this.projet = projet;
        this.layoutProject = layoutProject;
    }
    public void setEditTextDepot(EditText isDepot) {
        this.isDepot = isDepot;
    }
}
