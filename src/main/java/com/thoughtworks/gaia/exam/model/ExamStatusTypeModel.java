package com.thoughtworks.gaia.exam.model;

import com.thoughtworks.gaia.common.jpa.IdBaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "exam_status_type")
public class ExamStatusTypeModel extends IdBaseModel {
    @Column(name = "status", nullable = false, length = 8)
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
