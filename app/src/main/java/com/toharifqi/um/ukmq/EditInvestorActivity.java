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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.toharifqi.um.ukmq.helpers.Config;

import es.dmoral.toasty.Toasty;

public class EditInvestorActivity extends AppCompatActivity {
    Toolbar mToolbar;

    private static final String TAG = "NewProjectActivity";
    private static final String REQUIRED = "Mohon masukkan data dengan benar.";
    private DatabaseReference mDatabaseReference, userDb;
    private StorageReference storageReference;

    TextInputLayout txtNamaLengkap, txtNoTelepon, txtJalan, txtRtRw, txtKecamatan,
    txtKabupaten;

    private ImageView profilPic;
    private Uri profilPicUri;

    Button editProfilBtn;
    public ProgressDialog dialog;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_investor);

        // Action Bar
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();
        userDb = FirebaseDatabase.getInstance().getReference("users").child(fAuth.getCurrentUser().getUid());

        txtNamaLengkap = findViewById(R.id.text_namalengkap);
        txtNoTelepon = findViewById(R.id.text_teleponinvest);
        txtJalan = findViewById(R.id.text_jalan);
        txtRtRw = findViewById(R.id.text_rtrw);
        txtKecamatan = findViewById(R.id.text_kecamatan);
        txtKabupaten = findViewById(R.id.text_kabupaten);
        
        profilPic = findViewById(R.id.profil_image);
        profilPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });
        
        editProfilBtn = findViewById(R.id.edit_profil_btn);
        editProfilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        
    }

    private void setEditingEnabled(boolean enabled) {
        if (enabled) {
            editProfilBtn.setVisibility(View.VISIBLE);
        } else {
            editProfilBtn.setVisibility(View.GONE);
        }
    }

    private void submitPost() {
        dialog = ProgressDialog.show(EditInvestorActivity.this, "",
                "Mengubah data profil Investor anda. Mohon tunggu ...", true);

        String namaLengkap = txtNamaLengkap.getEditText().getText().toString();
        String noTelepon = txtNoTelepon.getEditText().getText().toString();
        String jalan = txtJalan.getEditText().getText().toString();
        String rtRw = txtRtRw.getEditText().getText().toString();
        String kecamatan = txtKecamatan.getEditText().getText().toString();
        String kabupaten = txtKabupaten.getEditText().getText().toString();

        boolean isNullPhotoUrl = profilPicUri == null;

        if (namaLengkap.isEmpty()){
            showDialogNoInput();
            txtNamaLengkap.setError(REQUIRED);
        }else if (noTelepon.isEmpty()){
            showDialogNoInput();
            txtNoTelepon.setError(REQUIRED);
        }else if (jalan.isEmpty()){
            showDialogNoInput();
            txtJalan.setError(REQUIRED);
        }else if (rtRw.isEmpty()){
            showDialogNoInput();
            txtRtRw.setError(REQUIRED);
        }else if (kecamatan.isEmpty()){
            showDialogNoInput();
            txtKecamatan.setError(REQUIRED);
        }else if (kabupaten.isEmpty()){
            showDialogNoInput();
            txtKabupaten.setError(REQUIRED);
        }else if (isNullPhotoUrl){
            showDialogNoInput();
            txtNoTelepon.setError(REQUIRED);
            Toasty.error(getApplicationContext(), Config.IMAGE_URL_NULL_MESSAGE, Toasty.LENGTH_SHORT, true).show();
        }else {
            setEditingEnabled(false);
        }

        if (!isNullPhotoUrl){
            writeNewPost(namaLengkap, noTelepon, jalan, rtRw, kecamatan, kabupaten, profilPicUri);
            setEditingEnabled(true);
        }
    }

    private void writeNewPost(final String namaLengkap, final String noTelepon, final String jalan,
                              final String rtRw, final String kecamatan, final String kabupaten,
                              Uri profilPicUri) {
        storageReference = FirebaseStorage.getInstance().getReference().child("profil/"+fAuth.getCurrentUser().getUid()).child(Config.STORAGE_PATH + profilPicUri.getLastPathSegment());
        StorageTask storageTask = storageReference.putFile(profilPicUri);
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

                    userDb.child(Config.USER_NAME).setValue(namaLengkap);
                    userDb.child(Config.NAMA_PEMILIK).setValue(namaLengkap);
                    userDb.child(Config.NO_TELEPON).setValue(noTelepon);
                    userDb.child(Config.JALAN).setValue(jalan);
                    userDb.child(Config.RT_RW).setValue(rtRw);
                    userDb.child(Config.KECAMATAN).setValue(kecamatan);
                    userDb.child(Config.KABUPATEN).setValue(kabupaten);
                    userDb.child(Config.PROFIL_PIC).setValue(downloadURi.toString());
                    Toasty.success(getApplicationContext(), Config.PROFIL_EDIT_SUCCESS_MSG, Toasty.LENGTH_SHORT, true).show();

                }
                dialog.dismiss();
                // Finish this Activity, back to the stream
                finish();
            }
        });
    }

    private void captureImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, Config.PHOTO_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== Config.PHOTO_REQUEST_CODE && resultCode==RESULT_OK && data!=null){
            profilPicUri=data.getData();
            profilPic.setImageURI(profilPicUri);
        }
    }

    private void showDialogNoInput() {
        dialog.dismiss();
        new MaterialStyledDialog.Builder(this)
                .setTitle("Oops...")
                .setDescription("Masukkan data Anda dengan benar. Pastikan tidak ada data yang kosong.")
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