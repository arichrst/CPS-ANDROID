package com.choki.cps.Models;

import java.io.Serializable;
import java.util.List;

public class ExposedPipe implements Serializable {
    private int id;
    private double longitude;
    private double latitude;
    private String notes;
    private String inspectionDate;
    private int routeId;
    private int userId;
    private List<ExposedPipeImage> exposedPipeImage;
    private Routes route;
    private User user;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(String inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<ExposedPipeImage> getExposedPipeImage() {
        return exposedPipeImage;
    }

    public void setExposedPipeImage(List<ExposedPipeImage> exposedPipeImage) {
        this.exposedPipeImage = exposedPipeImage;
    }

    public Routes getRoute() {
        return route;
    }

    public void setRoute(Routes route) {
        this.route = route;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
