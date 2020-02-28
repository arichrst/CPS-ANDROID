package com.choki.cps.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TestPoint implements Serializable {
    private int id;
    private String name;
    private double latitude;
    private double longitude;
    private String notes;
    private String inspectionDate;
    private int routeId;
    private int userId;
    private double kpLocation;
    private double nativePipe;
    private double anode;
    private double protection;
    private double anodePower;
    private double soilResistivity;
    private double ph;
    private String landCorrosivity;
    private List<TestPointImage> tpImage;
    private Routes route;
    private User user;
    private boolean isOffline;

    public boolean isOffline() {
        return isOffline;
    }

    public void setOffline(boolean offline) {
        isOffline = offline;
    }

    private boolean isEdit;

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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

    public double getKpLocation() {
        return kpLocation;
    }

    public void setKpLocation(double kpLocation) {
        this.kpLocation = kpLocation;
    }

    public double getNativePipe() {
        return nativePipe;
    }

    public void setNativePipe(double nativePipe) {
        this.nativePipe = nativePipe;
    }

    public double getAnode() {
        return anode;
    }

    public void setAnode(double anode) {
        this.anode = anode;
    }

    public double getProtection() {
        return protection;
    }

    public void setProtection(double protection) {
        this.protection = protection;
    }

    public double getAnodePower() {
        return anodePower;
    }

    public void setAnodePower(double anodePower) {
        this.anodePower = anodePower;
    }

    public double getSoilResistivity() {
        return soilResistivity;
    }

    public void setSoilResistivity(double soilResistivity) {
        this.soilResistivity = soilResistivity;
    }

    public double getPh() {
        return ph;
    }

    public void setPh(double ph) {
        this.ph = ph;
    }

    public String getLandCorrosivity() {
        if(soilResistivity < 500)
            return "Very Corrosive";
        if(soilResistivity >= 500 && soilResistivity < 1000)
            return  "Corrosive";
        if(soilResistivity >= 1000 && soilResistivity < 2000)
            return  "Moderately Corrosive";
        if(soilResistivity >= 2000 && soilResistivity < 10000)
            return  "Mildly Corrosive";
        else
            return  "Progressively less corrosive";
    }

    public void setLandCorrosivity(String landCorrosivity) {
        this.landCorrosivity = landCorrosivity;
    }

    public List<TestPointImage> getTpImage() {
        return tpImage;
    }

    public void setTpImage(List<TestPointImage> tpImage) {
        this.tpImage = tpImage;
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
