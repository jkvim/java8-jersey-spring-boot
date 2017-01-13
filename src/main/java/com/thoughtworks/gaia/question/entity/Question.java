package com.thoughtworks.gaia.question.entity;

import java.util.Date;

public class Question {
    private Long id;

    private Long type_id;

    private String content;

    private String result;

    private Date timeCreated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResult() { return result; }

    public void setResult() { this.result = result; }

    public Long getTypeId() { return type_id; }

    public void setTypeId() { this.type_id = type_id; }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }
}
