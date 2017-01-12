package com.thoughtworks.gaia.exam.entity;

import java.util.Date;

public class Exam {
    private Long id;

    private String name;

    private Date timeCreated;

    private int logicQuestionCount;

    private int progQuestionCount;

    private int statusId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public int getLogicQuestionCount() {
        return logicQuestionCount;
    }

    public void setLogicQuestionCount(int logicQuestionCount) {
        this.logicQuestionCount = logicQuestionCount;
    }

    public int getProgQuestionCount() {
        return progQuestionCount;
    }

    public void setProgQuestionCount(int progQuestionCount) {
        this.progQuestionCount = progQuestionCount;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
}
