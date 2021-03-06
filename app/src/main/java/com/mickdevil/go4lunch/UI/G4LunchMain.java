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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class G4LunchMain extends AppCompatActivity {

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


        //the things of navigation
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //find the drawer layout
        drawer = findViewById(R.id.drawer_layout);
        NavigationView sideNavView = findViewById(R.id.side_nav_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
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
        Task<Location> task = locationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                curentLat = location.getLatitude();
                curentLng = location.getLongitude();
            }
        });

        String url = "https://maps.googleapis.com/maps/api/nearbysearch/json" + "?location=" + curentLat + "," + curentLng +
                "&radius=5000" + "&type=" + placeTypesList[1] + "&sensor=true" + "&key=" + "AIzaSyBZ1yf43MqKZwPmDvEkUx5CBufQpf01yDI";

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


   private class PlaceTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
          //Initialyze data
         String data = null;
            try {
                data = downloadUrl(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            new ParserTask.execute(s);
        }

        private String downloadUrl(String string) throws IOException {
            //init URL
        URL url = new URL(string);
        //init conection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
  connection.connect();

        InputStream stream = connection.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        StringBuilder builder = new StringBuilder();

        String line = "";

        while((line = reader.readLine()) != null ){
           builder.append(line);

        }

        String data = builder.toString();

        reader.close();

        return data;
        }


        private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {

            @Override
            protected List<HashMap<String, String>> doInBackground(String... strings) {
               //createJson
                OtherJsonParser
                return null;
            }
        }





    }




}





