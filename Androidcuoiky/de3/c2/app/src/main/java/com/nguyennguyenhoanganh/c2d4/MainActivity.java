package com.nguyennguyenhoanganh.c2d4;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

import com.nguyennguyenhoanganh.c2d4.databinding.ActivityMainBinding;
import com.nguyennguyenhoanganh.c2d4.databinding.DialogAddStudentBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private StudentAdapter adapter;
    private List<Student> studentList;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new DatabaseHelper(this);
        studentList = new ArrayList<>();

        loadStudents();
        adapter = new StudentAdapter(this, studentList);
        binding.listView.setAdapter(adapter);

        registerForContextMenu(binding.listView);

        binding.listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showOptionsDialog(position);
                return true;
            }
        });
    }

    private void loadStudents() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_STUDENTS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ID);
                int nameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME);
                int classIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CLASS);

                if (idIndex >= 0 && nameIndex >= 0 && classIndex >= 0) {
                    String id = cursor.getString(idIndex);
                    String name = cursor.getString(nameIndex);
                    String className = cursor.getString(classIndex);
                    studentList.add(new Student(id, name, className));
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void showOptionsDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn hành động");
        builder.setItems(new String[]{"Sửa", "Xóa","Thêm"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Student student = studentList.get(position);
                switch (which) {
                    case 0: // Edit
                        showEditStudentDialog(student);
                        break;
                    case 1: // Delete
                        deleteStudent(student);
                        break;
                    case 2: // Delete
                        showAddStudentDialog();
                        break;
                }
            }
        });
        builder.show();
    }

    private void showEditStudentDialog(Student student) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chỉnh sửa Thông Tin Sinh Viên");

        DialogAddStudentBinding dialogBinding = DialogAddStudentBinding.inflate(getLayoutInflater());
        builder.setView(dialogBinding.getRoot());

        dialogBinding.edtStudentId.setText(student.getId());
        dialogBinding.edtStudentName.setText(student.getName());
        dialogBinding.edtStudentClass.setText(student.getClassName());

        builder.setPositiveButton("Lưu Lại", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String studentId = dialogBinding.edtStudentId.getText().toString();
                String studentName = dialogBinding.edtStudentName.getText().toString();
                String studentClass = dialogBinding.edtStudentClass.getText().toString();

                if (!studentId.isEmpty() && !studentName.isEmpty() && !studentClass.isEmpty()) {
                    updateStudent(new Student(studentId, studentName, studentClass));
                }
            }
        });

        builder.setNegativeButton("Trở về", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void showAddStudentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nhập Thông Tin Sinh Viên");

        DialogAddStudentBinding dialogBinding = DialogAddStudentBinding.inflate(getLayoutInflater());
        builder.setView(dialogBinding.getRoot());

        builder.setPositiveButton("Lưu Lại", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String studentId = dialogBinding.edtStudentId.getText().toString();
                String studentName = dialogBinding.edtStudentName.getText().toString();
                String studentClass = dialogBinding.edtStudentClass.getText().toString();

                if (!studentId.isEmpty() && !studentName.isEmpty() && !studentClass.isEmpty()) {
                    addStudent(new Student(studentId, studentName, studentClass));
                }
            }
        });

        builder.setNegativeButton("Trở về", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void addStudent(Student student) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ID, student.getId());
        values.put(DatabaseHelper.COLUMN_NAME, student.getName());
        values.put(DatabaseHelper.COLUMN_CLASS, student.getClassName());
        db.insert(DatabaseHelper.TABLE_STUDENTS, null, values);
        studentList.clear();
        loadStudents();
        adapter.notifyDataSetChanged();
    }

    private void updateStudent(Student student) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, student.getName());
        values.put(DatabaseHelper.COLUMN_CLASS, student.getClassName());
        db.update(DatabaseHelper.TABLE_STUDENTS, values, DatabaseHelper.COLUMN_ID + "=?", new String[]{student.getId()});
        studentList.clear();
        loadStudents();
        adapter.notifyDataSetChanged();
    }

    private void deleteStudent(Student student) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_STUDENTS, DatabaseHelper.COLUMN_ID + "=?", new String[]{student.getId()});
        studentList.remove(student);
        adapter.notifyDataSetChanged();
    }
}
