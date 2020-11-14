package com.toharifqi.um.ukmq.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.toharifqi.um.ukmq.R;
import com.toharifqi.um.ukmq.adapter.HistoryRecyclerAdapter;
import com.toharifqi.um.ukmq.listener.IFirebaseLoadDonePayment;
import com.toharifqi.um.ukmq.listener.IFirebaseLoadDoneProduct;
import com.toharifqi.um.ukmq.model.PaymentModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class tabUnverifiedFragment extends Fragment implements IFirebaseLoadDonePayment {
    private RecyclerView historyRecyclerView;
    private ImageView emptyImage;
    private HistoryRecyclerAdapter historyRecyclerAdapter;

    IFirebaseLoadDonePayment iFirebaseLoadDone;

    DatabaseReference productDb;

    public tabUnverifiedFragment() {
        // Required empty public constructor
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            List<PaymentModel> paymentModelList = new ArrayList<>();
            if (!dataSnapshot.exists()){
                emptyImage.setVisibility(View.VISIBLE);
            }
            for (DataSnapshot paymentSnapshot:dataSnapshot.getChildren())
                paymentModelList.add(paymentSnapshot.getValue(PaymentModel.class));
            iFirebaseLoadDone.onFirebaseLoadSuccess(paymentModelList);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    Query query;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_unverified, container, false);

        query = FirebaseDatabase.getInstance().getReference("payments").orderByChild("status").equalTo("unPaid");
        emptyImage = view.findViewById(R.id.empty_image);

        iFirebaseLoadDone = this;

        loadPayments();

        historyRecyclerView = view.findViewById(R.id.recyclerview_id);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    private void loadPayments() {
        query.addValueEventListener(valueEventListener);
    }

    @Override
    public void onFirebaseLoadSuccess(List<PaymentModel> paymentModelList) {
        historyRecyclerAdapter = new HistoryRecyclerAdapter(getActivity(), paymentModelList);
        historyRecyclerView.setAdapter(historyRecyclerAdapter);

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