package com.toharifqi.um.ukmq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.toharifqi.um.ukmq.helpers.Config;
import com.toharifqi.um.ukmq.model.ProductModel;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.Locale;

public class ProductActivity extends AppCompatActivity {
    private ProductModel productModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        //get product model from adapter
        productModel = getIntent().getExtras().getParcelable(Config.PRODUCT_MODEL);

        //fab
        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //views
        ImageView productImage = findViewById(R.id.product_image);
        TextView productName = findViewById(R.id.product_name);
        TextView productPrice = findViewById(R.id.product_price);
        TextView productStock = findViewById(R.id.product_stock);
        TextView productCat = findViewById(R.id.product_cat);
        TextView productDesc = findViewById(R.id.product_desc);
        TextView productCity = findViewById(R.id.product_city);

        String price = NumberFormat.getNumberInstance(Locale.GERMAN).format(productModel.getProductPrice());

        Glide.with(ProductActivity.this).load(productModel.getProductPic()).into(productImage);
        productName.setText(productModel.getProductName());
        productPrice.setText("Rp. " + price);
        productStock.setText("Stock: " + productModel.getProductStock() + " pcs");
        productCat.setText("Kategori: " + productModel.getProductCat());
        productDesc.setText(productModel.getProductDesc());
        productCity.setText(productModel.getProductCity());




    }
}