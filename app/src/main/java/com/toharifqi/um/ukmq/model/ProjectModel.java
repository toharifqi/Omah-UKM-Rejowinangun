package com.toharifqi.um.ukmq.model;

public class ProjectModel {
    String projectCode, projectPic, projectName, projectCorp, projectCity, projectId, projectForm;
    int projectPrice;

    public ProjectModel() {
    }

    public ProjectModel(String projectCode, String projectPic, String projectName, String projectCorp, String projectCity, String projectId, String projectForm, int projectPrice) {
        this.projectCode = projectCode;
        this.projectPic = projectPic;
        this.projectName = projectName;
        this.projectCorp = projectCorp;
        this.projectCity = projectCity;
        this.projectId = projectId;
        this.projectForm = projectForm;
        this.projectPrice = projectPrice;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

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

    public String getProjectCorp() {
        return projectCorp;
    }

    public void setProjectCorp(String projectCorp) {
        this.projectCorp = projectCorp;
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

    public String getProjectForm() {
        return projectForm;
    }

    public void setProjectForm(String projectForm) {
        this.projectForm = projectForm;
    }

    public int getProjectPrice() {
        return projectPrice;
    }

    public void setProjectPrice(int projectPrice) {
        this.projectPrice = projectPrice;
    }
}
