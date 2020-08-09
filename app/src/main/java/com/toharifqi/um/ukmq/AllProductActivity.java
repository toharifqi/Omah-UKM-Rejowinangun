package com.toharifqi.um.ukmq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toharifqi.um.ukmq.adapter.ProductGridAdapter;
import com.toharifqi.um.ukmq.listener.IFirebaseLoadDoneProduct;
import com.toharifqi.um.ukmq.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class AllProductActivity extends AppCompatActivity implements IFirebaseLoadDoneProduct {
    private RecyclerView productRecyclerView;
    private ProductGridAdapter productGridAdapter;

    AlertDialog dialog;

    IFirebaseLoadDoneProduct iFirebaseLoadDone;

    Toolbar mToolbar;

    DatabaseReference productDb;

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            List<ProductModel> productList = new ArrayList<>();
            for (DataSnapshot productSnapshot:dataSnapshot.getChildren())
                productList.add(productSnapshot.getValue(ProductModel.class));
            iFirebaseLoadDone.onFirebaseLoadSuccess(productList);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            iFirebaseLoadDone.onFirebaseLoadFailed(databaseError.getMessage());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);

        // Action Bar
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);

        productDb = FirebaseDatabase.getInstance().getReference("products");

        iFirebaseLoadDone = this;

        //to display loading dialog
        dialog = new SpotsDialog.Builder().setContext(AllProductActivity.this).build();
        dialog.setMessage("Mohon tunggu...");

        loadProduct();

        productRecyclerView = findViewById(R.id.recyclerview_id);
        productRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

    }

    private void loadProduct() {
        dialog.show();
        productDb.addValueEventListener(valueEventListener);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onFirebaseLoadSuccess(List<ProductModel> productList) {
        productGridAdapter = new ProductGridAdapter(AllProductActivity.this, productList);
        productRecyclerView.setAdapter(productGridAdapter);
        dialog.dismiss();
    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    @Override
    public void onDestroy() {
        productDb.removeEventListener(valueEventListener);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        productDb.addValueEventListener(valueEventListener);
    }

    @Override
    public void onStop() {
        productDb.removeEventListener(valueEventListener);
        super.onStop();
    }
}