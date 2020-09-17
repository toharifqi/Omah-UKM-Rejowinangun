package com.toharifqi.um.ukmq.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class ProjectModel implements Parcelable {
    String projectPic, projectName, projectCode, projectCity, projectUserId, projectDesc, projectParentId;
    int projectPrice, projectReturn, projectMonth, projectInvested;

    public ProjectModel() {
    }

    public ProjectModel(String projectPic, String projectName, String projectCode, String projectCity, String projectId, String projectParentId, int projectMonth, String projectDesc, int projectPrice, int projectReturn, int projectInvested) {
        this.projectPic = projectPic;
        this.projectName = projectName;
        this.projectCode = projectCode;
        this.projectCity = projectCity;
        this.projectUserId = projectId;
        this.projectMonth = projectMonth;
        this.projectDesc = projectDesc;
        this.projectPrice = projectPrice;
        this.projectReturn = projectReturn;
        this.projectParentId = projectParentId;
        this.projectInvested = projectInvested;
    }


    protected ProjectModel(Parcel in) {
        projectPic = in.readString();
        projectName = in.readString();
        projectCode = in.readString();
        projectCity = in.readString();
        projectUserId = in.readString();
        projectMonth = in.readInt();
        projectDesc = in.readString();
        projectParentId = in.readString();
        projectPrice = in.readInt();
        projectReturn = in.readInt();
        projectInvested = in.readInt();
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

    public int getProjectMonth() {
        return projectMonth;
    }

    public int getProjectInvested() {
        return projectInvested;
    }

    public String getProjectPic() {
        return projectPic;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public String getProjectCity() {
        return projectCity;
    }

    public String getProjectUserId() {
        return projectUserId;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public int getProjectPrice() {
        return projectPrice;
    }

    public int getProjectReturn() {
        return projectReturn;
    }

    public String getProjectParentId() {
        return projectParentId;
    }

    public void setProjectParentId(String projectParentId) {
        this.projectParentId = projectParentId;
    }

    public Map<String, Object> addProject() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("projectPic", projectPic);
        result.put("projectName", projectName);
        result.put("projectCode", projectCode);
        result.put("projectCity", projectCity);
        result.put("projectUserId", projectUserId);
        result.put("projectParentId", projectParentId);
        result.put("projectMonth", projectMonth);
        result.put("projectDesc", projectDesc);
        result.put("projectPrice", projectPrice);
        result.put("projectReturn", projectReturn);
        result.put("projectInvested", projectInvested);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(projectPic);
        parcel.writeString(projectName);
        parcel.writeString(projectCode);
        parcel.writeString(projectCity);
        parcel.writeString(projectUserId);
        parcel.writeInt(projectMonth);
        parcel.writeString(projectDesc);
        parcel.writeString(projectParentId);
        parcel.writeInt(projectPrice);
        parcel.writeInt(projectReturn);
        parcel.writeInt(projectInvested);
    }
}
