package com.gsoft.wallet.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.graphics.drawable.ColorDrawable;
import android.app.Activity;
import android.view.Display;
import android.view.WindowManager;
import com.gsoft.wallet.R;
import com.gsoft.wallet.utils.Utils;
import android.widget.ActionMenuView.LayoutParams;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;
import android.widget.EditText; 


public class SweetDialog{
    public Dialog dialog;
    private Context context;
    private Activity activity;
    private Utils u;
    private int id_lay;
    
    public SweetDialog(Context ctx, int n_id_lay){
        this.context = ctx;
        this.activity = (Activity) context;
        this.dialog = new Dialog(context);
        this.u = new Utils(context);
        this.id_lay = n_id_lay;
        this.initDialog();
    }
    
    private void initDialog(){
        Display display = activity.getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = width;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;      
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(id_lay);
        dialog.setCancelable(true);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setLayout(width-70, LayoutParams.WRAP_CONTENT);
        lp.copyFrom(dialog.getWindow().getAttributes());
        dialog.getWindow().setAttributes(lp);
    }
    
    public View setView(int lay, String text, String font) {
        View view = dialog.findViewById(lay);
        if(view instanceof TextView){
            TextView t = (TextView) view;
            t.setText(text);
        }
        else if(view instanceof Button){
            Button btn = (Button) view;
            btn.setText(text);
        }
        else if(view instanceof EditText){
            EditText edt = (EditText) view;
          
            //edt.setText(text);
        }
        if (font.length() > 1) u.setFont(view, font);
        return view;
    }
      
    public void setLayout(int lay) {
        dialog.setContentView(lay);
    }
    
    public void show(){
        dialog.show();
    }
    
    public void onCancel(int id) {
        Button btn = (Button) setView(id, "Cancel", "Regular");
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dialog.dismiss();
            }
        });
    }
    
    public void dismiss()
    {
        dialog.dismiss();
    }
}



