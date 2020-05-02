package com.example.bridge_admin.Model;

public class StudentModel {

    private String Name;
    private String Admission_no;
    private String Standard;
    private String Sec;

    public StudentModel(){}

    public StudentModel(String name, String Admission_no, String Standard, String Sec) {
        this.Name = name;
        this.Admission_no=Admission_no;
        this.Standard=Standard;
        this.Sec=Sec;
    }

    public String getName() {
        return Name;
    }
    public String getAdmission_no() {
        return Admission_no;
    }

    public String getStandard() {
        return Standard;
    }
    public String getSec(){
        return Sec;
    }


}
