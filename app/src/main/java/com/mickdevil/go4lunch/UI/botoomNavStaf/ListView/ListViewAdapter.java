package com.mickdevil.go4lunch.UI.botoomNavStaf.ListView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mickdevil.go4lunch.R;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.Holder> {


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_holder, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView restoName, restoAddress, restoOpenColse, restoDistance, workmatesWillGo;
        ImageView restImg , rateStar1, rateStar2, rateStar3, rateStar4, rateStar5;


        public Holder(@NonNull View itemView) {
            super(itemView);
            restoName = itemView.findViewById(R.id.restoName);
            restoAddress = itemView.findViewById(R.id.restoAddress);
            restoOpenColse = itemView.findViewById(R.id.restoOpenColse);
            restImg = itemView.findViewById(R.id.restImg);
            restoDistance = itemView.findViewById(R.id.restoDistance);
            workmatesWillGo = itemView.findViewById(R.id.workmatesWillGo);
       //the stars to set resto rate
            rateStar1 = itemView.findViewById(R.id.rateStar1);
            rateStar2 = itemView.findViewById(R.id.rateStar2);
            rateStar3 = itemView.findViewById(R.id.rateStar3);
            rateStar4 = itemView.findViewById(R.id.rateStar4);
            rateStar5 = itemView.findViewById(R.id.rateStar5);
        }
    }
}
