package com.thoughtworks.gaia.user.entity;

import java.util.Date;

public class User {
    private Long id;

    private String email;

    private String password;

    private Long userTypeId;

    private Date timeCreated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Long userTypeId) {
        this.userTypeId = userTypeId;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) { this.timeCreated = timeCreated; }
}
