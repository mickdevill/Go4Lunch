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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mickdevil.go4lunch.AppUser;
import com.mickdevil.go4lunch.GetPlases.PlaceG4Lunch;
import com.mickdevil.go4lunch.R;
import com.mickdevil.go4lunch.UI.SignIn.MainSigninActivity;
import com.mickdevil.go4lunch.GetPlases.CustomPlace;
import com.mickdevil.go4lunch.UI.botoomNavStaf.WorkMates.WorkMatesRcvAdapter;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class PlaceDetailsActivity extends AppCompatActivity {

    public static final String keyForDetails = "thisPlace";
    PlaceG4Lunch place;

    ImageView placeDetailImage, callResto, LikeResto, webSiteResto;
    RecyclerView placeDetailsRCV;
    FloatingActionButton goToThePlace;
    DatabaseReference DBRef;
    TextView DetailsPlaceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_details_activity);
        initViews();

        place = getIntent().getParcelableExtra(keyForDetails);

        placeDetailImage.setImageBitmap(place.getPhoto());
        DetailsPlaceName.setText(place.getPlaceName());


        placeDetailsRCV.setLayoutManager(new LinearLayoutManager(PlaceDetailsActivity.this));
        placeDetailsRCV.addItemDecoration(new DividerItemDecoration(PlaceDetailsActivity.this, DividerItemDecoration.VERTICAL));
        DBRef = FirebaseDatabase.getInstance().getReference(MainSigninActivity.USER_KEY);

  callResto.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
          String call = "tel:0665807323";
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
                }
                else {
                    Toast.makeText(PlaceDetailsActivity.this, "this hobo place even don't have web site!!!", Toast.LENGTH_SHORT);
                }

            }
        });



    }

    private void getDataFromRTDB() {
        List<AppUser> workmates = new ArrayList<>();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    AppUser appUser = ds.getValue(AppUser.class);

                    workmates.add(appUser);

                    placeDetailsRCV.setAdapter(new WorkMatesRcvAdapter(workmates));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        DBRef.addValueEventListener(valueEventListener);





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


}