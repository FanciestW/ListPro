package com.fanciestw.listpro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class register extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText registerEditName = (EditText)findViewById(R.id.registerEditName);
    private EditText registerEditEmail = (EditText)findViewById(R.id.registerEditEmail);
    private EditText registerEditPassword = (EditText)findViewById(R.id.registerEditPassword);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart(){
        super.onStart();
        mAuth.signOut();
    }
}
