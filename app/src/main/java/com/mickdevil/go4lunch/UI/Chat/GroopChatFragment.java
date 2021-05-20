package com.mickdevil.go4lunch.UI.Chat;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mickdevil.go4lunch.R;
import com.mickdevil.go4lunch.UI.G4LunchMain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class GroopChatFragment extends Fragment {

    private static final String TAG = "GroopChatFragment";

    EditText editMSG;
    ImageView sendMSG;
    RecyclerView ChatRCV;

    public static LinearLayoutManager linearManager;

    DatabaseReference mesegesReff;


    String curetUser = G4LunchMain.appUserToUse.email.substring(0,
            G4LunchMain.appUserToUse.email.indexOf("@"));

    public static final String MESEGES = "MESEGES";

    private static long msgItemCount = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.groop_chat_activity, container, false);

        editMSG = v.findViewById(R.id.editMSG);
        sendMSG = v.findViewById(R.id.sendMSG);
        ChatRCV = v.findViewById(R.id.ChatRCV);
        linearManager = new LinearLayoutManager(getContext());

        //linearManager.setStackFromEnd(false);


        ChatRCV.setLayoutManager(linearManager);
        ChatRCV.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));


        mesegesReff = FirebaseDatabase.getInstance().getReference(MESEGES);
        updateMeseges();


        sendMSG.setImageResource(R.drawable.msg_not_writen);

        watcher(editMSG, sendMSG);

        sendMSG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String theContent = editMSG.getText().toString();

                if (theContent.length() != 0) {
                    Calendar calendar = Calendar.getInstance();

                    Date date = new Date();

                    TimeZone tz = TimeZone.getTimeZone("Europe/Paris");

                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    long creationTime = date.getTime();
                    //tz.getOffset(date.getTime());

                    Log.d(TAG, "TIME FROM TIME ZONE " + creationTime);

                    Mesege mesege = new Mesege(curetUser, G4LunchMain.appUserToUse.photo,
                            editMSG.getText().toString(), "all", creationTime, year, month, day, msgItemCount);

                    String childName = Integer.toString(mesege.day) + Integer.toString(mesege.month)
                            + Integer.toString(mesege.year) + Long.toString(mesege.creationTime);

                    sendFierBaseMSG(mesege, childName);

                    editMSG.getText().clear();

                    hideKeyboard(getActivity());
                }


            }
        });


        return v;
    }


    private void sendFierBaseMSG(Mesege mesege, String childName) {
        mesegesReff.child(childName).setValue(mesege);
    }

    private void updateMeseges() {

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<Mesege> updatedmesegesList = new ArrayList<>();

                Mesege mesege;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    mesege = ds.getValue(Mesege.class);

                    msgItemCount = snapshot.getChildrenCount() + 1;
                    Log.d(TAG, "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA   =====  " + msgItemCount);
                    updatedmesegesList.add(mesege);

                }

                sortMSGes(updatedmesegesList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mesegesReff.addValueEventListener(valueEventListener);
    }


    private void sortMSGes(List<Mesege> mesegeList) {

        if (mesegeList.size() > 1) {
            for (int x = 0; x < mesegeList.size(); x++) {
                for (int i = 0; i < mesegeList.size() - i; i++) {
                    if (mesegeList.get(i).mesegeCount > mesegeList.get(i + 1).mesegeCount) {

                        mesegeList.set(i, mesegeList.get(i + 1));

                        mesegeList.set(i + 1, mesegeList.get(i));

                    }

                }

            }
        }

        ChatRCV.swapAdapter(new ChatRCVAdapter(mesegeList), false);
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

    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void ScrolChatOnTheBotom(boolean setStackFromEnd) {

        if (setStackFromEnd) {
            linearManager.setStackFromEnd(true);
        } else {

            linearManager.setStackFromEnd(false);
        }

    }


}

