package com.nguyennguyenhoanganh.myapplication;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends Activity {

    private DatabaseHelper dbHelper;
    private ListView listView;
    private ProductAdapter adapter;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.listView);
        productList = dbHelper.getAllProducts();

        adapter = new ProductAdapter(this, productList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showProductDetailsDialog(productList.get(position));
            }
        });
    }

    private void showProductDetailsDialog(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_product_details, null);
        builder.setView(dialogView);

        TextView txtId = dialogView.findViewById(R.id.txtId);
        TextView txtName = dialogView.findViewById(R.id.txtName);
        TextView txtPrice = dialogView.findViewById(R.id.txtPrice);

        txtId.setText(product.getId());
        txtName.setText(product.getName());
        txtPrice.setText(String.valueOf(product.getPrice()));

        builder.setPositiveButton("Xóa sản phẩm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbHelper.deleteProduct(product.getId());
                productList.remove(product);
                adapter.notifyDataSetChanged();
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
}
