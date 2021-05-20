package com.mickdevil.go4lunch.UI.Chat;

public class Mesege {

    public String userName;

    public String userPhoto;

    public String msgText;

    public String toWho;

    public long creationTime;

   public int year;

   public int month;

   public int day;

   public long mesegeCount;

    public Mesege() {

    }

    public Mesege(String userName, String userPhoto, String msgText, String toWho, long creationTime, int year, int month, int day, long mesegeCount) {
        this.userName = userName;
        this.userPhoto = userPhoto;
        this.msgText = msgText;
        this.toWho = toWho;
        this.creationTime = creationTime;
        this.year = year;
        this.month = month;
        this.day = day;
        this.mesegeCount = mesegeCount;
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

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public long getMesegeCount() {
        return mesegeCount;
    }

    public void setMesegeCount(long mesegeCount) {
        this.mesegeCount = mesegeCount;
    }








}


