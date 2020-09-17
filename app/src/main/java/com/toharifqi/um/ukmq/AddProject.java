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
import com.toharifqi.um.ukmq.model.ProjectModel;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import es.dmoral.toasty.Toasty;

public class AddProject extends AppCompatActivity {

    Toolbar mToolbar;

    private static final String TAG = "NewProductActivity";
    private static final String REQUIRED = "Mohon masukkan data dengan benar.";
    private DatabaseReference mDatabaseReference;
    private StorageReference storageReference;
    private FirebaseAuth fAuth;

    private TextInputLayout textProjectName, textProjectPrice, textProjectReturn, textProjectMonth, textProjectDesc;

    private ImageView projectPic;
    private Uri projectPicUri;

    Button addProjectBtn;
    public ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        // Action Bar
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        textProjectName = findViewById(R.id.project_name);
        textProjectPrice = findViewById(R.id.project_price);
        textProjectReturn = findViewById(R.id.project_return);
        textProjectMonth = findViewById(R.id.project_month);
        textProjectDesc = findViewById(R.id.project_desc);

        projectPic = findViewById(R.id.project_image);
        projectPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });

        addProjectBtn = findViewById(R.id.add_project);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);

        addProjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();
    }

    private void setEditingEnabled(boolean enabled) {
        if (enabled) {
            addProjectBtn.setVisibility(View.VISIBLE);
        } else {
            addProjectBtn.setVisibility(View.GONE);
        }
    }

    private void showDialogNoInput() {
        dialog.dismiss();
        new MaterialStyledDialog.Builder(this)
                .setTitle("Oops...")
                .setDescription("Masukkan data project Anda dengan benar. Pastikan tidak ada data yang kosong.")
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

    private void submitPost() {
        dialog = ProgressDialog.show(AddProject.this, "",
                "Menambahkan data project baru. Mohon tunggu ...", true);

        final String projectName = textProjectName.getEditText().getText().toString();
        final int projectPrice = Integer.parseInt(textProjectPrice.getEditText().getText().toString());
        final int projectReturn = Integer.parseInt(textProjectReturn.getEditText().getText().toString());
        final int projectMonth = Integer.parseInt(textProjectMonth.getEditText().getText().toString());
        final String projectDesc = textProjectDesc.getEditText().getText().toString();

        boolean isNullPhotoUrl = projectPicUri == null;

        if (projectName.isEmpty()){
            showDialogNoInput();
            textProjectName.setError(REQUIRED);
        }else if (projectPrice == 0){
            showDialogNoInput();
            textProjectPrice.setError(REQUIRED);
        }else if (projectReturn == 0){
            showDialogNoInput();
            textProjectReturn.setError(REQUIRED);
        }else if (projectMonth == 0){
            showDialogNoInput();
            textProjectMonth.setError(REQUIRED);
        }else if (projectDesc.isEmpty()){
            showDialogNoInput();
            textProjectDesc.setError(REQUIRED);
        }else if(isNullPhotoUrl){
            showDialogNoInput();
            Toasty.error(getApplicationContext(), Config.IMAGE_URL_NULL_MESSAGE, Toasty.LENGTH_SHORT, true).show();
        }else{
            setEditingEnabled(false);
        }

        if (!isNullPhotoUrl){
            writeNewPost(projectName, projectPrice, projectReturn, projectMonth, projectDesc, projectPicUri);
            setEditingEnabled(true);
        }
    }

    private void writeNewPost(final String projectName,
                              final int projectPrice, final int projectReturn, final int projectMonth, final String projectDesc, Uri projectPicUri) {

        Date timeStamp = Calendar.getInstance().getTime();
        final String projectId = timeStamp.toString().replace(" ","");
        final String productIdUser = fAuth.getCurrentUser().getUid();

        final String uniqueKey = mDatabaseReference.push().getKey();
        storageReference = FirebaseStorage.getInstance().getReference().child("project/" + uniqueKey).child(Config.STORAGE_PATH + projectPicUri.getLastPathSegment());
        StorageTask storageTask = storageReference.putFile(projectPicUri);
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
                    ProjectModel project = new ProjectModel(downloadURi.toString(), projectName, Config.userNamaUsaha, city,
                            productIdUser, projectId, projectMonth, projectDesc, projectPrice, projectReturn, 0);

                    Map<String, Object> postValues = project.addProject();
                    Map<String, Object> childUpdates = new HashMap<>();

                    childUpdates.put("/projects/" + projectId, postValues);

                    mDatabaseReference.updateChildren(childUpdates);
                    Toasty.success(getApplicationContext(), Config.PROJECT_ADD_SUCCESS_MSG, Toasty.LENGTH_SHORT, true).show();
                }
                dialog.dismiss();
                // Finish this Activity, back to the stream
                finish();
            }
        });

    }

//    private String generateString() {
//        char[] chars = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890".toCharArray();
//        StringBuilder stringBuilder = new StringBuilder();
//        Random random = new Random();
//        for (int i = 0; i< 5; i++){
//            char c = chars[random.nextInt(chars.length)];
//            stringBuilder.append(c);
//        } return stringBuilder.toString();
//    }

    private void captureImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, Config.PHOTO_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== Config.PHOTO_REQUEST_CODE && resultCode==RESULT_OK && data!=null){
            projectPicUri=data.getData();
            projectPic.setImageURI(projectPicUri);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}