package com.thoughtworks.gaia.exam.dao;

import com.thoughtworks.gaia.common.jpa.BaseDaoWrapper;
import com.thoughtworks.gaia.exam.model.ExamModel;
import org.springframework.stereotype.Component;

@Component
public class ExamDao extends BaseDaoWrapper<ExamModel> {
    public ExamDao() {
        super(ExamModel.class);
    }
}
