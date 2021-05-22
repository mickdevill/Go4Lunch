package com.mickdevil.go4lunch.UI.PlaceDetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPhotoResponse;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mickdevil.go4lunch.AppUser;
import com.mickdevil.go4lunch.GetPlases.GetPlacesTheRightWay;
import com.mickdevil.go4lunch.GetPlases.PlaceG4Lunch;
import com.mickdevil.go4lunch.R;
import com.mickdevil.go4lunch.UI.G4LunchMain;
import com.mickdevil.go4lunch.UI.SignIn.MainSigninActivity;
import com.mickdevil.go4lunch.UI.botoomNavStaf.ListView.ListViewAdapter;
import com.mickdevil.go4lunch.UI.botoomNavStaf.WorkMates.WorkMatesRcvAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PlaceDetailsActivity extends AppCompatActivity {

    public static final String keyForDetails = "thisPlace";
    private static final String TAG = "PlaceDetailsActivity";
    private String curendUserUidOnRTDB;

    //view elements//////////////////////////////////////////////////////////////////
    ImageView placeDetailImage, callResto, LikeResto, webSiteResto, detailsStar1, detailsStar2, detailsStar3;
    RecyclerView placeDetailsRCV;
    FloatingActionButton goToThePlace;
    TextView DetailsPlaceName, detailsAddress;

//-------------------------------------------------------------------------------

    //class logic & work vars////////////////////////////////////////////////////
    String placeID;
    String userMail = G4LunchMain.appUserToUse.email;


    PlaceG4Lunch place;
//-----------------------------------------------------------------------------

    //fierbase/////////////////////////////////////////////////////////////////
    DatabaseReference usersDbReff;
    DatabaseReference likes;
    public static final String LIKES = "Likes";

    //----------------------------------------------------------------------------

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_details_activity);
        initViews();

        placeID = getIntent().getStringExtra(keyForDetails);

        place = getThePlace(placeID);

        if (place.getPhoto() != null) {
            placeDetailImage.setImageBitmap(place.getPhoto());
        } else {
            getAndSetPhoto(placeID);
        }

        DetailsPlaceName.setText(place.getPlaceName());
        detailsAddress.setText(place.getVicinity());



        placeDetailsRCV.setLayoutManager(new LinearLayoutManager(PlaceDetailsActivity.this));
        placeDetailsRCV.addItemDecoration(new DividerItemDecoration(PlaceDetailsActivity.this, DividerItemDecoration.VERTICAL));

        usersDbReff = FirebaseDatabase.getInstance().getReference(MainSigninActivity.USER_PATH);
        likes = FirebaseDatabase.getInstance().getReference(LIKES);
        if (!G4LunchMain.appUserToUse.email.isEmpty()) {
            curendUserUidOnRTDB = G4LunchMain.appUserToUse.email.substring(0,
                    G4LunchMain.appUserToUse.email.indexOf("@"));
        }
        getDataFromRTDB();
        getLikes();

        setDrawebleToLikeBTN();
        setDrawebleToGoInThePlaceBTN();

        callResto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String call = "tel:" + place.getPhoneNumber();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(call));
                startActivity(callIntent);
            }
        });


        webSiteResto.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (!place.getWebSite().isEmpty()) {

                    Uri uri = null;
                    uri = Uri.parse(place.getWebSite());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else {
                    Toast.makeText(PlaceDetailsActivity.this, "this hobo's place even don't have web site!!!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        goToThePlace.setOnClickListener(new View.OnClickListener() {
            AppUser curentUser;

            @Override
            public void onClick(View view) {

                usersDbReff.child(curendUserUidOnRTDB).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        AppUser appUser = dataSnapshot.getValue(AppUser.class);
                        if (appUser.placeID != null) {

                            if (appUser.placeID.equals(placeID)) {
                                goToThePlace.setImageResource(R.drawable.not_going);
                                curentUser = G4LunchMain.appUserToUse;
                                curentUser.placeID = "not going here";
                                usersDbReff.child(curendUserUidOnRTDB).updateChildren(AppUser.toMap(curentUser));
                                Log.d(TAG, "remouve place id");
                                getDataFromRTDB();

                            } else {
                                goToThePlace.setImageResource(R.drawable.going_here);
                                curentUser = G4LunchMain.appUserToUse;
                                curentUser.placeID = place.getPlaceId();
                                usersDbReff.child(curendUserUidOnRTDB).updateChildren(AppUser.toMap(curentUser));
                                Log.d(TAG, "add place id becose it's not selected");
                                getDataFromRTDB();
                            }
                        } else {
                            goToThePlace.setImageResource(R.drawable.going_here);
                            curentUser = G4LunchMain.appUserToUse;
                            curentUser.placeID = place.getPlaceId();
                            usersDbReff.child(curendUserUidOnRTDB).updateChildren(AppUser.toMap(curentUser));
                            Log.d(TAG, "add place id becose it's not NULL");
                            getDataFromRTDB();
                        }


                    }
                });


            }
        });

        LikeResto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                likes.child(curendUserUidOnRTDB).child("places").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        List<String> likedPlaces = (List<String>) dataSnapshot.getValue();

                        boolean switchAction = true;

                        try {
                            if (likedPlaces.contains(placeID)) {
                                likedPlaces.remove(placeID);
                                HashMap<String, Object> likedPlacesUpdated = new HashMap<>();
                                likedPlacesUpdated.put("places", likedPlaces);
                                likes.child(curendUserUidOnRTDB).updateChildren(likedPlacesUpdated);
                                LikeResto.setImageResource(R.drawable.not_liked);
                                switchAction = false;
                            }

                            if (switchAction) {
                                likedPlaces.add(placeID);
                                HashMap<String, Object> likedPlacesUpdated = new HashMap<>();
                                likedPlacesUpdated.put("places", likedPlaces);
                                likes.child(curendUserUidOnRTDB).updateChildren(likedPlacesUpdated);
                                LikeResto.setImageResource(R.drawable.liked);
                            }


                        } catch (NullPointerException np) {
                            np.printStackTrace();

                            List<String> places = new ArrayList<>();
                            places.add(placeID);
                            likes.child(curendUserUidOnRTDB).child("places").setValue(places);
                            LikeResto.setImageResource(R.drawable.liked);

                        }

                    }
                });

            }
        });


    }

    private void getDataFromRTDB() {

        usersDbReff.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                List<AppUser> workmates = new ArrayList<>();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    AppUser appUser = ds.getValue(AppUser.class);
                    if (appUser.placeID != null) {

                        if (!appUser.placeID.equals(placeID)) {
                            workmates.remove(appUser);
                            placeDetailsRCV.setAdapter(new WorkMatesRcvAdapter(workmates, 0));
                        }

                        if (appUser.placeID.equals(placeID)) {
                            workmates.add(appUser);
                            placeDetailsRCV.setAdapter(new WorkMatesRcvAdapter(workmates, 0));
                        }

                    }
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }


    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }


    private void initViews() {
        placeDetailImage = findViewById(R.id.placeDetailImage);
        callResto = findViewById(R.id.callResto);
        LikeResto = findViewById(R.id.LikeResto);
        webSiteResto = findViewById(R.id.webSiteResto);
        placeDetailsRCV = findViewById(R.id.placeDetailsRCV);
        goToThePlace = findViewById(R.id.goToThePlace);
        DetailsPlaceName = findViewById(R.id.DetailsPlaceName);
        detailsAddress = findViewById(R.id.detailsAddress);
        detailsStar1 = findViewById(R.id.detailsStar1);
        detailsStar2 = findViewById(R.id.detailsStar2);
        detailsStar3 = findViewById(R.id.detailsStar3);
    }


    private PlaceG4Lunch getThePlace(String id) {
        PlaceG4Lunch result = null;
        List<PlaceG4Lunch> myPlacesSource;

        if (GetPlacesTheRightWay.finalPlacesResult.size() > 0) {
            myPlacesSource = GetPlacesTheRightWay.finalPlacesResult;

        } else {
            myPlacesSource = GetPlacesTheRightWay.placesFromFierBase;
        }


        for (int i = 0; i < myPlacesSource.size(); i++) {

            if (id.equals(myPlacesSource.get(i).getPlaceId())) {
                result = myPlacesSource.get(i);
                break;
            }
        }
        return result;
    }

    private void setDrawebleToGoInThePlaceBTN() {
        usersDbReff.child(curendUserUidOnRTDB).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                AppUser appUser = dataSnapshot.getValue(AppUser.class);

                Log.d(TAG, "compare ID'is " + appUser.placeID + " " + placeID);
                if (appUser.placeID != null) {
                    if (appUser.placeID.equals(placeID)) {
                        goToThePlace.setImageResource(R.drawable.going_here);
                        Log.d(TAG, "set positive IMG");
                    }
                    if (!appUser.placeID.equals(placeID)) {
                        goToThePlace.setImageResource(R.drawable.not_going);
                        Log.d(TAG, "set negative IMG");
                    }
                } else {
                    goToThePlace.setImageResource(R.drawable.not_going);
                    Log.d(TAG, "set negative IMG");
                }

            }
        });

    }


    private void setDrawebleToLikeBTN() {

        likes.child(curendUserUidOnRTDB).child("places").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                List<String> likedPlaces = (List<String>) dataSnapshot.getValue();

                try {
                    if (likedPlaces.contains(placeID)) {
                        LikeResto.setImageResource(R.drawable.liked);
                    }

                    if (!likedPlaces.contains(placeID)) {
                        LikeResto.setImageResource(R.drawable.not_liked);
                    }

                } catch (NullPointerException npxa) {
                    npxa.printStackTrace();
                    LikeResto.setImageResource(R.drawable.not_liked);
                }
            }
        });


    }


    private void getAndSetPhoto(String placeId) {
        // Define a Place ID.


// Specify fields. Requests for photos must always have the PHOTO_METADATAS field.
        final List<Place.Field> fields = Collections.singletonList(Place.Field.PHOTO_METADATAS);

// Get a Place object (this example uses fetchPlace(), but you can also use findCurrentPlace())
        final FetchPlaceRequest placeRequest = FetchPlaceRequest.newInstance(placeId, fields);

        G4LunchMain.client.fetchPlace(placeRequest).addOnSuccessListener((response) -> {
            final Place place = response.getPlace();

            // Get the photo metadata.
            final List<PhotoMetadata> metadata = place.getPhotoMetadatas();
            if (metadata == null || metadata.isEmpty()) {
                return;
            }

            final PhotoMetadata photoMetadata = metadata.get(0);

            // Get the attribution text.
            final String attributions = photoMetadata.getAttributions();

            // Create a FetchPhotoRequest.
            final FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(photoMetadata)
                    .setMaxWidth(500) // Optional.
                    .setMaxHeight(300) // Optional.
                    .build();
            G4LunchMain.client.fetchPhoto(photoRequest).addOnSuccessListener(new OnSuccessListener<FetchPhotoResponse>() {
                @Override
                public void onSuccess(FetchPhotoResponse fetchPhotoResponse) {
                    Bitmap bitmap = fetchPhotoResponse.getBitmap();
                    if (bitmap != null) {
                        Log.d(TAG, "request photu sucses ");
                        placeDetailImage.setImageBitmap(bitmap);

                    } else {
                        Log.d(TAG, "request photu fail ");

                        placeDetailImage.setImageResource(R.drawable.no_place_photo);
                    }

                }
            });

        });
    }


    private void getLikes() {

        likes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> likedPlaces;
                int likesOnPlace = 0;

                for (DataSnapshot ds : snapshot.getChildren()) {
                    likedPlaces = (List<String>) ds.child("places").getValue();

                    Log.d(TAG, "DATA CHANGE!!!!!!!");

                    for (int i = 0; i < likedPlaces.size(); i++) {

                        if (placeID.equals(likedPlaces.get(i))) {
                            likesOnPlace++;
                        }
                    }


                }

                detailsStar1.setImageResource(R.drawable.not_liked);
                detailsStar2.setImageResource(R.drawable.not_liked);
                detailsStar3.setImageResource(R.drawable.not_liked);


                if (likesOnPlace == 1) {
                    detailsStar1.setImageResource(R.drawable.star_rate_24);
                }

                if (likesOnPlace == 2) {
                    detailsStar1.setImageResource(R.drawable.star_rate_24);
                    detailsStar2.setImageResource(R.drawable.star_rate_24);
                }
                if (likesOnPlace == 3 || likesOnPlace > 3) {
                    detailsStar1.setImageResource(R.drawable.star_rate_24);
                    detailsStar2.setImageResource(R.drawable.star_rate_24);
                    detailsStar3.setImageResource(R.drawable.star_rate_24);
                }

                Log.d(TAG, "liks on Place" + likesOnPlace);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }


}