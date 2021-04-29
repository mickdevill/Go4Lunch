package com.mickdevil.go4lunch.UI.botoomNavStaf.ListView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mickdevil.go4lunch.GetPlases.GetPlacesTheRightWay;
import com.mickdevil.go4lunch.GetPlases.PlaceG4Lunch;
import com.mickdevil.go4lunch.R;

import java.util.List;

public class ListView extends Fragment {

    RecyclerView mapListView;


private List<PlaceG4Lunch> places;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_view, container, false);


        mapListView = view.findViewById(R.id.mapListView);

        mapListView.setLayoutManager(new LinearLayoutManager(getContext()));

        mapListView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        places = GetPlacesTheRightWay.finalPlacesResult;

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        places = GetPlacesTheRightWay.finalPlacesResult;

        mapListView.setAdapter(new ListViewAdapter(places));


    }




}