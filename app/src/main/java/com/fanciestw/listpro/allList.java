package com.fanciestw.listpro;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class allList extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mList = mDatabase.getReference().child("lists");
    private AllListAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_list);
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

        arrayAdapter = new AllListAdapter(this, new ArrayList<List>());
        ListView listView = (ListView)findViewById(R.id.all_list_listView);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Position", i + " " + l);
                List clickedList  = arrayAdapter.getItem(i);
                Log.d("List Clicked: ", clickedList.listTitle + " " + clickedList.getListDescription());
            }
        });
        mList.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("List Added", "Current User: " + mAuth.getCurrentUser().getUid() + " List Owner: " + dataSnapshot.child("user").getValue());
                if(dataSnapshot.child("user").getValue().equals(mAuth.getCurrentUser().getUid())) {
                    List rList = dataSnapshot.getValue(List.class);
                    arrayAdapter.add(rList);
                    Log.d("List Returned on Add", rList.listTitle + " " + rList.listDescription);
                } else Log.d("List Does Not Belong", "Belongs to: " + dataSnapshot.child("user").getValue());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("List Changed", "Current User: " + mAuth.getCurrentUser().getUid() + " List Owner: " + dataSnapshot.child("user").getValue());
                if(dataSnapshot.child("user").getValue().equals(mAuth.getCurrentUser().getUid())){
                    List rList = dataSnapshot.getValue(List.class);
                    Log.d("List Returned on Change", rList.listTitle + " " + rList.listDescription);
                    updateAList(rList);
                } else Log.d("List Does Not Belong", "Belongs to: " + dataSnapshot.child("user").getValue());
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("List Removed", "Current User: " + mAuth.getCurrentUser().getUid() + " List Owner: " + dataSnapshot.child("user").getValue());
                if(dataSnapshot.child("user").getValue().equals(mAuth.getCurrentUser().getUid())){
                    List rList = dataSnapshot.getValue(List.class);
                    removeList(rList);
                    Log.d("List Returned on Remove", rList.listTitle + " " + rList.listDescription);
                } else Log.d("List Does Not Belong", "Belongs to: " + dataSnapshot.child("user").getValue());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d("List Moved", "Current User: " + mAuth.getCurrentUser().getUid() + " List Owner: " + dataSnapshot.child("user").getValue());
                if(dataSnapshot.child("user").getValue().equals(mAuth.getCurrentUser().getUid())){
                    List newList = dataSnapshot.getValue(List.class);
                    Log.d("List Returned on Remove", newList.listTitle + " " + newList.listDescription);
                } else Log.d("List Does Not Belong", "Belongs to: " + dataSnapshot.child("user").getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d("allList Activity", "onStart");
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d("allList Activity", "onStop");
        if(mAuthStateListener != null) mAuth.removeAuthStateListener(mAuthStateListener);
    }

    public void addNewList(View view){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New List");

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

                Log.d("New List Details", title + ", " + desc);
                List newList = new List(title, desc, mAuth.getCurrentUser().getUid());
                String newListID = mList.push().getKey();
                newList.setListID(newListID);
                mList.child(newListID).setValue(newList);
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

    public void deleteList(int position, final List list){
        Log.d("Delete List", "Deleting list at position " + position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("DELETE LIST");
        builder.setMessage("You are about to delete \"" + list.getListTitle() + "\"\nAre You Sure?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                Log.d("List removed", "List to remove " + list.getListTitle());
                mList.child(list.getListID()).removeValue();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                Log.d("List not removed", "List not removed " + list.getListTitle());
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void removeList(List listToRemove){
        for(int i = 0; i < arrayAdapter.getCount(); i++){
            if(arrayAdapter.getItem(i).getListID() == listToRemove.getListID()){
                arrayAdapter.remove(arrayAdapter.getItem(i));
            }
        }
    }

    public void updateAList(List updatedList){
        Log.d("Trying to update a List", updatedList.listTitle);
        for(int i = 0; i < arrayAdapter.getCount(); i++){
            if(arrayAdapter.getItem(i).getListID() == updatedList.getListID()){
                arrayAdapter.allList.set(i, updatedList);
                arrayAdapter.notifyDataSetChanged();
            }
        }
    }

    public void signOut(View view){
        mAuth.signOut();
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }
}