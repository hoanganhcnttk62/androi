package com.application.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Db extends SQLiteOpenHelper {
    public static final String DB_NAME = "dbC2.sqlite";
    public static final int VERSION = 1;
    public static final String TBL_NAME = "C2";
    public static final String COL_CODE = "colCode";
    public static final String COL_NAME =  "colName";
    public static final String COL_PRICE =  "colPrice";
    public Db(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TBL_NAME + " ( " + COL_CODE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " NVARCHAR(50), " + COL_PRICE + " REAL) ";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TBL_NAME;

        db.execSQL(sql);
        onCreate(db);
    }

    public Cursor getData(String sql){
        try{
            SQLiteDatabase db = getReadableDatabase();
            return db.rawQuery(sql, null);
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }


    public boolean execSql(String sql){
        try{
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(sql);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public int getNumberOfRows(){
        Cursor cursor = getData("SELECT * FROM " + TBL_NAME);
        int numberOfRows = cursor.getCount();
        cursor.close();

        return numberOfRows;
    }

    public void createSampleData(){
        if(getNumberOfRows() == 0){
            try {
                execSql("INSERT INTO " + TBL_NAME + " VALUES(null, 'Vertu Constellation', 10000)");
                execSql("INSERT INTO " + TBL_NAME + " VALUES(null, 'IPhone 5S', 10000)");
                execSql("INSERT INTO " + TBL_NAME + " VALUES(null, 'Nokia Lumia 925', 500000)");
                execSql("INSERT INTO " + TBL_NAME + " VALUES(null, 'SamSung Galaxy S4', 200000)");
                execSql("INSERT INTO " + TBL_NAME + " VALUES(null, 'HTC Desire 600', 200000)");
                execSql("INSERT INTO " + TBL_NAME + " VALUES(null, 'HKPhone Revo LEAD', 200000)");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public boolean deleteData(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        int delete = db.delete(TBL_NAME, COL_CODE + " = ?", new String[]{String.valueOf(id)});
        db.close();

        return delete > 0;
    }
//     Thêm phương thức này vào lớp Db
    public boolean updateProduct(int id, String name, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_PRICE, price);
        int result = db.update(TBL_NAME, contentValues, COL_CODE + " = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }
    public boolean addProduct(String name, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_PRICE, price);
        long result = db.insert(TBL_NAME, null, contentValues);
        return result != -1; // Nếu result không phải là -1, thêm thành công
    }

}
