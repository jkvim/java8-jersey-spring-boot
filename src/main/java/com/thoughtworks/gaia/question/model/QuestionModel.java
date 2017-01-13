package com.thoughtworks.gaia.question.model;

import com.thoughtworks.gaia.common.jpa.IdBaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "question")
public class QuestionModel extends IdBaseModel {
    @Column(name = "content", nullable = true, length = 4096)
    private String content;

    @Column(name = "result", nullable = true, length = 512)
    private String result;

    @Column(name = "time_created", nullable = false, updatable = false)
    private Date timeCreated;

    public String getContent() { return content; }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResult() { return result; }

    public void setResult(String result) { this.result = result; }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }
}
