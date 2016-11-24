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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private EditText loginEditEmail;
    private EditText loginEditPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d("Login Activity", "onCreate");
        loginEditEmail = (EditText)findViewById(R.id.loginEditEmail);
        loginEditPassword = (EditText)findViewById(R.id.loginEditPassword);
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
        final String email = loginEditEmail.getText().toString();
        final String password = loginEditPassword.getText().toString();
        Log.d("User Login Info", "Email: " + email + " Password: " + password);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        mAuth.signInWithEmailAndPassword(email, password)
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
                            Log.d("Login Successful", "Signed in with email: " + email);
                            Intent intent = new Intent(getBaseContext(), allList.class);
                            startActivity(intent);
                        } else {
                            Log.d("Login Un-successful", "Sign in with email: " + email + " failed");
                            try{
                                throw task.getException();
                            } catch(FirebaseAuthInvalidCredentialsException ex){
                                builder.setMessage("Invalid Credentials")
                                        .setTitle("Sorry");
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            } catch(FirebaseAuthException ex){
                                builder.setMessage("FirebaseAuth Exception")
                                        .setTitle(ex.getMessage());
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

    public void goToRegister(View view){
        Intent intent = new Intent(this, register.class);
        startActivity(intent);
    }
}
