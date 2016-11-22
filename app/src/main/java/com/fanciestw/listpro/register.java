package com.fanciestw.listpro;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

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
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    Log.d("User Activity", "User Signed In");
                } else {
                    Log.d("User Activity", "User Signed Out");
                }
            }
        };
    }

    @Override
    public void onStart(){
        super.onStart();
        registerEditName.setText("");
        registerEditEmail.setText("");
        registerEditPassword.setText("");
        mAuth.addAuthStateListener(mAuthListener);
        mAuth.signOut();
    }

    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener != null) mAuth.removeAuthStateListener(mAuthListener);
    }

    public void createUser(View view) throws FirebaseAuthUserCollisionException{
        //TODO::Validate user inputted info
        if(validateInfo()) {
            final String name = registerEditName.getText().toString();
            final String email = registerEditEmail.getText().toString();
            final String password = registerEditPassword.getText().toString();
            Log.d("User Info", "Name: " + name + "\tEmail: " + email + "\tPassword: " + password);
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });

                            if(task.isSuccessful()) {
                                User newUser = new User(name, email);
                                String uid = task.getResult().getUser().getUid();
                                mDatabase.child(uid).setValue(newUser);
                                signUserIn();
                            } else {
                                try{
                                    throw task.getException();
                                } catch(FirebaseAuthUserCollisionException ex){
                                    builder.setMessage("There exists an account with this email")
                                            .setTitle("Email In Use");
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                } catch(FirebaseAuthWeakPasswordException ex){
                                    builder.setMessage(ex.getReason())
                                            .setTitle("Weak Password");
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                } catch(Exception ex){
                                    builder.setMessage("Exception Caught")
                                            .setTitle(ex.getMessage());
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                }
                            }
                        }
                    });
        }
    }

    public boolean validateInfo(){
        String name = registerEditName.getText().toString();
        String email = registerEditEmail.getText().toString();
        String password = registerEditPassword.getText().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        if(name.equals("")){
            builder.setMessage("Please enter your name")
                    .setTitle("Error");
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        }
        if(email.equals("") && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            builder.setMessage("Please enter a valid email")
                    .setTitle("Error");

            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        }
        if(password.equals("")){
            builder.setMessage("Please enter a password")
                    .setTitle("Error");
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        }
        return true;
    }

    public void signUserIn(){
        try{
            String uid = mAuth.getCurrentUser().getUid();
            Log.d("User Status: ", uid);
            Intent intent = new Intent(this, allList.class);
            startActivity(intent);
        } catch (NullPointerException ex){
            Log.d("User Status: ", "User no signed in");
        }
    }

    public void goToLogin(){
        //TODO::Create function to go to login activity
    }
}