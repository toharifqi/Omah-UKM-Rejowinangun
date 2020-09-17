package com.toharifqi.um.ukmq.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.toharifqi.um.ukmq.AllProductActivity;
import com.toharifqi.um.ukmq.R;
import com.toharifqi.um.ukmq.adapter.ProductGridAdapter;
import com.toharifqi.um.ukmq.helpers.Config;
import com.toharifqi.um.ukmq.listener.IFirebaseLoadDoneProduct;
import com.toharifqi.um.ukmq.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class tabProduct extends Fragment implements IFirebaseLoadDoneProduct {
    private RecyclerView productRecyclerView;
    private ProductGridAdapter productGridAdapter;
    private ImageView emptyImage;

    IFirebaseLoadDoneProduct iFirebaseLoadDone;

    DatabaseReference productDb;
    FirebaseUser fUser;

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            List<ProductModel> productList = new ArrayList<>();
            if (!dataSnapshot.exists()){
                emptyImage.setVisibility(View.VISIBLE);
            }
            for (DataSnapshot productSnapshot:dataSnapshot.getChildren())
                productList.add(productSnapshot.getValue(ProductModel.class));
            iFirebaseLoadDone.onFirebaseLoadSuccess(productList);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            iFirebaseLoadDone.onFirebaseLoadFailed(databaseError.getMessage());
        }
    };

    public tabProduct() {
        // Required empty public constructor
    }

    Query query;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_product, container, false);

        productDb = FirebaseDatabase.getInstance().getReference("products");

        fUser = FirebaseAuth.getInstance().getCurrentUser();

        //data from product or project activity
        Bundle intent = getActivity().getIntent().getExtras();
        assert intent != null;
        String uId = null;

        if (getActivity().getIntent().getExtras() ==  null){
            uId = fUser.getUid();
        }else if (getActivity().getIntent().getExtras() != null){
            uId = intent.getString(Config.USER_ID);
        }

        query = productDb.orderByChild("productId").equalTo(uId);
        emptyImage = view.findViewById(R.id.empty_image);

        iFirebaseLoadDone = this;


        loadProduct();

        productRecyclerView = view.findViewById(R.id.recyclerview_id);
        productRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        return view;
    }

    private void loadProduct() {
        query.addValueEventListener(valueEventListener);
    }

    @Override
    public void onFirebaseLoadSuccess(List<ProductModel> productList) {
        productGridAdapter = new ProductGridAdapter(getActivity(), productList);
        productRecyclerView.setAdapter(productGridAdapter);
    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
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