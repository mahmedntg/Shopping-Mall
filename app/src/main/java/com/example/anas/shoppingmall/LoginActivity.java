package com.example.anas.shoppingmall;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        alertDialogBuilder.setTitle("data not valid");
        alertDialogBuilder
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        alertDialog = alertDialogBuilder.create();
        email = findViewById(R.id.user_email);
        password = findViewById(R.id.user_password);
        findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!email.getText().toString().equals("") && !password.getText().toString().equals("")) {
                    login(email.getText().toString(), password.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill all boxes", Toast.LENGTH_LONG).show();
                }
            }
        });

        findViewById(R.id.register_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToRegisterAC();
            }
        });
    }

    public void login(String email, String pass) {
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String userId = firebaseAuth.getCurrentUser().getUid();
                    firebaseDatabase.getReference("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            progressDialog.hide();
                            startActivity(new Intent(LoginActivity.this, StoreActivity

                                    .class));
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            progressDialog.hide();
                        }
                    });

                } else {
                    progressDialog.hide();
                    alertDialog.setMessage(task.getException().getMessage());
                    alertDialog.show();
                }

            }
        });

    }

    private void navigateToRegisterAC() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

}

