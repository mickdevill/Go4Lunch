package com.mickdevil.go4lunch.UI.botoomNavStaf.WorkMates;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mickdevil.go4lunch.AppUser;
import com.mickdevil.go4lunch.R;

import java.util.ArrayList;
import java.util.List;

public class WorkMatesRcvAdapter extends RecyclerView.Adapter<WorkMatesRcvAdapter.Holder> {
List<AppUser> worMates = new ArrayList<>();

    public WorkMatesRcvAdapter(List<AppUser> worMates) {
        this.worMates = worMates;
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
if (appUser.Lname != null){
    holder.nameAndPlaceHeWillGo.setText(appUser.Fname = appUser.Lname);
}
else {
    holder.nameAndPlaceHeWillGo.setText(appUser.Fname);
}

    }

    @Override
    public int getItemCount() {
        return worMates.size();
    }

    class Holder extends  RecyclerView.ViewHolder{
        ImageView wokmateProfilePhoto;
        TextView nameAndPlaceHeWillGo;

    public Holder(@NonNull View itemView) {
        super(itemView);
         wokmateProfilePhoto = itemView.findViewById(R.id.wokmateProfilePhoto);
        nameAndPlaceHeWillGo = itemView.findViewById(R.id.nameAndPlaceHeWillGo);
    }


    }

}
