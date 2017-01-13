package com.thoughtworks.gaia.exam.dao;

import com.thoughtworks.gaia.common.jpa.BaseDaoWrapper;
import com.thoughtworks.gaia.exam.model.ExamStatusTypeModel;
import org.springframework.stereotype.Component;

@Component
public class ExamStatusTypeDao extends BaseDaoWrapper<ExamStatusTypeModel> {
    public ExamStatusTypeDao() {
        super(ExamStatusTypeModel.class);
    }
}
