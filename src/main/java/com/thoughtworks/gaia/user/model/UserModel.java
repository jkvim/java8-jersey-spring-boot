package com.thoughtworks.gaia.user.model;

import com.thoughtworks.gaia.common.jpa.IdBaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "user")
public class UserModel extends IdBaseModel {
    @Column(name = "email", nullable = false, length = 64)
    private String email;

    @Column(name = "password", nullable = false, length = 24)
    private String password;

    @Column(name = "name", nullable = true, length = 64)
    private String name;

    @Column(name = "gender", nullable = true)
    private boolean gender;

    @Column(name = "tel", nullable = true, length = 11)
    private String tel;

    @Column(name = "school", nullable = true, length = 64)
    private String school;

    @Column(name = "major", nullable = true, length = 64)
    private String major;

    @Column(name = "user_type_id", nullable = false)
    private short user_type_id;

    @Column(name = "time_created", nullable = false, updatable = false)
    private Date timeCreated;

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

    public void setTel(String tel) {
        this.tel = tel;
    }

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

    public short getUserTypeId() {
        return user_type_id;
    }

    public void setUserTypeId(short userTypeId) {
        this.user_type_id = userTypeId;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }
}
