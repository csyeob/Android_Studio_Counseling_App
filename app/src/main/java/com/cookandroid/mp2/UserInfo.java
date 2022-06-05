package com.cookandroid.mp2;

public class UserInfo {
    String id;
    String pwd;
    String name;
    String birth;
    String phone;
    String family;

    public UserInfo(String id, String pwd, String name, String birth, String phone, String family) {
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.birth = birth;
        this.phone = phone;
        this.family = family;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }
}
