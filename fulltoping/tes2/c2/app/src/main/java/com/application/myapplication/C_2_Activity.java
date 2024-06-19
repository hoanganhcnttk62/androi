package com.application.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.application.myapplication.databinding.ActivityC2Binding;

import java.util.ArrayList;
import java.util.List;

public class C_2_Activity extends AppCompatActivity {
    ActivityC2Binding binding;
    Db db;
    ProductAdapter adapter;
    ArrayList<Product> products;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityC2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = new Db(this);
        db.createSampleData();

        loadData();
        addEvents();
    }

    private void addEvents() {
        binding.lvProduct.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent iDialog = new Intent(C_2_Activity.this, DialogActivity.class);
                iDialog.putExtra("product", products.get(position));
                startActivity(iDialog);
                return false;
            }
        });
    }

    private void loadData() {
        adapter = new ProductAdapter(C_2_Activity.this, R.layout.item, getDataFromDb());
        binding.lvProduct.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private List<Product> getDataFromDb() {
        products = new ArrayList<>();

        Cursor cursor = db.getData("SELECT * FROM " + Db.TBL_NAME);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            products.add(new Product(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2)));

            cursor.moveToNext();
        }
        cursor.close();
        return products;
    }
}