package com.thoughtworks.gaia.user.dao;

import com.thoughtworks.gaia.common.jpa.BaseDaoWrapper;
import com.thoughtworks.gaia.user.model.UserExamModel;
import org.springframework.stereotype.Component;

@Component
public class UserExamDao extends BaseDaoWrapper<UserExamModel> {
    public UserExamDao() {
        super(UserExamModel.class);
    }
}
