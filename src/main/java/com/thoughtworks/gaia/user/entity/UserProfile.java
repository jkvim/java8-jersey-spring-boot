package com.thoughtworks.gaia.user.entity;

import java.util.Date;

public class UserProfile {
    private Long id;

    private String name;

    private boolean gender;

    private String tel;

    private String school;

    private String major;

    private Date timeCreated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) { this.tel = tel; }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) { this.timeCreated = timeCreated; }
}
