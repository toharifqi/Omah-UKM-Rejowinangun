package com.toharifqi.um.ukmq;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zolad.zoominimageview.ZoomInImageViewAttacher;

public class ZoomDocActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_doc);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra("imageUrl");

        // Action Bar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);

        ImageView imageView = findViewById(R.id.progress_imageview);

        Glide.with(this).load(imageUrl).into(imageView);

        ZoomInImageViewAttacher imageViewAttacher = new ZoomInImageViewAttacher();
        imageViewAttacher.attachImageView(imageView);
        imageViewAttacher.setZoomable(true);


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}