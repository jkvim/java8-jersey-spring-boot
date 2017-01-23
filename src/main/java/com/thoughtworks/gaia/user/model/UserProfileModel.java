package com.thoughtworks.gaia.user.model;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "user_profile")
public class UserProfileModel {
    @Id
    @GeneratedValue (generator = "pkGenerator" )
    @GenericGenerator(
            name = "pkGenerator" ,
            strategy = "foreign" ,
            parameters = @Parameter(name = "property", value = "userModel")
    )
    private Long id;

    @Column(name = "name", length = 64)
    private String name;

    @Column(name = "gender")
    private boolean gender;

    @Column(name = "tel", length = 11)
    private String tel;

    @Column(name = "school", length = 64)
    private String school;

    @Column(name = "major", length = 64)
    private String major;

    @Column(name = "time_created", nullable = false, updatable = false)
    private Date timeCreated;

    @OneToOne (fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private UserModel userModel;

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

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public UserModel getUserModel() { return userModel; }

    public void setUserModel(UserModel userModel) { this.userModel = userModel; }
}
