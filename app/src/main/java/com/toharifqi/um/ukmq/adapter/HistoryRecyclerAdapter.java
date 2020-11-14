package com.toharifqi.um.ukmq.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.toharifqi.um.ukmq.PaymentsDetailActivity;
import com.toharifqi.um.ukmq.ProductActivity;
import com.toharifqi.um.ukmq.R;
import com.toharifqi.um.ukmq.helpers.Config;
import com.toharifqi.um.ukmq.model.PaymentModel;
import com.toharifqi.um.ukmq.model.ProductModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.MyViewHolder> {
    private Context context;
    private List<PaymentModel> paymentModelList;

    public HistoryRecyclerAdapter(Context context, List<PaymentModel> paymentModelList) {
        this.context = context;
        this.paymentModelList = paymentModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final PaymentModel paymentModel = paymentModelList.get(position);

        Query productRef = FirebaseDatabase.getInstance().getReference("products").child(paymentModel.getProductInvestId());

        final ProductModel[] productModel = new ProductModel[1];
        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int productPrice = Integer.valueOf(dataSnapshot.child("productPrice").getValue().toString()) ;
                int productStock = Integer.valueOf(dataSnapshot.child("productStock").getValue().toString()) ;
                String productPic = dataSnapshot.child("productPic").getValue().toString();
                String productPriceString = NumberFormat.getNumberInstance(Locale.GERMAN).format(productPrice);

                String productCode = dataSnapshot.child("productCode").getValue().toString();
                String productName = dataSnapshot.child("productName").getValue().toString();
                String productDesc = dataSnapshot.child("productDesc").getValue().toString();
                String productCat = dataSnapshot.child("productCat").getValue().toString();
                String productCity = dataSnapshot.child("productCity").getValue().toString();
                String productId = dataSnapshot.child("productId").getValue().toString();
                String parentId = dataSnapshot.child("parentId").getValue().toString();


                Glide.with(context).load(productPic).into(holder.productImage);
                holder.productPrice.setText(productPriceString);

                productModel[0] = new ProductModel(productCode, productName, productDesc, productCat, productCity,
                        productId, productPic, productPrice, productStock, parentId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.paymentDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PaymentsDetailActivity.class);

                intent.putExtra("bankProvider", paymentModel.getBankProvider());
                intent.putExtra("pemilikRekening", paymentModel.getAccountName());
                intent.putExtra("nomorRekening", paymentModel.getNoRekening());
                intent.putExtra("nominalTrans", paymentModel.getNominalTransaction());
                intent.putExtra("dateTrans", paymentModel.getTimeStamp());
                intent.putExtra("productName", paymentModel.getProductInvestName());
                intent.putExtra("docUrl", paymentModel.getBuktiUrl());
                intent.putExtra("paymentId", paymentModel.getParentId());

                context.startActivity(intent);
            }
        });


        holder.lookProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do something here to go to product activity
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra(Config.PRODUCT_MODEL, productModel[0]);
                context.startActivity(intent);
            }
        });


        holder.productName.setText(paymentModel.getProductInvestName());
        int nominalInt = Integer.parseInt(paymentModel.getNominalTransaction());
        String nominalString = NumberFormat.getNumberInstance(Locale.GERMAN).format(nominalInt);
        holder.productPaid.setText(nominalString);



    }

    @Override
    public int getItemCount() {
        return paymentModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice, productPaid;
        Button lookProductBtn, paymentDetailBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productPaid = itemView.findViewById(R.id.product_paid);
            lookProductBtn = itemView.findViewById(R.id.look_product);
            paymentDetailBtn = itemView.findViewById(R.id.detail_payment);
        }
    }
}
