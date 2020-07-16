package com.toharifqi.um.ukmq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.toharifqi.um.ukmq.R;
import com.toharifqi.um.ukmq.model.ProductModel;

import java.util.List;

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
        View view = layoutInflater.inflate(R.layout.)

        return super.instantiateItem(container, position);
    }
}
