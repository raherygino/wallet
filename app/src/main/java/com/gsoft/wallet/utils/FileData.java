package com.gsoft.wallet.utils;
import android.content.Context;
import java.io.File;
import android.os.Environment;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class FileData
{
    private Context context;
    private Utils u;
    public FileData(Context ctx) {
        this.context = ctx;
        this.u = new Utils(ctx);
    }
    public String Open(String fileName) {
        String data = "";
        try {
            File file = new File(Environment.getExternalStorageDirectory(), fileName);

            FileInputStream inputStream = new FileInputStream(file);
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(streamReader);

            StringBuilder stringBuilder = new StringBuilder();

            String Text;

            while ((Text = bufferedReader.readLine()) != null) {
                stringBuilder.append(Text);
            }

            inputStream.close();
            data = stringBuilder.toString();


        } catch (IOException e) {
            e.printStackTrace();
            u.msg(e.getMessage());
        }
        return data;
    }
    }