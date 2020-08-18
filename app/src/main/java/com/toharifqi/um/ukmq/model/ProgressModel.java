package com.toharifqi.um.ukmq.model;

import java.util.HashMap;
import java.util.Map;

public class ProgressModel {
    String progressWriter, progressTitle, progressDesc, docPic, proggressId;

    public ProgressModel(String namaPengisi, String progressTitle, String progressDesc, String docPic, String proggressId) {
        this.progressWriter = namaPengisi;
        this.progressTitle = progressTitle;
        this.progressDesc = progressDesc;
        this.docPic = docPic;
        this.proggressId = proggressId;
    }

    public String getProgressWriter() {
        return progressWriter;
    }

    public void setProgressWriter(String progressWriter) {
        this.progressWriter = progressWriter;
    }

    public String getProgressTitle() {
        return progressTitle;
    }

    public void setProgressTitle(String progressTitle) {
        this.progressTitle = progressTitle;
    }

    public String getProgressDesc() {
        return progressDesc;
    }

    public void setProgressDesc(String progressDesc) {
        this.progressDesc = progressDesc;
    }

    public String getDocPic() {
        return docPic;
    }

    public void setDocPic(String docPic) {
        this.docPic = docPic;
    }

    public String getProggressId() {
        return proggressId;
    }

    public void setProggressId(String proggressId) {
        this.proggressId = proggressId;
    }

    public Map<String, Object> addProgress() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("progressWriter", progressWriter);
        result.put("progressTitle", progressTitle);
        result.put("progressDesc", progressDesc);
        result.put("docPic", docPic);
        result.put("progressId", proggressId);
        return result;
    }
}
