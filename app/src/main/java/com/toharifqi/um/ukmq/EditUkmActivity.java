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
import com.toharifqi.um.ukmq.model.ProductModel;

import es.dmoral.toasty.Toasty;

public class EditUkmActivity extends AppCompatActivity {
    Toolbar mToolbar;

    private static final String TAG = "NewProjectActivity";
    private static final String REQUIRED = "Mohon masukkan data dengan benar.";
    private DatabaseReference mDatabaseReference, userDb;
    private StorageReference storageReference;

    TextInputLayout txtKodePu, txtNamaUsaha, txtNamaPemilik, txtNamaMerk, txtNoTelepon,
    txtPirt, txtBpom, txtHalal, txtSni, txtJalan, txtRt, txtKecamatan, txtKabupaten;

    private ImageView profilPic;
    private Uri profilPicUri;

    Button editProfilBtn;
    public ProgressDialog dialog;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ukm);

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


        txtKodePu = findViewById(R.id.text_kodepu);
        txtNamaUsaha = findViewById(R.id.text_namausaha);
        txtNamaPemilik = findViewById(R.id.text_namapemilik);
        txtNamaMerk = findViewById(R.id.text_namamerk);
        txtNoTelepon = findViewById(R.id.text_notelepon);
        txtPirt = findViewById(R.id.text_pirt);
        txtBpom = findViewById(R.id.text_bpom);
        txtHalal = findViewById(R.id.text_halal);
        txtSni = findViewById(R.id.text_sni);
        txtJalan = findViewById(R.id.text_jalan);
        txtRt = findViewById(R.id.text_rtrw);
        txtKecamatan = findViewById(R.id.text_kecamatan);
        txtKabupaten = findViewById(R.id.text_kabupaten);

        profilPic = findViewById(R.id.profil_image);
        profilPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });

        editProfilBtn = findViewById(R.id.edit_profil);
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
        dialog = ProgressDialog.show(EditUkmActivity.this, "",
                "Mengubah data profil UKM anda. Mohon tunggu ...", true);

        String kodePu = txtKodePu.getEditText().getText().toString();
        String namaUsaha= txtNamaUsaha.getEditText().getText().toString();
        String namaPemilik = txtNamaPemilik.getEditText().getText().toString();
        String namaMerk = txtNamaMerk.getEditText().getText().toString();
        String noTelepon = txtNoTelepon.getEditText().getText().toString();
        String kodePirt = txtPirt.getEditText().getText().toString();
        String kodeBpom = txtBpom.getEditText().getText().toString();
        String kodeHalal = txtHalal.getEditText().getText().toString();
        String kodeSni = txtSni.getEditText().getText().toString();
        String jalan = txtJalan.getEditText().getText().toString();
        String rtRw = txtRt.getEditText().getText().toString();
        String kecamatan = txtKecamatan.getEditText().getText().toString();
        String kabupaten = txtKabupaten.getEditText().getText().toString();

        boolean isNullPhotoUrl = profilPicUri == null;

        if (kodePu.isEmpty()){
            showDialogNoInput();
            txtKodePu.setError(REQUIRED);
        }else if (namaUsaha.isEmpty()){
            showDialogNoInput();
            txtNamaUsaha.setError(REQUIRED);
        }else if (namaPemilik.isEmpty()){
            showDialogNoInput();
            txtNamaPemilik.setError(REQUIRED);
        }else if (namaMerk.isEmpty()){
            showDialogNoInput();
            txtNamaMerk.setError(REQUIRED);
        }else if (noTelepon.isEmpty()){
            showDialogNoInput();
            txtNoTelepon.setError(REQUIRED);
        }else if (kodePirt.isEmpty()){
            showDialogNoInput();
            txtPirt.setError(REQUIRED);
        }else if (kodeBpom.isEmpty()){
            showDialogNoInput();
            txtBpom.setError(REQUIRED);
        }else if (kodeHalal.isEmpty()){
            showDialogNoInput();
            txtHalal.setError(REQUIRED);
        }else if (kodeSni.isEmpty()){
            showDialogNoInput();
            txtSni.setError(REQUIRED);
        }else if (jalan.isEmpty()){
            showDialogNoInput();
            txtJalan.setError(REQUIRED);
        }else if (rtRw.isEmpty()){
            showDialogNoInput();
            txtRt.setError(REQUIRED);
        }else if (kecamatan.isEmpty()){
            showDialogNoInput();
            txtKecamatan.setError(REQUIRED);
        }else if (kabupaten.isEmpty()){
            showDialogNoInput();
            txtKabupaten.setError(REQUIRED);
        }else if (isNullPhotoUrl){
            showDialogNoInput();
            Toasty.error(getApplicationContext(), Config.IMAGE_URL_NULL_MESSAGE, Toasty.LENGTH_SHORT, true).show();
        }else {
            setEditingEnabled(false);
        }

        if (!isNullPhotoUrl){
            writeNewPost(kodePu, namaUsaha, namaPemilik, namaMerk, noTelepon, kodePirt, kodeBpom,
                    kodeHalal, kodeSni, jalan, rtRw, kecamatan, kabupaten, profilPicUri);
            setEditingEnabled(true);
        }
    }

    private void writeNewPost(final String kodePu, final String namaUsaha,
                              final String namaPemilik, final String namaMerk, final String noTelepon,
                              final String kodePirt, final String kodeBpom, final String kodeHalal,
                              final String kodeSni, final String jalan, final String rtRw, final String kecamatan,
                              final String kabupaten, Uri profilPicUri) {

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

                    userDb.child(Config.KODE_PU).setValue(kodePu);
                    userDb.child(Config.NAMA_USAHA).setValue(namaUsaha);
                    userDb.child(Config.NAMA_PEMILIK).setValue(namaPemilik);
                    userDb.child(Config.USER_NAME).setValue(namaPemilik);
                    userDb.child(Config.NAMA_MERK).setValue(namaMerk);
                    userDb.child(Config.NO_TELEPON).setValue(noTelepon);
                    userDb.child(Config.IZIN_PIRT).setValue(kodePirt);
                    userDb.child(Config.IZIN_BPOM).setValue(kodeBpom);
                    userDb.child(Config.IZIN_HALAL).setValue(kodeHalal);
                    userDb.child(Config.IZIN_SNI).setValue(kodeSni);
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