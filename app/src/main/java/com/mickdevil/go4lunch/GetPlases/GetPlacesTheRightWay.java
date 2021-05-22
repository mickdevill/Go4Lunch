package com.mickdevil.go4lunch.GetPlases;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.SystemClock;
import android.util.Log;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.model.OpeningHours;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static java.lang.Double.valueOf;

public class GetPlacesTheRightWay {



    public static List<PlaceG4Lunch> finalPlacesResult = new ArrayList<>();

    //this is the list of places added from fierbase with PlaceG4lunch.getFromMap method
    public static List<PlaceG4Lunch> placesFromFierBase = new ArrayList<>();





    public static final String ALL_GETED_PACES_EVER = "ALL_GETED_PACES_EVER";

    private static final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private static final DatabaseReference placesReff = firebaseDatabase.getReference(ALL_GETED_PACES_EVER);


    private static final String TAG = "GetPlacesTheRightWay";

    public Location location;

    public GetPlacesTheRightWay(Location location) {
        this.location = location;
    }


    public void getPlacesWithGoogle(String radious) {
        if (finalPlacesResult.size() > 0) {
            G4LunchMain.handleMSG(2);
        } else {


            String myURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + location.getLatitude() + "," +
                    location.getLongitude() + "&radius=" + radious + "&type=restaurant&keyword=kebab&key=" +
                    "AIzaSyCvgGKZBsLoMHrd7TTl3LXRoxJv5x2apaw";

            List<JSONObject> theFullResult = new ArrayList<>();

            JSONObject johny = parseJohny(myURL);
            theFullResult.add(johny);
            Log.d(TAG, "getPlaces: " + johny);

            JSONObject cheker = johny;

            while (cheker != null) {
                SystemClock.sleep(1500);
                cheker = parseJohny(doItAgain(johny));

                johny = cheker;
                if (johny != null) {
                    theFullResult.add(johny);
                }
                //      Log.d(TAG, "johny is " + theFullResult.size());

            }

            finalPlacesResult = createTheFinalListWithGoglePlacesApi(cutBigObjectsToPlaceUnitsForGOOGLE(theFullResult));

            if (finalPlacesResult.size() == 0) {
                getPlacesAlternative("5000");
                // this method is located just below "createTheFinalListWithGoglePlacesApi" and "getMoreInfoAboutThePlace",
// all other methods to get get playces with out google places api are there to
            }

            G4LunchMain.handleMSG(2);

            addMyPlaycesToFierBase(finalPlacesResult);

        }


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

            //   Log.d(TAG, "getPlaces: " + data);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return Johny;

    }

    //THIS METHOD IS USED ONLY FOR GOOGLE
    private String doItAgain(JSONObject johny) {
        String nextPage = "";
        String myURL = "";

        try {


            nextPage = johny.getString("next_page_token");

            Log.d(TAG, "doItAgain: " + nextPage);

            myURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?pagetoken=" + nextPage + "&key=" +
                    "AIzaSyCvgGKZBsLoMHrd7TTl3LXRoxJv5x2apaw";


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return myURL;

    }

    //THIS IS USED ONLY FOR GOOGLE
    private List<JSONObject> cutBigObjectsToPlaceUnitsForGOOGLE(List<JSONObject> objects) {
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

        // Log.d(TAG, "decriptThatShit:  " + allMyPlaces.size());


        return allMyPlaces;

    }


    //THIS IS USED ONLY FOR GOOGLE
    public List<PlaceG4Lunch> createTheFinalListWithGoglePlacesApi(List<JSONObject> allMyPlaces) {

        List<PlaceG4Lunch> theFinalList = new ArrayList<>();
        PlaceG4Lunch placeG4Lunch;

        JSONObject thatPlace;

        String placeName = " ";
        String vicinity = " ";
        double latitude = 00.0;
        double longitude = 00.00;
        String placeId = " ";
        boolean opened = false;
        Bitmap photo = null;
        double distenceToUser = 00.00;
        String photoReff = " ";

        JSONObject moreInfo;
        String phoneNumber = "";
        String webSite = "";
        List<String> weekDaysOpen = new ArrayList<>();


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

                if (thatPlace.getJSONArray("photos") != null) {
                    photosArray = thatPlace.getJSONArray("photos");

                    JSONObject phtotoObj = (JSONObject) photosArray.getJSONObject(0);
                    photoReff = phtotoObj.getString("photo_reference");
                    photo = getPlacePhoto(photoReff);

                } else {
                    photoReff = null;
                    photo = null;
                }

                LatLng user = new LatLng(location.getLatitude(), location.getLongitude());
                LatLng placeLatLng = new LatLng(latitude, longitude);

                distenceToUser = SphericalUtil.computeDistanceBetween(user, placeLatLng);

                moreInfo = getMoreInfoAboutThePlace(placeId);


                phoneNumber = moreInfo.getString("formatted_phone_number");


                if (moreInfo.getString("website") != null) {
                    webSite = moreInfo.getString("website");
                } else {

                    webSite = null;
                }

                weekDaysOpen = new ArrayList<>();
                weekDaysOpen.add("");
                weekDaysOpen.add("");
                weekDaysOpen.add(moreInfo.getJSONObject("opening_hours").getJSONArray("weekday_text").getString(0));
                weekDaysOpen.add(moreInfo.getJSONObject("opening_hours").getJSONArray("weekday_text").getString(1));
                weekDaysOpen.add(moreInfo.getJSONObject("opening_hours").getJSONArray("weekday_text").getString(2));
                weekDaysOpen.add(moreInfo.getJSONObject("opening_hours").getJSONArray("weekday_text").getString(3));
                weekDaysOpen.add(moreInfo.getJSONObject("opening_hours").getJSONArray("weekday_text").getString(4));
                weekDaysOpen.add(moreInfo.getJSONObject("opening_hours").getJSONArray("weekday_text").getString(5));
                weekDaysOpen.add(moreInfo.getJSONObject("opening_hours").getJSONArray("weekday_text").getString(6));

            } catch (JSONException e) {
                e.printStackTrace();
            }


            if (placeName != null && placeId != null && vicinity != null) {

                placeG4Lunch = new PlaceG4Lunch(placeName, vicinity, latitude, longitude, placeId, opened, photo,
                        distenceToUser, photoReff, weekDaysOpen,
                        webSite, phoneNumber, "GOOGLE");

                Log.d(TAG, "PHOTO IN PARSER PHOTO IN PARSER PHOTO IN PARSER " + photo );
                theFinalList.add(placeG4Lunch);
            }


        }

