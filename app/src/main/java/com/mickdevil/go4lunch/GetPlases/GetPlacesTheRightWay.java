package com.mickdevil.go4lunch.GetPlases;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class GetPlacesTheRightWay {


    private static final String TAG = "GetPlacesTheRightWay";


//  0.54043
//  47.4782467


    public void getPlaces() {
        String myURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522," +
                "151.1957362&radius=1500&type=restaurant&keyword=cruise&key=" +


                "AIzaSyBjMRxsLtqdVWkeNxfNKA58SebE7c1XVnk";

        String line = "";
        String data = "";


        try {
            URL url = new URL(myURL);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            //  urlConnection.setRequestMethod("GET");
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            while (line != null) {

                line = bufferedReader.readLine();
                data += line;
            }

            Log.d(TAG, "getPlaces: " + data);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
