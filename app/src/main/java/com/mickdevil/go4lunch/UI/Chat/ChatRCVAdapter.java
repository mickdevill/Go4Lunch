package com.mickdevil.go4lunch.UI.Chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mickdevil.go4lunch.R;

import java.util.ArrayList;
import java.util.List;

public class ChatRCVAdapter extends RecyclerView.Adapter<ChatRCVAdapter.Holder> {

    public List<Mesege> meseges = new ArrayList<>();


    public ChatRCVAdapter(List<Mesege> meseges) {
        this.meseges = meseges;

    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mesege_holder, parent, false);
        Holder holder = new Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Mesege mesage = meseges.get(position);
        holder.MsgInfo.setText(mesage.userName + " " + mesage.day + "." + mesage.month + "." + mesage.year);
        holder.MsgContent.setText(mesage.msgText);
        Glide.with(holder.profileImage)
                .load(mesage.userPhoto)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.profileImage);

        if (holder.getLayoutPosition() > meseges.size() - 5) {
            GroopChatFragment.scrolChatOnTheBotom(1);
        }

        if (holder.getLayoutPosition() < meseges.size() - 15) {

            GroopChatFragment.scrolChatOnTheBotom(2);
        } else {

            GroopChatFragment.scrolChatOnTheBotom(3);

        }


    }


    @Override
    public int getItemCount() {
        return meseges.size();
    }


    public class Holder extends RecyclerView.ViewHolder {

        TextView MsgInfo, MsgContent;

        ImageView profileImage;

        public Holder(@NonNull View itemView) {
            super(itemView);

            MsgInfo = itemView.findViewById(R.id.MsgInfo);
            MsgContent = itemView.findViewById(R.id.MsgContent);
            profileImage = itemView.findViewById(R.id.profileImage);

        }
    }
}
