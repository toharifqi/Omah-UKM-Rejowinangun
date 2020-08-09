package com.toharifqi.um.ukmq;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.toharifqi.um.ukmq.helpers.Config;
import com.toharifqi.um.ukmq.model.ProductModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import es.dmoral.toasty.Toasty;

public class AddProduct extends AppCompatActivity {

    Toolbar mToolbar;

    private static final String TAG = "NewProductActivity";
    private static final String REQUIRED = "Mohon masukkan data dengan benar.";
    private DatabaseReference mDatabaseReference;
    private StorageReference storageReference;

    TextInputLayout txtProductName, txtProductDesc, txtProductCat, txtProductPrice, txtProductStock;

    private ImageView productPic;
    private Uri productPicUri;

    Button addProductBtn;
    public ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // Action Bar
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        txtProductName = findViewById(R.id.product_name);
        txtProductDesc = findViewById(R.id.product_desc);
        txtProductCat = findViewById(R.id.product_cat);
        txtProductPrice = findViewById(R.id.product_price);
        txtProductStock = findViewById(R.id.product_stock);

        productPic = findViewById(R.id.product_image);
        productPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });

        addProductBtn = findViewById(R.id.add_product);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);

        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private void setEditingEnabled(boolean enabled) {
        if (enabled) {
            addProductBtn.setVisibility(View.VISIBLE);
        } else {
            addProductBtn.setVisibility(View.GONE);
        }
    }

    private void submitPost() {
        dialog = ProgressDialog.show(AddProduct.this, "",
                "Menambahkan data produk baru. Mohon tunggu ...", true);

        final String productName = txtProductName.getEditText().getText().toString();
        final String productDesc = txtProductDesc.getEditText().getText().toString();
        final String productCat = txtProductCat.getEditText().getText().toString();
        final int productPrice =  Integer.parseInt(txtProductPrice.getEditText().getText().toString());
        final int productStock = Integer.parseInt(txtProductStock.getEditText().getText().toString());

        boolean isNullPhotoUrl = productPicUri == null;

        if (productName.isEmpty()){
            showDialogNoInput();
            txtProductName.setError(REQUIRED);
        }else if (productCat.isEmpty()){
            showDialogNoInput();
            txtProductCat.setError(REQUIRED);
        }else if (productPrice == 0){
            showDialogNoInput();
            txtProductPrice.setError(REQUIRED);
        }else if (productStock == 0){
            showDialogNoInput();
            txtProductStock.setError(REQUIRED);
        }else if (productDesc.isEmpty()){
            showDialogNoInput();
            txtProductDesc.setError(REQUIRED);
        }else if(isNullPhotoUrl){
            showDialogNoInput();
            Toasty.error(getApplicationContext(), Config.IMAGE_URL_NULL_MESSAGE, Toasty.LENGTH_SHORT, true).show();
        }else{
            setEditingEnabled(false);
        }

        if (!isNullPhotoUrl){
            writeNewPost(productName, productDesc, productCat, productPrice, productStock, productPicUri);
            setEditingEnabled(true);
        }



    }

    private void writeNewPost(final String productName, final String productDesc, final String productCat,
                              final int productPrice, final int productStock, Uri productPicUri) {

        String charRandom = generateString();
        String productCodeNospaces = productName.replace(" ", "");
        final String productId = productCodeNospaces.concat("-" + charRandom);

        final String uniqueKey = mDatabaseReference.push().getKey();
        storageReference = FirebaseStorage.getInstance().getReference().child("product/"+uniqueKey).child(Config.STORAGE_PATH + productPicUri.getLastPathSegment());
        StorageTask storageTask = storageReference.putFile(productPicUri);
        Task<Uri> uriTask=storageTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {

            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> taskSnapshot) throws Exception {
                if(!taskSnapshot.isSuccessful()){
                    throw taskSnapshot.getException();
                }
                return storageReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    Uri downloadURi=task.getResult();

                    String city = Config.userKecamatan + ", " + Config.userKabupaten;
                    ProductModel product = new ProductModel(Config.userNamaUsaha, productName,
                            productDesc, productCat, city, productId,
                            downloadURi.toString(), productPrice, productStock);

                    Map<String, Object> postValues = product.addProduct();
                    Map<String, Object> childUpdates = new HashMap<>();

                    childUpdates.put("/products/" + productId, postValues);

                    mDatabaseReference.updateChildren(childUpdates);
                    Toasty.success(getApplicationContext(), Config.BOOK_ADD_SUCCESS_MSG, Toasty.LENGTH_SHORT, true).show();
                }
                dialog.dismiss();
                // Finish this Activity, back to the stream
                finish();
            }
        });

    }

    private String generateString() {
        char[] chars = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i< 5; i++){
            char c = chars[random.nextInt(chars.length)];
            stringBuilder.append(c);
        } return stringBuilder.toString();
    }

    private void captureImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, Config.PHOTO_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== Config.PHOTO_REQUEST_CODE && resultCode==RESULT_OK && data!=null){
            productPicUri=data.getData();
            productPic.setImageURI(productPicUri);
        }
    }

    private void showDialogNoInput() {
        dialog.dismiss();
        new MaterialStyledDialog.Builder(this)
                .setTitle("Oops...")
                .setDescription("Masukkan data produk Anda dengan benar. Pastikan tidak ada data yang kosong.")
                .setPositiveText("OK")
                .setHeaderDrawable(R.drawable.unavailable)
                .setCancelable(true)
                .setScrollable(true)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    }
                }).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}