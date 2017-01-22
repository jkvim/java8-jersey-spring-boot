package com.thoughtworks.gaia.user.model;

import com.thoughtworks.gaia.common.jpa.IdBaseModel;
import com.thoughtworks.gaia.user.entity.UserProfile;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user")
public class UserModel extends IdBaseModel {
    @Column(name = "email", nullable = false, length = 64)
    private String email;

    @Column(name = "password", nullable = false, length = 24)
    private String password;

    @Column(name = "user_type_id", nullable = false)
    private Long userTypeId;

    @Column(name = "time_created", nullable = false, updatable = false)
    private Date timeCreated;

    @OneToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false )
    @PrimaryKeyJoinColumn
    private UserProfileModel userProfileModel;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) { this.password = password; }

    public Long getUserTypeId() { return userTypeId; }

    public void setUserTypeId(Long userTypeId) {
        this.userTypeId = userTypeId;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public UserProfileModel getUserProfileModel() { return userProfileModel; }

    public void setUserProfileModel(UserProfileModel userProfileModel) { this.userProfileModel = userProfileModel; }
}
