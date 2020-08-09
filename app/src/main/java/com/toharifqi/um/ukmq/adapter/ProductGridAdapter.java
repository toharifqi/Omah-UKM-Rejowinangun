package com.toharifqi.um.ukmq.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.toharifqi.um.ukmq.ProductActivity;
import com.toharifqi.um.ukmq.R;
import com.toharifqi.um.ukmq.helpers.Config;
import com.toharifqi.um.ukmq.model.ProductModel;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductGridAdapter extends RecyclerView.Adapter<ProductGridAdapter.MyViewHolder> {
    private Context context;
    private List<ProductModel> productList;

    public ProductGridAdapter(Context context, List<ProductModel> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductGridAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.item_product, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductGridAdapter.MyViewHolder holder, int position) {

        Glide.with(context).load(productList.get(position).getProductPic()).into(holder.productImage);
        holder.productName.setText(productList.get(position).getProductName());
        String price = NumberFormat.getNumberInstance(Locale.GERMAN).format(productList.get(position).getProductPrice());
        holder.productPrice.setText("Rp. " + price);
        holder.productCity.setText(productList.get(position).getProductCity());
        final ProductModel product = productList.get(position);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do something here to go to product activity
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra(Config.PRODUCT_MODEL, product);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        CardView cardView;
        TextView productName, productPrice, productCity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.product_cardview);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productCity = itemView.findViewById(R.id.product_city);
        }
    }
}
