package com.example.studentinterfaceapp;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StudentLogin extends AppCompatActivity {


    EditText username_et, password_et;

@Override
    protected void onCreate(Bundle savedInstanceState)
{
    super.onCreate(savedInstanceState);
    setContentView(R.layout.studentlogin);

    username_et = (EditText) findViewById(R.id.username_et);
    password_et = (EditText) findViewById(R.id.password_et);

}



}

