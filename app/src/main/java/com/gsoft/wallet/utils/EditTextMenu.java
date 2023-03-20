package com.gsoft.wallet.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.PopupMenu;

import com.gsoft.wallet.R;

public class EditTextMenu {
    public EditTextMenu(Context context, EditText edt, int menu) {
        PopupMenu popupMenu = new PopupMenu(context, edt);
        popupMenu.getMenuInflater().inflate(menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                edt.setText(item.getTitle().toString());
                return true;
            }
        });
        popupMenu.show();
    }
}
