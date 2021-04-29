package com.mickdevil.go4lunch;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;

public class AppUser implements Parcelable {

    public String ID, Fname, Lname, email, photo, placeID;


    public AppUser() {

    }

    public AppUser(String ID, String fname, String lname, String email, String photo, String placeID) {
        this.ID = ID;
        this.Fname = fname;
        this.Lname = lname;
        this.email = email;
        this.photo = photo;
        this.placeID = placeID;
    }

    public AppUser(Parcel in) {
        ID = in.readString();
        Fname = in.readString();
        Lname = in.readString();
        email = in.readString();
        photo = in.readString();
        placeID = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ID);
        dest.writeString(Fname);
        dest.writeString(Lname);
        dest.writeString(email);
        dest.writeString(photo);
        dest.writeString(placeID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppUser> CREATOR = new Creator<AppUser>() {
        @Override
        public AppUser createFromParcel(Parcel in) {
            return new AppUser(in);
        }

        @Override
        public AppUser[] newArray(int size) {
            return new AppUser[size];
        }
    };

public static HashMap<String, Object> toMap(AppUser appUser){
    HashMap<String, Object> result = new HashMap<>();
    result.put("ID", appUser.ID);
    result.put("Fname", appUser.Fname);
    result.put("Lname", appUser.Lname);
    result.put("email", appUser.email);
    result.put("photo", appUser.photo);
    result.put("placeID", appUser.placeID);
    return result;

}


}
