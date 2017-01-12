package com.thoughtworks.gaia.question.service;

import com.thoughtworks.gaia.common.Loggable;
import com.thoughtworks.gaia.common.exception.NotFoundException;
import com.thoughtworks.gaia.question.QuestionMapper;
import com.thoughtworks.gaia.question.dao.QuestionDao;
import com.thoughtworks.gaia.question.entity.Question;
import com.thoughtworks.gaia.question.model.QuestionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class QuestionService implements Loggable {
    @Autowired
    private QuestionMapper mapper;

    @Autowired
    private QuestionDao questionDao;

    public Question getQuestion(Long questionId) {
        QuestionModel questionModel = questionDao.idEquals(questionId).querySingle();
        if (questionModel == null) {
            error("Question not found with id: " + questionId);
            throw new NotFoundException();
        }

        return mapper.map(questionModel, Question.class);
    }
}
