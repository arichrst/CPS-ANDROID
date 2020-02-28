package com.choki.cps.Models;

import java.io.Serializable;

public class TestPointImage implements Serializable {
    private int id;
    private String link;
    private String notes;
    private int testPointId;
    private TestPoint testPoint;
    private boolean isOffline;

    public boolean isOffline() {
        return isOffline;
    }

    public void setOffline(boolean offline) {
        isOffline = offline;
    }

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

    public int getTestPointId() {
        return testPointId;
    }

    public void setTestPointId(int testPointId) {
        this.testPointId = testPointId;
    }

    public TestPoint getTestPoint() {
        return testPoint;
    }

    public void setTestPoint(TestPoint testPoint) {
        this.testPoint = testPoint;
    }
}
