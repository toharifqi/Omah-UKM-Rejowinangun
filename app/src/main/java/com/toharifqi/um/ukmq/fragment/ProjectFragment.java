package com.toharifqi.um.ukmq.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.toharifqi.um.ukmq.R;
import com.toharifqi.um.ukmq.adapter.ProjectAdapter;
import com.toharifqi.um.ukmq.listener.IFirebaseLoadDoneProject;
import com.toharifqi.um.ukmq.model.ProjectModel;

import java.util.ArrayList;
import java.util.List;

public class ProjectFragment extends Fragment implements IFirebaseLoadDoneProject {
    ViewPager viewPagerProject;
    ProjectAdapter projectAdapter;

    IFirebaseLoadDoneProject iFirebaseLoadDone;

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            List<ProjectModel> projectList = new ArrayList<>();
            for (DataSnapshot productSnapshot:dataSnapshot.getChildren())
                projectList.add(productSnapshot.getValue(ProjectModel.class));
            iFirebaseLoadDone.onFirebaseLoadSuccess(projectList);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            iFirebaseLoadDone.onFirebaseLoadFailed(databaseError.getMessage());
        }
    };

    Query query;
    View view;

    public ProjectFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_project, container, false);
        query = FirebaseDatabase.getInstance().getReference("projects");

        iFirebaseLoadDone = this;

        loadProject();

        viewPagerProject = (ViewPager) view.findViewById(R.id.projectViewPager);
        viewPagerProject.setPadding(20,0,200,0);
        viewPagerProject.setPageMargin(18);
        viewPagerProject.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }

    private void loadProject() {
        query.addValueEventListener(valueEventListener);
    }

    @Override
    public void onFirebaseLoadSuccess(List<ProjectModel> projectList) {
        projectAdapter = new ProjectAdapter(projectList, getContext());
        viewPagerProject.setAdapter(projectAdapter);
    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        Toast.makeText(getContext(), ""+message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        query.removeEventListener(valueEventListener);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        query.addValueEventListener(valueEventListener);
    }

    @Override
    public void onStop() {
        query.removeEventListener(valueEventListener);
        super.onStop();
    }
}
