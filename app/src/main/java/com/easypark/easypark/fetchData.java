package com.easypark.easypark;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class fetchData extends AsyncTask<Void, Void, Void> {
    String data = "";
    String dataParsed = "";
    String singleParsed = "";
    String tempString;
    static String parkingSpots[]= {"","","",""}; //= {"Occupied","Occupied","Occupied","Vacant"};

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            URL url = new URL("https://muhammad-16-25-89.000webhostapp.com/SD2/JSON.php");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null)
            {
                line = bufferedReader.readLine();
                data = data + line;
            }


            JSONArray JA = new JSONArray(data);
            JSONObject JO = (JSONObject) JA.get(0);

            singleParsed = "ParkingSpotOne: " + JO.get("F1") + "\n" +
                        "ParkingSpotTwo: " + JO.get("F2") + "\n" +
                        "ParkingSpotThree: " + JO.get("F3") + "\n" +
                        "ParkingSpotFour: " + JO.get("F4") + "\n";

                dataParsed = dataParsed + singleParsed + "\n";

                for (int j = 0; j < 4; j++) {
                    parkingSpots[j] = (String) JO.get("F" + Integer.toString(j+1));
                }

                httpURLConnection.disconnect();
                inputStream.close();
                bufferedReader.close();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] getParkingSpots(){
        return parkingSpots;
    }

    //public static String haha(){ return parkingSpots[1];}
}

