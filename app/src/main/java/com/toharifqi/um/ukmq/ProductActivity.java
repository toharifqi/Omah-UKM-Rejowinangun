package com.toharifqi.um.ukmq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toharifqi.um.ukmq.helpers.Config;
import com.toharifqi.um.ukmq.model.ProductModel;

import java.text.NumberFormat;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductActivity extends AppCompatActivity {
    private ProductModel productModel;
    private DatabaseReference userDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        //get product model from adapter
        productModel = getIntent().getExtras().getParcelable(Config.PRODUCT_MODEL);

        //fab
        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //views
        ImageView productImage = findViewById(R.id.product_image);
        TextView productName = findViewById(R.id.product_name);
        TextView productPrice = findViewById(R.id.product_price);
        TextView productStock = findViewById(R.id.product_stock);
        TextView productCat = findViewById(R.id.product_cat);
        TextView productDesc = findViewById(R.id.product_desc);
        TextView productCity = findViewById(R.id.product_city);
        TextView productCorp = findViewById(R.id.product_corp);
        final CircleImageView imageCorp = findViewById(R.id.image_corp);

        userDb = FirebaseDatabase.getInstance().getReference("users").child(productModel.getProductId());

        userDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String profilPic = dataSnapshot.child("profilPicture").getValue().toString();
                if (!profilPic.equals("")){
                    Glide.with(ProductActivity.this).load(profilPic).into(imageCorp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        String price = NumberFormat.getNumberInstance(Locale.GERMAN).format(productModel.getProductPrice());

        Glide.with(ProductActivity.this).load(productModel.getProductPic()).into(productImage);
        productName.setText(productModel.getProductName());
        productPrice.setText("Rp. " + price);
        productStock.setText("Stock: " + productModel.getProductStock() + " pcs");
        productCat.setText("Kategori: " + productModel.getProductCat());
        productDesc.setText(productModel.getProductDesc());
        productCity.setText(productModel.getProductCity());
        productCorp.setText(productModel.getProductCode());

    }

    public void toCheckOut(View view){
        Intent intent = new Intent(ProductActivity.this, CheckInvestActivity.class);
        intent.putExtra(Config.CHECKOUT_NAME, productModel.getProductName());
        startActivity(intent);
    }

    public void toProfil(View view){
        Intent intent = new Intent(ProductActivity.this, ProfilActivity.class);
        intent.putExtra(Config.USER_ID, productModel.getProductId());
        startActivity(intent);
    }
}