package com.toharifqi.um.ukmq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toharifqi.um.ukmq.adapter.OngoingProjectAdapter;
import com.toharifqi.um.ukmq.listener.IFirebaseLoadDoneProject;
import com.toharifqi.um.ukmq.model.ProjectModel;

import java.util.ArrayList;
import java.util.List;

public class OngoingProjectActivity extends AppCompatActivity implements IFirebaseLoadDoneProject {
    private RecyclerView ongoingRecyclerView;
    LinearLayout shimmerLayout;

    IFirebaseLoadDoneProject iFirebaseLoadDone;

    Toolbar mToolbar;
    DatabaseReference projectDb;

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            List<ProjectModel> projectModelList = new ArrayList<>();
            for (DataSnapshot projectSnapshot: dataSnapshot.getChildren())
                projectModelList.add(projectSnapshot.getValue(ProjectModel.class));
            iFirebaseLoadDone.onFirebaseLoadSuccess(projectModelList);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            iFirebaseLoadDone.onFirebaseLoadFailed(databaseError.getMessage());
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ongoing_project);

        // Action Bar
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);

        projectDb = FirebaseDatabase.getInstance().getReference("projects");

        iFirebaseLoadDone = this;

        loadProject();
        shimmerLayout = findViewById(R.id.shimmer_ongoing);

        ongoingRecyclerView = findViewById(R.id.ongoing_recycler);
        ongoingRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void loadProject() {
        projectDb.addValueEventListener(valueEventListener);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onFirebaseLoadSuccess(List<ProjectModel> projectList) {
        OngoingProjectAdapter ongoingProjectAdapter = new OngoingProjectAdapter(OngoingProjectActivity.this, projectList);
        ongoingRecyclerView.setAdapter(ongoingProjectAdapter);
        shimmerLayout.setVisibility(View.GONE);
        ongoingRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

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