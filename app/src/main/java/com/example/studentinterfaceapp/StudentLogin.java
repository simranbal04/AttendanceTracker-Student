package com.example.studentinterfaceapp;

import android.content.Intent;
import android.os.Bundle;
import android.content.pm.PackageManager;
import android.view.View;
import android.os.Build;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class StudentLogin extends AppCompatActivity {
    //Database Refrences to firebase
    FirebaseDatabase database;
    DatabaseReference studentReference;

    EditText username_et, password_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentlogin);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        studentReference = database.getReference("students");
        //refer to edittext from xml file
        username_et = findViewById(R.id.username_et);
        password_et = findViewById(R.id.password_et);

    }


    public void studentLogin(View view) {
//when user will click on LOGIN button, get value of username & password and store it in a varible
        final String username = username_et.getText().toString().trim();
        final String password = password_et.getText().toString().trim();
        //if either username or password is empty a toast msg will show up
        if(username.isEmpty()||password.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please Fill all Fields",Toast.LENGTH_LONG).show();
        }
        else {
// make connection with database to fetch the value of username & password
            studentReference.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //if username is correct,data will exist
                    if (dataSnapshot.exists())
                    {
                        Students student_data = dataSnapshot.getValue(Students.class);
                        if (student_data.getPassword().equalsIgnoreCase(password))
                        {
                            Intent in = new Intent(getApplicationContext(), StudentDashboard.class);
                            in.putExtra("username", username);
                            startActivity(in);
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_LONG).show();
                            }
                    }
                    else
                        {
                          Toast.makeText(getApplicationContext(),"Wrong username",Toast.LENGTH_LONG).show();
                        }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}

