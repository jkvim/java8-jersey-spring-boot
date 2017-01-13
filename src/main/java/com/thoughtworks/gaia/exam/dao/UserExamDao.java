package com.thoughtworks.gaia.exam.dao;

import com.thoughtworks.gaia.common.jpa.BaseDaoWrapper;
import com.thoughtworks.gaia.exam.model.UserExamModel;
import org.springframework.stereotype.Component;

@Component
public class UserExamDao extends BaseDaoWrapper<UserExamModel> {
    public UserExamDao() {
        super(UserExamModel.class);
    }
}
