package com.Cbic_Aaklan_Project.entity;

import java.io.Serializable;

public class Zone implements Serializable {
    private String name;
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Zone(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
