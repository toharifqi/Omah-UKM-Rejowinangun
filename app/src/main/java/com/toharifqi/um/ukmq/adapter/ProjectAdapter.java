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
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.toharifqi.um.ukmq.ProjectActivity;
import com.toharifqi.um.ukmq.R;
import com.toharifqi.um.ukmq.helpers.Config;
import com.toharifqi.um.ukmq.model.ProjectModel;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProjectAdapter extends PagerAdapter {
    private List<ProjectModel> projectList;
    private LayoutInflater layoutInflater;
    private Context context;

    public ProjectAdapter(Context context) {
        this.context = context;
    }

    public ProjectAdapter(List<ProjectModel> projectList, Context context) {
        this.projectList = projectList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return projectList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_project, container, false);

        ImageView projectImage;
        Button investButton;
        TextView projectName, projectCity, projectCorp, projectPrice;

        projectImage = view.findViewById(R.id.project_image);
        projectName = view.findViewById(R.id.project_name);
        projectCity = view.findViewById(R.id.project_city);
        projectCorp = view.findViewById(R.id.project_owner);
        projectPrice = view.findViewById(R.id.project_price);
        investButton = view.findViewById(R.id.invest_button);

        Glide.with(context).load(projectList.get(position).getProjectPic()).into(projectImage);
        projectName.setText(projectList.get(position).getProjectName());
        projectCity.setText(projectList.get(position).getProjectCity());
        projectCorp.setText(projectList.get(position).getProjectCode());
        String price = NumberFormat.getNumberInstance(Locale.GERMAN).format(projectList.get(position).getProjectPrice());
        projectPrice.setText("Rp. " + price);

        final ProjectModel project = projectList.get(position);

        investButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something here dude
                Intent intent = new Intent(context, ProjectActivity.class);
                intent.putExtra(Config.PROJECT_MODEL, project);
                context.startActivity(intent);
            }
        });

        container.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
