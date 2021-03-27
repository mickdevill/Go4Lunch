package com.mickdevil.go4lunch;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.URI;
import java.net.URL;

public class AppUser implements Parcelable  {

    public String ID, Fname, Lname, email, photo;


    public AppUser() {

    }

    public AppUser(String ID, String Fname, String Lname, String email, String photo) {
        this.ID = ID;
        this.Fname = Fname;
        this.email = email;
        this.Lname = Lname;
       this.photo = photo;
    }

    protected AppUser(Parcel in) {
        ID = in.readString();
        Fname = in.readString();
        Lname = in.readString();
        email = in.readString();
        photo = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ID);
        dest.writeString(Fname);
        dest.writeString(Lname);
        dest.writeString(email);
        dest.writeString(photo);
    }
}
