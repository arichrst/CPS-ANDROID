package com.choki.cps.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Routes implements Serializable {
    private int id;
    private String name;
    private String fromRegion;
    private String toRegion;
    private double distance;
    private int userId;
    private double diameter;
    private String field;
    private String protectionCatodicType;
    private String anodeMaterial;
    private String fieldTools;
    private String pipeLengthTools;
    private String diameterTools;
    private String catodicProtectionTools;
    private String anodeMaterialTools;
    private String fieldToolsBrand;
    private String pipeLengthToolsBrand;
    private String catodicProtectionToolsBrand;
    private String anodeMaterialToolsBrand;
    private String diameterToolsBrand;
    private User user;
    private List<ExposedPipe> exposedPipe;
    private List<TestPoint> testPoint;

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

    public Routes() {
        this.user = new User();
        this.exposedPipe = new ArrayList<>();
        this.testPoint = new ArrayList<>(

        );
    }

    public String getDiameterToolsBrand() {
        return diameterToolsBrand;
    }

    public void setDiameterToolsBrand(String diameterToolsBrand) {
        this.diameterToolsBrand = diameterToolsBrand;
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

    public String getFromRegion() {
        return fromRegion;
    }

    public void setFromRegion(String fromRegion) {
        this.fromRegion = fromRegion;
    }

    public String getToRegion() {
        return toRegion;
    }

    public void setToRegion(String toRegion) {
        this.toRegion = toRegion;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getProtectionCatodicType() {
        return protectionCatodicType;
    }

    public void setProtectionCatodicType(String protectionCatodicType) {
        this.protectionCatodicType = protectionCatodicType;
    }

    public String getAnodeMaterial() {
        return anodeMaterial;
    }

    public void setAnodeMaterial(String anodeMaterial) {
        this.anodeMaterial = anodeMaterial;
    }

    public String getFieldTools() {
        return fieldTools;
    }

    public void setFieldTools(String fieldTools) {
        this.fieldTools = fieldTools;
    }

    public String getPipeLengthTools() {
        return pipeLengthTools;
    }

    public void setPipeLengthTools(String pipeLengthTools) {
        this.pipeLengthTools = pipeLengthTools;
    }

    public String getDiameterTools() {
        return diameterTools;
    }

    public void setDiameterTools(String diameterTools) {
        this.diameterTools = diameterTools;
    }

    public String getCatodicProtectionTools() {
        return catodicProtectionTools;
    }

    public void setCatodicProtectionTools(String catodicProtectionTools) {
        this.catodicProtectionTools = catodicProtectionTools;
    }

    public String getAnodeMaterialTools() {
        return anodeMaterialTools;
    }

    public void setAnodeMaterialTools(String anodeMaterialTools) {
        this.anodeMaterialTools = anodeMaterialTools;
    }

    public String getFieldToolsBrand() {
        return fieldToolsBrand;
    }

    public void setFieldToolsBrand(String fieldToolsBrand) {
        this.fieldToolsBrand = fieldToolsBrand;
    }

    public String getPipeLengthToolsBrand() {
        return pipeLengthToolsBrand;
    }

    public void setPipeLengthToolsBrand(String pipeLengthToolsBrand) {
        this.pipeLengthToolsBrand = pipeLengthToolsBrand;
    }

    public String getCatodicProtectionToolsBrand() {
        return catodicProtectionToolsBrand;
    }

    public void setCatodicProtectionToolsBrand(String catodicProtectionToolsBrand) {
        this.catodicProtectionToolsBrand = catodicProtectionToolsBrand;
    }

    public String getAnodeMaterialToolsBrand() {
        return anodeMaterialToolsBrand;
    }

    public void setAnodeMaterialToolsBrand(String anodeMaterialToolsBrand) {
        this.anodeMaterialToolsBrand = anodeMaterialToolsBrand;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ExposedPipe> getExposedPipe() {
        return exposedPipe;
    }

    public void setExposedPipe(List<ExposedPipe> exposedPipe) {
        this.exposedPipe = exposedPipe;
    }

    public List<TestPoint> getTestPoint() {
        return testPoint;
    }

    public void setTestPoint(List<TestPoint> testPoint) {
        this.testPoint = testPoint;
    }
}
