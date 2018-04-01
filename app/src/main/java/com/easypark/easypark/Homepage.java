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

public class Homepage extends AppCompatActivity {
    //Initialize variables
    ProgressBar bar1;
    ProgressBar bar2;

    Button garageAbutton;
    Button garageBbutton;
    Button garageCbutton;
    Button garageDbutton;

    TextView txt;
    TextView txt2;
    TextView txtS1;

    final String bar1name = "bar1name";
    final String bar2name = "bar2name";
    String stringTemp;

    static int dataGarageA;
    int garageATotal = 8;
    static int dataGarageB;
    int garageBTotal = 10;
    int temp;

    Handler handler = new Handler();
    Runnable refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //pair variables to id
        bar1 = findViewById(R.id.mBar1);
        bar2 = findViewById(R.id.mBar2);
        txt = findViewById(R.id.textP1);
        txt2 = findViewById(R.id.textP2);
        txtS1 = findViewById(R.id.textS1);
        garageAbutton = findViewById(R.id.mButton1);
        garageBbutton = findViewById(R.id.mButton2);
        garageCbutton = findViewById(R.id.mButton3);
        garageDbutton = findViewById(R.id.mButton4);

        //auto refresh
        refresh = new Runnable() {
            public void run() {

                new fetchData().execute();

                //refresh bar1
                dataGarageA = garage_a.getGarageAdata();
                temp = numToPercentage(dataGarageA, garageATotal);
                bar1.setProgress(temp);
                stringTemp = Integer.toString(temp) + "%";
                txt.setText(stringTemp);
                txtS1.setText(Integer.toString(garageATotal - dataGarageA));
                progressUpdate(bar1);

                //Refresh bar2
                //dataGarageB = garage_b.getGarageBdata();
                temp = numToPercentage(dataGarageB, garageBTotal);
                bar2.setProgress(temp);
                stringTemp = Integer.toString(temp) + "%";
                txt2.setText(stringTemp);
                progressUpdate(bar2);

                handler.postDelayed(refresh, 1000);
            }
        };
        handler.post(refresh);

        //Buttons to next page
        garageAbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGarageA();
            }
        });

        garageBbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGarageB();
            }
        });

        garageCbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGarageC();
            }
        });

        garageDbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGarageD();
            }
        });

    }

    public void openGarageA() {
        Intent intent = new Intent(this, garage_a.class);
        startActivity(intent);
    }

    public void openGarageB() {
        Intent intent = new Intent(this, garage_b.class);
        startActivity(intent);
    }

    public void openGarageC() {
        Intent intent = new Intent(this, garage_c.class);
        startActivity(intent);
    }

    public void openGarageD() {
        Intent intent = new Intent(this, garage_d.class);
        startActivity(intent);
    }

    //Updates progressbar color
    public void progressUpdate(ProgressBar progbar) {
        if (progbar.getProgress() < 50) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                progbar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
            }
        } else if (progbar.getProgress() >= 50 && progbar.getProgress() < 70) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                progbar.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
            }
        } else if (progbar.getProgress() >= 70 && progbar.getProgress() < 85) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                progbar.setProgressTintList(ColorStateList.valueOf(Color.rgb(255, 140, 0)));
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                progbar.setProgressTintList(ColorStateList.valueOf(Color.RED));
            }
        }
    }

    //Converts number of spots over total to percentage
    public int numToPercentage(int a, int total) {
        return (a * 100) / total;
    }

    //Saves the data
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(bar1name, bar1.getProgress());
        editor.putInt(bar2name, bar2.getProgress());
        editor.apply();
    }

    public void onResume() {
        super.onResume();
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        temp = sharedPref.getInt(bar1name, 0);
        bar1.setProgress(temp);
        temp = sharedPref.getInt(bar2name, 0);
        bar2.setProgress(temp);
        progressUpdate(bar1);
        progressUpdate(bar2);
    }
}