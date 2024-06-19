package com.nguyennguyenhoanganh.myapplication1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNumber;
    private Button buttonDraw;
    private LinearLayout linearLayoutViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNumber = findViewById(R.id.editTextNumber);
        buttonDraw = findViewById(R.id.buttonDraw);
        linearLayoutViews = findViewById(R.id.linearLayoutViews);

        buttonDraw.setOnClickListener(v -> {
            String numberText = editTextNumber.getText().toString();
            int number;
            try {
                number = Integer.parseInt(numberText);
            } catch (NumberFormatException e) {
                number = 0;
            }

            new DrawViewsTask().execute(number);
        });
    }

    private class DrawViewsTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... params) {
            int number = params[0];
            Random random = new Random();
            runOnUiThread(() -> linearLayoutViews.removeAllViews());

            for (int i = 0; i < number; i++) {
                int randomNumber = random.nextInt(101);

                if (i % 2 == 0) {
                    Button button = new Button(MainActivity.this);
                    button.setText(String.valueOf(randomNumber));
                    runOnUiThread(() -> linearLayoutViews.addView(button));
                } else {
                    EditText editText = new EditText(MainActivity.this);
                    editText.setText(String.valueOf(randomNumber));
                    runOnUiThread(() -> linearLayoutViews.addView(editText));
                }
            }

            return null;
        }
    }
}
