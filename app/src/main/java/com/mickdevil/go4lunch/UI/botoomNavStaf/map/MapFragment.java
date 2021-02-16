package com.mickdevil.go4lunch.UI.botoomNavStaf.map;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.mickdevil.go4lunch.R;


public class MapFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private FloatingActionButton findMe;
    private static final String TAG = "MapFragment";
    // private GeoHandler geoHandler;

    //double[] latlngList;
    private static final int REQUEST_LOCATION = 1;
    private LocationManager locationManager;
    private double lat, lng;

    private GoogleApiClient mGoogleApiClient;
    private Location location;
    // private LocationRequest mLocationRequest;
    // private com.google.android.gms.location.LocationListener listener;
    private static final long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private static final long FASTEST_INTERVAL = 20000; /* 20 sec */

    private FusedLocationProviderClient locationProviderClient;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {

            locationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

            findMe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Dexter.withContext(getContext()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                                return;
                            }
                            locationProviderClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    // Got last known location. In some rare situations this can be null.
                                    if (location != null) {
                                        LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
                                        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                                        Log.d(TAG, "location = " + location);
                                    }
                                }
                            });

                        }
                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                        }
                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                        }
                    }).check();


                }

            });




        }


    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        findMe = view.findViewById(R.id.findMe);
        Log.d(TAG, "BTN find me: = "+ findMe);
        return view;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {

            mapFragment.getMapAsync(callback);
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}

















//dead trys dead code

  // private void getLoc() {
  //     if (ActivityCompat.checkSelfPermission(
  //             getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
  //             getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
  //         requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
  //     } else {

  //        locationManager.getCurrentLocation();
  //         Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

  //         lat = locationGPS.getLatitude();
  //         lng = locationGPS.getLongitude();

  //     }


  // }

// private class GeoHandler extends Handler {
//     @Override
//     public void handleMessage(@NonNull Message msg) {
//         if (msg.what == 1){
//             Bundle bundle = msg.getData();
//             latlngList = bundle.getDoubleArray(key);
//         }
//     }
// }


//Geolocation.getAddress(getActivity().getApplicationContext(), geoHandler);

// LatLng latLng = new LatLng(latlngList[0], latlngList[1]);
//   Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
//   String result = null;
//   String myAddress = "myAddres";
// getLoc();

//    LatLng latLng = new LatLng(lat, lng );
//
//    googleMap.addMarker(new MarkerOptions().position(latLng));
//    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//    googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//        @Override
//        public void onMapClick(LatLng latLng) {
//
//        }
//    });


//geoHandler = new GeoHandler();