package com.thoughtworks.gaia.question.dao;

import com.thoughtworks.gaia.common.jpa.BaseDaoWrapper;
import com.thoughtworks.gaia.question.model.QuestionTypeModel;
import org.springframework.stereotype.Component;

@Component
public class QuestionTypeDao extends BaseDaoWrapper<QuestionTypeModel> {
    public QuestionTypeDao() {
        super(QuestionTypeModel.class);
    }
}
