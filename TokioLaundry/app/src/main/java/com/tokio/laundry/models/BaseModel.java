package com.tokio.laundry.models;

import java.io.Serializable;

public class BaseModel implements Serializable {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BaseModel() {
        this.id = String.valueOf(System.currentTimeMillis());
    }
}
