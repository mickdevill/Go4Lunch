package com.mickdevil.go4lunch.UI;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.mickdevil.go4lunch.R;
import com.mickdevil.go4lunch.UI.botoomNavStaf.GetPlaces;
import com.mickdevil.go4lunch.UI.botoomNavStaf.map.MapFragment;

import java.util.Arrays;
import java.util.List;

public class G4LunchMain extends AppCompatActivity {


    private AppBarConfiguration mAppBarConfiguration;

    private DrawerLayout drawer;
    private Toolbar toolbar;

    private com.google.android.material.appbar.AppBarLayout AppBarLayout;
    GetPlaces getPlaces;
    private FusedLocationProviderClient locationProviderClient;
    PlacesClient client;

    List<Place.Field> fieldList;

    private static final int REQUESTAUTOCOMPLITION = 100;

    @SuppressLint("VisibleForTests")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g4_lunch_main);

         fieldList = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.OPENING_HOURS, Place.Field.RATING);


        Places.initialize(G4LunchMain.this, "AIzaSyBZ1yf43MqKZwPmDvEkUx5CBufQpf01yDI");

        locationProviderClient = new FusedLocationProviderClient(G4LunchMain.this);

        Places.createClient(G4LunchMain.this);

        getPlaces = new GetPlaces(this);

        //the things of navigation
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        drawer = findViewById(R.id.drawer_layout);

        AppBarLayout = findViewById(R.id.AppBarLayout);
        toolbar = findViewById(R.id.toolbar);




        BottomNavigationView botomNavigation = findViewById(R.id.botomNavigation);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.maps, R.id.mapListV, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(botomNavigation, navController);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    }

    public static void start(Activity activity) {
        Intent intent = new Intent(activity.getApplicationContext(), G4LunchMain.class);
        activity.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.g4_lunch_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

         if (id == R.id.searchIcon) {
             Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                     fieldList ).build(G4LunchMain.this);
             startActivityForResult(intent, REQUESTAUTOCOMPLITION);

         }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MapFragment.GPS_REQUEST_CODE) {

            LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

            boolean providerEnable = locationManager.isProviderEnabled(locationManager.GPS_PROVIDER);

            if (providerEnable) {
                Toast.makeText(this, "it worck", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "it DON'T worck", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == REQUESTAUTOCOMPLITION && resultCode == RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);


        }

    }


    public void getLoc() {
        if (ActivityCompat.checkSelfPermission(G4LunchMain.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(G4LunchMain.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }

        locationProviderClient.getLastLocation().addOnSuccessListener(G4LunchMain.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null) {
                    LatLng me = new LatLng(location.getLatitude(), location.getLongitude());


                }
            }

        }).addOnFailureListener(G4LunchMain.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


}


//RIP dear code, I will remember you


//the set elevation method to make view above others in "steck"
// searchOnMap.setElevation(1000);



//  public void searchMdoe(Boolean barMode) {

//      if (barMode) {
//          setSupportActionBar(toolbar);

//          NavigationView navigationView = findViewById(R.id.nav_view);
//          toolbar.setNavigationIcon(R.drawable.ic_open_drawer);

//          toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//              @Override
//              public void onClick(View v) {
//                  drawer.openDrawer(Gravity.LEFT);
//              }
//          });

//      } else {
//          AppBarLayout.setVisibility(View.INVISIBLE);
//          toolbar.setVisibility(View.INVISIBLE);
//          searchOnMap.setVisibility(View.VISIBLE);
//          searchOnMap.setFocusable(false);
//          searchOnMap.bringToFront();

//          searchOnMap.setOnClickListener(new View.OnClickListener() {
//              @Override
//              public void onClick(View v) {
//                  Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
//                          getPlaces.getPlacesList() ).build(G4LunchMain.this);
//              startActivityForResult(intent, REQUESTAUTOCOMPLITION);

//              }
//          });
//      }

//  }








