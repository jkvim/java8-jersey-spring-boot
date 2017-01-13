package com.thoughtworks.gaia.exam.model;

import com.thoughtworks.gaia.common.jpa.IdBaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "exam")
public class ExamModel extends IdBaseModel {
    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Column(name = "time_created", nullable = false, updatable = false)
    private Date timeCreated;

    @Column(name = "logic_q_count", nullable = false, updatable = false)
    private int logicQuestionCount;

    @Column(name = "prog_q_count", nullable = false, updatable = false)
    private int progQuestionCount;

    @Column(name = "status_id", nullable = false, updatable = true)
    private Long statusId;

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

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }
}
