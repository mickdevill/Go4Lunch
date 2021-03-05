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

import com.mickdevil.go4lunch.R;
import com.mickdevil.go4lunch.UI.botoomNavStaf.GetPlaces;

public class ListView extends Fragment {
    GetPlaces getPlaces;
    RecyclerView mapListView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_view, container, false);

        getPlaces = new GetPlaces(getContext());
        mapListView = view.findViewById(R.id.mapListView);


        mapListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mapListView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
      //  mapListView.setAdapter(new ListViewAdapter(getPlaces.curentPlaceRaqForList()));


        return view;
    }
}