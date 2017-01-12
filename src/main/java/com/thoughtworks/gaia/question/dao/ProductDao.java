package com.thoughtworks.gaia.question.dao;

import com.thoughtworks.gaia.common.jpa.BaseDaoWrapper;
import com.thoughtworks.gaia.question.model.QuestionModel;
import org.springframework.stereotype.Component;

@Component
public class QuestionDao extends BaseDaoWrapper<QuestionModel> {
    public QuestionDao() {
        super(QuestionModel.class);
    }
}
