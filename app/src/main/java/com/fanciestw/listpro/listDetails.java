package com.fanciestw.listpro;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.util.Date;

public class listDetails extends AppCompatActivity {

    private List list;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mListItems = mDatabase.getReference().child("lists");
    private AllListAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_details);
        mAuthStateListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    Log.d("User Activity", "User Signed In");
                } else {
                    Log.d("User Activity", "User Signed Out");
                    signOut(getCurrentFocus());
                }
            }
        };
        setItemDetails();
        //TODO::Add ChildEventListener for listItems
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d("listDetail Activity", "onStart");
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d("listDetail Activity", "onStop");
        if(mAuthStateListener != null) mAuth.removeAuthStateListener(mAuthStateListener);
    }

    public void addNewItem(View view){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New List Item");

        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_new_list_form, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(dialogView);

        // Set up the buttons
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = ((EditText)dialogView.findViewById(R.id.add_list_title)).getText().toString();
                String desc = ((EditText)dialogView.findViewById(R.id.add_list_desc)).getText().toString();

                Log.d("New ListItem Details", title + ", " + desc);
                ListItem newListItem = new ListItem(title, desc, mAuth.getCurrentUser().getUid());
                String newListID = mListItems.push().getKey();
                newListItem.setItemID(newListID);
                mListItems.child(list.getListID()).child("items").child(newListID).setValue(newListItem);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void signOut(View view){
        mAuth.signOut();
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }

    public void setItemDetails(){
        TextView listTitleText = (TextView)findViewById(R.id.listDetail_Title);
        TextView listDescText = (TextView)findViewById(R.id.listDetail_Description);
        TextView listDateText = (TextView)findViewById(R.id.listDetail_DateCreated);
        list = getIntent().getParcelableExtra("ListParcelable");
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
