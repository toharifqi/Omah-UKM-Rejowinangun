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
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.toharifqi.um.ukmq.helpers.Config;
import com.toharifqi.um.ukmq.model.PaymentModel;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class CheckOutActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;

    private static final String REQUIRED = "Mohon masukkan data dengan benar.";
    private DatabaseReference mDatabaseReference;
    private StorageReference storageReference;
    private FirebaseAuth fAuth;

    private TextInputLayout txBankProvider, txAccountName, txNoRekening, txNominalTrans;

    private ImageView checkoutPic;
    private Uri checkoutPicUri;

    private String productInvestId, productInvestName, productInvestPrice, senderId, timeStampString, type;

    Button checkOutbtn;
    public ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_invest);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();

        productInvestId = getIntent().getStringExtra(Config.PRODUCTPROJECT_ID);
        productInvestName = getIntent().getExtras().getString(Config.CHECKOUT_NAME);
        productInvestPrice = getIntent().getExtras().getString(Config.CHECKOUT_PRICE);
        type = getIntent().getExtras().getString(Config.PROJECTPRODUCT_TYPE);
        senderId = fAuth.getCurrentUser().getUid();
        Date timeStamp = Calendar.getInstance().getTime();
        timeStampString = timeStamp.toString();

        // Action Bar
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);

        txBankProvider = findViewById(R.id.bank_provider);
        txAccountName = findViewById(R.id.account_name);
        txNoRekening = findViewById(R.id.no_rekening);
        txNominalTrans = findViewById(R.id.nominal_trans);

        TextView checkoutName = findViewById(R.id.invest_name);
        TextView checkoutPrice = findViewById(R.id.checkout_price);
        checkoutName.setText(productInvestName);
        checkoutPrice.setText(productInvestPrice);
        checkOutbtn = findViewById(R.id.checkout_btn);
        checkoutPic = findViewById(R.id.checkout_image);

        checkOutbtn.setOnClickListener(this);
        checkoutPic.setOnClickListener(this);



    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.checkout_btn:
                submitPost();
                break;
            case R.id.checkout_image:
                captureImage();
                break;
        }
    }

    private void submitPost() {
        dialog = ProgressDialog.show(CheckOutActivity.this, "",
                "Menambahkan data laporan progress baru. Mohon tunggu ...", true);

        String bankProvider = txBankProvider.getEditText().getText().toString();
        String accountName = txAccountName.getEditText().getText().toString();
        String noRekening = txNoRekening.getEditText().getText().toString();
        String nominalTrans = txNominalTrans.getEditText().getText().toString();

        boolean isNullPhotoUrl = checkoutPicUri == null;

        if (bankProvider.isEmpty()){
            showDialogNoInput();
            txBankProvider.setError(REQUIRED);
        }else if (accountName.isEmpty()){
            showDialogNoInput();
            txAccountName.setError(REQUIRED);
        }else if (noRekening.isEmpty()){
            showDialogNoInput();
            txNoRekening.setError(REQUIRED);
        }else if (nominalTrans.isEmpty()){
            showDialogNoInput();
            txNominalTrans.setError(REQUIRED);
        }else if (isNullPhotoUrl){
            showDialogNoInput();
            Toasty.error(getApplicationContext(), Config.IMAGE_URL_NULL_MESSAGE, Toasty.LENGTH_SHORT, true).show();
        }else{
            setEditingEnabled(false);
        }

        if (!isNullPhotoUrl){
            writeNewPost(bankProvider, accountName, noRekening, nominalTrans, checkoutPicUri, productInvestName, senderId,
                    productInvestId, timeStampString, type);
            setEditingEnabled(true);
        }

    }

    private void writeNewPost(final String bankProvider, final String accountName, final String noRekening,
                              final String nominalTrans, Uri checkoutPicUri, final String productInvestName,
                              final String senderId, final String productInvestId, final String timeStampString, final String type) {
        final String paymentId = productInvestId + "-" + timeStampString.replace(" ", "");

        String uniqueKey = mDatabaseReference.push().getKey();
        storageReference = FirebaseStorage.getInstance().getReference().child("payments/"+uniqueKey).child(Config.STORAGE_PATH + checkoutPicUri.getLastPathSegment());
        StorageTask storageTask = storageReference.putFile(checkoutPicUri);
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
                if (task.isSuccessful()){
                    Uri downloadURi=task.getResult();

                    PaymentModel payment = new PaymentModel(bankProvider, accountName, noRekening, nominalTrans,
                            downloadURi.toString(), productInvestName, senderId, productInvestId, timeStampString, "unPaid", type, paymentId);

                    Map<String, Object> postValues = payment.addProgress();
                    Map<String, Object> childUpdates = new HashMap<>();

                    childUpdates.put("/payments/" + paymentId, postValues);

                    mDatabaseReference.updateChildren(childUpdates);
                    Toasty.success(getApplicationContext(), Config.BOOK_ADD_SUCCESS_MSG, Toasty.LENGTH_SHORT, true).show();
                }
                dialog.dismiss();
                finish();
            }
        });

    }

    private void setEditingEnabled(boolean enabled) {
        if (enabled) {
            checkOutbtn.setVisibility(View.VISIBLE);
        } else {
            checkOutbtn.setVisibility(View.GONE);
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

    private void captureImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, Config.PHOTO_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.PHOTO_REQUEST_CODE && resultCode == RESULT_OK && data!=null){
            checkoutPicUri = data.getData();
            checkoutPic.setImageURI(checkoutPicUri);
        }
    }

}