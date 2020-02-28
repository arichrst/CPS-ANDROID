package com.choki.cps.Models;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private int id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String token;
    private List<ExposedPipe> exposedPipe;
    private List<Routes> route;
    private List<TestPoint> testPoint;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<ExposedPipe> getExposedPipe() {
        return exposedPipe;
    }

    public void setExposedPipe(List<ExposedPipe> exposedPipe) {
        this.exposedPipe = exposedPipe;
    }

    public List<Routes> getRoute() {
        return route;
    }

    public void setRoute(List<Routes> route) {
        this.route = route;
    }

    public List<TestPoint> getTestPoint() {
        return testPoint;
    }

    public void setTestPoint(List<TestPoint> testPoint) {
        this.testPoint = testPoint;
    }
}
