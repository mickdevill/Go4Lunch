package com.mickdevil.go4lunch.GetPlases;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import com.mickdevil.go4lunch.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class PlaceG4Lunch implements Parcelable {

    public String placeName;
    public String vicinity;
    public double latitude;
    public double longitude;
    public String placeId;
    public boolean opened;
    public Bitmap photo;
    public double distenceToUser;
    public String photoReff;
    public List<String> weekdaysOpen;
    public String webSite;
    public String phoneNumber;
    public String foromGoogleOrAlternative;


    public PlaceG4Lunch() {
    }

    public PlaceG4Lunch(String placeName, String vicinity, double latitude, double longitude, String placeId,
                        boolean opened, Bitmap photo, double distenceToUser,
                        String photoReff, List<String> weekdaysOpen, String webSite, String phoneNumber, String foromGoogleOrAlternative) {
        this.placeName = placeName;
        this.vicinity = vicinity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.placeId = placeId;
        this.opened = opened;
        this.photo = photo;
        this.distenceToUser = distenceToUser;
        this.photoReff = photoReff;
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
        distenceToUser = in.readDouble();
        photoReff = in.readString();
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
        dest.writeDouble(distenceToUser);
        dest.writeString(photoReff);
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


    public void setSomeBodyGoing(boolean someBodyGoing) {
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




    //PLACE HASH MAP METHODS //PLACE HASH MAP METHODS //PLACE HASH MAP METHODS //PLACE HASH MAP METHODS //PLACE HASH MAP METHODS //PLACE HASH MAP METHODS //PLACE HASH MAP METHODS
    //PLACE HASH MAP METHODS //PLACE HASH MAP METHODS //PLACE HASH MAP METHODS //PLACE HASH MAP METHODS //PLACE HASH MAP METHODS //PLACE HASH MAP METHODS //PLACE HASH MAP METHODS
    //PLACE HASH MAP METHODS //PLACE HASH MAP METHODS //PLACE HASH MAP METHODS //PLACE HASH MAP METHODS //PLACE HASH MAP METHODS //PLACE HASH MAP METHODS //PLACE HASH MAP METHODS

    public static HashMap<String, Object> placeG4LunchToMap(PlaceG4Lunch place) {
        HashMap<String, Object> result = new HashMap<>();

        String placeName = "placeName";
        String vicinity = "vicinity";
        String latitude = "latitude";
        String longitude = "longitude";
        String placeId = "placeId";
        String opened = "opened";
        String photo = "photo";
        String distenceToUser = "distenceToUser";
        String photoReff = "photoReff";
        String weekdaysOpen = "weekdaysOpen";
        String webSite = "webSite";
        String phoneNumber = "phoneNumber";
        String foromGoogleOrAlternative = "foromGoogleOrAlternative";

        result.put(placeName, place.getPlaceName());
        result.put(vicinity, place.getVicinity());
        result.put(latitude, place.getLatitude());
        result.put(longitude, place.getLongitude());
        result.put(placeId, place.getPlaceId());
        result.put(opened, place.opened);
        result.put(photo, null);
        result.put(distenceToUser, place.getDistenceToUser());
        result.put(photoReff, place.photoReff);
        result.put(weekdaysOpen, place.getWeekdaysOpen());
        result.put(webSite, place.getWebSite());
        result.put(phoneNumber, place.getPhoneNumber());
        result.put(foromGoogleOrAlternative, place.getForomGoogleOrAlternative());


        return result;

    }

    public static PlaceG4Lunch placeG4LunchFROMMap(HashMap<String, Object> placeMap, Bitmap bitmapPhoto) {
        PlaceG4Lunch result;
//Strings that is keys
        String placeName2get = "placeName";
        String vicinity2get = "vicinity";
        String latitude2get = "latitude";
        String longitude2get = "longitude";
        String placeId2get = "placeId";
        String opened2get = "opened";
        String photo2get = "photo";
        String distenceToUser2get = "distenceToUser";
        String photoReff2get = "photoReff";
        String weekdaysOpen2get = "weekdaysOpen";
        String webSite2get = "webSite";
        String phoneNumber2get = "phoneNumber";
        String foromGoogleOrAlternative2get = "foromGoogleOrAlternative";
        //vars for the future PlaceG4Lunch!!!!!!!!!!!!!!!
        String placeName = (String) placeMap.get(placeName2get);
        String vicinity = (String) placeMap.get(vicinity2get);
        double latitude = (Double) placeMap.get(latitude2get);
        double longitude = (Double) placeMap.get(longitude2get);
        String placeId = (String) placeMap.get(placeId2get);
        boolean opened = (Boolean) placeMap.get(opened2get);
        double distenceToUser = (Double) placeMap.get(distenceToUser2get);
        String photoReff = (String) placeMap.get(photoReff2get);
        List<String> weekdaysOpen = (List<String>) placeMap.get(weekdaysOpen2get);
        String webSite = (String) placeMap.get(webSite2get);
        String phoneNumber = (String) placeMap.get(phoneNumber2get);
        String foromGoogleOrAlternative = (String) placeMap.get(foromGoogleOrAlternative2get);
        Bitmap photo;
        if (bitmapPhoto != null){
          photo = bitmapPhoto;
        }
        else {
           photo = null;
        }



        result = new PlaceG4Lunch(placeName,vicinity,latitude,longitude,placeId,opened,photo,distenceToUser,
                photoReff,weekdaysOpen,webSite,phoneNumber,foromGoogleOrAlternative2get);



        return result;

    }

    //PLACE HASH MAP METHODS END //PLACE HASH MAP METHODS END //PLACE HASH MAP METHODS END //PLACE HASH MAP METHODS END //PLACE HASH MAP METHODS END //PLACE HASH MAP METHODS END
    //PLACE HASH MAP METHODS END //PLACE HASH MAP METHODS END //PLACE HASH MAP METHODS END //PLACE HASH MAP METHODS END //PLACE HASH MAP METHODS END //PLACE HASH MAP METHODS END
    //PLACE HASH MAP METHODS END //PLACE HASH MAP METHODS END //PLACE HASH MAP METHODS END //PLACE HASH MAP METHODS END //PLACE HASH MAP METHODS END //PLACE HASH MAP METHODS END
    //PLACE HASH MAP METHODS END //PLACE HASH MAP METHODS END //PLACE HASH MAP METHODS END //PLACE HASH MAP METHODS END //PLACE HASH MAP METHODS END //PLACE HASH MAP METHODS END


}
