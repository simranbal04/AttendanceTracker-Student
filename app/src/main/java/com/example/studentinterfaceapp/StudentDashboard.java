package com.example.studentinterfaceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StudentDashboard extends AppCompatActivity {

    Button ViewProfilebutton;
    Button TakeAttendancebutton;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_dashboard);
        username=getIntent().getStringExtra("username");

        TakeAttendancebutton = (Button) findViewById(R.id.TakeAttendancebutton);
        ViewProfilebutton = (Button) findViewById(R.id.ViewProfilebutton);


        ViewProfilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TakeAttendance.class);
                startActivity(intent);
            }
        });

        TakeAttendancebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),ViewProfile.class);
                startActivity(intent1);
            }
        });
    }



}
