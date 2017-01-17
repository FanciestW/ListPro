package com.fanciestw.listpro;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by wlin on 11/12/16.
 */

public class List implements Parcelable {

    public String listTitle, listDescription, dateCreated, user, listID;

    public List(String title, String descr, String uid){
        Calendar cal = Calendar.getInstance();
        this.listTitle = title;
        this.listDescription = descr;
        this.dateCreated = cal.getTime().toString();
        this.user = uid;
    }
    public List(){}

    public void setListTitle(String title){ this.listTitle = title; }
    public void setListDescription(String description){ this.listDescription = description; }
    public void setDateCreated(String date){ this.dateCreated = date; }
    public void setUser(String user){ this.user = user; }
    public void setListID(String id){ this.listID = id; }

    public String getListTitle(){ return this.listTitle; }
    public String getListDescription(){ return this.listDescription; }
    public String getDateCreated(){ return this.dateCreated; }
    public String getUser(){ return this.user; }
    public String getListID(){ return this.listID; }

    public static final Parcelable.Creator<List> CREATOR = new Creator<List>(){

        public List createFromParcel(Parcel source){
            List list = new List();
            list.listTitle = source.readString();
            list.listDescription = source.readString();
            list.dateCreated = source.readString();
            list.user = source.readString();
            list.listID = source.readString();
            return list;
        }

        public List[] newArray(int size){
            return new List[size];
        }
    };

    public int describeContents(){
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags){
        parcel.writeString(listTitle);
        parcel.writeString(listDescription);
        parcel.writeString(dateCreated);
        parcel.writeString(user);
        parcel.writeString(listID);
    }
}
