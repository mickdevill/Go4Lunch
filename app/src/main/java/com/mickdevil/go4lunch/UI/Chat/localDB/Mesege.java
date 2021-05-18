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
public class Mesege {

    //for msg status
    public static final String MSG_STATUS_OK = "OK";
    public static final String MSG_STATUS_NON_READ_BY_EVREE_ONE = "NOT_OK";


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

    public int hour;

    public int min;

    public int sec;

    @ColumnInfo(name = "milis")
    public int milis;
    // public String userName, userPhoto, msgText, toWho;
    // public int day, moth, year;

    //this string is used to remove the messeges, for extra high optimization
    @ColumnInfo(name = "msgStatus")
    public String msgStatus;


    public Mesege() {

    }

    public Mesege(String userName, String userPhoto, String msgText, String toWho, int day, int moth, int year, String msgStatus, int milis, int hour, int sec, int min) {
        this.userName = userName;
        this.userPhoto = userPhoto;
        this.msgText = msgText;
        this.toWho = toWho;
        this.day = day;
        this.moth = moth;
        this.year = year;
        this.msgStatus = msgStatus;
        this.milis = milis;
        this.hour = hour;
        this.sec = sec;
        this.min = min;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }


    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getSec() {
        return sec;
    }

    public void setSec(int sec) {
        this.sec = sec;
    }

    public String getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(String msgStatus) {
        this.msgStatus = msgStatus;
    }


    public int getMilis() {
        return milis;
    }

    public void setMilis(int milis) {
        this.milis = milis;
    }


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
//TODO remove this one, i dont use RDB for msg and don't get msg with hash map

//  public static Mesege fromMap(HashMap<String, Object> map) {
//      Mesege mesege;

//      String userName = (String) map.get("userName");
//      String userPhoto = (String) map.get("userPhoto");
//      String msgText = (String) map.get("msgText");
//      String toWho = (String) map.get("toWho");
//      String msgStatus = (String) map.get("msgStatus");

//      Long dayL = (long) map.get("day");
//      Long mothL = (long) map.get("moth");
//      Long yearL = (long) map.get("year");
//      Long milisL = (long) map.get("milis");

//      String dayS = Long.toString(dayL);
//      String mothS = Long.toString(mothL);
//      String yearS = Long.toString(yearL);
//      String milisS = Long.toString(yearL);


//      int milis = valueOf(milisS);
//      int day = valueOf(dayS);
//      int moth = valueOf(mothS);
//      int year = valueOf(yearS);


//      mesege = new Mesege(userName, userPhoto,
//              msgText, toWho, day, moth, year, msgStatus, milis);
//      return mesege;
//  }


}


