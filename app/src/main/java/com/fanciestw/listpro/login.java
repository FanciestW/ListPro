package com.fanciestw.listpro;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private EditText loginEditEmail = (EditText)findViewById(R.id.loginEditEmail);
    private EditText loginEditPassword = (EditText)findViewById(R.id.loginEditPassword);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Login Activity", "onCreate");
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Log.d("User Activity", "User Signed In");
                } else {
                    Log.d("User Activity", "User Sign Out");
                }
            }
        };
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d("Login Activity", "onStart");
        loginEditEmail.setText("");
        loginEditPassword.setText("");
        mAuth.addAuthStateListener(mAuthStateListener);
        mAuth.signOut();
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d("Login Activity", "onStop");
        if(mAuthStateListener != null) mAuth.removeAuthStateListener(mAuthStateListener);
    }

    public void login(View view){
        //TODO::Log user in
    }

    public void goToRegister(View view){
        Intent intent = new Intent(this, register.class);
        startActivity(intent);
    }
}
