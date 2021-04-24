package com.mickdevil.go4lunch;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.URI;
import java.net.URL;

public class AppUser implements Parcelable {

    public String ID, Fname, Lname, email, photo, placeID, UserUID;


    public AppUser() {

    }

    public AppUser(String ID, String fname, String lname, String email, String photo, String placeID, String userUID) {
        this.ID = ID;
        Fname = fname;
        Lname = lname;
        this.email = email;
        this.photo = photo;
        this.placeID = placeID;
        UserUID = userUID;
    }

    protected AppUser(Parcel in) {
        ID = in.readString();
        Fname = in.readString();
        Lname = in.readString();
        email = in.readString();
        photo = in.readString();
        placeID = in.readString();
        UserUID = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ID);
        dest.writeString(Fname);
        dest.writeString(Lname);
        dest.writeString(email);
        dest.writeString(photo);
        dest.writeString(placeID);
        dest.writeString(UserUID);
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
}
