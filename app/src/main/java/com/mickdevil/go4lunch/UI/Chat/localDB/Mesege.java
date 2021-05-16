package com.mickdevil.go4lunch.UI.Chat.localDB;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;

import static java.lang.Integer.valueOf;

@Entity(tableName = "meseges")
public class Mesege implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "projectID")
    public long projectId;

    @SuppressWarnings("NullableProblems")
    @ColumnInfo(name = "userName")
    public String userName;

    @SuppressWarnings("NullableProblems")
    @ColumnInfo(name = "userPhoto")
    public String userPhoto;

    @SuppressWarnings("NullableProblems")
    @ColumnInfo(name = "msgText")
    public String msgText;


    @SuppressWarnings("NullableProblems")
    @NonNull
    @ColumnInfo(name = "toWho")
    public String toWho;


    @ColumnInfo(name = "day")
    public int day;

    @ColumnInfo(name = "moth")
    public int moth;

    @ColumnInfo(name = "year")
    public int year;

    // public String userName, userPhoto, msgText, toWho;
    // public int day, moth, year;


    public Mesege() {

    }

    public Mesege(String userName, String userPhoto, String msgText, String toWho, int day, int moth, int year) {
        this.userName = userName;
        this.userPhoto = userPhoto;
        this.msgText = msgText;
        this.toWho = toWho;
        this.day = day;
        this.moth = moth;
        this.year = year;
    }

    protected Mesege(Parcel in) {
        userName = in.readString();
        userPhoto = in.readString();
        msgText = in.readString();
        toWho = in.readString();
        day = in.readInt();
        moth = in.readInt();
        year = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(userPhoto);
        dest.writeString(msgText);
        dest.writeString(toWho);
        dest.writeInt(day);
        dest.writeInt(moth);
        dest.writeInt(year);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Mesege> CREATOR = new Creator<Mesege>() {
        @Override
        public Mesege createFromParcel(Parcel in) {
            return new Mesege(in);
        }

        @Override
        public Mesege[] newArray(int size) {
            return new Mesege[size];
        }
    };

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    public String getToWho() {
        return toWho;
    }

    public void setToWho(String toWho) {
        this.toWho = toWho;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMoth() {
        return moth;
    }

    public void setMoth(int moth) {
        this.moth = moth;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

//TODO need to remove that

 //  public HashMap<String, Object> toMap(Mesege mesege) {

 //      HashMap<String, Object> result = new HashMap<>();
 //      result.put("userName", mesege.userName);
 //      result.put("userPhoto", mesege.userPhoto);
 //      result.put("msgText", mesege.msgText);
 //      result.put("toWho", mesege.toWho);
 //      result.put("day", mesege.day);
 //      result.put("moth", mesege.moth);
 //      result.put("year", mesege.year);

 //      return result;

 //  }

    public static Mesege fromMap(HashMap<String, Object> map) {

        Mesege mesege;

        String userName = (String) map.get("userName");
        String userPhoto = (String) map.get("userPhoto");
        String msgText = (String) map.get("msgText");
        String toWho = (String) map.get("toWho");

        Long dayL = (long) map.get("day");
        Long mothL = (long) map.get("moth");
        Long yearL = (long) map.get("year");

        String dayS = Long.toString(dayL);
        String mothS = Long.toString(mothL);
        String yearS = Long.toString(yearL);

        int day = valueOf(dayS);
        int moth = valueOf(mothS);
        int year = valueOf(yearS);

        mesege = new Mesege(userName, userPhoto,
                msgText, toWho, day, moth, year);


        return mesege;

    }

public void zsefg(){

}


}


