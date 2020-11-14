package com.toharifqi.um.ukmq.listener;

import com.toharifqi.um.ukmq.model.PaymentModel;

import java.util.List;

public interface IFirebaseLoadDonePayment {
    void onFirebaseLoadSuccess(List<PaymentModel> paymentModelList);
    void onFirebaseLoadFailed(String message);
}
