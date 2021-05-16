package com.mickdevil.go4lunch.UI.PlaceDetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
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
import com.mickdevil.go4lunch.UI.botoomNavStaf.WorkMates.WorkMatesRcvAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlaceDetailsActivity extends AppCompatActivity {

    public static final String keyForDetails = "thisPlace";
    private static final String TAG = "PlaceDetailsActivity";
    private String curendUserUidOnRTDB;

    //view elements//////////////////////////////////////////////////////////////////
    ImageView placeDetailImage, callResto, LikeResto, webSiteResto;
    RecyclerView placeDetailsRCV;
    FloatingActionButton goToThePlace;
    TextView DetailsPlaceName;
    List<AppUser> workmatesWillGo;
//-------------------------------------------------------------------------------

    //class logic & work vars////////////////////////////////////////////////////
    String placeID;
    String userMail = G4LunchMain.appUserToUse.email;
    boolean isImGoing;

    PlaceG4Lunch place;
//-----------------------------------------------------------------------------

    //fierbase/////////////////////////////////////////////////////////////////
    DatabaseReference RootDBRef;
    DatabaseReference likes;
    public static final String LIKES = "Likes";

    //----------------------------------------------------------------------------

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_details_activity);
        initViews();

        placeID = getIntent().getStringExtra(keyForDetails);

        place = GetPlacesTheRightWay.finalPlacesResult.get(getThePlace(placeID));


        placeDetailImage.setImageBitmap(place.getPhoto());
        DetailsPlaceName.setText(place.getPlaceName());

        workmatesWillGo = new ArrayList<>();

        placeDetailsRCV.setLayoutManager(new LinearLayoutManager(PlaceDetailsActivity.this));
        placeDetailsRCV.addItemDecoration(new DividerItemDecoration(PlaceDetailsActivity.this, DividerItemDecoration.VERTICAL));

        RootDBRef = FirebaseDatabase.getInstance().getReference(MainSigninActivity.USER_PATH);
        likes = FirebaseDatabase.getInstance().getReference(LIKES);
        if (!G4LunchMain.appUserToUse.email.isEmpty()) {
            curendUserUidOnRTDB = G4LunchMain.appUserToUse.email.substring(0,
                    G4LunchMain.appUserToUse.email.indexOf("@"));
        }
        getDataFromRTDB();

        if (isImGoing) {
            goToThePlace.setImageResource(R.drawable.going_here);
        } else {
            goToThePlace.setImageResource(R.drawable.not_going);
        }

        setDrawebleToLikeBTN();
        isImGoing = isImGoing();


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
            List<AppUser> workmates = new ArrayList<>();
            boolean ImGoing;
            AppUser curentUser;

            @Override
            public void onClick(View view) {
                if (isImGoing) {
                    goToThePlace.setImageResource(R.drawable.not_going);

                    curentUser = G4LunchMain.appUserToUse;
                    curentUser.placeID = "not going here";
                    RootDBRef.child(curendUserUidOnRTDB).updateChildren(AppUser.toMap(curentUser));
                    isImGoing = false;
                } else {
                    goToThePlace.setImageResource(R.drawable.going_here);
                    curentUser = G4LunchMain.appUserToUse;
                    curentUser.placeID = place.getPlaceId();
                    RootDBRef.child(curendUserUidOnRTDB).updateChildren(AppUser.toMap(curentUser));
                    isImGoing = true;
                }

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

        ValueEventListener valueEventListener = new ValueEventListener() {
            AppUser theDude;
            List<AppUser> workmates = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    AppUser appUser = ds.getValue(AppUser.class);
                    workmates.add(appUser);
                    workmatesWillGo.add(appUser);

                }


                for (int i = 0; i < workmates.size(); i++) {
                    theDude = workmates.get(i);

                    if (place.getPlaceId() != theDude.placeID) {
                        workmates.remove(i);
                    }

                }

                placeDetailsRCV.setAdapter(new WorkMatesRcvAdapter(workmates));


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        RootDBRef.addValueEventListener(valueEventListener);

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
    }

    private int getThePlace(String id) {
        int result = 0;

        for (int i = 0; i < GetPlacesTheRightWay.finalPlacesResult.size(); i++) {

            if (id.equals(GetPlacesTheRightWay.finalPlacesResult.get(i).getPlaceId())) {
                result = i;
                break;
            }
        }
        return result;
    }

    private boolean isImGoing() {
        boolean result = false;

        for (int i = 0; i < workmatesWillGo.size(); i++) {
            if (workmatesWillGo.get(i).email.equals(userMail)) {

                result = true;
                break;
            }
        }

        return result;
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


}