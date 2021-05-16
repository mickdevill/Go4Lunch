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
import android.view.textclassifier.ConversationActions;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mickdevil.go4lunch.R;
import com.mickdevil.go4lunch.UI.Chat.localDB.AppST;
import com.mickdevil.go4lunch.UI.Chat.localDB.MainViewModel;
import com.mickdevil.go4lunch.UI.Chat.localDB.Mesege;
import com.mickdevil.go4lunch.UI.G4LunchMain;
import com.mickdevil.go4lunch.UI.SignIn.MainSigninActivity;
import com.mickdevil.go4lunch.UI.botoomNavStaf.ListView.ListViewAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static java.lang.Double.valueOf;

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
        LinearLayoutManager linearManager = new LinearLayoutManager(getContext());

        ChatRCV.setLayoutManager(linearManager);
        ChatRCV.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mesegesList = new ArrayList<>();

        mesegesReff = FirebaseDatabase.getInstance().getReference(MESEGES);
        updateMeseges();


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
                            editMSG.getText().toString(), "all", day, mouth, year);

                    sendFierBaseMSG(mesege);

                    editMSG.getText().clear();

                    ChatRCV.setAdapter(new ChatRCVAdapter(mesegesList));
                    ChatRCV.smoothScrollToPosition(mesegesList.size());
                }


            }
        });


        return v;
    }


    private void sendFierBaseMSG(Mesege mesege) {

        mesegesReff.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                List<Mesege> messeges = (List<Mesege>) dataSnapshot.getValue();

                if (messeges != null) {
                    messeges.add(mesege);
                    mesegesReff.setValue(messeges);
                } else {
                    List<Mesege> nunNullMeseges = new ArrayList<>();
                    nunNullMeseges.add(mesege);
                    mesegesReff.setValue(nunNullMeseges);
                }
            }
        });

        updateMeseges();
    }


    private void updateMeseges() {
        mesegesReff.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    HashMap<String, Object> mesege = (HashMap<String, Object>) ds.getValue();

                    if (!mesegesList.contains(mesege)) {
                        mvm.insert(Mesege.fromMap(mesege));

                    }
                    Log.d(TAG, "onSuccess: IT WORK " + Mesege.fromMap(mesege).getMsgText());
                }
            }
        });


    }


    public void observeLVD() {
        mvm = ViewModelProviders.of(this).get(MainViewModel.class);
        mvm.getLVD().observe(getActivity(), new Observer<List<Mesege>>() {
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

