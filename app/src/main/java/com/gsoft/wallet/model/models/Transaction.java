package com.gsoft.wallet.model.models;

import static com.gsoft.wallet.utils.Utils.DATE_FORMAT;

import android.annotation.SuppressLint;
import com.gsoft.wallet.utils.Utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction
{
    private int id;
    private final String amount;
    private final String title;
    private final String type;
    private final String date;

    public Transaction(String nTitle, String nAmount, String nType, String  nDate) {
        this.amount = nAmount;
        this.title = nTitle;
        this.type = nType;
        this.date = nDate;
    }

    @SuppressLint("SimpleDateFormat")
    public Date getDateFomatted() {
        Date nDate = null;
        try {
            nDate = new SimpleDateFormat(DATE_FORMAT).parse(this.date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return nDate;
    }

    public void addId(int nId) {
        this.id = nId;
    }

    public int getId() {
        return this.id;
    }

    public String getAmout(){
        return this.amount;
    }

    public String getFormatedAmount(Utils u) {
        return u.numberFormat(this.amount);
    }

    public String getTitle(){
        return this.title;
    }

    public String getType() {
        return this.type;
    }

    public String getDate(){
        return this.date;
    }

    public String getFormatedDate(){
        String[] d = this.date.split(" ");
        return d[0];
    }
    public String getTime() {
        String[] d = this.date.split(" ");
        return d[1];
    }
}
