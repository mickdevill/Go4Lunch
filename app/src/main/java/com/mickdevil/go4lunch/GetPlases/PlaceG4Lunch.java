package com.mickdevil.go4lunch.GetPlases;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class PlaceG4Lunch implements Parcelable {

    private String placeName;
    private String vicinity;
    private double latitude;
    private double longitude;
    private String placeId;
    private boolean opened;
    private Bitmap photo;
    private boolean isSomeBodyGoing;
    private List<String> usersMails;
    private double distenceToUser;
    private String photoReff;
    private double rating;


    public PlaceG4Lunch(String placeName, String vicinity, double latitude, double longitude, String placeId, boolean opened,
                        Bitmap photo, boolean isSomeBodyGoing, List<String> usersMails, double distenceToUser, String photoReff, double rating) {
        this.placeName = placeName;
        this.vicinity = vicinity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.placeId = placeId;
        this.opened = opened;
        this.photo = photo;
        this.isSomeBodyGoing = isSomeBodyGoing;
        this.usersMails = usersMails;
        this.distenceToUser = distenceToUser;
        this.photoReff = photoReff;
        this.rating = rating;
    }

    protected PlaceG4Lunch(Parcel in) {
        placeName = in.readString();
        vicinity = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        placeId = in.readString();
        opened = in.readByte() != 0;
        photo = in.readParcelable(Bitmap.class.getClassLoader());
        isSomeBodyGoing = in.readByte() != 0;
        usersMails = in.createStringArrayList();
        distenceToUser = in.readDouble();
        photoReff = in.readString();
        rating = in.readDouble();
    }

    public static final Creator<PlaceG4Lunch> CREATOR = new Creator<PlaceG4Lunch>() {
        @Override
        public PlaceG4Lunch createFromParcel(Parcel in) {
            return new PlaceG4Lunch(in);
        }

        @Override
        public PlaceG4Lunch[] newArray(int size) {
            return new PlaceG4Lunch[size];
        }
    };

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

    public List<String> getUsersMails() {
        return usersMails;
    }

    public void setUsersMails(List<String> usersMails) {
        this.usersMails = usersMails;
    }

    public double getDistenceToUser() {
        return distenceToUser;
    }

    public void setDistenceToUser(double distenceToUser) {
        this.distenceToUser = distenceToUser;
    }

    public String getPhotoReff() {
        return photoReff;
    }

    public void setPhotoReff(String photoReff) {
        this.photoReff = photoReff;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(placeName);
        parcel.writeString(vicinity);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeString(placeId);
        parcel.writeByte((byte) (opened ? 1 : 0));
        parcel.writeParcelable(photo, i);
        parcel.writeByte((byte) (isSomeBodyGoing ? 1 : 0));
        parcel.writeStringList(usersMails);
        parcel.writeDouble(distenceToUser);
        parcel.writeString(photoReff);
        parcel.writeDouble(rating);
    }
}
