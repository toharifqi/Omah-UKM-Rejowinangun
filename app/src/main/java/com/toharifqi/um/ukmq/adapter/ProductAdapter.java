package com.toharifqi.um.ukmq.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.toharifqi.um.ukmq.ProductActivity;
import com.toharifqi.um.ukmq.R;
import com.toharifqi.um.ukmq.helpers.Config;
import com.toharifqi.um.ukmq.model.ProductModel;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends PagerAdapter {
    private List<ProductModel> productList;
    private LayoutInflater layoutInflater;
    private Context context;

    public ProductAdapter(Context context) {
        this.context = context;
    }

    public ProductAdapter(List<ProductModel> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater =  LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_product, container, false);

        ImageView productImage;
        TextView productName, productPrice, productCity;

        productImage = view.findViewById(R.id.product_image);
        productName = view.findViewById(R.id.product_name);
        productPrice = view.findViewById(R.id.product_price);
        productCity = view.findViewById(R.id.product_city);

        Glide.with(context).load(productList.get(position).getProductPic()).into(productImage);
        productName.setText(productList.get(position).getProductName());
        String price = NumberFormat.getNumberInstance(Locale.GERMAN).format(productList.get(position).getProductPrice());
        productPrice.setText("Rp. " + price);
        productCity.setText(productList.get(position).getProductCity());

        final ProductModel product = productList.get(position);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //do something here to go to product activity
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra(Config.PRODUCT_MODEL, product);
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
