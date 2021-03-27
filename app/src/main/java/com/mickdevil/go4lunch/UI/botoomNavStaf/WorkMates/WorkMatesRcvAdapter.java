package com.mickdevil.go4lunch.UI.botoomNavStaf.WorkMates;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mickdevil.go4lunch.AppUser;

import java.util.ArrayList;
import java.util.List;

public class WorkMatesRcvAdapter extends RecyclerView.Adapter<WorkMatesRcvAdapter.holder> {
List<AppUser> worMates = new ArrayList<>();

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return worMates.size();
    }

    class holder extends  RecyclerView.ViewHolder{

    public holder(@NonNull View itemView) {
        super(itemView);
    }
}

}
