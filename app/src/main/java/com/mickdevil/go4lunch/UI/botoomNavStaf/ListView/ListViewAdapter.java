package com.mickdevil.go4lunch.UI.botoomNavStaf.ListView;

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

import com.mickdevil.go4lunch.GetPlases.PlaceG4Lunch;
import com.mickdevil.go4lunch.R;
import com.mickdevil.go4lunch.UI.PlaceDetails.PlaceDetailsActivity;

import java.util.List;

import static java.lang.Double.valueOf;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.Holder> {
    List<PlaceG4Lunch> places;
    private static final String TAG = "ListViewAdapter";

    public ListViewAdapter(List<PlaceG4Lunch> places) {
        this.places = places;

    }

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
        holder.restoName.setText(place.getPlaceName());

        holder.restoDistance.setText(cutTheDistence(valueOf(place.getDistenceToUser()).toString()));

        if (place.getVicinity().contains(",")) {
            holder.restoAddress.setText(cutTheAdress(place.getVicinity()));
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

        if (place.getRating() > 1.6 && place.getRating() < 3.2) {
            holder.rateStar1.setImageResource(R.drawable.star_rate_24);
        }
        if (place.getRating() > 3.2 && place.getRating() < 4) {
            holder.rateStar1.setImageResource(R.drawable.star_rate_24);
            holder.rateStar2.setImageResource(R.drawable.star_rate_24);
        }
        if (place.getRating() > 4) {
            holder.rateStar1.setImageResource(R.drawable.star_rate_24);
            holder.rateStar2.setImageResource(R.drawable.star_rate_24);
            holder.rateStar3.setImageResource(R.drawable.star_rate_24);
        }
   //     if (place.getUsersMails() != null) {
   //         holder.workmatesWillGo.setText(valueOf(place.getUsersMails().size()).toString());
   //     } else {
          //  holder.workmatesWillGo.setText(position);
    //    }




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

            //the stars to set resto rate
            rateStar1 = itemView.findViewById(R.id.rateStar1);
            rateStar2 = itemView.findViewById(R.id.rateStar2);
            rateStar3 = itemView.findViewById(R.id.rateStar3);



        }
    }

private String cutTheAdress(String vicinity){
        String result;
        result = vicinity.substring(0, vicinity.indexOf(","));
        return result;
    }

    private String cutTheDistence(String distence){
        String result;
        result = distence.substring(0, distence.indexOf(".")) + "m";
        return result;

}


}
