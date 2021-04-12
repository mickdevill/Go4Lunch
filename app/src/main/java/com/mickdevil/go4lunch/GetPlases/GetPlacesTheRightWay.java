package com.mickdevil.go4lunch.GetPlases;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mickdevil.go4lunch.UI.botoomNavStaf.GetPlaces;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class GetPlacesTheRightWay {


    private static final String TAG = "GetPlacesTheRightWay";

    Location location;

    public static List<JSONObject> theFullResult = new ArrayList<>();


    public GetPlacesTheRightWay(Location location) {
        this.location = location;
    }

    public void getPlaces() {
        String myURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + location.getLatitude() + "," +
                location.getLongitude() + "&radius=15000&type=restaurant&keyword=kebab&key=" +
                "AIzaSyBjMRxsLtqdVWkeNxfNKA58SebE7c1XVnk";

        JSONObject johny = parseJohny(myURL);

        JSONObject cheker = johny;

        theFullResult.add(johny);

        while (cheker != null){

            int i = 0;

            cheker =  parseJohny(doItAgain(theFullResult.get(i)));

            if (i != 0){
                if (cheker.remove("next_page_token").toString() == theFullResult.get(i-1).remove("next_page_token").toString()){
                    break;
                }
            }

            if (cheker != null){

                theFullResult.add(cheker);

                Log.d(TAG, "my whileD cheker" +  theFullResult.size());
            }
i++;


        }




//      String line = "";
//      String data = "";


//      try {
//          URL url = new URL(myURL);
//          HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
//          //  urlConnection.setRequestMethod("GET");
//          InputStream inputStream = urlConnection.getInputStream();
//          BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

//          while (line != null) {

//              line = bufferedReader.readLine();
//              data += line;
//          }

//          JSONObject Johny = new JSONObject(data);

//          String nextPge = Johny.getString("next_page_token");


//          Log.d(TAG, "getPlaces: " + nextPge);


//      } catch (MalformedURLException e) {
//          e.printStackTrace();
//      } catch (IOException e) {
//          e.printStackTrace();
//      } catch (JSONException e) {
//          e.printStackTrace();
//      }
    }


    private JSONObject parseJohny(String myUrl) {
        String line = "";
        String data = "";
        JSONObject Johny = null;

        try {
            URL url = new URL(myUrl);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            //  urlConnection.setRequestMethod("GET");
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            while (line != null) {

                line = bufferedReader.readLine();
                data += line;
            }

            Johny = new JSONObject(data);

          //  Log.d(TAG, "getPlaces: " + data);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return Johny;

    }


    private String doItAgain(JSONObject johny) {
        String nextPage = "";
        String myURL = "";

        try {
            nextPage = johny.getString("next_page_token");

          //  Log.d(TAG, "doItAgain: " + nextPage);

            myURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + location.getLatitude() + "," +
                    location.getLongitude() + "&radius=15000&type=restaurant&page_token=" + nextPage + "&key=" +

                    "AIzaSyBjMRxsLtqdVWkeNxfNKA58SebE7c1XVnk";

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return myURL;

    }

}












