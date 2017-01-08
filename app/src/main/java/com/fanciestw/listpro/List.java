package com.fanciestw.listpro;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;

import java.util.ArrayList;

/**
 * Created by wlin on 11/12/16.
 */

public class List {

    public String listTitle, listDescription, dateCreated, user, listID;

    public List(String title, String descr, String uid){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        this.listTitle = title;
        this.listDescription = descr;
        this.dateCreated = format.format(cal.getTime());
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
}
