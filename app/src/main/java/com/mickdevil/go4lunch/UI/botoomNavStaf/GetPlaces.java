package com.mickdevil.go4lunch.UI.botoomNavStaf;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class GetPlaces {

    static Context context;
    static PlacesClient placesClient;

    public GetPlaces(Context context) {
        this.context = context;
        this.placesClient = placesClient;
    }


    public static List<CustomPlace> curentPlaceRaqForList() {
        // Use fields to define the data types to return.
        List<Place.Field> placeFields = Collections.singletonList(Place.Field.NAME);

        List<CustomPlace> customPlaces = new ArrayList<>();
//Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.OPENING_HOURS, Place.Field.RATING);

// Use the builder to create a FindCurrentPlaceRequest.
        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.newInstance(placeFields);

// Call findCurrentPlace and handle the response (first check that the user has granted permission).
        if (ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Task<FindCurrentPlaceResponse> placeResponse = placesClient.findCurrentPlace(request);

            placeResponse.addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {
                            FindCurrentPlaceResponse response = task.getResult();

                            for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
                                //  Log.i(TAG, String.format("Place '%s' has likelihood: %f",
                                customPlaces.add(new CustomPlace(placeLikelihood.getPlace().getName(), placeLikelihood.getPlace().getAddress(),
                                        placeLikelihood.getPlace().getOpeningHours(), placeLikelihood.getPlace().getRating()
                                        , placeLikelihood.getPlace().getLatLng()));

                            }
                        } else {
                            Exception exception = task.getException();
                            if (exception instanceof ApiException) {
                                ApiException apiException = (ApiException) exception;
                                // Log.e(TAG, "Place not found: " + apiException.getStatusCode());
                            }
                        }
                    }
            );
        }

        return customPlaces;
    }

    List<Place.Field> fieldList = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.OPENING_HOURS, Place.Field.RATING);

  //  public List<Place.Field> getPlacesList() {
  //      List<Place.Field> places = new ArrayList<>(fieldList);
//
  //      return places;
  //  }


}
