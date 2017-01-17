package com.fanciestw.listpro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class listDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_details);
        TextView listTitleText = (TextView)findViewById(R.id.listDetail_Title);
        TextView listDescText = (TextView)findViewById(R.id.listDetail_Description);
        List list = getIntent().getParcelableExtra("ListParcelable");
        Log.d("List Name:", list.getListTitle());
        listTitleText.setText(list.getListTitle());
        listDescText.setText(list.getListDescription());
    }
}
