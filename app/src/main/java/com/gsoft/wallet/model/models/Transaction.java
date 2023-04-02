package com.gsoft.wallet.model.models;

import com.gsoft.wallet.utils.Utils;

public class Transaction
{
    private int id;
    private final String amount;
    private final String title;
    private final String type;
    private final String date;
    public String ICON;
    public int COLOR;

    public Transaction(String nTitle, String nAmount, String nType, String  nDate) {
        this.amount = nAmount;
        this.title = nTitle;
        this.type = nType;
        this.date = nDate;
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

    public String toJson() {
        String json = "{";
        json += "\"title\":\""+this.title+"\",";
        json += "\"type\":\""+this.type+"\",";
        json += "\"amount\":\""+this.amount+"\",";
        json += "\"date\":\""+this.getFormatedDate()+" "+this.getTime()+"\"}";
        return json;
    }
}
