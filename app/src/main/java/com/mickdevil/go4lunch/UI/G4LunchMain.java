package com.mickdevil.go4lunch.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.mickdevil.go4lunch.R;
import com.mickdevil.go4lunch.UI.botoomNavStaf.map.MapFragment;

import java.util.Arrays;
import java.util.List;

public class G4LunchMain extends AppCompatActivity {


    private AppBarConfiguration mAppBarConfiguration;

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private EditText searchOnMap;
    private com.google.android.material.appbar.AppBarLayout AppBarLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g4_lunch_main);
        Places.initialize(G4LunchMain.this, "AIzaSyBZ1yf43MqKZwPmDvEkUx5CBufQpf01yDI");

        //the things of navigation
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        drawer = findViewById(R.id.drawer_layout);
        searchOnMap = findViewById(R.id.searchOnMap);
        AppBarLayout = findViewById(R.id.AppBarLayout);
        toolbar = findViewById(R.id.toolbar);
        searchOnMap.setVisibility(View.INVISIBLE);

        //the toolBar
        searchMdoe(true);


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
            searchMdoe(false);

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

    }

    public void searchMdoe(Boolean barMode) {

        if (barMode) {
            setSupportActionBar(toolbar);

            NavigationView navigationView = findViewById(R.id.nav_view);
            toolbar.setNavigationIcon(R.drawable.ic_open_drawer);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawer.openDrawer(Gravity.LEFT);
                }
            });

        } else {
           AppBarLayout.setVisibility(View.INVISIBLE);
            toolbar.setVisibility(View.INVISIBLE);
            searchOnMap.setVisibility(View.VISIBLE);
            searchOnMap.setFocusable(false);
            searchOnMap.bringToFront();

        searchOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.)
            }
        });
        }

    }


}