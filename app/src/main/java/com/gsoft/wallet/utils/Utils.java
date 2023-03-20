package com.gsoft.wallet.utils;

import android.content.Context;
import android.widget.TextView;
import android.graphics.Typeface;
import android.widget.Button;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Calendar;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.Activity;
import java.io.File;
import android.os.Environment;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import android.widget.ImageView;
import androidx.core.content.ContextCompat;

import com.gsoft.wallet.R;
import com.gsoft.wallet.model.database.DatabaseHelper;
import com.gsoft.wallet.model.models.Balance;
import com.gsoft.wallet.view.dialog.ConfirmDialog;

public class Utils{
    
    private Context context;
    private String fonts = "Poppins_";
    private ConfirmDialog dialog;
    private Activity activity;
    public static String SEMI_BOLD = "SemiBold";
    public static String REGULAR = "Regular";
    public static String LIGHT = "Light";
    
    public Utils(Context ctx){ 
        this.context = ctx; 
        this.activity = (Activity) context;
    }
    
    public void setFont(View view,String font) {
        String fnts = fonts+font+".ttf";
        Typeface tface = Typeface.createFromAsset(context.getAssets(),fnts); 
        if (view instanceof Button) {
            Button btn = (Button) view;
            btn.setTypeface(tface);
        }
        else if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setTypeface(tface);
        }
    }


    public int getPriorityByValue(String value) {
        int priority = 0;
        if (Objects.equals(value, activity.getString(R.string.high))) {
            priority = 1;
        } else if (Objects.equals(value, activity.getString(R.string.middle))) {
            priority = 2;
        } else {
            priority = 3;
        }
        return priority;
    }
    public String getValueByPriority(int priority) {
        String value = "";
        switch (priority) {
            case 1:
                value = activity.getString(R.string.high);
                break;
            case 2:
                value = activity.getString(R.string.middle);
                break;
            case 3:
                value = activity.getString(R.string.low);
                break;
        }
        return value;
    }

    public void btnClick(View v){
      final Animation anim = AnimationUtils.loadAnimation(context, R.anim.btn_click);
      v.startAnimation(anim);
    }
    
    public void msg(String mssg){
        Toast.makeText(context, mssg, Toast.LENGTH_SHORT).show();
    }
    
    public String formatDate(String od) {
        String[] months = new String[12];
        months[0] = "Jan";
        months[1] = "Fev";
        months[2] = "Mar";
        months[3] = "Avr";
        months[4] = "Mai";
        months[5] = "Juin";
        months[6] = "Juil";
        months[7] = "Aout";
        months[8] = "Sept";
        months[9] = "Oct";
        months[10] = "Nov";
        months[11] = "Déc";
        String[] dd = od.split("-");
        String y = dd[0];
        String m = dd[1];
        String d = dd[2];
        return d+" "+months[Integer.parseInt(m)-1]+" "+y;
    }
    
    public Drawable getDrawable(String uri) {
        int imageResource = context.getResources().getIdentifier("@drawable/"+uri, null, context.getPackageName());
        Drawable res = context.getResources().getDrawable(imageResource);
        return res;
    }
    
    public String numberFormat(String num) {
        String res = "";
        int len = num.length();
        for (int i = 0; i < len; i++) {
            if(i == len-4 || i == len-7 || i == len-10) {
                res+= num.charAt(i)+" ";
            }
            else { res += num.charAt(i); }
        }
        return res;
    }
    
    public String DateSQLFormatNow() {
        Date currentTime = Calendar.getInstance().getTime();
        String s_currentTime = currentTime.toString();
        String y = s_currentTime.substring(s_currentTime.length()-4, s_currentTime.length());
        String dd = "";
        dd += y+"-";
        dd += currentTime.getMonth()+1+"-";
        dd += currentTime.getDate()+" ";
        dd += currentTime.getHours()+":";
        dd += currentTime.getMinutes()+":";
        dd += currentTime.getSeconds();
        return dd;
    }

    public Date stringToDate(String value) {
        String date = value.split(" ")[0];
        String[] date_split = date.split("-");
        return new Date(
                Integer.parseInt(date_split[0]),
                Integer.parseInt(date_split[1]),
                Integer.parseInt(date_split[2])
        );
    }

    public ArrayList<Balance> balanceSortedByDate(DatabaseHelper db) {
        ArrayList<Balance> list_balance = new ArrayList<>();
        ArrayList<Balance> list_balance_db = db.listData();
        Date[] dates = {};
        for (int i = 0; i < list_balance_db.size() ; i++) {
            dates[i] = stringToDate(list_balance_db.get(i).getDate());
        }
        Arrays.sort(dates);
        for (Date date : dates) {
            msg(date.toString());
        }
        return list_balance_db;
    }
    
    public void setColor(View v, int color) {
        if (v instanceof ImageView) {
            ImageView image = (ImageView) v;
            image.setColorFilter(
                ContextCompat.getColor(context, color), 
                android.graphics.PorterDuff.Mode.SRC_IN); 
        }
        else if(v instanceof TextView){
            TextView txt = (TextView) v;
            txt.setTextColor(ContextCompat.getColor(context, color));
        }
    }

    public String intToString(int integer) {
        return  ""+integer;
    }
    
}
