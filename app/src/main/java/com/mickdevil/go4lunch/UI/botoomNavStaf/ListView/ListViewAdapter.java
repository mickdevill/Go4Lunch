package com.mickdevil.go4lunch.UI.botoomNavStaf.ListView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mickdevil.go4lunch.AppUser;
import com.mickdevil.go4lunch.GetPlases.PlaceG4Lunch;
import com.mickdevil.go4lunch.R;
import com.mickdevil.go4lunch.UI.PlaceDetails.PlaceDetailsActivity;
import com.mickdevil.go4lunch.UI.SignIn.MainSigninActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.lang.Double.valueOf;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.Holder> {
    List<PlaceG4Lunch> places;
    private static final String TAG = "ListViewAdapter";

    public ListViewAdapter(List<PlaceG4Lunch> places) {
        this.places = places;

    }

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    DatabaseReference likes = firebaseDatabase.getReference(PlaceDetailsActivity.LIKES);
    DatabaseReference wormatesDbReff = firebaseDatabase.getReference(MainSigninActivity.USER_PATH);

    long totalWorkMates;

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.places_list_holder, parent, false);
        return new Holder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        PlaceG4Lunch place = places.get(position);

holder.setIsRecyclable(false);
        holder.restoName.setText(place.getPlaceName());

        holder.restoDistance.setText(cutTheDistence(valueOf(place.getDistenceToUser()).toString()));
        if (place.getForomGoogleOrAlternative() == "GOOGLE") {
            if (place.getVicinity().contains(",")) {
                holder.restoAddress.setText(cutTheAdress(place.getVicinity()));
            } else {
                holder.restoAddress.setText(place.getVicinity());
            }
        } else {
            holder.restoAddress.setText(place.getVicinity());
        }

        if (place.getPhoto() != null) {
            holder.restImg.setImageBitmap(place.getPhoto());
        }
