package com.easypark.easypark;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class garage_a_floor1 extends AppCompatActivity {

    static String tempString[] = new String[4];
//    TextView data;

    Handler handler = new Handler();
    Runnable refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor_map);

//        data = findViewById(R.id.fetcheddata);


        refresh = new Runnable() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void run() {
                new fetchData().execute();

                tempString = fetchData.getParkingSpots();
                for (int i = 0; i < tempString.length; i++) {
                    String name = "mSpot" + Integer.toString(i+24);
                    int id = getResources().getIdentifier(name, "id", getPackageName());
                    TextView Spot = findViewById(id);
                    if (tempString[i].equals("Occupied")) {
                        Spot.setBackgroundColor(Color.RED);
                    }
                    else{
                        Spot.setBackgroundColor(Color.GREEN);
                    }
                }

//                data.setText("ParkingSpotOne: " + tempString[0] + "\n" +
//                        "ParkingSpotTwo: " + tempString[1] + "\n"+
//                        "ParkingSpotThree: " + tempString[2] + "\n" +
//                        "ParkingSpotFour: " + tempString[3] + "\n");
                handler.postDelayed(refresh, 1000);
            }
        };
        handler.post(refresh);

    }

    static int OccupiedCheck(String[] parkingspots){

        if(parkingspots == null){
            return 0;
        }
        else {
            int numOfOccupied = 0;
            for (int i = 0; i < parkingspots.length; i++) {
                if (parkingspots[i].equals("Occupied")) {
                    numOfOccupied++;
                }
            }
            return numOfOccupied;
        }
    }

    public static int getFloor1Data(){
        tempString = fetchData.getParkingSpots();
        return OccupiedCheck(tempString);
    }
}
