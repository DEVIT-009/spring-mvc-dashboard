package com.pos.dashboardmvc.models;

public class Sample {
    private int id;
    private String sampleName;
    private Boolean sampleStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public Boolean getSampleStatus() {
        return sampleStatus;
    }

    public void setSampleStatus(Boolean sampleStatus) {
        this.sampleStatus = sampleStatus;
    }
}
