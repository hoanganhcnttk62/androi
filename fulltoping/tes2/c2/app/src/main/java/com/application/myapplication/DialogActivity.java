package com.application.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.application.myapplication.databinding.ActivityDialogBinding;


public class DialogActivity extends AppCompatActivity {
    ActivityDialogBinding binding;
    Product product;
    Db db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = new Db(DialogActivity.this);

       //dùng cho phương thuc xoa sửa
        getData();
        addEvents();
    }
    //----------------------------------------------xóa------------------------------------------

//    private void addEvents() {
//        binding.btnDel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean del = db.deleteData(product.productCode);
//                if (del)
//                    finish();
//            }
//        });
//
//        binding.btnTroVe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//    }
//
//    private void getData() {
//        Intent iGet = getIntent();
//        product = (Product) iGet.getSerializableExtra("product");
//
//        binding.edProductCode.setText(String.valueOf(product.productCode));
//        binding.edProductName.setText(product.productName);
//        binding.edProductPrice.setText(String.format("%.1f", product.productPrice));
//    }
    //------------------------------------------------------------------------------------

    //------------------------------------------SỬA--------------------------------------
private void addEvents() {
    binding.btnEdit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Lấy thông tin sản phẩm từ các ô nhập liệu
            String name = binding.edProductName.getText().toString();
            double price = Double.parseDouble(binding.edProductPrice.getText().toString());

            // Gọi phương thức updateProduct để cập nhật sản phẩm
            boolean updated = db.updateProduct(product.productCode, name, price);

            if (updated) {
                Toast.makeText(DialogActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                finish(); // Đóng Activity hiện tại
            } else {
                Toast.makeText(DialogActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    });

    binding.btnTroVe.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish(); // Đóng Activity hiện tại
        }
    });
}

    // Lấy dữ liệu sản phẩm từ Intent và hiển thị lên các ô nhập liệu
    private void getData() {
        Intent iGet = getIntent();
        product = (Product) iGet.getSerializableExtra("product");

        // Hiển thị thông tin sản phẩm lên các ô nhập liệu
        binding.edProductCode.setText(String.valueOf(product.productCode));
        binding.edProductName.setText(product.productName);
        binding.edProductPrice.setText(String.format("%.1f", product.productPrice));
    }
    //--------------------------------------------------------------------------------------------
    //-------------------------------------------THêm-------------------------------------------------
//    private void addEvents() {
//        binding.btnAddProduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String productName = binding.edProductName.getText().toString();
//                String productPriceStr = binding.edProductPrice.getText().toString();
//
//                // Kiểm tra xem các trường có rỗng không
//                if (productName.isEmpty() || productPriceStr.isEmpty()) {
//                    Toast.makeText(DialogActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                double productPrice = Double.parseDouble(productPriceStr);
//
//                boolean added = db.addProduct(productName, productPrice);
//
//                if (added) {
//                    Toast.makeText(DialogActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
//                    finish();
//                } else {
//                    Toast.makeText(DialogActivity.this, "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        binding.btnTroVe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//    }

}