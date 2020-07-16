package com.toharifqi.um.ukmq.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.toharifqi.um.ukmq.R;
import com.toharifqi.um.ukmq.adapter.ProductAdapter;
import com.toharifqi.um.ukmq.listener.IFirebaseLoadDoneProduct;
import com.toharifqi.um.ukmq.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class ProductFragment extends Fragment implements IFirebaseLoadDoneProduct {
    ViewPager viewPagerProduct;
    ProductAdapter productAdapter;

    IFirebaseLoadDoneProduct iFirebaseLoadDone;

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            List<ProductModel> productList = new ArrayList<>();
            for (DataSnapshot productSnapshot:dataSnapshot.getChildren())
                productList.add(productSnapshot.getValue(ProductModel.class));
            iFirebaseLoadDone.onFirebaseLoadSuccess(productList);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            iFirebaseLoadDone.onFirebaseLoadFailed(databaseError.getMessage());
        }
    };

    Query query;
    View view;

    public ProductFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_product, container, false);
        query = FirebaseDatabase.getInstance().getReference("products");

        iFirebaseLoadDone = this;

        loadProduct();

        viewPagerProduct = (ViewPager) view.findViewById(R.id.productViewPager);
        viewPagerProduct.setPadding(20,0,540,0);
        viewPagerProduct.setPageMargin(18);
        viewPagerProduct.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }

    private void loadProduct() {
        query.addValueEventListener(valueEventListener);
    }

    @Override
    public void onFirebaseLoadSuccess(List<ProductModel> productList) {
        productAdapter = new ProductAdapter(productList, getContext());
        viewPagerProduct.setAdapter(productAdapter);

    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        Toast.makeText(getContext(), ""+message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        query.removeEventListener(valueEventListener);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        query.addValueEventListener(valueEventListener);
    }

    @Override
    public void onStop() {
        query.removeEventListener(valueEventListener);
        super.onStop();
    }
}
