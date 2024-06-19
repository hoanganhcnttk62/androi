package com.nguyennguyenhoanganh.c2d4;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "students.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_STUDENTS = "students";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CLASS = "class";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STUDENTS_TABLE = "CREATE TABLE " + TABLE_STUDENTS + "("
                + COLUMN_ID + " TEXT PRIMARY KEY,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_CLASS + " TEXT" + ")";
        db.execSQL(CREATE_STUDENTS_TABLE);

        insertStudent(db, "SV001", "Nguyen Van A", "Lop 10A");
        insertStudent(db, "SV002", "Tran Thi B", "Lop 10B");
        insertStudent(db, "SV003", "Le Van C", "Lop 11A");
        insertStudent(db, "SV004", "Pham Thi D", "Lop 11B");
        insertStudent(db, "SV005", "Nguyen Van E", "Lop 12A");
        insertStudent(db, "SV006", "Tran Thi F", "Lop 12B");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }

    private void insertStudent(SQLiteDatabase db, String id, String name, String className) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_CLASS, className);
        db.insert(TABLE_STUDENTS, null, values);
    }
}