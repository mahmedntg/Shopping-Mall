package com.example.anas.shoppingmall;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.anas.shoppingmall.utillity.Store;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddStoreActivity extends BaseActivity implements TimePickerDialog.OnTimeSetListener {
    private ImageButton imageBtn;
    private EditText nameET, descriptionET;
    private TextView openTimeTV, closeTimeTV;
    private Uri uri = null;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private DatabaseReference userDatabaseReference;
    private FirebaseAuth firebaseAuth;
    private Spinner categorySpinner;
    List<String> list = new ArrayList<>();
    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_store);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("store");
        userDatabaseReference = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference categoryRef = FirebaseDatabase.getInstance().getReference("category");
        nameET = (EditText) findViewById(R.id.name);
        descriptionET = (EditText) findViewById(R.id.description);
        openTimeTV = (TextView) findViewById(R.id.openTime);
        closeTimeTV = (TextView) findViewById(R.id.closeTime);
        categorySpinner = (Spinner) findViewById(R.id.storeCategory);

        openTimeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddStoreActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String am_pm = (selectedHour < 12) ? "AM" : "PM";
                        openTimeTV.setText(selectedHour + ":" + selectedMinute + " " + am_pm);
                    }
                }
                        , hour, minute, false);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        closeTimeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddStoreActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String am_pm = (selectedHour < 12) ? "AM" : "PM";
                        closeTimeTV.setText(selectedHour + ":" + selectedMinute + " " + am_pm);
                    }
                }
                        , hour, minute, false);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(dataAdapter);
        categoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    list.add(postSnapshot.child("name").getValue(String.class));
                }
                dataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        findViewById(R.id.addStore_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nameET.getText().toString().equals("") && !openTimeTV.getText().toString().equals("") && !closeTimeTV.getText().toString().equals("") &&
                        !categorySpinner.getSelectedItem().toString().equals("")) {
                    addStore(nameET.getText().toString(), openTimeTV.getText().toString(), closeTimeTV.getText().toString(), categorySpinner.getSelectedItem().toString(), descriptionET.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill all boxes", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void addStore(final String name, final String openTime, final String closeTime, final String category, final String description) {

        progressDialog.setMessage("Adding Store ...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        StorageReference filePath = storageReference.child(uri.getLastPathSegment());
        final String userUid = firebaseAuth.getCurrentUser().getUid();
        filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                final Uri downloadUrl = taskSnapshot.getDownloadUrl();
                final DatabaseReference ref = databaseReference.push();
                Store store = new Store(name, category, openTime, closeTime, downloadUrl.toString(), description);
                ref.setValue(store);
                progressDialog.dismiss();
                startActivity(new Intent(AddStoreActivity.this, StoreActivity.class));
            }
        });
    }

    public void imageBtnClicked(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            uri = data.getData();
            imageBtn = (ImageButton) findViewById(R.id.storeImageBtn);
            imageBtn.setImageURI(uri);
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

}
