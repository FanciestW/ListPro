package com.fanciestw.listpro;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    private EditText registerEditName;
    private EditText registerEditEmail;
    private EditText registerEditPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerEditName = (EditText)findViewById(R.id.registerEditName);
        registerEditEmail = (EditText)findViewById(R.id.registerEditEmail);
        registerEditPassword = (EditText)findViewById(R.id.registerEditPassword);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart(){
        super.onStart();
        mAuth.signOut();
        registerEditName.setText("");
        registerEditEmail.setText("");
        registerEditPassword.setText("");
    }

    public void createUser(View view){
        final String name = registerEditName.getText().toString();
        final String email = registerEditEmail.getText().toString();
        final String password = registerEditPassword.getText().toString();

        Log.d("User Info", "Name: " + name + "\tEmail: " + email + "\tPassword: " + password);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        User newUser = new User(name, email);
                        String uid = task.getResult().getUser().getUid();
                        mDatabase.child("users").child(uid).setValue(newUser);
                        //TODO::Fix com.google.firebase.database.DatabaseException: No properties to serialize found on class com.fanciestw.listpro.User
                    }
                });
    }
}
