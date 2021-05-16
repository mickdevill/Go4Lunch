package com.mickdevil.go4lunch.UI.botoomNavStaf.WorkMates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mickdevil.go4lunch.AppUser;
import com.mickdevil.go4lunch.R;
import com.mickdevil.go4lunch.UI.SignIn.MainSigninActivity;

import java.util.ArrayList;
import java.util.List;

public class Workmates extends Fragment {

    private WorkMatesRcvAdapter workMatesRcvAdapter;
    private RecyclerView workmatesRCV;
    private DatabaseReference DBRef;
    private static final String TAG = "Workmates";
    private int iflationTaskCode;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.workmates, container, false);

            workmatesRCV = root.findViewById(R.id.workmatesRcv);
            workmatesRCV.setLayoutManager(new LinearLayoutManager(getContext()));
            workmatesRCV.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

            DBRef = FirebaseDatabase.getInstance().getReference(MainSigninActivity.USER_PATH);
            getDataFromRTDB();


        return root;

    }


    private void getDataFromRTDB() {
        List<AppUser> workmates = new ArrayList<>();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    AppUser appUser = ds.getValue(AppUser.class);

                    workmates.add(appUser);

                    workmatesRCV.setAdapter(new WorkMatesRcvAdapter(workmates));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        DBRef.addValueEventListener(valueEventListener);

    }


}