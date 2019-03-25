package com.example.anas.shoppingmall;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class StoreDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageView;
    private TextView openTimeTV, closeTimeTV, descriptionTV, saleFromDateTV, saleToDateTV;
    private CardView addSaledBTN;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    int fromYear, fromMonth, fromDay;
    int toYear, toMonth, toDay;
    int year, month, day;
    private DatabaseReference databaseReference;
    private Store store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_details);
        databaseReference = FirebaseDatabase.getInstance().getReference("store");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        firebaseAuth = FirebaseAuth.getInstance();
        imageView = findViewById(R.id.storeImageView);
        openTimeTV = findViewById(R.id.openTime);
        closeTimeTV = findViewById(R.id.closeTime);
        descriptionTV = findViewById(R.id.description);
        saleFromDateTV = findViewById(R.id.saleFromDate);
        saleToDateTV = findViewById(R.id.saleToDate);
        addSaledBTN = findViewById(R.id.addSale_btn);
        Bundle extras = getIntent().getExtras();
        store = (Store) extras.get("store");
        openTimeTV.setText("Opening At: " + store.getOpenTime());
        closeTimeTV.setText("Closing At: " + store.getCloseTime());
        descriptionTV.setText(store.getDescription());
        if (!TextUtils.isEmpty(store.getSaleFromDate()) || !TextUtils.isEmpty(store.getSaleToDate())) {
            saleFromDateTV.setText("Sale From: " + store.getSaleFromDate());
            saleToDateTV.setText("Sale To: " + store.getSaleToDate());
        }
        Picasso.with(this).load(store.getImage()).into(imageView);
        progressDialog.hide();
        String email = firebaseAuth.getCurrentUser().getEmail();
        if (email.equals(SharedUtils.email)) {
            addSaledBTN.setVisibility(View.VISIBLE);
            addSaledBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addSale();
                }
            });
            saleFromDateTV.setClickable(true);
            saleFromDateTV.setOnClickListener(this);
            saleToDateTV.setClickable(true);
            saleToDateTV.setOnClickListener(this);
        } else if (TextUtils.isEmpty(store.getSaleFromDate()) || TextUtils.isEmpty(store.getSaleToDate())) {
            saleFromDateTV.setVisibility(View.GONE);
            saleToDateTV.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {

        if (view == saleFromDateTV) {
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            saleFromDateTV.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);


                        }
                    }, year, month, day);
            datePickerDialog.show();
        } else if (view == saleToDateTV) {
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            saleToDateTV.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                        }
                    }, year, month, day);
            datePickerDialog.show();
        }
    }

    private void addSale() {
        progressDialog.setMessage("Adding Store Sale...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        Map<String, Object> saleDate = new HashMap<>();
        saleDate.put("saleFromDate", saleFromDateTV.getText().toString());
        saleDate.put("saleToDate", saleToDateTV.getText().toString());
        databaseReference.child(store.getKey()).updateChildren(saleDate);
        progressDialog.hide();
        startActivity(new Intent(this, StoreActivity.class));
        sendNotification();
    }


    private void sendNotification() {

    }

}
