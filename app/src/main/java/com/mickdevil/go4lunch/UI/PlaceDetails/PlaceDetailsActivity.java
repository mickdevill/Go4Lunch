package com.mickdevil.go4lunch.UI.PlaceDetails;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
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
import com.mickdevil.go4lunch.GetPlases.CustomPlace;
import com.mickdevil.go4lunch.UI.botoomNavStaf.WorkMates.WorkMatesRcvAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static com.mickdevil.go4lunch.UI.SignIn.MainSigninActivity.firebaseAuth;

public class PlaceDetailsActivity extends AppCompatActivity {

    public static final String keyForDetails = "thisPlace";
    private static final String TAG = "PlaceDetailsActivity";


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

        RootDBRef = FirebaseDatabase.getInstance().getReference(MainSigninActivity.USER_KEY);
        likes = FirebaseDatabase.getInstance().getReference(LIKES);

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
                    RootDBRef.child(G4LunchMain.appUserToUse.email.substring(0,
                            G4LunchMain.appUserToUse.email.indexOf("@"))).updateChildren(AppUser.toMap(curentUser));
                    isImGoing = false;
                } else {
                    goToThePlace.setImageResource(R.drawable.going_here);
                    curentUser = G4LunchMain.appUserToUse;
                    curentUser.placeID = place.getPlaceId();
                    RootDBRef.child(G4LunchMain.appUserToUse.email.substring(0,
                            G4LunchMain.appUserToUse.email.indexOf("@"))).updateChildren(AppUser.toMap(curentUser));
                    isImGoing = true;
                }

            }
        });

        //  G4LunchMain.appUserToUse.email.substring(0,
        //                                G4LunchMain.appUserToUse.email.indexOf("@"))

        LikeResto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> oneLike = new HashMap<>();
                List<HashMap<String, Object>> allLikes = new ArrayList<>();

                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            HashMap<String, Object> theLike = ds.getValue(HashMap.class);

                            allLikes.add(theLike);

                        }
                        boolean switchAction = true;

                        for (int i = 0; i < allLikes.size(); i++) {
                            HashMap<String, Object> theLike = allLikes.get(i);

                            if (theLike.get("user") == G4LunchMain.appUserToUse.email.substring(0,
                                    G4LunchMain.appUserToUse.email.indexOf("@"))) {

                                oneLike.put("user", null);
                                oneLike.put("place", null);

                                likes.child(G4LunchMain.appUserToUse.email.substring(0,
                                        G4LunchMain.appUserToUse.email.indexOf("@"))).updateChildren(oneLike);
                                LikeResto.setImageResource(R.drawable.not_liked);

                                Log.d(TAG, "onSUCKses: " + "ono rabotaet");
                                switchAction = false;

                                break;
                            }
                        }

                        if (switchAction){
                            oneLike.put("user", G4LunchMain.appUserToUse.email.substring(0,
                                    G4LunchMain.appUserToUse.email.indexOf("@")));
                            oneLike.put("place", placeID);

                            likes.child(G4LunchMain.appUserToUse.email.substring(0,
                                    G4LunchMain.appUserToUse.email.indexOf("@"))).setValue(oneLike);

                            LikeResto.setImageResource(R.drawable.liked);

                            Log.d(TAG, "onFailure: " + "ono rabotaet");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };
                likes.addValueEventListener(valueEventListener);


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

        likes.child(G4LunchMain.appUserToUse.email.substring(0,
                G4LunchMain.appUserToUse.email.indexOf("@"))).child("place").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {

                LikeResto.setImageResource(R.drawable.liked);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                LikeResto.setImageResource(R.drawable.not_liked);


            }
        });
    }


}