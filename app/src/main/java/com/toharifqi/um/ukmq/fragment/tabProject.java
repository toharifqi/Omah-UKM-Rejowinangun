package com.toharifqi.um.ukmq.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.toharifqi.um.ukmq.R;
import com.toharifqi.um.ukmq.adapter.ProjectGridAdapter;
import com.toharifqi.um.ukmq.listener.IFirebaseLoadDoneProject;
import com.toharifqi.um.ukmq.model.ProductModel;
import com.toharifqi.um.ukmq.model.ProjectModel;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class tabProject extends Fragment implements IFirebaseLoadDoneProject{
    private RecyclerView projectRecyclerView;
    private ProjectGridAdapter projectGridAdapter;
    private ImageView emptyImage;

    IFirebaseLoadDoneProject iFirebaseLoadDone;

    DatabaseReference projectDb;
    FirebaseUser fUser;

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            List<ProjectModel> projectList = new ArrayList<>();
            if (!dataSnapshot.exists()){
                emptyImage.setVisibility(View.VISIBLE);
            }
            for (DataSnapshot productSnapshot:dataSnapshot.getChildren())
                projectList.add(productSnapshot.getValue(ProjectModel.class));
            iFirebaseLoadDone.onFirebaseLoadSuccess(projectList);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            iFirebaseLoadDone.onFirebaseLoadFailed(databaseError.getMessage());
        }
    };

    public tabProject() {
        // Required empty public constructor
    }

    Query query;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_project, container, false);

        projectDb = FirebaseDatabase.getInstance().getReference("projects");
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        query = projectDb.orderByChild("projectId").equalTo(fUser.getUid());
        emptyImage = view.findViewById(R.id.empty_image);

        iFirebaseLoadDone = this;


        loadProject();

        projectRecyclerView = view.findViewById(R.id.recyclerview_id);
        projectRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        return view;
    }

    private void loadProject() {
        query.addValueEventListener(valueEventListener);
    }

    @Override
    public void onFirebaseLoadSuccess(List<ProjectModel> projectList) {
        projectGridAdapter = new ProjectGridAdapter(getActivity(), projectList);
        projectRecyclerView.setAdapter(projectGridAdapter);
    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
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