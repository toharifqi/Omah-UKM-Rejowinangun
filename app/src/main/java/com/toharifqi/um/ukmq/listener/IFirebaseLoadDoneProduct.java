package com.toharifqi.um.ukmq.listener;

import com.denzcoskun.imageslider.models.SlideModel;
import com.toharifqi.um.ukmq.model.ProductModel;

import java.util.List;

public interface IFirebaseLoadDoneProduct {
    void onFirebaseLoadSuccess(List<ProductModel> productList);
    void onFirebaseLoadFailed(String message);
}
