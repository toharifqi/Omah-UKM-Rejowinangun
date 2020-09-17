package com.toharifqi.um.ukmq.model;

import java.util.HashMap;
import java.util.Map;

public class ProgressModel {
    String progressWriter, progressTitle, progressDesc, docPic, proggressId, timeStamp, progressIdUser;

    public ProgressModel() {
    }

    public ProgressModel(String namaPengisi, String progressTitle, String progressDesc, String docPic, String proggressId, String timeStamp, String progressIdUser) {
        this.progressWriter = namaPengisi;
        this.progressTitle = progressTitle;
        this.progressDesc = progressDesc;
        this.docPic = docPic;
        this.proggressId = proggressId;
        this.timeStamp = timeStamp;
        this.progressIdUser = progressIdUser;
    }

    public String getProgressWriter() {
        return progressWriter;
    }

    public String getProgressTitle() {
        return progressTitle;
    }

    public String getProgressDesc() {
        return progressDesc;
    }

    public String getDocPic() {
        return docPic;
    }

    public String getProggressId() {
        return proggressId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public Map<String, Object> addProgress() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("progressWriter", progressWriter);
        result.put("progressTitle", progressTitle);
        result.put("progressDesc", progressDesc);
        result.put("docPic", docPic);
        result.put("progressId", proggressId);
        result.put("timeStamp", timeStamp);
        result.put("progressIdUser", progressIdUser);
        return result;
    }
}
