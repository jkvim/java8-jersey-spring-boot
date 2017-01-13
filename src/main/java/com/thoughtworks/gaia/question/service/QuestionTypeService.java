package com.thoughtworks.gaia.question.service;

import com.thoughtworks.gaia.common.Loggable;
import com.thoughtworks.gaia.common.exception.NotFoundException;
import com.thoughtworks.gaia.question.entity.QuestionType;
import com.thoughtworks.gaia.question.QuestionTypeMapper;
import com.thoughtworks.gaia.question.dao.QuestionTypeDao;
import com.thoughtworks.gaia.question.model.QuestionTypeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class QuestionTypeService implements Loggable {
    @Autowired
    private QuestionTypeMapper mapper;

    @Autowired
    private QuestionTypeDao questionTypeDao;

    public QuestionType getProduct(Long questionTypeId) {
        QuestionTypeModel questionTypeModel = questionTypeDao.idEquals(questionTypeId).querySingle();
        if (questionTypeModel == null) {
            error("QuestionType not found with id: " + questionTypeId);
            throw new NotFoundException();
        }

        return mapper.map(questionTypeModel, QuestionType.class);
    }
}
