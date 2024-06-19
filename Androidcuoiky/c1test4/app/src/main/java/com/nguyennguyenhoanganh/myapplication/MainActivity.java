package com.nguyennguyenhoanganh.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nguyennguyenhoanganh.myapplication.databinding.ActivityMainBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    int percent;
    int count;
    Random random = new Random();
    Handler handler = new Handler();

    // Main/UI - Thread
    Runnable foregroundThread = new Runnable() {
        @Override
        public void run() {
            binding.txtPercent.setText(percent + " %");
            binding.pbPercent.setProgress(percent);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, 100, 1);
            params.setMargins(5, 10, 5, 10);
            LinearLayout.LayoutParams ln_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout linearLayout = new LinearLayout(MainActivity.this);
            linearLayout.setLayoutParams(ln_params);
            linearLayout.setWeightSum(3);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            Log.i("Hello", String.valueOf(count));

            for (int j = 0; j < 3; ++j) {
                if (count >= Integer.parseInt(binding.edtViewNumber.getText().toString())) break;

                int randNumb = random.nextInt(10);

                TextView textView = new TextView(MainActivity.this);
                textView.setLayoutParams(params);
                textView.setText(String.valueOf(randNumb));
                textView.setTextSize(22);
                textView.setTextColor(Color.WHITE);
                if (randNumb % 2 == 0) {
                    textView.setBackgroundColor(Color.BLUE);
                } else {
                    textView.setBackgroundColor(Color.GRAY);
                }
                linearLayout.addView(textView);
                count++;
            }
            binding.container.addView(linearLayout);

            if (percent == 100) {
                binding.txtPercent.setText("DONE!");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        addEvents();
    }

    private void addEvents() {
        binding.btnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.container.removeAllViews();
                execBackGroundThread();
            }
        });
    }

    private void execBackGroundThread() {
        binding.container.removeAllViews();
        count = 0;
        int numberOfViews = Integer.parseInt(binding.edtViewNumber.getText().toString());
        Thread backgroundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= numberOfViews; i++) {
                    percent = i * 100 / numberOfViews;
                    if (count % 3 == 0 || i == numberOfViews) {
                        handler.post(foregroundThread);
                    }
                    SystemClock.sleep(100);
                }
            }
        });
        backgroundThread.start();
    }
}
