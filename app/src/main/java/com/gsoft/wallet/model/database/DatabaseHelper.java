package com.gsoft.wallet.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import java.util.ArrayList;
import java.io.File;
import android.os.Environment;
import java.io.FileOutputStream;
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import com.gsoft.wallet.utils.FileData;
import com.gsoft.wallet.model.models.Balance;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "budget.db";

    public static final String TABLE_NAME = "balance";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_TYPE = "type_trans";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DATE = "daty";


    Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_AMOUNT + " INTEGER, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DATE + " DATETIME );";
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DELETE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(SQL_DELETE);
        onCreate(db);
    }
    
    public void importData(ArrayList<Balance> list) {
        for (int i = 0; i < list.size(); i++) {
            this.insertData(list.get(i));
        }
    }
    
    private ContentValues values(Balance balance) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_AMOUNT, balance.getAmout());
        values.put(COLUMN_TYPE, balance.getType());
        values.put(COLUMN_TITLE, balance.getTitle());
        values.put(COLUMN_DATE, balance.getDate());
        return values;
    }

    public long insertData(Balance balance) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(TABLE_NAME, null, values(balance));
    }

    public long updateData(String id, Balance balance) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(TABLE_NAME, values(balance), COLUMN_ID+" = ?", new String[]{id});
    }

    public long deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID+" = ?", new String[]{id+""});
    }

    public Cursor readData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME+" ORDER BY "+COLUMN_ID+" DESC";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
    
    public ArrayList<Balance> listData() {
        ArrayList<Balance> arrayList = new ArrayList<Balance>();
        Cursor data = this.readData();
        while (data.moveToNext()) {
            Balance blc = new Balance(data.getString(3), data.getString(1), data.getString(2), data.getString(4));
            blc.addId(data.getInt(0));
            arrayList.add(blc);
        }
        return arrayList;
    }
    
    public String exportJson() {
        String Json = "{\"data\":[";
        ArrayList<Balance> list = this.listData();
        for (int i = 0; i < list.size(); i++) {
            Json += list.get(i).toJson()+",";
        }
        Json += "]}";
        Json = Json.replace(",]","]");
        return Json;
    }
    
    public ArrayList<Balance> jsonParse(String json) {
        ArrayList<Balance> list = new ArrayList<Balance>();
        try {
            JSONObject object = new JSONObject(json);
            JSONArray array = object.getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                JSONObject data = array.getJSONObject(i);
                String title = data.getString("title");
                String type = data.getString("type");
                String amount = data.getString("amount");
                String date = data.getString("date");
                list.add(new Balance(title, amount, type, date));
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public void saveFile() {
        String fileName = "data.json";
        String fileContents = this.exportJson();
        File root = Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath() + "/myBudget");
        dir.mkdirs();
        File file = new File(dir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(fileContents.getBytes());
            fos.close();
        } catch(IOException e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    
    public void openFile(String file) {
        deleteAllData();
        importData(jsonParse(file));
    }
    
   public int total(String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        // SQL Statement
        String query = "SELECT sum("+COLUMN_AMOUNT+") as isa FROM " + TABLE_NAME + " WHERE "+COLUMN_TYPE+" = '"+type+"'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public Cursor searchData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_TYPE};
        Cursor cursor = db.query(TABLE_NAME, columns, "ID = ?", new String[]{id},
                null, null, null, null);
        return cursor;
    }

    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

}

