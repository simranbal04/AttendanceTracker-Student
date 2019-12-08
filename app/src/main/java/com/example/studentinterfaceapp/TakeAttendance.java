package com.example.studentinterfaceapp;

import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class TakeAttendance extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener
{

 private QRCodeReaderView qrCodeReaderView;
 private TextView date_tv, message_tv;
 private FirebaseDatabase database;
 private DatabaseReference attendanceReference, textRefrence;

 private LinearLayout sec1,sec2;
 private String username;
 private String todayDate;
 private String text_to_match;

 @Override
 protected void onCreate(Bundle savedInstances) {
     super.onCreate(savedInstances);
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
     textRefrence = database.getReference("attendancetext");
     checkAttendanceForToday();
 }
 public void checkAttendanceForToday(){
     //get today's date from calender class in a date format
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD", Locale.CANADA);
     todayDate = sdf.format(Calendar.getInstance().getTime());

     date_tv.setText("("+ todayDate +")");
     //check from database if student's attendance already marked
     //Show in a format
     attendanceReference.child(todayDate + "").addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             if(dataSnapshot.exists()){
                 // if attendance already marked
                 if(dataSnapshot.hasChild(username)) {
                     //if attendance marked
                     sec2.setVisibility(View.VISIBLE);
                     sec1.setVisibility(View.GONE);
                 }
                 else{
                     // if attendance not marked
                     getTextToMatch();
                 }

             }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {

         }
     });

 }
 public void enableQRScanner(){
     sec2.setVisibility(View.GONE);
     sec1.setVisibility(View.VISIBLE);

     //Enable QR Scanner
     qrCodeReaderView.setOnQRCodeReadListener(this);

     //Use this function to enable or disable decoding QR code
     qrCodeReaderView.setQRDecodingEnabled(true);

     //Use ths function to enable or disable torch to focus on QR code
     qrCodeReaderView.setTorchEnabled(true);

     //Use this function to set back camera
     qrCodeReaderView.setBackCamera();


 }
  public void getTextToMatch() {
      textRefrence.child(todayDate).addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              //if text exists in database
              if (dataSnapshot.exists()) {
                  text_to_match = (String) dataSnapshot.getValue();
                  enableQRScanner();

              } else {
                  Toast.makeText(getApplicationContext(), "Teacher did not start taking attendance", Toast.LENGTH_LONG).show();
                  TakeAttendance.this.finish();
              }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });
  }

         // called when a QR is decoded
         // "text" - the text encoded in QR
         //"points" - points where QR control points are placed in View


         @Override
         public void onQRCodeRead(final String text, PointF[] points) {
             Log.d("MYMSG", todayDate + "--" + text);
             Toast.makeText(getApplicationContext(), todayDate + "--" + text, Toast.LENGTH_LONG).show();
             // when any text will be scanned through qr code , it will be matched with the code present in database
             if (text_to_match.equals(text.trim())) {
                 attendanceReference.child(todayDate).child(username).setValue(true);
                 Toast.makeText(getApplicationContext(), "Attendance Marked", Toast.LENGTH_LONG).show();
                 TakeAttendance.this.finish();
             } else {
                 //if the code is wrong
                 Toast.makeText(getApplicationContext(), "Invalid QR Code", Toast.LENGTH_LONG).show();
             }
         }
    @Override
    protected  void onResume(){
     super.onResume();
     qrCodeReaderView.startCamera();

    }
    @Override
    protected  void onPause(){
     super.onPause();
     qrCodeReaderView.stopCamera();
    }
}
