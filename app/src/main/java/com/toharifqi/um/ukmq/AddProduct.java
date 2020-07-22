package com.toharifqi.um.ukmq;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;
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

    TextInputLayout txtProductCode, txtProductName, txtProductDesc, txtProductCat, txtProductPrice, txtProductStock;

    private ImageView productPic;
    private Uri productPicUri;

    Button addProductBtn;
    public ProgressDialog dialog;

    WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // Action Bar
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        txtProductCode = findViewById(R.id.product_code);
        txtProductName = findViewById(R.id.product_name);
    }
}