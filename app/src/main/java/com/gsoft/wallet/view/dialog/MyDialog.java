package com.gsoft.wallet.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.graphics.drawable.ColorDrawable;
import android.app.Activity;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ActionMenuView.LayoutParams;
import android.widget.Toast;

import com.gsoft.wallet.R;
import com.gsoft.wallet.utils.Utils;

public class MyDialog{
    Dialog dialog;
    Context context;
    Activity activity;
    Utils u;
    public MyDialog(Context ctx, String n_title, String n_desc){
        context = ctx;
        activity = (Activity) context;
        dialog = new Dialog(ctx);
        u = new Utils(context);
        Display display = activity.getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = width;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;      
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.setCancelable(true);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
       /* TextView title = dialog.findViewById(R.id.dialog_title);
        TextView description = dialog.findViewById(R.id.dialog_desc);
        Button btn_cancel = dialog.findViewById(R.id.dialog_btn_cancel);
        Button btn_ok = dialog.findViewById(R.id.dialog_btn_ok);
        u.setTextFont(title, "SemiBold");
        u.setTextFont(description, "Light");
        u.setButtonFont(btn_cancel, "Light");
        u.setButtonFont(btn_ok, "Light");
        title.setText(n_title);
        description.setText(n_desc);*/
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(width-70, LayoutParams.WRAP_CONTENT);
        lp.copyFrom(dialog.getWindow().getAttributes());
        dialog.getWindow().setAttributes(lp);       
    }
   
    public void hello(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}



