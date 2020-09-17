package com.toharifqi.um.ukmq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toharifqi.um.ukmq.adapter.ProgressAdapter;
import com.toharifqi.um.ukmq.helpers.Config;
import com.toharifqi.um.ukmq.listener.IFirebaseLoadDoneProgress;
import com.toharifqi.um.ukmq.model.ProgressModel;

import java.util.ArrayList;
import java.util.List;

public class ProgressActivity extends AppCompatActivity implements IFirebaseLoadDoneProgress {
    private RecyclerView progressDetailRecyclerView;
    LinearLayout shimmerLayout;

    IFirebaseLoadDoneProgress iFirebaseLoadDone;

    Toolbar mToolbar;
    DatabaseReference progressDb;

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            List<ProgressModel> progressModelList = new ArrayList<>();
            for (DataSnapshot progressSnapshot : dataSnapshot.getChildren())
                progressModelList.add(progressSnapshot.getValue(ProgressModel.class));
            iFirebaseLoadDone.onFirebaseLoadSuccess(progressModelList);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            iFirebaseLoadDone.onFirebaseLoadFailed(databaseError.getMessage());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        TextView progressName = findViewById(R.id.progress_name);
        progressName.setText("Nama Project: "+getIntent().getStringExtra(Config.PROJECT_NAME));

        // Action Bar
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);

        progressDb = FirebaseDatabase.getInstance().getReference("progress");

        iFirebaseLoadDone = this;

        loadProgress();
        shimmerLayout = findViewById(R.id.shimmer_progress);

        progressDetailRecyclerView = findViewById(R.id.progress_recycler);
        progressDetailRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadProgress() {
        progressDb.addValueEventListener(valueEventListener);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onFirebaseLoadSuccess(List<ProgressModel> progressModelList) {
        ProgressAdapter progressAdapter = new ProgressAdapter(ProgressActivity.this, progressModelList);
        progressDetailRecyclerView.setAdapter(progressAdapter);
        shimmerLayout.setVisibility(View.GONE);
        progressDetailRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        progressDb.removeEventListener(valueEventListener);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        progressDb.addValueEventListener(valueEventListener);
    }

    @Override
    public void onStop() {
        progressDb.removeEventListener(valueEventListener);
        super.onStop();
    }
}