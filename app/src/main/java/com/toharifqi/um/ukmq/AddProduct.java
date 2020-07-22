package com.toharifqi.um.ukmq;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddProduct extends AppCompatActivity {

    Toolbar mToolbar;

    private static final String TAG = "NewPostActivity";
    private static final String REQUIRED = "Mohon masukkan data dengan benar.";
    private DatabaseReference mDatabaseReference;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
    }
}