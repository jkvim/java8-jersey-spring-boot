package com.thoughtworks.gaia.user.entity;

import java.util.Date;

public class UserExamQuestion {
    private Long id;

    private Long userExamId;

    private long questionId;

    private String answer;

    private boolean isCorrect;

    private Date timeCreated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserExamId() {
        return userExamId;
    }

    public void setUserExamId(Long userExamId) {
        this.userExamId = userExamId;
    }

    public Long getQuestionId() {
        return questionId;
    }

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

    public void setTimeCreated(Date timeCreated) { this.timeCreated = timeCreated; }
}
