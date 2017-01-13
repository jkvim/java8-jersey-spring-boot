package com.thoughtworks.gaia.exam.service;

import com.thoughtworks.gaia.common.Loggable;
import com.thoughtworks.gaia.common.exception.NotFoundException;
import com.thoughtworks.gaia.exam.ExamMapper;
import com.thoughtworks.gaia.exam.dao.ExamDao;
import com.thoughtworks.gaia.exam.entity.Exam;
import com.thoughtworks.gaia.exam.model.ExamModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class ExamService implements Loggable {
    @Autowired
    private ExamMapper mapper;

    @Autowired
    private ExamDao examDao;

    public Exam getExam(Long examId) {
        ExamModel examModel = examDao.idEquals(examId).querySingle();
        if (examModel == null) {
            error("Exam not found with id: " + examId);
            throw new NotFoundException();
        }

        return mapper.map(examModel, Exam.class);
    }

    public List<ExamModel> getAllExam() {
        List<ExamModel> allExam = examDao.where().queryList();
        return allExam;
    }

}
