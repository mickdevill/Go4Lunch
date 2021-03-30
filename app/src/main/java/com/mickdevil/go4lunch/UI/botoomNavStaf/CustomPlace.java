package com.mickdevil.go4lunch.UI.botoomNavStaf;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.OpeningHours;
import com.google.android.libraries.places.api.model.Place;

public class CustomPlace implements Parcelable {

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




    //parcelable implementation, maybe I will need to change it if I modify the class

    protected CustomPlace(Parcel in) {
        name = in.readString();
        address = in.readString();
        openTime = in.readParcelable(OpeningHours.class.getClassLoader());
        latLng = in.readParcelable(LatLng.class.getClassLoader());
        bitmap = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<CustomPlace> CREATOR = new Creator<CustomPlace>() {
        @Override
        public CustomPlace createFromParcel(Parcel in) {
            return new CustomPlace(in);
        }

        @Override
        public CustomPlace[] newArray(int size) {
            return new CustomPlace[size];
        }
    };






    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeParcelable(openTime, flags);
        dest.writeParcelable(latLng, flags);
        dest.writeParcelable(bitmap, flags);
    }
}
