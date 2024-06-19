package com.nguyennguyenhoanganh.c1d2;


import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText edtNumber;
    private Button btnDraw;
    private ListView listView;
    private List<Integer> numberList;
    private ArrayAdapter<Integer> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtNumber = findViewById(R.id.edtNumber);
        btnDraw = findViewById(R.id.btnDraw);
        listView = findViewById(R.id.listView);

        numberList = new ArrayList<>();
        adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, numberList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                if (position % 2 == 0) {
                    textView.setBackgroundColor(Color.YELLOW);
                } else {
                    textView.setBackgroundColor(Color.TRANSPARENT);
                }
                textView.setGravity(position % 2 == 0 ? Gravity.CENTER : Gravity.START);
                return textView;
            }
        };
        listView.setAdapter(adapter);

        btnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawViews();
            }
        });
    }

    private void drawViews() {
        numberList.clear();

        String inputText = edtNumber.getText().toString();
        if (inputText.isEmpty()) {
            edtNumber.setError("Vui lòng nhập số");
            return;
        }

        int numberOfItems;
        try {
            numberOfItems = Integer.parseInt(inputText);
        } catch (NumberFormatException e) {
            edtNumber.setError("Số nhập vào không hợp lệ");
            return;
        }

        if (numberOfItems <= 0) {
            edtNumber.setError("Số lượng View phải lớn hơn 0");
            return;
        }

        Random random = new Random();
        for (int i = 0; i < numberOfItems; i++) {
            numberList.add(random.nextInt(101));  // Số ngẫu nhiên trong khoảng [0...100]
        }

        // Sắp xếp danh sách theo yêu cầu
        List<Integer> sortedList = sortListByPattern(numberList);
        numberList.clear();
        numberList.addAll(sortedList);
        adapter.notifyDataSetChanged();
    }

    private List<Integer> sortListByPattern(List<Integer> originalList) {
        List<Integer> sortedList = new ArrayList<>();
        List<Integer> tempList = new ArrayList<>(originalList);

        for (int i = 0; i < originalList.size(); i++) {
            if (i % 2 == 0) {
                // Thêm vào giữa
                sortedList.add(tempList.remove(tempList.size() / 2));
            } else {
                // Thêm vào đầu
                sortedList.add(tempList.remove(0));
            }
        }
        return sortedList;
    }
}
