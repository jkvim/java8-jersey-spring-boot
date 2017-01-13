package com.thoughtworks.gaia.exam.service;

import com.thoughtworks.gaia.common.Loggable;
import com.thoughtworks.gaia.common.exception.NotFoundException;
import com.thoughtworks.gaia.exam.ExamStatusTypeMapper;
import com.thoughtworks.gaia.exam.dao.ExamStatusTypeDao;
import com.thoughtworks.gaia.exam.entity.ExamStatusType;
import com.thoughtworks.gaia.exam.model.ExamStatusTypeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ExamStatusTypeService implements Loggable {
    @Autowired
    private ExamStatusTypeMapper mapper;

    @Autowired
    private ExamStatusTypeDao examStatusTypeDao;

    public ExamStatusType getExamStatusType(Long examStatusTypeId) {
        ExamStatusTypeModel examStatusTypeModel = examStatusTypeDao.idEquals(examStatusTypeId).querySingle();
        if (examStatusTypeModel == null) {
            error("Exam status type not found with id: " + examStatusTypeId);
            throw new NotFoundException();
        }

        return mapper.map(examStatusTypeModel, ExamStatusType.class);
    }
}
