package com.example.studentinterfaceapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class StudentLogin extends AppCompatActivity {
    //Database Refrences to firebase
    FirebaseDatabase database;
    DatabaseReference studentReferences;

    EditText username_et, password_et;
    Button regbutton;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentlogin);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        studentReferences = database.getReference("students");

        //refer edittext from xml file
        username_et=findViewById(R.id.username_et);
        password_et=findViewById(R.id.password_et);
        regbutton =findViewById(R.id.regbutton);

        askpermission();
    }




    @RequiresApi(api = Build.VERSION_CODES.M)
    public void askpermission()
    {

        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    33);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant

            return;
        } else {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 33: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! do the
                    // calendar task you need to do.

                } else {

                    askpermission();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }


        }
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
            studentReferences.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
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

