package com.mickdevil.go4lunch.UI.botoomNavStaf.ListView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.libraries.places.api.net.PlacesClient;
import com.mickdevil.go4lunch.GetPlases.GetPlacesTheRightWay;
import com.mickdevil.go4lunch.R;
import com.mickdevil.go4lunch.UI.G4LunchMain;
import com.mickdevil.go4lunch.UI.botoomNavStaf.GetPlaces;

public class ListView extends Fragment {

    RecyclerView mapListView;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_view, container, false);


        mapListView = view.findViewById(R.id.mapListView);

        mapListView.setLayoutManager(new LinearLayoutManager(getContext()));

        mapListView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        mapListView.setAdapter(new ListViewAdapter(GetPlacesTheRightWay.finalPlacesResult));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();



    }

}