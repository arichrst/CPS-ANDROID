package com.choki.cps.Models;

import java.io.Serializable;

public class ExposedPipeImage implements Serializable {
    private int id;
    private String link;
    private String notes;
    private int exposedPipeId;
    private ExposedPipe exposedPipe;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getExposedPipeId() {
        return exposedPipeId;
    }

    public void setExposedPipeId(int exposedPipeId) {
        this.exposedPipeId = exposedPipeId;
    }

    public ExposedPipe getExposedPipe() {
        return exposedPipe;
    }

    public void setExposedPipe(ExposedPipe exposedPipe) {
        this.exposedPipe = exposedPipe;
    }
}
