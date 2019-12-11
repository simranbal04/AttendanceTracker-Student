package com.example.studentinterfaceapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class StudentRegister extends AppCompatActivity {
    EditText etclass, etemail, etinstruc, etname, etpassword, etprog,etstuid, etusername;
    Button regbutton;
    Spinner prog;
    TextView loginBtn;
    String progtxt;


    private FirebaseAuth firebaseAuth;
    private DatabaseReference stuReference;
    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_register);


        etclass = findViewById(R.id.etclass);
        etemail = findViewById(R.id.etemail);
        etinstruc = findViewById(R.id.etinstruc);
        etname = findViewById(R.id.etname);
        etpassword = findViewById(R.id.etpassword);
        etprog = findViewById(R.id.etprog);
        etstuid = findViewById(R.id.etstuid);
        etusername = findViewById(R.id.etusername);
        regbutton = findViewById(R.id.regbutton);
        //prog = findViewById(R.id.etprog);


//        String[] items = new String[]{"MAD-5234", "MAD-5244", "MAD-5254", "MAD-5264", "MAD-5314"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//        prog.setAdapter(adapter);
//
//        prog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                progtxt =prog.getSelectedItem().toString();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                // TODO Auto-generated method stub
//
//            }
//        });

        regbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String classname = etclass.getText().toString();
                final String email = etemail.getText().toString().trim();
                final String instructor = etinstruc.getText().toString();
                final String name = etname.getText().toString();
                final String password = etpassword.getText().toString().trim();
                final String studentid = etstuid.getText().toString();
                final String username = etusername.getText().toString();
                final String program = etprog.getText().toString();


                stuReference = FirebaseDatabase.getInstance().getReference("NewStudents");
                firebaseAuth = FirebaseAuth.getInstance();

                if (TextUtils.isEmpty(classname)) {
                    etclass.setError("Class Name is Required.");
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    etemail.setError("Email is Required.");
                    return;
                }

                if (TextUtils.isEmpty(instructor)) {
                    etinstruc.setError("Instructor Name is Required.");
                    return;
                }

                if (TextUtils.isEmpty(name)) {
                    etname.setError("Name is Required.");
                    return;
                }

                if (password.length() < 6) {
                    etpassword.setError("Password Must be >= 6 Characters");
                    return;
                }
                if (TextUtils.isEmpty(studentid)) {
                    etstuid.setError("StudentID is Required.");
                    return;
                }

                if (TextUtils.isEmpty(username)) {
                    etusername.setError("Username is Required.");
                    return;
                }


                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(StudentRegister.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    Students info = new Students(

                                             name,program,classname,instructor,username, password,studentid,email
                                    );
                                    FirebaseDatabase.getInstance().getReference("NewStudents")

                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())

                                            .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(StudentRegister.this, "New Student Added", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(),StudentLogin.class));
                                        }
                                    });

                                } else
                                    {
                                    // If sign in fails, display a message to the user.

                                }

                                // ...
                            }
                        });




            }
        });



//        regbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(StudentRegister.this, "REgistration Successful", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(getApplicationContext(), StudentLogin.class));
//            }
//        });



    }


}