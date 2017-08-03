package com.epam.company.model.dto;


import com.epam.company.model.types.TaskType;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

/**
 * DTO object for Task Subscribers.
 *
 * @author @belrbeZ
 * @since 10.05.2017
 */
public class TaskSubscribersDTO {

    public static final TaskSubscribersDTO EMPTY = new TaskSubscribersDTO();

    @NotNull
    private Long taskId;
    private LocalDateTime lastModificationDate;
    private Set<Long> subscribers;

    private TaskSubscribersDTO() {
        this.taskId = TaskType.EMPTY.getValue();
        this.lastModificationDate = LocalDateTime.MIN;
        this.subscribers = Collections.emptySet();
    }

    public TaskSubscribersDTO(Long taskId, LocalDateTime lastModificationDate, Set<Long> subscribers) {
        this.taskId = taskId;
        this.lastModificationDate = lastModificationDate;
        this.subscribers = subscribers;
    }

    //<editor-fold desc="GetterAndSetter">

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public LocalDateTime getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(LocalDateTime lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public Set<Long> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<Long> subscribers) {
        this.subscribers = subscribers;
    }
    //</editor-fold>

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskSubscribersDTO that = (TaskSubscribersDTO) o;

        return taskId.equals(that.taskId);
    }

    @Override
    public int hashCode() {
        return taskId.hashCode();
    }
}
