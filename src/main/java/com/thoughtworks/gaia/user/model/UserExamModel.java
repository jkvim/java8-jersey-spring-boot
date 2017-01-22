package com.thoughtworks.gaia.user.model;

import com.thoughtworks.gaia.common.jpa.IdBaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "user_exam")
public class UserExamModel extends IdBaseModel {
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "exam_id", nullable = false, updatable = false)
    private Long examId;

    @Column(name = "score", nullable = false, updatable = true)
    private int score;

    @Column(name = "time_created", nullable = false, updatable = false)
    private Date timeCreated;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }
}