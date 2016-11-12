package com.fanciestw.listpro;

import java.util.Date;

/**
 * Created by wlin on 11/12/16.
 */

public class List {
    private String listTitle, listDescription;

    public List(String title, String descr){
        this.listTitle = title;
        this.listDescription = descr;
    }

    public String getTitle() { return this.listTitle; }
    public String getDescription() { return this.listDescription; }

}
