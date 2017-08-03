package com.epam.company.model.util;


import com.epam.company.model.types.TaskType;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Modified task class.
 *
 * @author @belrbeZ
 * @since 13.05.2017
 */
public class TaskModification {

    public static final TaskModification EMPTY = new TaskModification();

    @NotNull
    private Long id;
    @NotNull
    private Long taskId;

    private Long modifyCounter;
    private LocalDateTime lastModifyDate;

    private TaskModification() {
        this.id =  0L;
        this.taskId = TaskType.EMPTY.getValue();
        this.modifyCounter = 0L;
        this.lastModifyDate = LocalDateTime.MIN;
    }

    public TaskModification(Long id, Long taskId, Long modifyCounter, LocalDateTime lastModifyDate) {
        this.id = id;
        this.taskId = taskId;
        this.modifyCounter = modifyCounter;
        this.lastModifyDate = lastModifyDate;
    }

    public void modify() {
        modifyCounter++;
        lastModifyDate = LocalDateTime.now();
    }

    public void reset() {
        modifyCounter = 0L;
    }

    //<editor-fold desc="GetterAndSetter">

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(LocalDateTime lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }
    //</editor-fold>

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskModification that = (TaskModification) o;

        return taskId.equals(that.taskId);
    }

    @Override
    public int hashCode() {
        return taskId.hashCode();
    }
}
