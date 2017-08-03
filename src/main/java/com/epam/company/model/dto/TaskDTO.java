package com.epam.company.model.dto;


import com.epam.company.model.types.TaskState;
import com.epam.company.model.types.TaskType;
import com.epam.company.model.types.UserProfileType;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

/**
 * Created by @belrbeZ
 */
public class TaskDTO implements Comparator<LocalDateTime> {

    public static final TaskDTO EMPTY = new TaskDTO();

    @NotNull
    private Long id;
    @NotNull
    private Long topicStarterId;

    private Long companyStarterId;

    private TaskType type;

    @NotEmpty
    private String theme;
    @NotEmpty
    private String descr;

    private Boolean subscribed;
    private Long modifyCount;

    private TaskState state;

    private LocalDateTime deadline;

    private LocalDateTime createDate;

    private LocalDateTime lastModifyDate;

    public TaskDTO() {
        this.id = TaskType.EMPTY.getValue();
        this.topicStarterId = UserProfileType.EMPTY.getUserProfileType();
        this.companyStarterId = UserProfileType.EMPTY.getUserProfileType();
        this.type = TaskType.EMPTY;
        this.theme = "";
        this.descr = "";
        this.state = TaskState.UNKNOWN;
        this.deadline = null;
        this.createDate = null;
        this.lastModifyDate = null;
        this.subscribed = false;
        this.modifyCount = 0L;
    }

    public TaskDTO(TaskDTO taskDTO) {
        this.id = taskDTO.getId();
        this.topicStarterId = taskDTO.getTopicStarterId();
        this.companyStarterId = 0L;
        this.type = taskDTO.getType();
        this.theme = taskDTO.getTheme();
        this.descr = taskDTO.getDescr();
        this.state = taskDTO.getState();
        this.deadline = taskDTO.getDeadline();
        this.createDate = taskDTO.getCreateDate();
        this.lastModifyDate = taskDTO.getLastModifyDate();
        this.subscribed = taskDTO.getSubscribed();
        this.modifyCount = taskDTO.getModifyCount();
    }

    public TaskDTO(Long id, Long topicStarterId,
                   TaskType type, String theme, String descr,
                   TaskState state, Timestamp deadline,
                   Timestamp createDate, Timestamp lastModifyDate) {
        this.id = id;
        this.topicStarterId = topicStarterId;
        this.companyStarterId = companyStarterId;
        this.type = type;
        this.theme = theme;
        this.descr = descr;
        this.state = state;
        this.deadline = (deadline != null) ? deadline.toLocalDateTime() : null;
        this.createDate     = (createDate != null) ? createDate.toLocalDateTime() : null;
        this.lastModifyDate = (lastModifyDate != null) ? lastModifyDate.toLocalDateTime() : null;
        this.subscribed = false;
        this.modifyCount = 0L;
    }

    public String getPrettyCreateDate() {
        return (createDate != null)
                ? createDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                : "";
    }

     public String getPrettyLastModifyDate() {
         return (lastModifyDate != null)
                 ? lastModifyDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                 : "";
     }


    public Long getModifyCount() {
        return modifyCount;
    }

    public void setModifyCount(Long modifyCount) {
        if(modifyCount != null) {
            subscribed = true;

            if(modifyCount > 0)
                this.modifyCount = modifyCount;
        }
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

    public Boolean getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        this.subscribed = subscribed;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(LocalDateTime lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    //</editor-fold>

    @Override
    public int compare(LocalDateTime o1, LocalDateTime o2) {
        return o1.compareTo(o2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskDTO taskDTO = (TaskDTO) o;

        if (id != null ? !id.equals(taskDTO.id) : taskDTO.id != null) return false;
        return topicStarterId != null ? topicStarterId.equals(taskDTO.topicStarterId) : taskDTO.topicStarterId == null;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "TaskDTO{" +
                "id=" + id +
                ", topicStarterId=" + topicStarterId +
                ", companyStarterId=" + companyStarterId +
                ", type=" + type +
                ", theme='" + theme + '\'' +
                ", descr='" + descr + '\'' +
                ", subscribed=" + subscribed +
                ", modifyCount=" + modifyCount +
                ", state=" + state +
                ", deadline=" + deadline +
                ", createDate=" + createDate +
                ", lastModifyDate=" + lastModifyDate +
                '}';
    }
}
