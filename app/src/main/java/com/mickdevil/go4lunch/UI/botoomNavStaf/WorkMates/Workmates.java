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

import com.google.android.material.card.MaterialCardView;
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
 private MaterialCardView cardViewForViewPageNavigationCHAT;
    private DatabaseReference DBRef;
    private static final String TAG = "Workmates";
    public boolean makeVisibleOrNot = true;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.workmates, container, false);

        workmatesRCV = root.findViewById(R.id.workmatesRcv);
        cardViewForViewPageNavigationCHAT = root.findViewById(R.id.cardViewForViewPageNavigationCHAT);
        workmatesRCV.setLayoutManager(new LinearLayoutManager(getContext()));
        workmatesRCV.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        DBRef = FirebaseDatabase.getInstance().getReference(MainSigninActivity.USER_PATH);
        switchMainAction(makeVisibleOrNot);

        return root;

    }


    private void switchMainAction(boolean makeVisibleOrNot) {

        if (makeVisibleOrNot) {
            workmatesRCV.setVisibility(View.INVISIBLE);
            cardViewForViewPageNavigationCHAT.setVisibility(View.INVISIBLE);


        } else {
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    List<AppUser> workmates = new ArrayList<>();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        AppUser appUser = ds.getValue(AppUser.class);

                        workmates.add(appUser);

                        workmatesRCV.setAdapter(new WorkMatesRcvAdapter(workmates,1));
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };
            DBRef.addValueEventListener(valueEventListener);

        }


    }


}