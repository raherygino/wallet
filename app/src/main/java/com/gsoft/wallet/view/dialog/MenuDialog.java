package com.gsoft.wallet.view.dialog;

import static com.gsoft.wallet.utils.Utils.LIGHT;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import com.gsoft.wallet.R;

public class MenuDialog extends SweetDialog{
    private Context context;
    public MenuDialog(Context context) {
        super(context, R.layout.dialog_menu_new);
        this.context = context;
        Activity activity = (Activity) context;
        Button itemNewProject = (Button) super.setView(R.id.item_new_project,activity.getString(R.string.project), LIGHT);
        Button itemNewTransasction = (Button) super.setView(R.id.item_new_trans,activity.getString(R.string.transaction), LIGHT);
        itemNewProject.setOnClickListener(new OnClick());
        itemNewTransasction.setOnClickListener(new OnClick());
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
            dialog.dismiss();
            switch (id) {
                case R.id.item_new_project:
                    EditProjectDialog editProject = new EditProjectDialog(context);
                    editProject.show();
                    break;

                case R.id.item_new_trans:
                    EditTransactionDialog newBalance = new EditTransactionDialog(context);
                    newBalance.setPosition(-1);
                    newBalance.show();
                    break;
            }
        }
    }
}
