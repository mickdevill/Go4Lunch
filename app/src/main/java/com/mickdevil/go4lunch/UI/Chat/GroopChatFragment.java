package com.mickdevil.go4lunch.UI.Chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mickdevil.go4lunch.R;
import com.mickdevil.go4lunch.UI.Chat.localDB.AppST;
import com.mickdevil.go4lunch.UI.Chat.localDB.MainViewModel;
import com.mickdevil.go4lunch.UI.Chat.localDB.Mesege;
import com.mickdevil.go4lunch.UI.G4LunchMain;
import com.mickdevil.go4lunch.UI.SignIn.MainSigninActivity;

import java.util.Calendar;
import java.util.List;

public class GroopChatFragment extends Fragment {

    private static final String TAG = "GroopChatFragment";

    EditText editMSG;
    ImageView sendMSG;
    RecyclerView ChatRCV;

    DatabaseReference mesegesReff;

    String curetUser = G4LunchMain.appUserToUse.email.substring(0,
            G4LunchMain.appUserToUse.email.indexOf("@"));

    MainViewModel mvm;


    List<Mesege> mesegesList;

    public static final String MESEGES = "MESEGES";

    Calendar calendar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.groop_chat_activity, container, false);

        editMSG = v.findViewById(R.id.editMSG);
        sendMSG = v.findViewById(R.id.sendMSG);
        ChatRCV = v.findViewById(R.id.ChatRCV);

        ChatRCV.setLayoutManager(new LinearLayoutManager(getContext()));
        ChatRCV.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        mesegesReff = FirebaseDatabase.getInstance().getReference(MESEGES);

        sendMSG.setImageResource(R.drawable.msg_not_writen);

        watcher(editMSG, sendMSG);

        observeLVD();
        calendar = Calendar.getInstance();



        sendMSG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String theContent = editMSG.getText().toString();

                if (theContent.length() != 0) {



                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int mouth = calendar.get(Calendar.MONTH);
                    int year = calendar.get(Calendar.YEAR);

                    Mesege mesege = new Mesege(curetUser, G4LunchMain.appUserToUse.photo,
                            editMSG.getText().toString(), "all", day,mouth,year);

                    mvm.insert(mesege);

                    editMSG.getText().clear();

                    ChatRCV.setAdapter(new ChatRCVAdapter(mesegesList));

                }


            }
        });


        return v;
    }


    public void observeLVD() {
        mvm = ViewModelProviders.of(this).get(MainViewModel.class);
        mvm.getLVD().observe( getActivity(), new Observer<List<Mesege>>() {
            @Override
            public void onChanged(List<Mesege> meseges) {
                mesegesList = meseges;
                ChatRCV.setAdapter(new ChatRCVAdapter(mesegesList));
            }
        });
    }


    public void watcher(EditText editMSG, ImageView imageView) {


        editMSG.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() != 0) {
                    sendMSG.setImageResource(R.drawable.send_msg);
                } else {
                    sendMSG.setImageResource(R.drawable.msg_not_writen);
                }

            }
        });

    }


}

