package com.example.anas.shoppingmall;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.anas.shoppingmall.utils.notificationcall.Controller;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddEventActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText nameET, descriptionET;
    private TextView dateTV;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        progressDialog = new ProgressDialog(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("event");
        nameET = (EditText) findViewById(R.id.eventName);
        descriptionET = (EditText) findViewById(R.id.eventDescription);
        dateTV = (TextView) findViewById(R.id.eventDate);
        dateTV.setOnClickListener(this);
        findViewById(R.id.addEventBTN).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == dateTV) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            dateTV.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);


                        }
                    }, year, month, day);
            datePickerDialog.show();
        } else if (view.getId() == R.id.addEventBTN) {
            addEvent();
        }
    }

    private void addEvent() {
        if (!nameET.getText().toString().equals("") && !descriptionET.getText().toString().equals("") && !dateTV.getText().toString().equals("")) {
            progressDialog.setMessage("Adding Store Sale...");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
            final DatabaseReference ref = databaseReference.push();
            ref.child("name").setValue(nameET.getText().toString());
            ref.child("description").setValue(descriptionET.getText().toString());
            ref.child("date").setValue(dateTV.getText().toString());
            progressDialog.hide();
            sendNotification();
            startActivity(new Intent(this, StoreActivity.class));

        } else {
            Toast.makeText(getApplicationContext(), "Please fill all boxes", Toast.LENGTH_LONG).show();
        }
    }

    private void sendNotification() {
        Controller controller
                = new Controller();
        controller.sendAllNotification("", "New Event", "Please, Join us for " + nameET.getText().toString() + " Event On: " + dateTV.getText().toString());
    }
}
