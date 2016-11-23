package com.fanciestw.listpro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Login Activity", "onCreate");
        setContentView(R.layout.activity_login);
        Log.d("Login Activity", "onStart");
        Log.d("Login Activity", "onStop");
    }

    public void goToRegister(View view){
        Intent intent = new Intent(this, register.class);
        startActivity(intent);
    }
}
