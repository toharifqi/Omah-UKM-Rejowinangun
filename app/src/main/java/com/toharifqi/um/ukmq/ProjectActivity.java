package com.toharifqi.um.ukmq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.toharifqi.um.ukmq.helpers.Config;
import com.toharifqi.um.ukmq.model.ProjectModel;

import java.text.NumberFormat;
import java.util.Locale;

public class ProjectActivity extends AppCompatActivity {
    ProjectModel projectModel;

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

        projectModel = getIntent().getExtras().getParcelable(Config.PROJECT_MODEL);
        String price = NumberFormat.getNumberInstance(Locale.GERMAN).format(projectModel.getProjectPrice());

        projectCity.setText(projectModel.getProjectCity());
        projectDesc.setText(projectModel.getProjectDesc());
        projectName.setText(projectModel.getProjectName());
        projectPrice.setText("Rp. "+ price);
        projectReturn.setText(projectModel.getProjectReturn() + "%");

    }

    public void toCheckOut(View view){
        Intent intent = new Intent(ProjectActivity.this, CheckInvestActivity.class);
        intent.putExtra(Config.CHECKOUT_NAME, projectModel.getProjectName());
        startActivity(intent);
    }
}