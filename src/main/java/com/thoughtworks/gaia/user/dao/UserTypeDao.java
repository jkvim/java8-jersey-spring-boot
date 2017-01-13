package com.thoughtworks.gaia.user.dao;

import com.thoughtworks.gaia.common.jpa.BaseDaoWrapper;
import com.thoughtworks.gaia.user.model.UserTypeModel;
import org.springframework.stereotype.Component;

@Component
public class UserTypeDao extends BaseDaoWrapper<UserTypeModel> {
    public UserTypeDao() {
        super(UserTypeModel.class);
    }

    public Long findIdByUserType(String key) {
        return where(field("type").like(key)).querySingle().getId();
    }
}
