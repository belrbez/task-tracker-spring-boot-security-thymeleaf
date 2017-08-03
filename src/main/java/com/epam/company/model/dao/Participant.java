package com.epam.company.model.dao;


import com.epam.company.model.types.TaskType;
import com.epam.company.model.types.UserProfileType;
import com.epam.company.model.util.TaskModification;
import com.epam.company.util.resolvers.DatabaseResolver;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Participant entity.
 *
 * @author belrbeZ
 * @since 10.05.2017
 */
@Entity
@Table(name = DatabaseResolver.TABLE_PARTICIPANTS, schema = DatabaseResolver.SCHEMA)
public class Participant {

    public static final Participant EMPTY = new Participant();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "userId", nullable = false)
    private Long userId;

    @Column(name = "taskId", nullable = false)
    private Long taskId;

    @Column(name = "modifyCounter", nullable = false)
    private Long modifyCounter;

    @Column(name = "createDate", nullable = false)
    private Timestamp createDate;

    @Column(name = "lastModifyDate", nullable = false)
    private Timestamp lastModifyDate;

    private Participant() {
        this.id = 0L;
        this.userId = UserProfileType.EMPTY.getUserProfileType();
        this.taskId = TaskType.EMPTY.getValue();
        this.modifyCounter = 0L;
        this.lastModifyDate = Timestamp.valueOf(LocalDateTime.MIN);
    }

    public Participant(Long userId, Long taskId) {
        this.userId = userId;
        this.taskId = taskId;
        this.createDate = Timestamp.valueOf(LocalDateTime.now());
        this.lastModifyDate = createDate;
        this.modifyCounter = 0L;
    }

    // FOR Cached Service
    public Participant(TaskModification modification, Long userId) {
        this.id = modification.getId();
        this.userId = userId;
        this.taskId = modification.getTaskId();
        this.modifyCounter = 0L;
        this.lastModifyDate = Timestamp.valueOf(modification.getLastModifyDate());
    }

    public void modify() {
        this.modifyCounter++;
        this.lastModifyDate = Timestamp.valueOf(LocalDateTime.now());
    }

    public void reset() {
        this.modifyCounter = 0L;
    }

    //<editor-fold desc="GetterAndSetter">

    private void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getModifyCounter() {
        return modifyCounter;
    }

    public void setModifyCounter(Long modifyCounter) {
        this.modifyCounter = modifyCounter;
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
    //</editor-fold>

}
