package com.gsoft.wallet.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import java.io.File;
import android.os.Environment;

import com.gsoft.wallet.model.models.Balance;
import com.gsoft.wallet.model.models.Deposit;
import com.gsoft.wallet.model.models.Project;
import com.gsoft.wallet.utils.Utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "budget.db";

    public static final String TABLE_NAME = "balance";
    public static final String TABLE_PROJECT = "project";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_TYPE = "type_trans";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DATE = "daty";

    public static final String COLUMN_TARGET = "target";
    public static final String COLUMN_DEPOSIT = "deposit";
    public static final String COLUMN_PRIORITY = "priority";

    public static final String TABLE_DEPOSIT = "deposit";
    public static final String COLUMN_ID_PROJECT = "id_project";
    public static final String COLUMN_ID_TRANSACTION = "id_transaction";
    private Utils utils;
    Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        this.utils = new Utils(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_AMOUNT + " INTEGER, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DATE + " DATETIME );";

        String SQL_CREATE_PROJECT = "CREATE TABLE " + TABLE_PROJECT +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_TARGET + " INTEGER, " +
                COLUMN_DEPOSIT + " INTEGER, " +
                COLUMN_PRIORITY + " INTEGER, " +
                COLUMN_DATE + " DATETIME );";

        String SQL_CREATE_DEPOSIT = "CREATE TABLE " + TABLE_DEPOSIT +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ID_PROJECT + " INTEGER, " +
                COLUMN_ID_TRANSACTION + " INTEGER, " +
                COLUMN_DATE + " DATETIME );";

        db.execSQL(SQL_CREATE);
        db.execSQL(SQL_CREATE_PROJECT);
        db.execSQL(SQL_CREATE_DEPOSIT);
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
    
    private ContentValues values(Balance balance, boolean isDate) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_AMOUNT, balance.getAmout());
        values.put(COLUMN_TYPE, balance.getType());
        values.put(COLUMN_TITLE, balance.getTitle());
        if (isDate) {
            values.put(COLUMN_DATE, balance.getDate());
        }
        return values;
    }

    public long insertData(Balance balance) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(TABLE_NAME, null, values(balance, true));
    }

    public long updateData(int id, Balance balance) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(TABLE_NAME, values(balance, false), COLUMN_ID+" = ?", new String[]{id+""});
    }

    public long deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DEPOSIT, COLUMN_ID_TRANSACTION+" = ?", new String[]{String.valueOf(id)});
        return db.delete(TABLE_NAME, COLUMN_ID+" = ?", new String[]{String.valueOf(id)});
    }

    public Cursor readData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME+" ORDER BY "+COLUMN_ID+" DESC";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public int maxId()  {
        int id = 0;
        Cursor cursor = readData();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            id = cursor.getInt(0);
        }
        return id;
    }

    public Balance show(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Balance blc = null;
        String query = "SELECT * FROM " + TABLE_NAME+" WHERE "+COLUMN_ID+" = '"+id+"'";
        Cursor data = db.rawQuery(query, null);
        while(data.moveToNext()) {
            blc = new Balance(data.getString(3), data.getString(1), data.getString(2), data.getString(4));
        }
        return blc;
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

    /*PROJECT*/

    private ContentValues valuesProject(Project project) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, project.getTitle());
        contentValues.put(COLUMN_TYPE, project.getType());
        contentValues.put(COLUMN_PRIORITY, project.getPriority());
        contentValues.put(COLUMN_DEPOSIT, project.getDeposit());
        contentValues.put(COLUMN_TARGET, project.getTarget());
        contentValues.put(COLUMN_DATE,utils.DateSQLFormatNow());
        return contentValues;
    }

    public long insertProject(Project project) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(TABLE_PROJECT, null, valuesProject(project));
    }

    public Cursor readProject() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PROJECT+" ORDER BY "+COLUMN_ID+" DESC";
        return db.rawQuery(query, null);
    }

    private Project getProjectFromCursor(Cursor cursor) {
        String title = cursor.getString(1);
        String type = cursor.getString(2);
        int target = cursor.getInt(3);
        int diposit = cursor.getInt(4);
        int priority = cursor.getInt(5);
        Project project = new Project(title, type, priority, target,diposit);
        project.setId(cursor.getInt(0));
        return project;
    }

    public ArrayList<Project> listProject() {
        Cursor cursor = readProject();
        ArrayList<Project> list_project = new ArrayList<>();
        while (cursor.moveToNext()) {
            list_project.add(getProjectFromCursor(cursor));
        }
        return list_project;
    }

    public Project getProjectById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PROJECT+" WHERE "+COLUMN_ID+" = '"+id+"'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return getProjectFromCursor(cursor);
    }

    public long updateProject(Project project) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(TABLE_PROJECT, valuesProject(project), COLUMN_ID+" = ?", new String[]{String.valueOf(project.getId())});
    }

    public long deleteProject(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String idTransaction = String.valueOf(idTransactionByProject(id));
        db.delete(TABLE_NAME, COLUMN_ID+" = ?", new String[]{idTransaction});
        db.delete(TABLE_DEPOSIT, COLUMN_ID_PROJECT+" = ?", new String[]{String.valueOf(id)});
        return db.delete(TABLE_PROJECT, COLUMN_ID+" = ?", new String[]{ String.valueOf(id)});
    }

    /*** DEPOSIT ***/

    private ContentValues valuesDeposit(Deposit deposit) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID_PROJECT, deposit.getIdProject());
        contentValues.put(COLUMN_ID_TRANSACTION, deposit.getIdTransaction());
        contentValues.put(COLUMN_DATE, utils.DateSQLFormatNow());
        return contentValues;
    }
    public long insertDeposit(Deposit deposit) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(TABLE_DEPOSIT, null, valuesDeposit(deposit));
    }

    public Cursor getAllDeposit() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_DEPOSIT+" ORDER BY "+COLUMN_ID+" DESC";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public ArrayList<Deposit> listDeposit() {
        Cursor cursor = getAllDeposit();
        ArrayList<Deposit> listDeposit = new ArrayList<>();
        while (cursor.moveToNext()) {
            listDeposit.add(getDepositFromCursor(cursor));
        }
        return listDeposit;
    }
    public int idTransactionByProject(int id) {
        int idTransaction = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = new String[]{COLUMN_ID, COLUMN_ID_PROJECT, COLUMN_ID_TRANSACTION};
        Cursor cursor = db.query(TABLE_DEPOSIT, columns, COLUMN_ID_PROJECT+" = ?", new String[]{String.valueOf(id)},
                null, null, null, null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            idTransaction = cursor.getInt(2);
        }
        return idTransaction;
    }

    private Deposit getDepositFromCursor(Cursor cursor) {
        return new Deposit(cursor.getInt(0), cursor.getInt(0), cursor.getInt(0));
    }
}

