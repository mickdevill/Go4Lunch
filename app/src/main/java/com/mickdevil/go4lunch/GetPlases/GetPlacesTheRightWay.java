package com.mickdevil.go4lunch.GetPlases;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.SystemClock;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.OpeningHours;
import com.google.maps.android.SphericalUtil;
import com.mickdevil.go4lunch.UI.G4LunchMain;

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
                location.getLongitude() + "&radius=5000&type=restaurant&keyword=kebab&key=" +
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
        Bitmap photo = null;
        boolean isSomeBodyGoing = false;
        double distenceToUser;
        double rating;
        String photoReff;

        JSONObject moreInfo;
        String phoneNumber = "";
        String webSite = "";
        List<String> weekDaysOpen;


        JSONArray photosArray;


        URL url;
        InputStream inputStream;
        HttpsURLConnection urlConnection;

        for (Iterator<JSONObject> iterator = allMyPlaces.iterator(); iterator.hasNext(); ) {

            thatPlace = iterator.next();

            try {

                placeName = thatPlace.getString("name");

                vicinity = thatPlace.getString("vicinity");

                placeId = thatPlace.getString("place_id");

                latitude = thatPlace.getJSONObject("geometry").getJSONObject("location").getDouble("lat");

                longitude = thatPlace.getJSONObject("geometry").getJSONObject("location").getDouble("lng");

                opened = thatPlace.getJSONObject("opening_hours").getBoolean("open_now");

                rating = thatPlace.getDouble("rating");

                //   photosArray = thatPlace.getJSONArray("photos");
                photosArray = thatPlace.getJSONArray("photos");

                JSONObject phtotoObj = (JSONObject) photosArray.getJSONObject(0);


                photoReff = phtotoObj.getString("photo_reference");


                url = new URL("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" +
                        photoReff + "&key=AIzaSyBjMRxsLtqdVWkeNxfNKA58SebE7c1XVnk");
                urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                inputStream = urlConnection.getInputStream();
                photo = BitmapFactory.decodeStream(inputStream);
                inputStream.close();


                LatLng user = new LatLng(location.getLatitude(), location.getLongitude());
                LatLng placeLatLng = new LatLng(latitude, longitude);

                distenceToUser = SphericalUtil.computeDistanceBetween(user, placeLatLng);

                moreInfo = getMoreInfoAboutThePlace(placeId);


                if (moreInfo.getString("formatted_phone_number") != null){
                    phoneNumber = moreInfo.getString("formatted_phone_number");
                }
                if (moreInfo.getString("website") != null){
                    webSite = moreInfo.getString("website");
                }

                weekDaysOpen = new ArrayList<>();
                weekDaysOpen.add("");
                weekDaysOpen.add(moreInfo.getJSONObject("opening_hours").getJSONArray("weekday_text").getString(0));
                weekDaysOpen.add(moreInfo.getJSONObject("opening_hours").getJSONArray("weekday_text").getString(1));
                weekDaysOpen.add(moreInfo.getJSONObject("opening_hours").getJSONArray("weekday_text").getString(2));
                weekDaysOpen.add(moreInfo.getJSONObject("opening_hours").getJSONArray("weekday_text").getString(3));
                weekDaysOpen.add(moreInfo.getJSONObject("opening_hours").getJSONArray("weekday_text").getString(4));
                weekDaysOpen.add(moreInfo.getJSONObject("opening_hours").getJSONArray("weekday_text").getString(5));
                weekDaysOpen.add(moreInfo.getJSONObject("opening_hours").getJSONArray("weekday_text").getString(6));



                if (placeName != null && placeId != null && vicinity != null && photoReff != null) {

                    placeG4Lunch = new PlaceG4Lunch(placeName, vicinity, latitude, longitude, placeId, opened, photo,
                            false, new ArrayList<>(), distenceToUser, photoReff, rating, weekDaysOpen, webSite, phoneNumber);


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


    public JSONObject getMoreInfoAboutThePlace(String placeID) {
        String line = "";
        String data = "";
        JSONObject johny;

        JSONObject resultOBJ = null;


        String myUrl = "https://maps.googleapis.com/maps/api/place/details/json?place_id=" + placeID +
                "&fields=" + "formatted_phone_number,opening_hours,website" + "&key=AIzaSyBjMRxsLtqdVWkeNxfNKA58SebE7c1XVnk";


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

            johny = new JSONObject(data);
            resultOBJ = johny.getJSONObject("result");


            Log.d(TAG, "getMoreInfoAboutThePlace: " + resultOBJ);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return resultOBJ;

    }


}












