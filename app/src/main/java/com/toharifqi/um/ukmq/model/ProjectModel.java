package com.toharifqi.um.ukmq.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class ProjectModel implements Parcelable {
    String projectPic, projectName, projectCode, projectCity, projectId, projectDesc;
    int projectPrice, projectReturn;

    public ProjectModel() {
    }

    public ProjectModel(String projectPic, String projectName, String projectCode, String projectCity, String projectId, String projectDesc, int projectPrice, int projectReturn) {
        this.projectPic = projectPic;
        this.projectName = projectName;
        this.projectCode = projectCode;
        this.projectCity = projectCity;
        this.projectId = projectId;
        this.projectDesc = projectDesc;
        this.projectPrice = projectPrice;
        this.projectReturn = projectReturn;
    }

    protected ProjectModel(Parcel in) {
        projectPic = in.readString();
        projectName = in.readString();
        projectCode = in.readString();
        projectCity = in.readString();
        projectId = in.readString();
        projectDesc = in.readString();
        projectPrice = in.readInt();
        projectReturn = in.readInt();
    }

    public static final Creator<ProjectModel> CREATOR = new Creator<ProjectModel>() {
        @Override
        public ProjectModel createFromParcel(Parcel in) {
            return new ProjectModel(in);
        }

        @Override
        public ProjectModel[] newArray(int size) {
            return new ProjectModel[size];
        }
    };

    public String getProjectPic() {
        return projectPic;
    }

    public void setProjectPic(String projectPic) {
        this.projectPic = projectPic;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectCity() {
        return projectCity;
    }

    public void setProjectCity(String projectCity) {
        this.projectCity = projectCity;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    public int getProjectPrice() {
        return projectPrice;
    }

    public void setProjectPrice(int projectPrice) {
        this.projectPrice = projectPrice;
    }

    public int getProjectReturn() {
        return projectReturn;
    }

    public void setProjectReturn(int projectReturn) {
        this.projectReturn = projectReturn;
    }

    public Map<String, Object> addProject() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("projectPic", projectPic);
        result.put("projectName", projectName);
        result.put("projectCode", projectCode);
        result.put("projectCity", projectCity);
        result.put("projectId", projectId);
        result.put("projectDesc", projectDesc);
        result.put("projectPrice", projectPrice);
        result.put("projectReturn", projectReturn);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(projectPic);
        dest.writeString(projectName);
        dest.writeString(projectCode);
        dest.writeString(projectCity);
        dest.writeString(projectId);
        dest.writeString(projectDesc);
        dest.writeInt(projectPrice);
        dest.writeInt(projectReturn);
    }
}
