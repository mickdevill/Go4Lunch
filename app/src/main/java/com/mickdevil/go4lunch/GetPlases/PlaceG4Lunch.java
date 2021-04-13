package com.mickdevil.go4lunch.GetPlases;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class PlaceG4Lunch  {

    String placeName;
    String vicinity;
    double latitude;
    double longitude;
    String placeId;
    boolean opened;
    Bitmap photo;
    boolean isSomeBodyGoing;


    public PlaceG4Lunch(String placeName, String vicinity, double latitude, double longitude, String placeId, boolean opened, Bitmap photo, boolean isSomeBodyGoing) {
        this.placeName = placeName;
        this.vicinity = vicinity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.placeId = placeId;
        this.opened = opened;
        this.photo = photo;
        this.isSomeBodyGoing = isSomeBodyGoing;
    }



    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public boolean isSomeBodyGoing() {
        return isSomeBodyGoing;
    }

    public void setSomeBodyGoing(boolean someBodyGoing) {
        isSomeBodyGoing = someBodyGoing;
    }



}
