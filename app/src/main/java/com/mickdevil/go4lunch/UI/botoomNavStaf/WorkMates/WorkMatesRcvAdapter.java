package com.mickdevil.go4lunch.UI.botoomNavStaf.WorkMates;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPhotoResponse;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mickdevil.go4lunch.AppUser;
import com.mickdevil.go4lunch.GetPlases.GetPlacesTheRightWay;
import com.mickdevil.go4lunch.GetPlases.PlaceG4Lunch;
import com.mickdevil.go4lunch.R;
import com.mickdevil.go4lunch.UI.G4LunchMain;
import com.mickdevil.go4lunch.UI.PlaceDetails.PlaceDetailsActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class WorkMatesRcvAdapter extends RecyclerView.Adapter<WorkMatesRcvAdapter.Holder> {
    List<AppUser> worMates = new ArrayList<>();
    private static final String TAG = "WorkMatesRcvAdapter";
    int callerCase;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference placesReff = firebaseDatabase.getReference(GetPlacesTheRightWay.ALL_GETED_PACES_EVER);

    public WorkMatesRcvAdapter(List<AppUser> worMates, int callerCase) {
        this.worMates = worMates;
        this.callerCase = callerCase;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workmates_holder,
                parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        AppUser appUser = worMates.get(position);
        Glide.with(holder.wokmateProfilePhoto).load(appUser.photo)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.wokmateProfilePhoto);
        if (callerCase == 1) {
            workmateIsEating(holder, position);
        } else {
            holder.nameAndPlaceHeWillGo.setText(appUser.Fname);
        }
        if (appUser.placeID != null) {

            holder.workmatesHolderContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), PlaceDetailsActivity.class);
                    intent.putExtra(PlaceDetailsActivity.keyForDetails, appUser.placeID);
                    v.getContext().startActivity(intent);

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return worMates.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView wokmateProfilePhoto;
        TextView nameAndPlaceHeWillGo;
        RelativeLayout workmatesHolderContainer;

        public Holder(@NonNull View itemView) {
            super(itemView);
            wokmateProfilePhoto = itemView.findViewById(R.id.wokmateProfilePhoto);
            nameAndPlaceHeWillGo = itemView.findViewById(R.id.nameAndPlaceHeWillGo);
            workmatesHolderContainer = itemView.findViewById(R.id.workmatesHolderContainer);
        }


    }


    public void workmateIsEating(Holder holder, int position) {
        AppUser appUser = worMates.get(position);
        PlaceG4Lunch placeOfUser;
        boolean isPlaceExist = true;
        final Bitmap[] bitmap = new Bitmap[1];
        final boolean[] doWithOutPhoto = {true};
        if (appUser.placeID != null) {

            for (int i = 0; i < GetPlacesTheRightWay.finalPlacesResult.size(); i++) {

                if (appUser.placeID.equals(GetPlacesTheRightWay.finalPlacesResult.get(i).getPlaceId())) {
                    placeOfUser = GetPlacesTheRightWay.finalPlacesResult.get(i);
                    holder.nameAndPlaceHeWillGo.setText(appUser.Fname + " " + "is eating " + placeOfUser.getPlaceName());
                    isPlaceExist = false;
                    Log.d(TAG, "allredy in fetched  list ");
                    break;
                }
            }
            Log.d(TAG, "IS MY BOOLEAN WORKING " + isPlaceExist);
            if (isPlaceExist) {
                Log.d(TAG, "THE PLACE IS4T IN LIST!!!");

                workmateIsEatingTheFierBaseSide(appUser, holder);
            }
        } else {
            holder.nameAndPlaceHeWillGo.setText(appUser.Fname + " " + "hasn't decidet yet");
            Log.d(TAG, "is null " + appUser.Fname);
        }

    }


    private void workmateIsEatingTheFierBaseSide(AppUser appUser, Holder holder) {

        placesReff.child(appUser.placeID).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    HashMap<String, Object> placeFromFire = (HashMap<String, Object>) dataSnapshot.getValue();
                    PlaceG4Lunch placeG4Lunch = PlaceG4Lunch.placeG4LunchFROMMap(placeFromFire, null);
                    holder.nameAndPlaceHeWillGo.setText(appUser.Fname + " " + "is eating " + placeG4Lunch.getPlaceName());
                    Log.d(TAG, "from fier base " + placeG4Lunch.getPlaceName());

                    if (GetPlacesTheRightWay.finalPlacesResult.size() > 1) {

                        boolean allredyIn = true;

                        for (int i = 0; i < GetPlacesTheRightWay.finalPlacesResult.size(); i++) {

                            if (GetPlacesTheRightWay.finalPlacesResult.get(i).getPlaceId().equals(placeG4Lunch.getPlaceId())) {
                                allredyIn = false;
                            }
                        }

                        if (allredyIn) {
                            GetPlacesTheRightWay.finalPlacesResult.add(placeG4Lunch);
                        }

                    } else {
                        Log.d(TAG, "is my bit map null? " + placeG4Lunch.getPhoto());
                        GetPlacesTheRightWay.placesFromFierBase.add(placeG4Lunch);

                    }

                }
            });


    }


}
