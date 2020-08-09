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
import com.toharifqi.um.ukmq.adapter.ProjectGridAdapter;
import com.toharifqi.um.ukmq.listener.IFirebaseLoadDoneProject;
import com.toharifqi.um.ukmq.model.ProjectModel;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class AllProjectActivity extends AppCompatActivity implements IFirebaseLoadDoneProject {
    private RecyclerView projectRecyclerView;
    private ProjectGridAdapter projectGridAdapter;

    AlertDialog dialog;

    IFirebaseLoadDoneProject iFirebaseLoadDone;

    Toolbar mToolbar;

    DatabaseReference projectDb;

    ValueEventListener  valueEventListener =  new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            List<ProjectModel> projectList = new ArrayList<>();
            for (DataSnapshot projectSnapshot:dataSnapshot.getChildren())
                projectList.add(projectSnapshot.getValue(ProjectModel.class));
            iFirebaseLoadDone.onFirebaseLoadSuccess(projectList);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            iFirebaseLoadDone.onFirebaseLoadFailed(databaseError.getMessage());
        }
    };
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_project);

        // Action Bar
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);

        projectDb = FirebaseDatabase.getInstance().getReference("projects");

        iFirebaseLoadDone = this;

        //to display loading dialog
        dialog = new SpotsDialog.Builder().setContext(AllProjectActivity.this).build();
        dialog.setMessage("Mohon tunggu...");

        loadProject();

        projectRecyclerView = findViewById(R.id.recyclerview_id);
        projectRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

    }

    private void loadProject() {
        dialog.show();
        projectDb.addValueEventListener(valueEventListener);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onFirebaseLoadSuccess(List<ProjectModel> projectList) {
        projectGridAdapter = new ProjectGridAdapter(AllProjectActivity.this, projectList);
        projectRecyclerView.setAdapter(projectGridAdapter);
        dialog.dismiss();
    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    @Override
    public void onDestroy() {
        projectDb.removeEventListener(valueEventListener);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        projectDb.addValueEventListener(valueEventListener);
    }

    @Override
    public void onStop() {
        projectDb.removeEventListener(valueEventListener);
        super.onStop();
    }
}