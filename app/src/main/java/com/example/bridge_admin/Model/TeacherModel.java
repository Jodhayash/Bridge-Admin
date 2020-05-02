package com.example.bridge_admin.Model;

public class TeacherModel {
    private String Name;
    private String ID;
    private String Class_Handled;
    private String Sec_Handled;

    public TeacherModel(){}

    public TeacherModel(String name, String ID, String Class_Handled, String Sec_Handled) {
        this.Name = name;
        this.ID=ID;
        this.Class_Handled=Class_Handled;
        this.Sec_Handled=Sec_Handled;
    }

    public String getName() {
        return Name;
    }
    public String getID() {
        return ID;
    }

    public String getClass_Handled() {
        return Class_Handled;
    }
    public String getSec_Handled(){
        return Sec_Handled;
    }
}
