package com.easypark.easypark;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class garage_b_floor2 extends AppCompatActivity {

    private Button add;
    private Button sub;
    private ProgressBar progbar;
    public static int x;
    final String bar1s = "bar1s";
    int garageBFloor2Total = 5;
    int temp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor2);
        add = findViewById(R.id.button3);
        sub = findViewById(R.id.button4);
        progbar = findViewById(R.id.progressBar);

        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                x = x + 1;
                if(x >= garageBFloor2Total){
                    x = garageBFloor2Total;
                }
                temp = (x*100)/5;
                progbar.setProgress(temp);
                progressUpdate(progbar);
            }
        });

        sub.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                x = x - 1;
                if(x <= 0){
                    x = 0;
                }
                temp = (x*100)/5;
                progbar.setProgress(temp);
                progressUpdate(progbar);
            }
        });

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

    public static int getFloor2Data(){
        return x;
    }

    protected void onPause(){
        super.onPause();
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(bar1s, progbar.getProgress());
        editor.commit();
    }

    public void onResume(){
        super.onResume();
        progbar = (ProgressBar) findViewById(R.id.progressBar);
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        temp = sharedPref.getInt(bar1s, 0);
        progbar.setProgress(temp);
        progressUpdate(progbar);
    }
}
