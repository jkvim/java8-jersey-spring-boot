package com.thoughtworks.gaia.exam.service;

import com.thoughtworks.gaia.common.Loggable;
import com.thoughtworks.gaia.common.exception.NotFoundException;
import com.thoughtworks.gaia.user.UserExamMapper;
import com.thoughtworks.gaia.user.dao.UserExamDao;
import com.thoughtworks.gaia.user.entity.UserExam;
import com.thoughtworks.gaia.user.model.UserExamModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UserExamService implements Loggable {
    @Autowired
    private UserExamMapper mapper;

    @Autowired
    private UserExamDao userExamDao;

    public UserExam getUserExam(Long userExamId) {
        UserExamModel userExamModel = userExamDao.idEquals(userExamId).querySingle();
        if (userExamModel == null) {
            error("User exam not found with id: " + userExamId);
            throw new NotFoundException();
        }

        return mapper.map(userExamModel, UserExam.class);
    }
}
