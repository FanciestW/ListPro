package com.fanciestw.listpro;

import java.util.ArrayList;

/**
 * Created by wlin on 11/20/16.
 */

public class User {

    public String name, email;
    public ArrayList<String> lists = new ArrayList<>();

    User(String name, String email){
        this.name = name;
        this.email = email;
    }
}
