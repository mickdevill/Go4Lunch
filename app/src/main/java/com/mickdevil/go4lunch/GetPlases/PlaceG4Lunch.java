package com.mickdevil.go4lunch.GetPlases;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

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
    private List<String> weekdaysOpen;
    private String webSite;
    private String phoneNumber;
    private String foromGoogleOrAlternative;

    public PlaceG4Lunch(String placeName, String vicinity, double latitude, double longitude, String placeId,
                        boolean opened, Bitmap photo, boolean isSomeBodyGoing, List<String> usersMails, double distenceToUser,
                        String photoReff, double rating, List<String> weekdaysOpen, String webSite, String phoneNumber, String foromGoogleOrAlternative) {
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
        this.weekdaysOpen = weekdaysOpen;
        this.webSite = webSite;
        this.phoneNumber = phoneNumber;
        this.foromGoogleOrAlternative = foromGoogleOrAlternative;
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
        weekdaysOpen = in.createStringArrayList();
        webSite = in.readString();
        phoneNumber = in.readString();
        foromGoogleOrAlternative = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(placeName);
        dest.writeString(vicinity);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(placeId);
        dest.writeByte((byte) (opened ? 1 : 0));
        dest.writeParcelable(photo, flags);
        dest.writeByte((byte) (isSomeBodyGoing ? 1 : 0));
        dest.writeStringList(usersMails);
        dest.writeDouble(distenceToUser);
        dest.writeString(photoReff);
        dest.writeDouble(rating);
        dest.writeStringList(weekdaysOpen);
        dest.writeString(webSite);
        dest.writeString(phoneNumber);
        dest.writeString(foromGoogleOrAlternative);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public List<String> getWeekdaysOpen() {
        return weekdaysOpen;
    }

    public void setWeekdaysOpen(List<String> weekdaysOpen) {
        this.weekdaysOpen = weekdaysOpen;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getForomGoogleOrAlternative() {
        return foromGoogleOrAlternative;
    }

    public void setForomGoogleOrAlternative(String foromGoogleOrAlternative) {
        this.foromGoogleOrAlternative = foromGoogleOrAlternative;
    }
}
