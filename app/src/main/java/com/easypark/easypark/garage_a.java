package com.easypark.easypark;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class garage_a extends AppCompatActivity {
    ProgressBar bar1;
    ProgressBar bar2;

    TextView txtP1;
    TextView txtP2;
    TextView txtP3;
    TextView txtP4;
    TextView txtS1;
    TextView txtS2;
    TextView txtS3;
    TextView txtS4;

    final String bar1s = "bar1s";
    Button openFloor1;
    Button openFloor2;

    static int dataFloor1;
    int Floor1Total = 4;
    static int dataFloor2;
    int total = 4;
    int temp;
    Handler handler = new Handler();
    Runnable refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage_a);
        bar1 = (ProgressBar)findViewById(R.id.mBar1);
        bar2 = (ProgressBar)findViewById(R.id.mBar2);
        txtP1 = findViewById(R.id.textP1);
        txtP2 = findViewById(R.id.textP2);
        txtP3 = findViewById(R.id.textP3);
        txtP4 = findViewById(R.id.textP4);
        txtS1 = findViewById(R.id.textS1);
        txtS2 = findViewById(R.id.textS2);
        txtS3 = findViewById(R.id.textS3);
        txtS4 = findViewById(R.id.textS4);
        openFloor1 = findViewById(R.id.mButton1);
        openFloor2 = findViewById(R.id.mButton2);




        refresh = new Runnable() {
            public void run() {
                dataFloor1 = garage_a_floor1.getFloor1Data();
                temp = numToPercentage(dataFloor1,total);
                bar1.setProgress(temp);
                txtP1.setText(Integer.toString(temp) + "%");
                txtS1.setText(Integer.toString(total - dataFloor1));
                progressUpdate(bar1);

//                dataFloor2 = garage_a_floor2.getFloor2Data();
//                temp = numToPercentage(dataFloor2,total);
//                bar2.setProgress(temp);
//                txtP2.setText(Integer.toString(temp) + "%");
//                txtS2.setText(Integer.toString(total - dataFloor1));
//                progressUpdate(bar2);
                handler.postDelayed(refresh, 1000);
            }
        };
        handler.post(refresh);

        openFloor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFloor1();
            }
        });

//        openFloor2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openFloor2();
//            }
//        });
    }

    public void openFloor1(){
        Intent intent = new Intent(this, garage_a_floor1.class);
        startActivity(intent);
    }

    public void openFloor2(){
        Intent intent = new Intent(this, garage_a_floor2.class);
        startActivity(intent);
    }

    public void progressUpdate(ProgressBar progbar){
        if(progbar.getProgress() < 50){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                progbar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
            }
        }
        else if(progbar.getProgress() >= 50 && progbar.getProgress() <70) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                progbar.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
            }
        }
        else if(progbar.getProgress() >= 70 && progbar.getProgress() <85) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                progbar.setProgressTintList(ColorStateList.valueOf(Color.rgb(255,140,0)));
            }
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                progbar.setProgressTintList(ColorStateList.valueOf(Color.RED));
            }
        }
    }

    public int numToPercentage(int a, int total){
        return (a*100)/total;
    }

    protected void onPause(){
        super.onPause();
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(bar1s, bar1.getProgress());
        editor.commit();
    }

    public void onResume(){
        super.onResume();
        bar1 = findViewById(R.id.mBar1);
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        temp = sharedPref.getInt(bar1s, 0);
        bar1.setProgress(temp);
        progressUpdate(bar1);
    }

    public static int getGarageAdata(){
        dataFloor1 = garage_a_floor1.getFloor1Data();
        return dataFloor1 + dataFloor2;
    }
}
