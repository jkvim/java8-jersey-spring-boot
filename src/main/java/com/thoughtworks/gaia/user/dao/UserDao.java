package com.thoughtworks.gaia.user.dao;

import com.thoughtworks.gaia.common.jpa.BaseDaoWrapper;
import com.thoughtworks.gaia.user.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserDao extends BaseDaoWrapper<UserModel> {
    public UserDao() {
        super(UserModel.class);
    }
}
