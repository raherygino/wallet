package com.gsoft.wallet.utils;

import android.content.Context;
import android.widget.TextView;
import android.graphics.Typeface;
import android.widget.Button;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import com.gsoft.wallet.R;
import android.graphics.drawable.Drawable;
import java.util.Date;
import java.util.Calendar;
import com.gsoft.wallet.view.dialog.ConfirmDialog;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.Activity;
import java.io.File;
import android.os.Environment;
import java.io.FileOutputStream;
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import com.gsoft.wallet.model.models.Balance;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;

public class Utils{
    
    private Context context;
    private String fonts = "Poppins_";
    private ConfirmDialog dialog;
    private int EXTERNAL_STORAGE_PERMISSION_CODE = 23;
    private Activity activity;
    
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
    
    public void savePublicly() {

        // Requesting Permission to access External Storage

        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 

                                          EXTERNAL_STORAGE_PERMISSION_CODE);

     //   String editTextData = editText.getText().toString();



        // getExternalStoragePublicDirectory() represents root of external storage, we are using DOWNLOADS

        // We can use following directories: MUSIC, PODCASTS, ALARMS, RINGTONES, NOTIFICATIONS, PICTURES, MOVIES

        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);



        // Storing the data in file with name as geeksData.txt

        File file = new File(folder, "geeksData.txt");

        writeTextData(file, "hello");

    //    editText.setText("");

    }
    
    private void writeTextData(File file, String data) {

        FileOutputStream fileOutputStream = null;

        try {

            fileOutputStream = new FileOutputStream(file);

            fileOutputStream.write(data.getBytes());

            Toast.makeText(context, "Done" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            this.msg(e.getMessage());
            e.printStackTrace();

        } finally {

            if (fileOutputStream != null) {

                try {

                    fileOutputStream.close();

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }

        }

    }
    
    

    public void btnClick(View v){
      final Animation anim = AnimationUtils.loadAnimation(context,R.anim.btn_click);
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
    
    public void AlertDialog(String title, String description) {
        dialog = new ConfirmDialog(context, title, description);
        dialog.btn_1.setVisibility(View.GONE);
        dialog.btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnClick(v);
                dialog.dismiss();
            }
        });
        dialog.show();
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
