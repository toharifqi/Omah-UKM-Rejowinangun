package com.toharifqi.um.ukmq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.NumberFormat;
import java.util.Locale;

public class PaymentsDetailActivity extends AppCompatActivity {

    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments_detail);

        //fab
        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        TextView bankProvider = findViewById(R.id.bank_provider);
        TextView pemilikRekening = findViewById(R.id.pemilik_rekening);
        TextView nomorRekening = findViewById(R.id.nomor_rekening);
        TextView nominalTrans = findViewById(R.id.nominal_transaction);
        TextView dateTrans = findViewById(R.id.date_transaction);
        TextView nameProduct = findViewById(R.id.product_name);
        ImageView docPic = findViewById(R.id.doc_image);

        imageUrl = getIntent().getStringExtra("docUrl");
        String nominalString = getIntent().getStringExtra("nominalTrans");
        int nominalInt = Integer.parseInt(nominalString);
        String nominalTransString = NumberFormat.getNumberInstance(Locale.GERMAN).format(nominalInt);


        bankProvider.setText(getIntent().getStringExtra("bankProvider"));
        pemilikRekening.setText(getIntent().getStringExtra("pemilikRekening"));
        nomorRekening.setText(getIntent().getStringExtra("nomorRekening"));
        nominalTrans.setText("Rp. " + nominalTransString);
        dateTrans.setText(getIntent().getStringExtra("dateTrans"));
        nameProduct.setText(getIntent().getStringExtra("productName"));

        Glide.with(PaymentsDetailActivity.this).load(imageUrl).into(docPic);


    }

    public void toZoomDoc(View view){
        Intent intent = new Intent(PaymentsDetailActivity.this, ZoomDocActivity.class);
        intent.putExtra("imageUrl", imageUrl);
        startActivity(intent);
    }
}