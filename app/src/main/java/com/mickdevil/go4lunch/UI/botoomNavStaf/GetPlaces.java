package com.mickdevil.go4lunch.UI.botoomNavStaf;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class GetPlaces {


    private static final String TAG = "GetPlaces";

    private FusedLocationProviderClient locationProviderClient;
    PlacesClient placesClient;
    String apiKey;
    Context context;


    public GetPlaces(FusedLocationProviderClient locationProviderClient, PlacesClient placesClient, String apiKey, Context context) {
        this.locationProviderClient = locationProviderClient;
        this.placesClient = placesClient;
        this.apiKey = apiKey;
        this.context = context;

    }

    public static List<CustomPlace> myPlaces = new ArrayList<>();



    public void getPlacesLikeHood() {
// Use fields to define the data types to return.
        List<String> getPlacesID = new ArrayList<>();

        List<Place.Field> placeFields = Collections.singletonList(Place.Field.ID);

// Use the builder to create a FindCurrentPlaceRequest.
        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.newInstance(placeFields);

// Call findCurrentPlace and handle the response (first check that the user has granted permission).
        try {


            if (ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                Task<FindCurrentPlaceResponse> placeResponse = placesClient.findCurrentPlace(request);

                placeResponse.addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {


                        FindCurrentPlaceResponse response = task.getResult();
                        for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
                            Log.i(TAG, String.format("Place '%s' has likelihood: %f",
                                    placeLikelihood.getPlace().getId(),
                                    placeLikelihood.getLikelihood()));

                            getPlacesID.add(placeLikelihood.getPlace().getId());
                            Log.i(TAG, "and my list is: " + getPlacesID.size());

                            getPlaceDetail(placeLikelihood.getPlace().getId());
                            //  getPlacePhoto(placeLikelihood.getPlace().getId());

                        }


                    } else {
                        Exception exception = task.getException();
                        if (exception instanceof ApiException) {
                            ApiException apiException = (ApiException) exception;
                            Log.e(TAG, "Place not found: " + apiException.getStatusCode());
                        }
                    }
                });
            }
        } catch (NullPointerException np) {
            np.printStackTrace();
        }
    }

    public void getPlaceDetail(String placeId) {
        // Define a Place ID.
        //final String placeId = "INSERT_PLACE_ID_HERE";

        // Specify the fields to return.
        final List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS,
                Place.Field.OPENING_HOURS, Place.Field.LAT_LNG, Place.Field.PHOTO_METADATAS);

        // Construct a request object, passing the place ID and fields array.
        final FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);

        placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
            Place place = response.getPlace();

            // Get the photo metadata.
            final List<PhotoMetadata> metadata = place.getPhotoMetadatas();
            if (metadata == null || metadata.isEmpty()) {
                Log.w(TAG, "No photo metadata.");
                return;
            }
            final PhotoMetadata photoMetadata = metadata.get(0);

            // Get the attribution text.
            final String attributions = photoMetadata.getAttributions();

            // Create a FetchPhotoRequest.
            final FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(photoMetadata)
                    .setMaxWidth(500) // Optional.
                    .setMaxHeight(300) // Optional.
                    .build();
            placesClient.fetchPhoto(photoRequest).addOnSuccessListener((fetchPhotoResponse) -> {
                Bitmap bitmap = fetchPhotoResponse.getBitmap();

                CustomPlace customPlace = new CustomPlace(response.getPlace().getName(), response.getPlace().getAddress(),
                        response.getPlace().getOpeningHours(),
                        response.getPlace().getLatLng(), bitmap);

                myPlaces.add(customPlace);

                Log.i(TAG, "Place found: " + customPlace.getAddress() + customPlace.getName() + customPlace.getOpenTime());

            });

        })

                .addOnFailureListener((exception) -> {
                    if (exception instanceof ApiException) {
                        final ApiException apiException = (ApiException) exception;
                        Log.e(TAG, "Place not found: " + exception.getMessage());
                        final int statusCode = apiException.getStatusCode();
                        // TODO: Handle error with given status code.
                    }
                });


    }














}




