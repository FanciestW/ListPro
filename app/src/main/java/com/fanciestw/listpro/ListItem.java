package com.fanciestw.listpro;

import java.util.Calendar;

/**
 * Created by William on 1/17/2017.
 */

public class ListItem {
    public String itemTitle, itemDescription, dateCreated, itemID, user;
    public boolean checked;

    public ListItem(){}
    public ListItem(String title, String desc, String uid){
        this.itemTitle = title;
        this.itemDescription = desc;
        this.user = uid;
        this.dateCreated = Calendar.getInstance().getTime().toString();
        this.checked = false;
    }

    public void setItemTitle(String title){ this.itemTitle = title; }
    public void setItemDescription(String desc){ this.itemDescription = desc; }
    public void setItemID(String id){ this.itemID = id; }
    public void setUser(String uid){ this.user = uid; }

    public String getItemTitle(){ return itemTitle; }
    public String getItemDescription(){ return itemDescription; }
    public String getDateCreated(){ return dateCreated; }
    public String getItemID(){ return itemID; }
    public String getUser(){ return user; }
}
