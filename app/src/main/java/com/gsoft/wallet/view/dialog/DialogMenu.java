package com.gsoft.wallet.view.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.gsoft.wallet.R;

public class DialogMenu extends SweetDialog{

    private Context context;
    public DialogMenu(Context ctx) {
        super(ctx, R.layout.dialog_menu_new);
        context = ctx;
        Button item_new_project = (Button) super.setView(R.id.item_new_project,"Project", "Light");
        Button item_new_trans = (Button) super.setView(R.id.item_new_trans,"Transaction", "Light");
        item_new_project.setOnClickListener(new OnClick());
        item_new_trans.setOnClickListener(new OnClick());
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    class OnClick implements View.OnClickListener {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.item_new_project:
                    dialog.dismiss();
                    DialogEditProject editProject = new DialogEditProject(context);
                    editProject.show();
                    break;
                case R.id.item_new_trans:
                    dialog.dismiss();
                    DialogEditBalance n_balance = new DialogEditBalance(context);
                    n_balance.setPosition(-1);
                    n_balance.show();
                    break;
            }
        }
    }
}
