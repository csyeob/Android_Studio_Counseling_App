package com.cookandroid.mp2;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DataBase_User_Info {

    public String Array_id [];
    public String pwd;
    public String check;
    public String result;

    private DatabaseReference  mDatabase = FirebaseDatabase.getInstance().getReference();

    public void start_db(){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue().toString();
                check = value;

                Array_id = check.split(" ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        };
        mDatabase.child("Users").addValueEventListener(postListener);
    }

    public void FindDatabase(String c_id){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue().toString();
                result = value;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        };
        mDatabase.child("Results").child(c_id).addValueEventListener(postListener);
    }
}
