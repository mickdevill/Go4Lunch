package com.mickdevil.go4lunch.GetPlases;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.SystemClock;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.model.OpeningHours;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPhotoResponse;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.gson.JsonArray;
import com.google.maps.android.SphericalUtil;
import com.mickdevil.go4lunch.UI.G4LunchMain;
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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class GetPlacesTheRightWay {

    public static List<PlaceG4Lunch> finalPlacesResult = new ArrayList<>();


    private static final String TAG = "GetPlacesTheRightWay";

    Location location;

    public GetPlacesTheRightWay(Location location) {
        this.location = location;
    }




    public void getPlaces() {
        String myURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + location.getLatitude() + "," +
                location.getLongitude() + "&radius=50000&type=restaurant&keyword=kebab&key=" +
                "AIzaSyBjMRxsLtqdVWkeNxfNKA58SebE7c1XVnk";

         List<JSONObject> theFullResult = new ArrayList<>();

        JSONObject johny = parseJohny(myURL);
        theFullResult.add(johny);
        Log.d(TAG, "getPlaces: " + johny);

        JSONObject cheker = johny;
        //   Log.d(TAG, "johny is " + johny);

        while (cheker != null) {
            SystemClock.sleep(1500);
            cheker = parseJohny(doItAgain(johny));

            johny = cheker;
            if (johny != null) {
                theFullResult.add(johny);
            }
            //  Log.d(TAG, "johny is " + theFullResult.size());

        }

      finalPlacesResult = createTheFinalList(queDes10EtDes20(theFullResult));


        G4LunchMain.handleMSG(2);



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

            myURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?pagetoken=" + nextPage + "&key=" +

                    "AIzaSyBjMRxsLtqdVWkeNxfNKA58SebE7c1XVnk";


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return myURL;

    }


    private List<JSONObject> queDes10EtDes20(List<JSONObject> objects) {

        JSONObject jah;
        JSONArray result;

        List<JSONObject> allMyPlaces = new ArrayList<>();

        String name;
        String address;
        OpeningHours openTime;
        LatLng latLng;
        Bitmap bitmap;
        boolean isOpen = false;


        for (Iterator<JSONObject> iterator = objects.iterator(); iterator.hasNext(); ) {
            jah = iterator.next();

            try {
                result = jah.getJSONArray("results");

                for (int i = 0; i < result.length(); i++) {
                    allMyPlaces.add((JSONObject) result.get(i));
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        Log.d(TAG, "decriptThatShit:  " + allMyPlaces.size());


        return allMyPlaces;

    }


    public List<PlaceG4Lunch> createTheFinalList(List<JSONObject> allMyPlaces) {

        List<PlaceG4Lunch> theFinalList = new ArrayList<>();
        PlaceG4Lunch placeG4Lunch;

        JSONObject thatPlace;

        String placeName;
        String vicinity;
        double latitude = 66.666666;
        double longitude = 66.666666;
        String placeId;
        boolean opened = false;
        Bitmap photo;
        boolean isSomeBodyGoing = false;
        double distenceToUser;
        JSONArray photosArray;


        for (Iterator<JSONObject> iterator = allMyPlaces.iterator(); iterator.hasNext(); ) {

            thatPlace = iterator.next();

            try {

                placeName = thatPlace.getString("name");

                vicinity = thatPlace.getString("vicinity");

                placeId = thatPlace.getString("place_id");

                latitude = thatPlace.getJSONObject("geometry").getJSONObject("location").getDouble("lat");

                longitude = thatPlace.getJSONObject("geometry").getJSONObject("location").getDouble("lng");

                opened = thatPlace.getJSONObject("opening_hours").getBoolean("open_now");


                photosArray = thatPlace.getJSONArray("photos");
                String photoReference = photosArray.getString(3);



                URL url = new URL("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" +
                        photoReference +"&key=AIzaSyBjMRxsLtqdVWkeNxfNKA58SebE7c1XVnk");
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                //  urlConnection.setRequestMethod("GET");
                InputStream inputStream = urlConnection.getInputStream();
              photo = BitmapFactory.decodeStream(inputStream);

              LatLng user = new LatLng(location.getLatitude(), location.getLongitude());
              LatLng placeLatLng = new LatLng(latitude, longitude);

                distenceToUser = SphericalUtil.computeDistanceBetween(user, placeLatLng);

if (placeName != null && placeId != null && photo != null && vicinity != null) {
    placeG4Lunch = new PlaceG4Lunch(placeName, vicinity, latitude, longitude, placeId, opened, photo,
            false, new ArrayList<>(), distenceToUser);
theFinalList.add(placeG4Lunch);
}

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        Log.d(TAG, "createTheFinalList: " + theFinalList.size());
        return theFinalList;

    }


}












