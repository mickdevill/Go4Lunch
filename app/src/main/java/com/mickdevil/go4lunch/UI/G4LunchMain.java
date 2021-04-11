package com.mickdevil.go4lunch.UI;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.mickdevil.go4lunch.AppUser;
import com.mickdevil.go4lunch.R;
import com.mickdevil.go4lunch.TreadManager.HandlerForMsg;
import com.mickdevil.go4lunch.TreadManager.HavyTasksThread;
import com.mickdevil.go4lunch.UI.SignIn.MainSigninActivity;
import com.mickdevil.go4lunch.UI.botoomNavStaf.GetPlaces;

import java.util.Arrays;
import java.util.List;

public class G4LunchMain extends AppCompatActivity  {
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
    private static PlacesClient client;

    List<Place.Field> fieldList;
    private static final String apikey = "AIzaSyBZ1yf43MqKZwPmDvEkUx5CBufQpf01yDI";

    //-----------------------------------------------------------------------------------------
    private static HavyTasksThread havyTasksThread;


    //PSF strings
    ////////////////////////////////////////////////////////////////////////////////////////
    private static final int REQUESTAUTOCOMPLITION = 100;
    public static final String appUserKey = "appUser";
    //-----------------------------------------------------------------------------------------

    private AppUser appUserFromParcelForTest;
    private AppUser appUserToUse = null;

    NavigationView sideNavView;
    View header;
    //the onCreate
    @SuppressLint("VisibleForTests")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g4_lunch_main);


        appUserFromParcelForTest = getIntent().getParcelableExtra(G4LunchMain.appUserKey);


        fieldList = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.OPENING_HOURS, Place.Field.RATING);

        Places.initialize(G4LunchMain.this, apikey);

        client = Places.createClient(G4LunchMain.this);

        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);


//work With my thread!!!!!!!!!!
        havyTasksThread = new HavyTasksThread("havy task thread", G4LunchMain.this);

        if (!havyTasksThread.isAlive()) {
            havyTasksThread.start();
        }

//        handleMSG(1);

        Log.d(TAG, "onCreate: is runing");
        //the things of navigation
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //find the drawer layout
        drawer = findViewById(R.id.drawer_layout);
        sideNavView = findViewById(R.id.side_nav_view);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_open_drawer);
//use the nav icon to opent drawer layout
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
                menegeDrawerMenu();
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

        //set user info in side nav header and initing this views

        header = sideNavView.getHeaderView(0);



        TextView sideNavFLname = header.findViewById(R.id.sideNavFLname);
        TextView sideNavEmail = header.findViewById(R.id.sideNavEmail);
        ImageView sideNavProfilePhoto = header.findViewById(R.id.sideNavProfilePhoto);

        if (appUserFromParcelForTest != null) {

            appUserToUse = appUserFromParcelForTest;

            if (appUserToUse.Lname != null) {
                sideNavFLname.setText(appUserToUse.Fname + " " + appUserToUse.Lname);
            } else {
                sideNavFLname.setText(appUserToUse.Fname);
            }
            sideNavEmail.setText(appUserToUse.email);
            Glide.with(sideNavProfilePhoto)
                    .load(appUserToUse.photo)
                    .apply(RequestOptions.circleCropTransform())
                    .into(sideNavProfilePhoto);



        }
        //----------------------------------------------------------------------------------------------------------------------------------------------

    }

    //init the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.g4_lunch_main, menu);


        return true;
    }





    //WORKING WITH MENUS WORKING WITH MENUS WORKING WITH MENUS WORKING WITH MENUS WORKING WITH MENUS WORKING WITH MENUS WORKING WITH MENUS WORKING WITH MENUS WORKING WITH MENUS
    //WORKING WITH MENUS WORKING WITH MENUS WORKING WITH MENUS WORKING WITH MENUS WORKING WITH MENUS WORKING WITH MENUS WORKING WITH MENUS WORKING WITH MENUS WORKING WITH MENUS
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

    public void menegeDrawerMenu(){

        MenuItem yourLunch = sideNavView.getMenu().findItem(R.id.your_lunch);
        MenuItem setthings = sideNavView.getMenu().findItem(R.id.setthings);
        MenuItem logOut = sideNavView.getMenu().findItem(R.id.log_out);

        yourLunch.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(G4LunchMain.this, "this is my \" YOUR LUNCH\" action", Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        setthings.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //this is the last thing i will do becose i need to finish all project to do action on all strings at same time
                Toast.makeText(G4LunchMain.this, "this is my \" SETTHINGS\" action", Toast.LENGTH_SHORT).show();


                return false;
            }
        });

        logOut.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
finish();
                return false;
            }
        });

    }

//WORKING WITH MENUS END!!! //WORKING WITH MENUS END!!! //WORKING WITH MENUS END!!! //WORKING WITH MENUS END!!! //WORKING WITH MENUS END!!! //WORKING WITH MENUS END!!!  ////////
//WORKING WITH MENUS END!!! //WORKING WITH MENUS END!!! //WORKING WITH MENUS END!!! //WORKING WITH MENUS END!!! //WORKING WITH MENUS END!!! //WORKING WITH MENUS END!!! ////////

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


    public static void handleMSG(int taskCode) {
        Message msg = Message.obtain();
        msg.what = taskCode;
        havyTasksThread.getHuendler().sendMessage(msg);
    }


    public static void start(Activity activity, Intent intent) {
        activity.startActivity(intent);
    }


    public static FusedLocationProviderClient getLocationProviderClient() {
        return locationProviderClient;
    }

    public static PlacesClient getClient() {
        return client;
    }


    public static String getApikey() {
        return apikey;
    }

    public HavyTasksThread getHavyTasksThread() {
        return havyTasksThread;
    }



}







