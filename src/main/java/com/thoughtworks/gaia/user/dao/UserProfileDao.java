package com.thoughtworks.gaia.user.dao;

import com.thoughtworks.gaia.common.jpa.BaseDaoWrapper;
import com.thoughtworks.gaia.user.model.UserProfileModel;
import org.springframework.stereotype.Component;

@Component
public class UserProfileDao extends BaseDaoWrapper<UserProfileModel> {
    public UserProfileDao() { super(UserProfileModel.class); }
}