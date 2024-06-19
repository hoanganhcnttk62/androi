package com.application.myapplication;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button drawButton;
    private LinearLayout containerLayout;
    private boolean isReverseLayout = false; // Biến kiểm soát tỷ lệ layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        drawButton = findViewById(R.id.drawButton);
        containerLayout = findViewById(R.id.containerLayout);

        drawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numRows = Integer.parseInt(editText.getText().toString());
                new DrawTask().execute(numRows);
            }
        });
    }
    private class DrawTask extends AsyncTask<Integer, LinearLayout, Void> {
        private boolean isReverseLayout = false; // Track layout reversal

        @Override
        protected Void doInBackground(Integer... params) {
            int numRows = params[0];
            for (int i = 0; i < numRows; i++) {
                LinearLayout rowLayout = new LinearLayout(MainActivity.this);
                rowLayout.setOrientation(LinearLayout.HORIZONTAL);
                rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

                // Row Margin (top only)
                LinearLayout.LayoutParams rowParams = (LinearLayout.LayoutParams) rowLayout.getLayoutParams();
                rowParams.setMargins(0, 10, 0, 0);
                rowLayout.setLayoutParams(rowParams);

                // Add TextViews for the two cells in each row
                for (int j = 0; j < 2; j++) {
                    TextView textView = new TextView(MainActivity.this);

                    // Apply the alternating layout ratio
                    float weight = isReverseLayout ? (j == 0 ? 1f : 2f) : (j == 0 ? 2f : 1f);
                    LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
                            0, LinearLayout.LayoutParams.MATCH_PARENT, weight);

                    // Add Margin to each TextView
                    int marginInDp = 5;
                    int marginInPixels = (int) (marginInDp * getResources().getDisplayMetrics().density);
                    textViewParams.setMargins(marginInPixels, marginInPixels, marginInPixels, marginInPixels);

                    textView.setLayoutParams(textViewParams);
                    int randomNumber = new Random().nextInt(10);

                    // Ensure alternating colors within a row
                    int color = (randomNumber % 2 == 0) ? Color.GRAY : Color.GREEN;
                    textView.setText(String.valueOf(randomNumber));
                    textView.setBackgroundColor(color);

                    rowLayout.addView(textView);
                }

                isReverseLayout = !isReverseLayout; // Reverse for the next row
                publishProgress(rowLayout);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(LinearLayout... values) {
            containerLayout.addView(values[0]);
        }
    }

}
//package com.application.myapplication;
//import android.graphics.Color;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import androidx.appcompat.app.AppCompatActivity;
//import java.util.Random;
//
//public class MainActivity extends AppCompatActivity {
//
//scss
//Copy code
//private EditText editText;
//private Button drawButton;
//private LinearLayout containerLayout;
//private boolean isReverseLayout = false; // Biến kiểm soát tỷ lệ layout
//@Override
//protected void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    setContentView(R.layout.activity_main);
//
//    editText = findViewById(R.id.editText);
//    drawButton = findViewById(R.id.drawButton);
//    containerLayout = findViewById(R.id.containerLayout);
//
//    drawButton.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            int numRows = Integer.parseInt(editText.getText().toString());
//            new DrawTask().execute(numRows);
//        }
//    });
//}
//
//private class DrawTask extends AsyncTask<Integer, LinearLayout, Void> {
//    private boolean isReverseLayout = false; // Track layout reversal
//
//    @Override
//    protected Void doInBackground(Integer... params) {
//        int numRows = params[0];
//        for (int i = 0; i < numRows; i++) {
//            LinearLayout rowLayout = new LinearLayout(MainActivity.this);
//            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
//            rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT));
//
//            // Row Margin (top only)
//            LinearLayout.LayoutParams rowParams = (LinearLayout.LayoutParams) rowLayout.getLayoutParams();
//            rowParams.setMargins(0, 10, 0, 0);
//            rowLayout.setLayoutParams(rowParams);
//
//            // Add TextViews for the two cells in each row
//            for (int j = 0; j < 2; j++) {
//                TextView textView = new TextView(MainActivity.this);
//
//                // Apply the alternating layout ratio
//                float weight = isReverseLayout ? (j == 0 ? 1f : 2f) : (j == 0 ? 2f : 1f);
//                LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
//                        0, LinearLayout.LayoutParams.MATCH_PARENT, weight);
//
//                // Add Margin to each TextView
//                int marginInDp = 5;
//                int marginInPixels = (int) (marginInDp * getResources().getDisplayMetrics().density);
//                textViewParams.setMargins(marginInPixels, marginInPixels, marginInPixels, marginInPixels);
//
//                textView.setLayoutParams(textViewParams);
//                int randomNumber = new Random().nextInt(10);
//
//                // Ensure alternating colors within a row
//                int color = (i + j) % 2 == 0 ? Color.GREEN : Color.GRAY;
//                textView.setText(String.valueOf(randomNumber));
//                textView.setBackgroundColor(color);
//
//                rowLayout.addView(textView);
//            }
//
//            isReverseLayout = !isReverseLayout; // Reverse for the next row
//            publishProgress(rowLayout);
//        }
//        return null;
//    }
//
//    @Override
//    protected void onProgressUpdate(LinearLayout... values) {
//        containerLayout.addView(values[0]);
//    }
//}