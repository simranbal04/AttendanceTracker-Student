package com.example.studentinterfaceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    DatabaseReference studentRefrences;

    EditText username_et, password_et;
    Button regbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentlogin);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        studentRefrences = database.getReference("students");
        //refer to edittext from xml file
        username_et = (EditText) findViewById(R.id.username_et);
        password_et = (EditText) findViewById(R.id.password_et);



 
    }


    public void studentLogin(View view) {
//when user will click on LOGIN button, get value of username & password and store it in a varible
        final String username = username_et.getText().toString().trim();
        final String password = password_et.getText().toString().trim();
        //if either username or password is empty a toast msg will show up
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Fill all Fields", Toast.LENGTH_LONG).show();
        } else {
// make connection with database to fetch the value of username & password
            studentRefrences.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //if username is correct,data will exist
                    if (dataSnapshot.exists()) {
                        Students student_data = dataSnapshot.getValue(Students.class);
                        if (student_data.getPassword().equalsIgnoreCase(password)) {
                            Intent in = new Intent(getApplicationContext(), StudentDashboard.class);
                            in.putExtra("username", username);
                            startActivity(in);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Wrong username", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

        public void studentRegister(View view)
        {


            regbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), StudentRegister.class));
                }
            });


        }

}

