package com.nguyennguyenhoanganh.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "products.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_PRODUCTS = "products";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PRICE = "price";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + COLUMN_ID + " TEXT PRIMARY KEY,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_PRICE + " INTEGER" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);

        // Insert sample data
        insertProduct(db, new Product("SP-123", "Vertu Constellation", 10000));
        insertProduct(db, new Product("SP-124", "IPhone 5S", 20000));
        insertProduct(db, new Product("SP-125", "Nokia Lumia 925", 15000));
        insertProduct(db, new Product("SP-126", "SamSung Galaxy S4", 18000));
        insertProduct(db, new Product("SP-127", "HTC Desire 600", 17000));
        insertProduct(db, new Product("SP-128", "HKPhone Revo LEAD", 16000));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    public void insertProduct(SQLiteDatabase db, Product product) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, product.getId());
        values.put(COLUMN_NAME, product.getName());
        values.put(COLUMN_PRICE, product.getPrice());
        db.insert(TABLE_PRODUCTS, null, values);
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCTS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                int price = cursor.getInt(cursor.getColumnIndex(COLUMN_PRICE));
                products.add(new Product(id, name, price));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return products;
    }

    public void deleteProduct(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, COLUMN_ID + "=?", new String[]{id});
        db.close();
    }
}
