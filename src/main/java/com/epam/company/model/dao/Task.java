package com.epam.company.model.dao;

/*
 * Created by @belrbeZ
 */


import com.epam.company.model.types.TaskState;
import com.epam.company.model.types.TaskType;
import com.epam.company.model.types.UserProfileType;
import com.epam.company.util.resolvers.DatabaseResolver;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Task model implementation
 */
@Entity
@Table(name = DatabaseResolver.TABLE_TASKS, schema = DatabaseResolver.SCHEMA)
public class Task {

    public static final Task EMPTY = new Task();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "topicStarterId", nullable = false)
    private Long topicStarterId;

    @Column(name = "companyStarterId")
    private Long companyStarterId;

    @Column(name = "type", nullable = false)
    private TaskType type;

    @Column(name = "state")
    private TaskState state;

    @Column(name = "theme", nullable = false)
    private String theme;

    @Column(name = "descr")
    private String descr;

    @Column(name = "deadline")
    private Timestamp deadline;

    @Column(name = "createDate", nullable = false)
    private Timestamp createDate;

    @Column(name = "lastModifyDate", nullable = false)
    private Timestamp lastModifyDate;

    private Task() {
        this.id = TaskType.EMPTY.getValue();
        this.topicStarterId = UserProfileType.EMPTY.getUserProfileType();
        this.companyStarterId = UserProfileType.EMPTY.getUserProfileType();
        this.deadline = null;
        this.createDate = Timestamp.valueOf(LocalDateTime.MIN);
        this.lastModifyDate = Timestamp.valueOf(LocalDateTime.MAX);
        this.type = TaskType.EMPTY;
        this.state = TaskState.UNKNOWN;
        this.theme = "";
        this.descr = "";
    }

    public Task(Long topicStarterId, TaskType type, String theme, String descr, Timestamp deadline) {
        this.id = TaskType.EMPTY.getValue();
        this.topicStarterId = topicStarterId;
        this.type = type;
        this.theme = theme;
        this.descr = descr;
        this.deadline = deadline;
    }

    public Task(Long topicStarterId, Long companyStarterId, TaskType type,
                TaskState state, String theme, String descr, Timestamp deadline,
                Timestamp createDate, Timestamp lastModifyDate) {
        this(topicStarterId, type, theme, descr, deadline);
        this.companyStarterId = companyStarterId;
        this.state = state;
        this.createDate = createDate;
        this.lastModifyDate = lastModifyDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTopicStarterId() {
        return topicStarterId;
    }

    public void setTopicStarterId(Long topicStarterId) {
        this.topicStarterId = topicStarterId;
    }

    public Long getCompanyStarterId() {
        return companyStarterId;
    }

    public void setCompanyStarterId(Long companyStarterId) {
        this.companyStarterId = companyStarterId;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Timestamp lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }


    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", topicStarterId=" + topicStarterId +
                ", companyStarterId=" + companyStarterId +
                ", type=" + type +
                ", state=" + state +
                ", theme='" + theme + '\'' +
                ", descr='" + descr + '\'' +
                ", deadline=" + deadline +
                ", createDate=" + createDate +
                ", lastModifyDate=" + lastModifyDate +
                '}';
    }
}
