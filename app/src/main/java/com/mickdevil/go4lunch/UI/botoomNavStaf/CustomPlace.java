package com.mickdevil.go4lunch.UI.botoomNavStaf;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.OpeningHours;
import com.google.android.libraries.places.api.model.Place;

public class CustomPlace {

    private String name;
    private String address;
    private OpeningHours openTime;
   // private double rate;
    private LatLng latLng;


    public CustomPlace(String name, String address, OpeningHours openTime,
                       //double rate,
                       LatLng latLng) {
        this.name = name;
        this.address = address;
        this.openTime = openTime;
       // this.rate = rate;
        this.latLng = latLng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public OpeningHours getOpenTime() {
        return openTime;
    }

    public void setOpenTime(OpeningHours openTime) {
        this.openTime = openTime;
    }

 // public double getRate() {
 //     return rate;
 // }

 // public void setRate(double rate) {
 //     this.rate = rate;
 // }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
