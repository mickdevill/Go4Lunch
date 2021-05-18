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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mickdevil.go4lunch.AppUser;
import com.mickdevil.go4lunch.R;
import com.mickdevil.go4lunch.UI.Chat.localDB.AppST;
import com.mickdevil.go4lunch.UI.Chat.localDB.MainViewModel;
import com.mickdevil.go4lunch.UI.Chat.localDB.Mesege;
import com.mickdevil.go4lunch.UI.G4LunchMain;
import com.mickdevil.go4lunch.UI.SignIn.MainSigninActivity;
import com.mickdevil.go4lunch.UI.botoomNavStaf.ListView.ListViewAdapter;
import com.mickdevil.go4lunch.UI.botoomNavStaf.WorkMates.WorkMatesRcvAdapter;

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

    // MainViewModel mvm;


    public static final String MESEGES = "MESEGES";


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


        mesegesReff = FirebaseDatabase.getInstance().getReference(MESEGES);
        updateMeseges();


        sendMSG.setImageResource(R.drawable.msg_not_writen);

        watcher(editMSG, sendMSG);

        // observeLVD();


        sendMSG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String theContent = editMSG.getText().toString();

                if (theContent.length() != 0) {
                    Calendar calendar = Calendar.getInstance();

                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int mouth = calendar.get(Calendar.MONTH);
                    int year = calendar.get(Calendar.YEAR);
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int min = calendar.get(Calendar.MINUTE);
                    int sec = calendar.get(Calendar.SECOND);
                    int milis = calendar.get(Calendar.MILLISECOND);

                    Mesege mesege = new Mesege(curetUser, G4LunchMain.appUserToUse.photo,
                            editMSG.getText().toString(), "all", day, mouth, year,
                            Mesege.MSG_STATUS_NON_READ_BY_EVREE_ONE, milis, hour, sec, min);

                    String childName = Integer.toString(mesege.day) + Integer.toString(mesege.moth) + Integer.toString(mesege.year) + Integer.toString(mesege.milis);

                    sendFierBaseMSG(mesege, childName);

                    editMSG.getText().clear();

                }


            }
        });


        return v;
    }


    private void sendFierBaseMSG(Mesege mesege, String childName) {
        mesegesReff.child(childName).setValue(mesege);
        updateMeseges();
    }


    // private void updateMeseges() {
    //     mesegesReff.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
    //         @Override
    //         public void onSuccess(DataSnapshot dataSnapshot) {

    //             for (DataSnapshot ds : dataSnapshot.getChildren()) {
    //                 HashMap<String, Object> mesege = (HashMap<String, Object>) ds.getValue();

    //                 if (!mesegesList.contains(mesege)) {
    //                     mvm.insert(Mesege.fromMap(mesege));

    //                 }
    //                 Log.d(TAG, "onSuccess: IT WORK " + Mesege.fromMap(mesege).getMsgText());
    //             }
    //         }
    //     });


    // }


    private void updateMeseges() {

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<Mesege> UpdatedmesegesList = new ArrayList<>();

                Mesege mesege;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    mesege = ds.getValue(Mesege.class);

                    UpdatedmesegesList.add(mesege);

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        mesegesReff.addValueEventListener(valueEventListener);

    }


    private void sortMSGes(List<Mesege> unsortedMeseges){
        List<Mesege> sortedMeseges = new ArrayList<>();










        ChatRCV.setAdapter(new ChatRCVAdapter( sortedMeseges));

        ChatRCV.smoothScrollToPosition( sortedMeseges.size());



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

