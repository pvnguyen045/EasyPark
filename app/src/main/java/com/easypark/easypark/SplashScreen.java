package com.easypark.easypark;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class SplashScreen extends AppCompatActivity {

    private ImageView splashScreen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        splashScreen = findViewById(R.id.mSplashScreen);
        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        splashScreen.startAnimation(myanim);
        new fetchData().execute();
        final Intent intent = new Intent(this, Homepage.class);
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }
}
