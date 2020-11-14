package com.toharifqi.um.ukmq.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toharifqi.um.ukmq.AddReport;
import com.toharifqi.um.ukmq.ProgressActivity;
import com.toharifqi.um.ukmq.R;
import com.toharifqi.um.ukmq.helpers.Config;
import com.toharifqi.um.ukmq.model.ProjectModel;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class OngoingProjectAdapter extends RecyclerView.Adapter<OngoingProjectAdapter.MyViewHolder> {
    private Context context;
    private List<ProjectModel> projectModelList;
    private DatabaseReference userDb;
    private FirebaseUser userRef;

    public OngoingProjectAdapter(Context context, List<ProjectModel> projectModelList) {
        this.context = context;
        this.projectModelList = projectModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.item_ongoingproject, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final ProjectModel projectModel = projectModelList.get(position);
        userDb = FirebaseDatabase.getInstance().getReference("users").child(projectModel.getProjectUserId());
        userRef = FirebaseAuth.getInstance().getCurrentUser();
        if (userRef == null){
            holder.uploadProgressBtn.setVisibility(View.GONE);
        }

        userDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String profilPic = dataSnapshot.child("profilPicture").getValue().toString();
                String profilOwner = dataSnapshot.child("namaUsaha").getValue().toString();
                if (!profilPic.equals("")){
                    Glide.with(context).load(profilPic).into(holder.projectOwnerPic);
                }
                holder.projectOwner.setText(profilOwner);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Glide.with(context).load(projectModel.getProjectPic()).into(holder.projectPic);
        holder.projectName.setText(projectModel.getProjectName());
        String price = NumberFormat.getNumberInstance(Locale.GERMAN).format(projectModel.getProjectPrice());
        String invested = NumberFormat.getNumberInstance(Locale.GERMAN).format(projectModel.getProjectInvested());
        holder.projectPrice.setText("Rp. " + price);
        holder.projectInvested.setText("Rp. " + invested);
        Double projectProgressDouble = ((double) projectModel.getProjectInvested()/(double) projectModel.getProjectPrice())*100;
        final int projectProgress = projectProgressDouble.intValue();
        final double projectInvestedperMonth = (double) projectModel.getProjectPrice()/projectModel.getProjectMonth();
        holder.projectProgress.setText(projectProgress + "%");
        holder.progressBar.setProgress(projectProgress);

        holder.uploadProgressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddReport.class);
                Bundle bundle = new Bundle();
                bundle.putDouble(Config.PROJECT_INVESTED_PER_MONTH, projectInvestedperMonth);
                bundle.putString(Config.PROJECT_INVESTED, projectModel.getProjectInvested() + "");
                bundle.putString(Config.PROJECT_NAME, projectModel.getProjectName());
                bundle.putString(Config.USER_NAME, Config.userNamaPemilik);
                bundle.putString(Config.PROJECT_ID, projectModel.getProjectParentId());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        holder.detailProgressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProgressActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Config.PROJECT_NAME, projectModel.getProjectName());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return projectModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView projectOwner, projectName, projectPrice, projectInvested, projectProgress;
        ImageView projectOwnerPic, projectPic;
        ProgressBar progressBar;
        Button uploadProgressBtn, detailProgressBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            projectOwner = itemView.findViewById(R.id.project_corp);
            projectOwnerPic = itemView.findViewById(R.id.project_ownerpic);
            projectPic = itemView.findViewById(R.id.project_image);
            projectName = itemView.findViewById(R.id.project_name);
            projectInvested = itemView.findViewById(R.id.project_invested);
            projectPrice = itemView.findViewById(R.id.project_price2);
            projectProgress = itemView.findViewById(R.id.project_progress);
            progressBar = itemView.findViewById(R.id.project_progressbar);
            uploadProgressBtn = itemView.findViewById(R.id.upload_progress_btn);
            detailProgressBtn = itemView.findViewById(R.id.detail_progress_btn);
        }
    }
}
