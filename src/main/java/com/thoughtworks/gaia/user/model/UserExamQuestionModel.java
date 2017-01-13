package com.thoughtworks.gaia.user.model;

import com.thoughtworks.gaia.common.jpa.IdBaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "user_exam_question")
public class UserExamQuestionModel extends IdBaseModel {
    @Column(name = "user_exam_id", nullable = false)
    private Long userExamId;

    @Column(name = "question_id", nullable = false)
    private Long questionId;

    @Column(name = "answer", nullable = true, length = 8192)
    private String answer;

    @Column(name = "is_correct")
    private boolean isCorrect;

    @Column(name = "time_created", nullable = false, updatable = false)
    private Date timeCreated;

    public Long getUserExamId() { return userExamId; }

    public void setUserExamId(Long userExamId) {
        this.userExamId = userExamId;
    }

    public Long getQuestionId() { return questionId; }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }
}
