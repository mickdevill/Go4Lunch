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

    public class Holder extends RecyclerView.ViewHolder{
  TextView restoName;
TextView restoAddress;
TextView restoOpenColse;
ImageView restImg;
TextView restoDistance;
TextView workmatesWillGo;

    public Holder(@NonNull View itemView) {
        super(itemView);
        restoName = itemView.findViewById(R.id.restoName);
        restoAddress = itemView.findViewById(R.id.restoAddress);
        restoOpenColse = itemView.findViewById(R.id.restoOpenColse);
        restImg = itemView.findViewById(R.id.restImg);
        restoDistance = itemView.findViewById(R.id.restoDistance);
        workmatesWillGo = itemView.findViewById(R.id.workmatesWillGo);
    }
}
}
