package com.toharifqi.um.ukmq;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.toharifqi.um.ukmq.helpers.Config;

public class ProgressDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_detail);

        //fab
        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        TextView progressWriter = findViewById(R.id.progress_writer);
        TextView progressTitle = findViewById(R.id.progress_title);
        TextView progressDate = findViewById(R.id.progress_date);
        TextView progressDesc = findViewById(R.id.progress_desc);
        ImageView docImage = findViewById(R.id.doc_image);

        progressWriter.setText(getIntent().getExtras().getString(Config.PROGRESS_WRITER));
        progressTitle.setText(getIntent().getExtras().getString(Config.PROGRESS_TITLE));
        progressDate.setText(getIntent().getExtras().getString(Config.PROGRESS_DATE));
        progressDesc.setText(getIntent().getExtras().getString(Config.PROGRESS_DESC));
        Glide.with(ProgressDetailActivity.this).load(getIntent().getExtras().getString(Config.PROGRESS_IMAGE)).into(docImage);



    }
}