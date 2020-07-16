package com.toharifqi.um.ukmq.listener;

import com.denzcoskun.imageslider.models.SlideModel;

import java.util.List;

public interface IFirebaseLoadDoneProduct {
    void onFirebaseLoadSuccess(List<SlideModel> imageList);
    void onFirebaseLoadFailed(String message);
}
