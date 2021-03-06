package com.mickdevil.go4lunch;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class deadCode {


    //RIP dear code, I will remember you



// //method to geet curent Location
// public static LatLng getCurentLocation(Context context, Activity activity, FusedLocationProviderClient locationProviderClient) {
//     LatLng latLng;

//     if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
//             != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,
//             Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

//     }

//     locationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
//         @Override
//         public void onSuccess(Location location) {

//             latLng = new LatLng(location.getLatitude(), location.getLongitude());

//         }
//     }).addOnFailureListener(activity, new OnFailureListener() {
//         @Override
//         public void onFailure(@NonNull Exception e) {
//             Toast.makeText(context, "god damn", Toast.LENGTH_SHORT).show();
//         }
//     });

//     return latLng;

// }




//some stuf from mapFragment

// public boolean isGPSenabled() {
//     locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
//
//     boolean providerEnable = locationManager.isProviderEnabled(locationManager.GPS_PROVIDER);
//
//     if (providerEnable) {
//         return true;
//     } else {
//         AlertDialog alertDialog = new AlertDialog.Builder(getActivity().getApplicationContext())
//                 .setTitle("GPS ask").setMessage("the GPS is requierd for this app").setPositiveButton("ya man", new
//                         DialogInterface.OnClickListener() {
//                             @Override
//                             public void onClick(DialogInterface dialog, int which) {
//                                 Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                                 startActivityForResult(intent, GPS_REQUEST_CODE);
//                             }
//                         }).show();
//     }
//     return false;
// }

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


// public void getLoc() {
//     if (ActivityCompat.checkSelfPermission(G4LunchMain.this, Manifest.permission.ACCESS_FINE_LOCATION)
//             != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(G4LunchMain.this,
//             Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//     }
//
//     locationProviderClient.getLastLocation().addOnSuccessListener(G4LunchMain.this, new OnSuccessListener<Location>() {
//         @Override
//         public void onSuccess(Location location) {
//
//             if (location != null) {
//                 LatLng me = new LatLng(location.getLatitude(), location.getLongitude());
//
//
//             }
//         }
//
//     }).addOnFailureListener(G4LunchMain.this, new OnFailureListener() {
//         @Override
//         public void onFailure(@NonNull Exception e) {
//
//         }
//     });
// }

}