        Log.d(TAG, "createTheFinalList: " + theFinalList.size());
        return theFinalList;

    }


    //THIS METHOD IS USED ONLY FOR GOOGLE
    public JSONObject getMoreInfoAboutThePlace(String placeID) {
        String line = "";
        String data = "";
        JSONObject johny;

        JSONObject resultOBJ = null;


        String myUrl = "https://maps.googleapis.com/maps/api/place/details/json?place_id=" + placeID +
                "&fields=" + "formatted_phone_number,opening_hours,website" + "&key=AIzaSyCvgGKZBsLoMHrd7TTl3LXRoxJv5x2apaw";
        try {
            URL url = new URL(myUrl);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
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

    public static Bitmap getPlacePhoto(String photoReff) {
        Bitmap photo = null;

        try {
            URL url = new URL("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" +
                    photoReff + "&key=" + "AIzaSyCvgGKZBsLoMHrd7TTl3LXRoxJv5x2apaw");

            URLConnection urlConnection = null;

            urlConnection = (HttpsURLConnection) url.openConnection();

            InputStream inputStream = urlConnection.getInputStream();
            photo = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return photo;
    }


    public void addMyPlaycesToFierBase(List<PlaceG4Lunch> thePlacesFromApi) {
        placesReff.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            PlaceG4Lunch place;
            List<HashMap<String, Object>> places = new ArrayList<>();

            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() < 1) {

                    for (Iterator<PlaceG4Lunch> iterator = thePlacesFromApi.iterator(); iterator.hasNext(); ) {
                        place = iterator.next();
                        placesReff.child(place.getPlaceId()).setValue(PlaceG4Lunch.placeG4LunchToMap(place));
                    }
                }
                if (dataSnapshot.getChildrenCount() > 1) {

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        places.add((HashMap<String, Object>) ds.getValue());
                        G4LunchMain.handleMSG(0);

                        for (Iterator<PlaceG4Lunch> iterator = thePlacesFromApi.iterator(); iterator.hasNext(); ) {
                            place = iterator.next();
                            boolean allredyIn = false;

                            for (int i = 0; i < places.size(); i++) {

                                if (place.getPlaceId().equals(places.get(i).get("placeId"))) {
                                    allredyIn = true;
                                }
                            }
                            if (!allredyIn) {
                                placesReff.child(place.getPlaceId()).setValue(PlaceG4Lunch.placeG4LunchToMap(place));
                            }
                        }
                    }
                }


            }
        });
    }





    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////THIS IS ALTERNATIVE IMPLEMENTATION/////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //THIS IS USED ONLY FOR THE ALTERNATIVE WAY
    private void getPlacesAlternative(String radious) {
        //here I use the URL that I find on https://www.freemaptools.com/find-places-within-radius.htm by inspecting the page, that way
        //I get all places for free in Json format, it's so essy i even don't need to do web scraping with Jsoup, besids, IT'S FREE TO USE!!!! I AM A GENIUS!!!!

        String alternativeWayURL = "https://api.foursquare.com/v2/venues/search?ll=" + valueOf(location.getLatitude()) + "%2C" + valueOf(location.getLongitude())
                + "&radius=" + radious + "&categoryId=" +
                "4d4b7105d754a06374d81259" + "&client_id=MDW1CQIF4AUNLX2VXX2FJ1CA4CZIWQ3ACUQTCK0JNQ2MXVRO&client_secret=YI1SJ" +
                "4Q4CKJZV3NK0YPGCCLIYZTRLCNTW3MDBHPBRJXMUUCL&v=20200101";

        finalPlacesResult = createTheFinalListWithAlternativeWay(cutBigObjectsToPlaceUnitsForTheAlternativeWay(parseJohny(alternativeWayURL)));

    }


    //THIS IS USED ONLY FOR THE ALTERNATIVE WAY
    private List<JSONObject> cutBigObjectsToPlaceUnitsForTheAlternativeWay(JSONObject requestResult) {
        List<JSONObject> allMyPlaces = new ArrayList<>();
        try {

            JSONArray places = requestResult.getJSONObject("response").getJSONArray("venues");
            JSONObject onePlace;

            for (int i = 0; i < places.length(); i++) {
                onePlace = places.getJSONObject(i);
                allMyPlaces.add(onePlace);
                Log.d(TAG, "cutBigObjectsToPlaceUnitsForTheAlternativeWay: " + onePlace.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "cutBigObjectsToPlaceUnitsForTheAlternativeWay: " + allMyPlaces.size());
        return allMyPlaces;

    }

    //THIS IS USED ONLY FOR THE ALTERNATIVE WAY
    private List<PlaceG4Lunch> createTheFinalListWithAlternativeWay(List<JSONObject> places) {

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
        double rating = 0;
        String photoReff;

        JSONObject moreInfo;
        String phoneNumber = "";
        String webSite = "";
        List<String> weekDaysOpen;


        JSONArray photosArray;


        URL url;
        InputStream inputStream;
        HttpsURLConnection urlConnection;

        for (Iterator<JSONObject> iterator = places.iterator(); iterator.hasNext(); ) {

            thatPlace = iterator.next();

            try {
                placeId = thatPlace.getString("id");

                placeName = thatPlace.getString("name");

                JSONObject locationObject = thatPlace.getJSONObject("location");

                vicinity = locationObject.getString("address");

                latitude = locationObject.getDouble("lat");

                longitude = locationObject.getDouble("lng");


                //  opened = thatPlace.getJSONObject("opening_hours").getBoolean("open_now");

                LatLng user = new LatLng(location.getLatitude(), location.getLongitude());
                LatLng placeLatLng = new LatLng(latitude, longitude);
                distenceToUser = SphericalUtil.computeDistanceBetween(user, placeLatLng);

                //    test(placeLatLng, user, placeName);

                weekDaysOpen = new ArrayList<>();

                if (placeName != null && placeId != null && vicinity != null) {

                    placeG4Lunch = new PlaceG4Lunch(placeName, vicinity, latitude, longitude, placeId, opened, photo,
                            distenceToUser, null,
                            weekDaysOpen, webSite, phoneNumber, "ALTERNATIVE");


                    theFinalList.add(placeG4Lunch);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        Log.d(TAG, "createTheFinalList: " + theFinalList.size());
        return theFinalList;

    }

// //this Method is used only for the alternative way
// public static void test(LatLng latLngP, LatLng latLngU, String fullAdress) {

//     SystemClock.sleep(150);

//     // Create a new token for the autocomplete session. Pass this to FindAutocompletePredictionsRequest,
//     // and once again when the user makes a selection (for example when calling fetchPlace()).
//     AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

//     // Create a RectangularBounds object.
//     // RectangularBounds bounds = RectangularBounds.newInstance(
//     //         latLngP,
//     //         latLngU);
//     // Use the builder to create a FindAutocompletePredictionsRequest.
//     FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
//             // Call either setLocationBias() OR setLocationRestriction().
//             // .setLocationBias(bounds)
//             //.setLocationRestriction(bounds)
//             .setOrigin(latLngP)
//             .setCountries("FR")
//             .setTypeFilter(TypeFilter.ADDRESS)
//             .setSessionToken(token)
//             .setQuery("a")
//             .build();

//     G4LunchMain.client.findAutocompletePredictions(request).addOnSuccessListener((response) -> {
//         for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
//             Log.i(TAG, prediction.getPlaceId());
//             Log.i(TAG, prediction.getPrimaryText(null).toString());
//         }

//     }).addOnFailureListener((exception) -> {
//         if (exception instanceof ApiException) {
//             ApiException apiException = (ApiException) exception;
//             Log.e(TAG, "Place not found: " + apiException.getStatusCode());

//         }
//     });


// }


}












