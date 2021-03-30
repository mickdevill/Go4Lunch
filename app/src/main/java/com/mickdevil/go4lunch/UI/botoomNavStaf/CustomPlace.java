package com.mickdevil.go4lunch.UI.botoomNavStaf;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.OpeningHours;
import com.google.android.libraries.places.api.model.Place;

public class CustomPlace {

    private String name;
    private String address;
    private OpeningHours openTime;
    private LatLng latLng;
    private Bitmap bitmap;

    public CustomPlace(String name, String address, OpeningHours openTime,
                       LatLng latLng, Bitmap bitmap) {
        this.name = name;
        this.address = address;
        this.openTime = openTime;
        // this.rate = rate;
        this.latLng = latLng;
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public OpeningHours getOpenTime() {
        return openTime;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
