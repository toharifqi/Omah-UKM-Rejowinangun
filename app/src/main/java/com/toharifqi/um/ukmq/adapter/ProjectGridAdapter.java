package com.toharifqi.um.ukmq.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.toharifqi.um.ukmq.ProjectActivity;
import com.toharifqi.um.ukmq.R;
import com.toharifqi.um.ukmq.helpers.Config;
import com.toharifqi.um.ukmq.model.ProjectModel;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProjectGridAdapter extends RecyclerView.Adapter<ProjectGridAdapter.MyViewHolder> {
    private Context context;
    private List<ProjectModel> projectList;

    public ProjectGridAdapter(Context context, List<ProjectModel> projectList) {
        this.context = context;
        this.projectList = projectList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.item_project, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final ProjectModel projectModel = projectList.get(position);

        Glide.with(context).load(projectModel.getProjectPic()).into(holder.projectImage);
        holder.projectName.setText(projectModel.getProjectName());
        holder.projectOwner.setText(projectModel.getProjectCode());
        String price = NumberFormat.getNumberInstance(Locale.GERMAN).format(projectModel.getProjectPrice());
        holder.projectPrice.setText(price);
        holder.projectCity.setText(projectModel.getProjectCity());
        holder.projectIcon.setVisibility(View.GONE);

        holder.investButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something here dude
                Intent intent = new Intent(context, ProjectActivity.class);
                intent.putExtra(Config.PROJECT_MODEL, projectModel);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView projectName, projectCity, projectPrice, projectOwner;
        Button investButton;
        ImageView projectImage, projectIcon;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            projectIcon = itemView.findViewById(R.id.project_icon);
            projectName = itemView.findViewById(R.id.project_name);
            projectCity = itemView.findViewById(R.id.project_city);
            projectPrice = itemView.findViewById(R.id.project_price);
            projectOwner = itemView.findViewById(R.id.project_owner);
            investButton = itemView.findViewById(R.id.invest_button);
            projectImage = itemView.findViewById(R.id.project_image);
        }
    }
}
