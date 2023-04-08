package com.gsoft.wallet.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import java.io.File;
import android.os.Environment;

import com.gsoft.wallet.model.models.Transaction;
import com.gsoft.wallet.model.models.Column;
import com.gsoft.wallet.model.models.Deposit;
import com.gsoft.wallet.model.models.Project;
import com.gsoft.wallet.model.models.User;
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
    /*TRANSACTION*/
    public static final String TABLE_TRANSACTION = "trans";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_TYPE = "type_trans";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DATE = "daty";
    /*PROJECT*/
    public static final String TABLE_PROJECT = "project";
    public static final String COLUMN_TARGET = "target";
    public static final String COLUMN_DEPOSIT = "deposit";
    public static final String COLUMN_PRIORITY = "priority";
    /*DEPOSIT*/
    public static final String TABLE_DEPOSIT = "deposit";
    public static final String COLUMN_ID_PROJECT = "id_project";
    public static final String COLUMN_ID_TRANSACTION = "id_transaction";
    /** USER **/
    public static final String TABLE_USER = "user";
    public static final String COLUMN_LAST_NAME = "lastname";
    public static final String COLUMN_FIRST_NAME = "firstname";
    public static final String COLUMN_EMAIL = "email";
    private Utils utils;
    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        this.utils = new Utils(context);
    }

    public void createTable(SQLiteDatabase database,String table_name, Column[] columns) {
        StringBuilder SQL_CREATE = new StringBuilder("CREATE TABLE " + table_name + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ");
        for (Column column: columns) {
            SQL_CREATE.append(column.getName()).append(" ").append(column.getType()).append(", ");
        }
        SQL_CREATE.append(COLUMN_DATE + " DATETIME );");

        database.execSQL(SQL_CREATE.toString());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Column[] transactionColumns = new Column[] {
                new Column(COLUMN_AMOUNT, "INTEGER"),
                new Column(COLUMN_TYPE, "TEXT"),
                new Column(COLUMN_TITLE, "TEXT")
        };

        Column[] projectColumns = new Column[] {
                new Column(COLUMN_TITLE, "TEXT"),
                new Column(COLUMN_TYPE, "TEXT"),
                new Column(COLUMN_TARGET, "INTEGER"),
                new Column(COLUMN_DEPOSIT, "INTEGER"),
                new Column(COLUMN_PRIORITY, "INTEGER")
        };

        Column[] depositColumns = new Column[] {
                new Column(COLUMN_ID_PROJECT, "INTEGER"),
                new Column(COLUMN_ID_TRANSACTION, "INTEGER"),
        };

        Column[] userColumns = new Column[] {
                new Column(COLUMN_LAST_NAME, "TEXT"),
                new Column(COLUMN_FIRST_NAME, "TEXT"),
                new Column(COLUMN_EMAIL, "TEXT"),
        };

        createTable(db, TABLE_TRANSACTION, transactionColumns);
        createTable(db, TABLE_PROJECT, projectColumns);
        createTable(db, TABLE_DEPOSIT, depositColumns);
        createTable(db, TABLE_USER, userColumns);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String SQL_DELETE_TRANS = "DROP TABLE IF EXISTS " + TABLE_TRANSACTION;
        String SQL_DELETE_PROJECT = "DROP TABLE IF EXISTS " + TABLE_PROJECT;
        String SQL_DELETE_DEPOSIT = "DROP TABLE IF EXISTS " + TABLE_DEPOSIT;
        String SQL_DELETE_USER = "DROP TABLE IF EXISTS " + TABLE_USER;

        db.execSQL(SQL_DELETE_TRANS);
        db.execSQL(SQL_DELETE_PROJECT);
        db.execSQL(SQL_DELETE_DEPOSIT);
        db.execSQL(SQL_DELETE_USER);
        onCreate(db);
    }
    
    private ContentValues valuesTransaction(Transaction transaction, boolean isDate) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_AMOUNT, transaction.getAmout());
        values.put(COLUMN_TYPE, transaction.getType());
        values.put(COLUMN_TITLE, transaction.getTitle());
        if (isDate) {
            values.put(COLUMN_DATE, transaction.getDate());
        }
        return values;
    }
    public long insertData(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(TABLE_TRANSACTION, null, valuesTransaction(transaction, true));
    }

    public long updateData(int id, Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(TABLE_TRANSACTION, valuesTransaction(transaction, false), COLUMN_ID+" = ?", new String[]{String.valueOf(id)});
    }
    public Cursor getAllTransaction() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_TRANSACTION+" ORDER BY "+COLUMN_ID+" DESC";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public int getMaxIdTransaction()  {
        int id = 0;
        Cursor cursor = getAllTransaction();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            id = cursor.getInt(0);
        }
        return id;
    }

    public Transaction showTransaction(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Transaction blc = null;
        String query = "SELECT * FROM " + TABLE_TRANSACTION+" WHERE "+COLUMN_ID+" = '"+id+"'";
        Cursor data = db.rawQuery(query, null);
        while(data.moveToNext()) {
            blc = new Transaction(data.getString(3), data.getString(1), data.getString(2), data.getString(4));
        }
        return blc;
    }

    public ArrayList<Transaction> listTransaction() {
        ArrayList<Transaction> arrayList = new ArrayList<Transaction>();
        Cursor data = this.getAllTransaction();
        while (data.moveToNext()) {
            Transaction transaction = new Transaction(data.getString(3), data.getString(1), data.getString(2), data.getString(4));
            transaction.addId(data.getInt(0));
            arrayList.add(transaction);
        }
        return arrayList;
    }

    public int getAmountTransById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_TRANSACTION+" WHERE "+COLUMN_ID+" = '"+id+"'";
        Cursor cursor = db.rawQuery(query,null);
        int amount = 0;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            amount = cursor.getInt(1);
        }
        return amount;
    }

    public int total(String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT sum("+COLUMN_AMOUNT+") as isa FROM " + TABLE_TRANSACTION + " WHERE "+COLUMN_TYPE+" = '"+type+"'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public long deleteTransaction(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DEPOSIT, COLUMN_ID_TRANSACTION+" = ?", new String[]{String.valueOf(id)});
        return db.delete(TABLE_TRANSACTION, COLUMN_ID+" = ?", new String[]{String.valueOf(id)});
    }

    public void importData(ArrayList<Transaction> list) {
        for (int i = 0; i < list.size(); i++) {
            this.insertData(list.get(i));
        }
    }

    public String exportJson() {
        String Json = "{\"data\":[";
        ArrayList<Transaction> list = this.listTransaction();
        for (int i = 0; i < list.size(); i++) {
            Json += list.get(i).toJson()+",";
        }
        Json += "]}";
        Json = Json.replace(",]","]");
        return Json;
    }

    public ArrayList<Transaction> jsonParse(String json) {
        ArrayList<Transaction> list = new ArrayList<Transaction>();
        try {
            JSONObject object = new JSONObject(json);
            JSONArray array = object.getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                JSONObject data = array.getJSONObject(i);
                String title = data.getString("title");
                String type = data.getString("type");
                String amount = data.getString("amount");
                String date = data.getString("date");
                list.add(new Transaction(title, amount, type, date));
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

    public Cursor searchTransaction(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_TYPE};
        return db.query(TABLE_PROJECT, columns, "ID = ?", new String[]{id},null, null, null, null);
    }

    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PROJECT);
    }

    /**PROJECT**/
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

    public void insertProject(Project project) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_PROJECT, null, valuesProject(project));
    }

    public void updateProject(Project project) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_PROJECT, valuesProject(project), COLUMN_ID + " = ?", new String[]{String.valueOf(project.getId())});
    }

    public Cursor getAllProject() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PROJECT+" ORDER BY "+COLUMN_ID+" DESC";
        return db.rawQuery(query, null);
    }

    public ArrayList<Project> listProject() {
        Cursor cursor = getAllProject();
        ArrayList<Project> list_project = new ArrayList<>();
        while (cursor.moveToNext()) {
            list_project.add(getProjectFromCursor(cursor));
        }
        return list_project;
    }

    private Project getProjectFromCursor(Cursor cursor) {
        int id = cursor.getInt(0);
        String title = cursor.getString(1);
        String type = cursor.getString(2);
        int target = cursor.getInt(3);
        int priority = cursor.getInt(5);
        int diposit = getTotalDepositProject(id);
        Project project = new Project(title, type, priority, target,diposit);
        project.setId(id);
        return project;
    }

    public Project getProjectById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PROJECT+" WHERE "+COLUMN_ID+" = '"+id+"'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return getProjectFromCursor(cursor);
    }

    public void deleteProject(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String idTransaction = String.valueOf(idTransactionByProject(id));
        db.delete(TABLE_TRANSACTION, COLUMN_ID+" = ?", new String[]{idTransaction});
        db.delete(TABLE_DEPOSIT, COLUMN_ID_PROJECT+" = ?", new String[]{String.valueOf(id)});
        db.delete(TABLE_PROJECT, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    /*** DEPOSIT ***/
    private ContentValues valuesDeposit(Deposit deposit) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID_PROJECT, deposit.getIdProject());
        contentValues.put(COLUMN_ID_TRANSACTION, deposit.getIdTransaction());
        contentValues.put(COLUMN_DATE, utils.DateSQLFormatNow());
        return contentValues;
    }
    public void insertDeposit(Deposit deposit) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_DEPOSIT, null, valuesDeposit(deposit));
    }

    public Cursor getAllDeposit() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_DEPOSIT+" ORDER BY "+COLUMN_ID+" DESC";
        return db.rawQuery(query,null);
    }

    public ArrayList<Integer> getIdTransactionByProject(int idProject) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Integer> list = new ArrayList<>();
        String[] columns = new String[]{COLUMN_ID, COLUMN_ID_PROJECT, COLUMN_ID_TRANSACTION};
        Cursor cursor = db.query(TABLE_DEPOSIT, columns, COLUMN_ID_PROJECT+" = ?", new String[]{String.valueOf(idProject)},
                null, null, null, null);
        while (cursor.moveToNext()) {
            list.add(cursor.getInt(2));
        }
        return list;
    }

    public int getTotalDepositProject(int idProject) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Integer> listId = getIdTransactionByProject(idProject);
        int amount = 0;
        for (int id : listId) {
            amount += getAmountTransById(id);
        }
        return amount;
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

    public Project projectByTransaction(int id) {
        Project project = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_DEPOSIT+" WHERE "+COLUMN_ID_TRANSACTION+" = '"+id+"'";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            project = getProjectById(cursor.getInt(1));
        }
        return project;
    }

    public Boolean idTransactionIsDeposit(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_DEPOSIT+" WHERE "+COLUMN_ID_TRANSACTION+" = '"+id+"'";
        Cursor cursor = db.rawQuery(query,null);
        return cursor.getCount() != 0;
    }

    private Deposit getDepositFromCursor(Cursor cursor) {
        return new Deposit(cursor.getInt(0), cursor.getInt(0), cursor.getInt(0));
    }

    public void deleteDepositByIdProjectTrans(int idProject, int idTrans) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(
                TABLE_DEPOSIT,
                COLUMN_ID_PROJECT+" = ? AND "+COLUMN_ID_TRANSACTION+" = ? ",
                new String[]{String.valueOf(idProject), String.valueOf(idTrans)}
        );
    }

    /*** User ***/
    private ContentValues valuesUser(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_LAST_NAME, user.getLastName());
        contentValues.put(COLUMN_FIRST_NAME, user.getFirstName());
        contentValues.put(COLUMN_EMAIL, user.getEmail());
        contentValues.put(COLUMN_DATE, utils.DateSQLFormatNow());
        return contentValues;
    }
    public void insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_USER, null, valuesUser(user));
    }

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_USER, valuesUser(user), COLUMN_ID + " = ?", new String[]{String.valueOf(user.getId())});
    }

    public Cursor getAllUser() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_USER;
        return database.rawQuery(query, null);
    }

    public User getUser() {
        User user = null;
        Cursor cursor = getAllUser();
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            user = new User(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );
            user.setId(cursor.getInt(0));
        }
        return user;
    }
}

