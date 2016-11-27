package com.fanciestw.listpro;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;

import java.util.ArrayList;

/**
 * Created by wlin on 11/12/16.
 */

public class List {

    public String listTitle, listDescription, dateCreated;
    public ArrayList<String> items = new ArrayList<>();
    public ArrayList<String> share = new ArrayList<>();

    public List(String title, String descr){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        this.listTitle = title;
        this.listDescription = descr;
        this.dateCreated = format.format(cal.getTime());
    }
}
