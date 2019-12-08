package com.example.studentinterfaceapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.studentinterfaceapp.Students;

public class ViewProfile extends AppCompatActivity {

    private TextView student_name;
    private TextView student_program;
    private TextView student_class;
    private TextView student_id;
//    Button ok_button;

    // database details
    private  String username;

    private DatabaseReference studentReference;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewprofile);

        student_name = (TextView) findViewById(R.id.student_name);
        student_program = (TextView) findViewById(R.id.student_program);
        student_class = (TextView) findViewById(R.id.student_class);
        student_id = (TextView) findViewById(R.id.student_id);
//        ok_button = (Button) findViewById(R.id.ok_button);



        username= getIntent().getStringExtra("username");

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        studentReference = database.getReference("students");


        studentReference.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists())
                {
                    Students student_data = dataSnapshot.getValue(Students.class);
                    student_name.setText(student_data.getName());
                    student_program.setText("Program:" +student_data.getProgram());
                    student_id.setText("Student ID:" +student_data.getStudentid());
                    student_class.setText("Class:" +student_data.getClassname());



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    public  void okbtn(View view)
    {
        finish();
    }
}
