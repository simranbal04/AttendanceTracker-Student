package com.example.studentinterfaceapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewProfile extends AppCompatActivity {

    TextView student_name;
    TextView student_program;
    TextView student_class;
    TextView student_id;
    Button ok_button;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewprofile);

        student_name = (TextView) findViewById(R.id.student_name);
        student_program = (TextView) findViewById(R.id.student_program);
        student_class = (TextView) findViewById(R.id.student_class);
        student_id = (TextView) findViewById(R.id.student_id);
        ok_button = (Button) findViewById(R.id.ok_button);

    }
}
