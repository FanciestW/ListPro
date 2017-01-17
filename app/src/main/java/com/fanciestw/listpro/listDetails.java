package com.fanciestw.listpro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class listDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_details);
    }

    @Override
    public void onStart(){
        Intent intent = getIntent();
        int position = intent.getIntExtra("Position", -1);
        List clickedList = (List)intent.getSerializableExtra("List");
    }
}
