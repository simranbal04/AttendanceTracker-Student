package com.example.studentinterfaceapp;

import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class TakeAttendance extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {


    private QRCodeReaderView qrCodeReaderView;
    private TextView date_tv, message_tv;
    private FirebaseDatabase database;
    private DatabaseReference attendanceReference, textReference;

    private LinearLayout sec1, sec2;

    private String username;
    private String todayDate;
    private String text_to_match;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.takeattendance);


        //refer all views in java file
        date_tv = findViewById(R.id.date_tv);
        qrCodeReaderView = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        sec1 = findViewById(R.id.sec1);
        sec2 = findViewById(R.id.sec2);
        message_tv = findViewById(R.id.message_tv);

        //get username from intent extra
        username = getIntent().getStringExtra("username");

        database = FirebaseDatabase.getInstance();
        attendanceReference = database.getReference("dailyattendance");
        textReference = database.getReference("attendancedata");
        checkAttendanceForToday();


    }

    public void checkAttendanceForToday() {
        //get today date from Calender class in formatted form
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        todayDate = sdf.format(Calendar.getInstance().getTime());

        date_tv.setText("(" + todayDate + ")");

        ////check from database if attendance for this username is already marked
        // show appropriate views accordingly
        attendanceReference.child(todayDate + "").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d("TAG", "onDataChange: "+dataSnapshot);
                if (dataSnapshot.exists()) {
                    // if attendance is already marked

                    Log.d("TAG", "onDataChange: ");
                    if (dataSnapshot.hasChild(username)) {
                        //if already marked
                        sec2.setVisibility(View.VISIBLE);
                        sec1.setVisibility(View.GONE);
                        Log.d("TAG", "onDataChange: hasChild");

                    } else {
                        //if not already marked
                        getTextToMatch();
                        Log.d("TAG", "onDataChange: else");

                    }
                } else {
                    //if not already marked
                    getTextToMatch();
                    Log.d("TAG", "onDataChange: 2nd Else");

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void enableQRScanner() {


        sec2.setVisibility(View.GONE);
        sec1.setVisibility(View.VISIBLE);

        //enable QR Scanner
        qrCodeReaderView.setOnQRCodeReadListener(this);

        // Use this function to enable/disable decoding
        qrCodeReaderView.setQRDecodingEnabled(true);

        // Use this function to change the autofocus interval (default is 5 secs)
//        qrCodeReaderView.setAutofocusInterval(6000L);

        // Use this function to enable/disable Torch
        qrCodeReaderView.setTorchEnabled(true);

        // Use this function to set front camera preview
        // qrCodeReaderView.setFrontCamera();

        // Use this function to set back camera preview
        qrCodeReaderView.setBackCamera();
    }

    public void getTextToMatch() {
        textReference.child(todayDate).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("TAG", "onDataChange: outside if");
                //if text exist in databse
                if (dataSnapshot.exists()) {
                    Log.d("TAG", "onDataChange: "+dataSnapshot.getValue());
                    text_to_match = (String) dataSnapshot.getValue();
                    enableQRScanner();

                } else {
                    Toast.makeText(getApplicationContext(), "Teacher Did not start taking attendance yet", Toast.LENGTH_LONG).show();
                    TakeAttendance.this.finish();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    // Called when a QR is decoded
    // "text" : the text encoded in QR
    // "points" : points where QR control points are placed in View
    @Override
    public void onQRCodeRead(final String text, PointF[] points) {

        Log.d("TAG", todayDate + " -- " + text);
        Toast.makeText(getApplicationContext(), todayDate + " -- " + text, Toast.LENGTH_LONG).show();
        //when any text will be scanned through qr code , it will be matched with the code present in database


        Log.d("TAG", "onQRCodeRead: "+text_to_match);
        Log.d("TAG", "onQRCodeRead: "+text.trim());
        if (text_to_match.equals(text.trim())) {
            Log.d("TAG", "onQRCodeRead: IF");
            attendanceReference.child(todayDate).child(username).setValue(true);
            Toast.makeText(getApplicationContext(), "Attendance Marked", Toast.LENGTH_LONG).show();
            TakeAttendance.this.finish();

        } else {
            Log.d("TAG", "onQRCodeRead: ELSE");
            //if code is wrong , show toast
            Toast.makeText(getApplicationContext(), "Invalid QR CODE", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        qrCodeReaderView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        qrCodeReaderView.stopCamera();
    }

}
