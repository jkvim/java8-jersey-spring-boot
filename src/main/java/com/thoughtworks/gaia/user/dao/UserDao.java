package com.thoughtworks.gaia.user.dao;

import com.thoughtworks.gaia.common.jpa.BaseDaoWrapper;
import com.thoughtworks.gaia.user.model.UserModel;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class UserDao extends BaseDaoWrapper<UserModel> {
    public UserDao() {
        super(UserModel.class);
    }

    public List<UserModel> findUserByName(String name) {
        return where(field("name").like(name)).queryList();
    }
}
