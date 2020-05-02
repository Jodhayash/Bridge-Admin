package com.example.bridge_admin.Model;

public class BusModel {
    private String Bus_no;
    private String Driver_Name;

    public BusModel(){}

    public BusModel(String Bus_no, String Driver_Name) {
        this.Bus_no = Bus_no;
        this.Driver_Name = Driver_Name;
    }

    public String getBus_no() {
        return Bus_no;
    }
    public String getDriver_Name() {
        return Driver_Name;
    }

}
