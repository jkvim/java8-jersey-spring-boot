package com.thoughtworks.gaia.question.model;

import com.thoughtworks.gaia.common.jpa.IdBaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "question_type")
public class QuestionTypeModel extends IdBaseModel {
    @Column(name = "type", nullable = false, length = 16)
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
