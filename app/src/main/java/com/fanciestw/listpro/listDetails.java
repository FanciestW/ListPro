package com.fanciestw.listpro;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Date;

public class listDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_details);
        TextView listTitleText = (TextView)findViewById(R.id.listDetail_Title);
        TextView listDescText = (TextView)findViewById(R.id.listDetail_Description);
        TextView listDateText = (TextView)findViewById(R.id.listDetail_DateCreated);
        List list = getIntent().getParcelableExtra("ListParcelable");
        Log.d("List Name:", list.getListTitle());
        listTitleText.setText(list.getListTitle());
        listDescText.setText(list.getListDescription());
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
        try {
            Date date = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy").parse(list.getDateCreated());
            listDateText.setText("Date Created: " + formatter.format(date));
        } catch(ParseException ex){
            Log.e("ParseException", ex.getMessage());
        }
    }
}
