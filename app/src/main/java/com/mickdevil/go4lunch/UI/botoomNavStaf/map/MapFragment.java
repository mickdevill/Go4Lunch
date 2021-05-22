package com.mickdevil.go4lunch.UI.botoomNavStaf.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mickdevil.go4lunch.AppUser;
import com.mickdevil.go4lunch.GetPlases.PlaceG4Lunch;
import com.mickdevil.go4lunch.R;
import com.mickdevil.go4lunch.UI.G4LunchMain;
import com.mickdevil.go4lunch.UI.PlaceDetails.PlaceDetailsActivity;
import com.mickdevil.go4lunch.UI.SignIn.MainSigninActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;


public class MapFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private FloatingActionButton findMe;
    private View view;

    private static final String TAG = "MapFragment";
    private LocationManager locationManager;


    private FusedLocationProviderClient locationProviderClient;

    public static Location locationForPlaces;

    private static GoogleMap TheStaticMap;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @SuppressLint("VisibleForTests")
        @Override
        public void onMapReady(GoogleMap googleMap) {

            TheStaticMap = googleMap;

            locationProviderClient = new FusedLocationProviderClient(getContext());


            //the main thing in the class
            findMe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    getCurentLoc(googleMap);

                }
            });


        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_map, container, false);
        findMe = view.findViewById(R.id.findMe);

        Log.d(TAG, "BTN find me: = " + findMe);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(callback);
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


    private void getCurentLoc(GoogleMap googleMap) {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Log.d(TAG, "pas de permissions");
        }
        locationProviderClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, new CancellationToken() {
            @Override
            public boolean isCancellationRequested() {


                return false;
            }

            @NonNull
            @Override
            public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    locationForPlaces = location;

                    LatLng me = new LatLng(location.getLatitude(), location.getLongitude());
                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(me));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(me, 12));

                    G4LunchMain.handleMSG(1);


                }


            }
        });


    }


    public static void PutMarckersOnMap(List<PlaceG4Lunch> places, Context context) {


        DatabaseReference users = FirebaseDatabase.getInstance().getReference(MainSigninActivity.USER_PATH);

        users.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                List<AppUser> appUsers = new ArrayList<>();
                AppUser appUser;

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    appUser = ds.getValue(AppUser.class);
                    appUsers.add(appUser);

                }


                LatLng latLng;
                for (int i = 0; i < places.size(); i++) {
                    PlaceG4Lunch placeG4Lunch = places.get(i);
                    latLng = new LatLng(placeG4Lunch.getLatitude(), placeG4Lunch.getLongitude());
                    boolean bool = true;


                    for (Iterator<AppUser> iterator = appUsers.iterator(); iterator.hasNext(); ) {
                        appUser = iterator.next();

                        if (placeG4Lunch.getPlaceId().equals(appUser.placeID)) {
                            TheStaticMap.addMarker(new MarkerOptions().position(latLng).title(placeG4Lunch.getPlaceName()))
                                    .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                            bool = false;
                            break;
                        }

                    }

                    if (bool) {
                        TheStaticMap.addMarker(new MarkerOptions().position(latLng).title(placeG4Lunch.getPlaceName()))
                                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                    }

                    TheStaticMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            Intent intent = new Intent(context, PlaceDetailsActivity.class);
                            intent.putExtra(PlaceDetailsActivity.keyForDetails, placeG4Lunch.getPlaceId());
                            context.startActivity(intent);
                            return false;
                        }
                    });

                }


            }
        });


    }

    private static BitmapDescriptor bitmapFromVector(Context context, int vectorResId) {
        // below line is use to generate a drawable.
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);

        // below line is use to set bounds to our vector drawable.
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());

        // below line is use to create a bitmap for our
        // drawable which we have added.
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        // below line is use to add bitmap in our canvas.
        Canvas canvas = new Canvas(bitmap);

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas);

        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


}