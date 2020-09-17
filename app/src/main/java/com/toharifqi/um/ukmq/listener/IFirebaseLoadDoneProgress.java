package com.toharifqi.um.ukmq.listener;

import com.toharifqi.um.ukmq.model.ProgressModel;

import java.util.List;

public interface IFirebaseLoadDoneProgress {
    void onFirebaseLoadSuccess(List<ProgressModel> progressModelList);
    void onFirebaseLoadFailed(String message);
}
