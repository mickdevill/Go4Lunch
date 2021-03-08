package com.mickdevil.go4lunch.UI;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonParser;
import com.mickdevil.go4lunch.R;
import com.mickdevil.go4lunch.UI.botoomNavStaf.GetPlaces;
import com.mickdevil.go4lunch.UI.botoomNavStaf.map.MapFragment;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class G4LunchMain extends AppCompatActivity {
    private static final String TAG = "G4LunchMain";
    //views
    //////////////////////////////////////////////////////////////////////////////////////////
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private com.google.android.material.appbar.AppBarLayout AppBarLayout;
    //-----------------------------------------------------------------------------------------

    //api stuff
    /////////////////////////////////////////////////////////////////////////////////////////
    private static FusedLocationProviderClient locationProviderClient;
    PlacesClient client;
    List<Place.Field> fieldList;
    //-----------------------------------------------------------------------------------------

    //...........................=================............................
    //..........................||              ||.............................
    //..........................||              ||.............................
    //===========================DATA FOR SHIPMENT=============================

    private static double curentLat = 0, curentLng = 0;


    //===========================DATA FOR SHIPMENT=============================


    //request codes
    ////////////////////////////////////////////////////////////////////////////////////////
    private static final int REQUESTAUTOCOMPLITION = 100;

    //-----------------------------------------------------------------------------------------

    static Intent intent;

    //the onCreate
    @SuppressLint("VisibleForTests")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g4_lunch_main);

        intent = new Intent();

        fieldList = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.OPENING_HOURS, Place.Field.RATING);

        Places.initialize(G4LunchMain.this, "AIzaSyBZ1yf43MqKZwPmDvEkUx5CBufQpf01yDI");
        client = Places.createClient(G4LunchMain.this);

        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);

getPlaces();
        Log.d(TAG, "onCreate: is runing");
        //the things of navigation
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //find the drawer layout
        drawer = findViewById(R.id.drawer_layout);
        NavigationView sideNavView = findViewById(R.id.side_nav_view);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_open_drawer);
//use the nav icon to opent drawer layout
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });


        BottomNavigationView botomNavigation = findViewById(R.id.botomNavigation);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.maps, R.id.mapListV, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(botomNavigation, navController);
        //----------------------------------------------------------------------------------------------------------------------------------------------


    }


    //the method used to start activity when user is sign in
    public static void start(Activity activity) {
        Intent intent = new Intent(activity.getApplicationContext(), G4LunchMain.class);
        activity.startActivity(intent);
    }

    //init the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.g4_lunch_main, menu);
        return true;
    }

    //working with menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.searchIcon) {
            //to see other part of implementation go to onActivity result. on the botom of class
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                    fieldList).build(G4LunchMain.this);
            startActivityForResult(intent, REQUESTAUTOCOMPLITION);

        }

        return super.onOptionsItemSelected(item);
    }


    public void getPlaces() {
        String placeTypesList[] = {"restaurant", "cafe", "bar"};

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
         locationProviderClient.getLastLocation()
        .addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                curentLat = location.getLatitude();
                curentLng = location.getLongitude();


        String urlAsString = "https://maps.googleapis.com/maps/api/place/details/output" + "?location=" + curentLat + "," + curentLng +
                "&radius=5000" + "&type=" + placeTypesList[0] + "&sensor=true" + "&key=" + "AIzaSyBZ1yf43MqKZwPmDvEkUx5CBufQpf01yDI";
        Log.d(TAG, "getPlaces: " + curentLat + " " + curentLng);

        URL url = null;
        try {
            url = new URL(urlAsString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        FetchPlacesData fetchPlacesData = new FetchPlacesData(url);
        new Thread(fetchPlacesData).start();
        //call my runable here
       // new PlaceTask().execute(url);
            }
        });
    }


    //this on activity result is used for alot of things. and the only working is autocmplete with places api
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUESTAUTOCOMPLITION && resultCode == RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            //write the logic here
        } else if (requestCode == 666) {
            Toast.makeText(G4LunchMain.this, "it fucking worck with activity result", Toast.LENGTH_SHORT).show();
        }

    }

    class FetchPlacesData implements Runnable {
        URL url;
        String line = "";
        String data = "";
        String parsed = "";
        int i = 0;

        public FetchPlacesData(URL url) {
            this.url = url;
        }


        @Override
        public void run() {


            try {
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

while (bufferedReader.readLine() != null){
    data += bufferedReader.readLine();
}

JSONArray jsonArray = new JSONArray(data);

                Log.d(TAG, "Jason Array: " + jsonArray.toString());


            }
            catch (IOException | JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "error: " + e.getMessage(), e);
            }



        }

    }


}
//  private class PlaceTask extends AsyncTask<String, Integer, String> {

//      @Override
//      protected String doInBackground(String... strings) {
//          //Initialyze data
//          String data = null;
//          try {
//              data = downloadUrl(strings[0]);
//          } catch (IOException e) {
//              e.printStackTrace();
//          }
//          return data;
//      }

//      @Override
//      protected void onPostExecute(String s) {
//          new ParserTask().execute(s);
//      }

//      private String downloadUrl(String string) throws IOException {
//          //init URL
//          URL url = new URL(string);
//          //init conection
//          HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//          connection.connect();

//          InputStream stream = connection.getInputStream();

//          BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

//          StringBuilder builder = new StringBuilder();

//          String line = "";

//          while ((line = reader.readLine()) != null) {
//              builder.append(line);

//          }

//          String data = builder.toString();

//          reader.close();

//          return data;
//      }


//      private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {

//          List<Double> TheLatList = new ArrayList<>();
//          List<Double> TheLngList = new ArrayList<>();

//          @Override
//          protected List<HashMap<String, String>> doInBackground(String... strings) {
//              //createJson
//              OtherJsonParser otherJsonParser = new OtherJsonParser();

//              List<HashMap<String, String>> mapList = null;
//              JSONObject object = null;
//              try {
//                  object = new JSONObject(strings[0]);

//                  mapList = otherJsonParser.parseResult(object);

//              } catch (JSONException e) {
//                  e.printStackTrace();
//              }

//              return mapList;
//          }

//          @Override
//          protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
//              for (int i = 0; i < hashMaps.size(); i++) {
//                  HashMap<String, String> hashMapList = hashMaps.get(i);
//                  double lat = Double.parseDouble(hashMapList.get("lat"));
//                  double lng = Double.parseDouble(hashMapList.get("lng"));
//                  String name = hashMapList.get("name");
//                  LatLng latLng = new LatLng(lat, lng);

//                  TheLatList.add(lat);
//                  TheLngList.add(lng);
//                  if (i == hashMaps.size()){

//                  }

//              }

//          }
//      }


//  }


//





