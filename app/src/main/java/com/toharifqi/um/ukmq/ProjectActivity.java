package com.toharifqi.um.ukmq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toharifqi.um.ukmq.helpers.Config;
import com.toharifqi.um.ukmq.model.ProjectModel;

import java.text.NumberFormat;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProjectActivity extends AppCompatActivity {
    ProjectModel projectModel;
    private DatabaseReference userDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        //fab
        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //views
        TextView projectCity = findViewById(R.id.project_city);
        TextView projectDesc = findViewById(R.id.project_desc);
        TextView projectName = findViewById(R.id.project_name);
        TextView projectPrice = findViewById(R.id.project_price);
        TextView projectReturn = findViewById(R.id.project_return);
        TextView projectCorp = findViewById(R.id.project_corp);
        final CircleImageView imageCorp = findViewById(R.id.image_corp);

        projectModel = getIntent().getExtras().getParcelable(Config.PROJECT_MODEL);
        String price = NumberFormat.getNumberInstance(Locale.GERMAN).format(projectModel.getProjectPrice());

        userDb = FirebaseDatabase.getInstance().getReference("users").child(projectModel.getProjectUserId());

        projectCity.setText(projectModel.getProjectCity());
        projectDesc.setText(projectModel.getProjectDesc());
        projectName.setText(projectModel.getProjectName());
        projectPrice.setText("Rp. "+ price);
        projectReturn.setText(projectModel.getProjectReturn() + "%");
        projectCorp.setText(projectModel.getProjectCode());

        userDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String profilPic = dataSnapshot.child("profilPicture").getValue().toString();
                String userType = dataSnapshot.child("tipe_user").getValue().toString();
                if (!profilPic.equals("")){
                    Glide.with(ProjectActivity.this).load(profilPic).into(imageCorp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void toCheckOut(View view){
        Intent intent = new Intent(ProjectActivity.this, CheckInvestActivity.class);
        intent.putExtra(Config.CHECKOUT_NAME, projectModel.getProjectName());
        startActivity(intent);
    }

    public void toProfil(View view){
        Intent intent = new Intent(ProjectActivity.this, ProfilActivity.class);
        intent.putExtra(Config.USER_ID, projectModel.getProjectUserId());
        startActivity(intent);

    }
}