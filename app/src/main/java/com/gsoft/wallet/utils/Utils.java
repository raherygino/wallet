package com.gsoft.wallet.utils;

import android.annotation.SuppressLint;
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

import android.app.Activity;

import java.util.Objects;

import android.widget.ImageView;
import androidx.core.content.ContextCompat;

import com.gsoft.wallet.R;
import com.gsoft.wallet.model.database.DatabaseHelper;
import com.gsoft.wallet.model.models.Transaction;
import com.gsoft.wallet.view.dialog.ConfirmDialog;

public class Utils{
    
    private final Context context;
    private final Activity activity;
    public static String SEMI_BOLD = "SemiBold";
    public static String REGULAR = "Regular";
    public static String LIGHT = "Light";
    
    public Utils(Context ctx){ 
        this.context = ctx; 
        this.activity = (Activity) context;
    }
    
    public void setFont(View view,String font) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"Poppins_"+font+".ttf");
        if (view instanceof Button) {
            Button btn = (Button) view;
            btn.setTypeface(typeface);
        }
        else if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setTypeface(typeface);
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
    
    public void toast(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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
    
    @SuppressLint("UseCompatLoadingForDrawables")
    public Drawable getDrawable(String uri) {
        @SuppressLint("DiscouragedApi") int imageResource = context.getResources().getIdentifier("@drawable/"+uri, null, context.getPackageName());
        return context.getResources().getDrawable(imageResource);
    }
    
    public String numberFormat(String number) {
        StringBuilder res = new StringBuilder();
        int len = number.length();

        for (int i = 0; i < len; i++) {
            if(i == len-4 || i == len-7 || i == len-10) {
                res.append(number.charAt(i)).append(" ");
            }
            else { res.append(number.charAt(i)); }
        }
        return res.toString();
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

    public ArrayList<Transaction> balanceSortedByDate(DatabaseHelper db) {
        ArrayList<Transaction> list_transaction_db = db.listTransaction();
        Date[] dates = {};
        for (int i = 0; i < list_transaction_db.size() ; i++) {
            dates[i] = stringToDate(list_transaction_db.get(i).getDate());
        }
        Arrays.sort(dates);
        for (Date date : dates) {
            toast(date.toString());
        }
        return list_transaction_db;
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
    
}
