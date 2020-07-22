package com.toharifqi.um.ukmq.listener;

import com.toharifqi.um.ukmq.model.ProductModel;
import com.toharifqi.um.ukmq.model.ProjectModel;

import java.util.List;

public interface IFirebaseLoadDoneProject {
    void onFirebaseLoadSuccess(List<ProjectModel> projectList);
    void onFirebaseLoadFailed(String message);
}
