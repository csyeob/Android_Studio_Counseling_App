package com.cookandroid.mp2;

public class ChildInfo {
    String c_name;
    String c_number;
    String c_birth;

    public ChildInfo(String c_name, String c_number, String c_birth) {
        this.c_name = c_name;
        this.c_number = c_number;
        this.c_birth = c_birth;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_number() {
        return c_number;
    }

    public void setC_number(String c_number) {
        this.c_number = c_number;
    }

    public String getC_birth() {
        return c_birth;
    }

    public void setC_birth(String c_birth) {
        this.c_birth = c_birth;
    }
}