//set placePhoto
        if (place.getPhoto() != null) {
            holder.restImg.setImageBitmap(place.getPhoto());
        } else {
            holder.restImg.setImageResource(R.drawable.no_place_photo);
        }

        if (place.isOpened()) {
            holder.restoOpenColse.setText(R.string.isOpen);

        } else {
            holder.restoOpenColse.setText(R.string.isClose);
        }

        getLikes(holder, position);
        setRestoOpentClose(holder, position);
        howManyWorkmatesWillGo(holder, position);

        holder.placesListViewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PlaceDetailsActivity.class);
                intent.putExtra(PlaceDetailsActivity.keyForDetails, place.getPlaceId());
                view.getContext().startActivity(intent);
            }
        });

        Log.d(TAG, "onBindViewHolder: " + position);


    }


    @Override
    public int getItemCount() {
        return places.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView restoName, restoAddress, restoOpenColse, restoDistance, workmatesWillGo;
        ImageView restImg, rateStar1, rateStar2, rateStar3;
        RelativeLayout placesListViewHolder;

        public Holder(@NonNull View itemView) {
            super(itemView);
            restoName = itemView.findViewById(R.id.restoName);
            restoAddress = itemView.findViewById(R.id.restoAddress);
            restoOpenColse = itemView.findViewById(R.id.restoOpenColse);
            restImg = itemView.findViewById(R.id.restImg);
            restoDistance = itemView.findViewById(R.id.restoDistance);
            workmatesWillGo = itemView.findViewById(R.id.workmatesWillGo);
            placesListViewHolder = itemView.findViewById(R.id.placesListViewHolder);


            // the stars to set resto rate
            rateStar1 = itemView.findViewById(R.id.rateStar1);
            rateStar2 = itemView.findViewById(R.id.rateStar2);
            rateStar3 = itemView.findViewById(R.id.rateStar3);


        }
    }

    private String cutTheAdress(String vicinity) {
        String result;
        result = vicinity.substring(0, vicinity.indexOf(","));
        return result;
    }

    private String cutTheDistence(String distence) {
        String result;
        result = distence.substring(0, distence.indexOf(".")) + "m";
        return result;

    }


    public void howManyWorkmatesWillGo(Holder holder, int position) {
        wormatesDbReff.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                PlaceG4Lunch place = places.get(position);
                int howManyWillGoo = 0;
                AppUser appUser;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    appUser = ds.getValue(AppUser.class);

                    if (appUser.placeID != null) {

                        if (appUser.placeID.equals(place.getPlaceId())) {
                            howManyWillGoo++;
                        }
                    }
                }

                holder.workmatesWillGo.setText("(" + Integer.toString(howManyWillGoo) + ")");
            }
        });


    }


    @SuppressLint("SetTextI18n")
    public void setRestoOpentClose(Holder holder, int position) {
        PlaceG4Lunch place = places.get(position);

        //in my parser I add 2 empty string on 0 and 1, it simplify the interact with calendar

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (day == 1) {
            day = 8;
        }
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        if (place.getWeekdaysOpen().size() > 1) {

            String dayOfPlayce = place.getWeekdaysOpen().get(day).substring(place.getWeekdaysOpen().get(day).indexOf(" " + 1), place.getWeekdaysOpen().get(day).length());


            holder.restoOpenColse.setText(dayOfPlayce);
        }
        else {
            holder.restoOpenColse.setText(R.string.unknow_information);

        }
        //  String morning = "closed";
        //  String evening = "closed";

        //  if (dayOfPlayce.contains(",")) {
        //      morning = dayOfPlayce.substring(0, dayOfPlayce.indexOf(","));
        //      evening = dayOfPlayce.substring(dayOfPlayce.indexOf(","), dayOfPlayce.length());
        //  } else {
        //      morning = dayOfPlayce;
        //  }

        //  int endOfTimes = morning.indexOf(" ") + 3;

        //  if ( morning.substring(morning.indexOf(" ") + 1, endOfTimes).contains(":")){


        //  }
        // try {
        //     int placeHour = Integer.valueOf(morning.substring(morning.indexOf(" ") + 1, endOfTimes));
        //     int placeMin = Integer.valueOf(morning.substring(endOfTimes + 1, endOfTimes + 3));
        // }catch (NumberFormatException numberFormatException){


        // }

        // String openAt;


        // if (morning != "closed") {

        // }
        // Log.d(TAG, "setRestoOpentClose: " + "morning " + morning + "evening " + evening + "place hour " + placeHour + " place Min " + placeMin);

        // Log.d(TAG, "setRestoOpentClose: " + morning);
    }


    private void getLikes(Holder holder, int position) {


        likes.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                List<String> likedPlaces;
                int likesOnPlace = 0;

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    likedPlaces = (List<String>) ds.child("places").getValue();

                    for (int i = 0; i < likedPlaces.size(); i++) {

                        if (places.get(position).getPlaceId().equals(likedPlaces.get(i))) {
                            likesOnPlace++;
                        }
                    }


                }

                if (likesOnPlace == 1) {
                    holder.rateStar1.setImageResource(R.drawable.star_rate_24);
                }

                if (likesOnPlace == 2) {
                    holder.rateStar1.setImageResource(R.drawable.star_rate_24);
                    holder.rateStar2.setImageResource(R.drawable.star_rate_24);
                }
                if (likesOnPlace == 3 || likesOnPlace > 3) {
                    holder.rateStar1.setImageResource(R.drawable.star_rate_24);
                    holder.rateStar2.setImageResource(R.drawable.star_rate_24);
                    holder.rateStar3.setImageResource(R.drawable.star_rate_24);
                }

                Log.d(TAG, "liks on Place" + likesOnPlace);


            }


        });

    }
    // public void getTotalWorkmates() {
    //     wormatesDbReff.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
    //         @Override
    //         public void onSuccess(DataSnapshot dataSnapshot) {

    //             totalWorkMates = dataSnapshot.getChildrenCount();

    //         }
    //     });

    // }

}