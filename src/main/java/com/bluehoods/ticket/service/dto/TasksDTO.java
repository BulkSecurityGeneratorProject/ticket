package com.bluehoods.ticket.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.bluehoods.ticket.domain.enumeration.TaskStatus;

/**
 * A DTO for the Tasks entity.
 */
public class TasksDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String name;

    @Size(max = 20)
    private String code;

    private Integer level;

    private TaskStatus taskStatus;

    private String start;

    private String end;

    private Integer duration;

    private Boolean startIsMilestone;

    private Boolean endIsMilestone;

    @Size(max = 15)
    private String depends;

    private String description;

    private Integer progress;

    private Integer selectedRow;

    private Boolean canWrite;

    private Boolean canWriteOnParent;

    private Long assigsId;

    private Long projectId;

    private String projectName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Boolean isStartIsMilestone() {
        return startIsMilestone;
    }

    public void setStartIsMilestone(Boolean startIsMilestone) {
        this.startIsMilestone = startIsMilestone;
    }

    public Boolean isEndIsMilestone() {
        return endIsMilestone;
    }

    public void setEndIsMilestone(Boolean endIsMilestone) {
        this.endIsMilestone = endIsMilestone;
    }

    public String getDepends() {
        return depends;
    }

    public void setDepends(String depends) {
        this.depends = depends;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Integer getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(Integer selectedRow) {
        this.selectedRow = selectedRow;
    }

    public Boolean isCanWrite() {
        return canWrite;
    }

    public void setCanWrite(Boolean canWrite) {
        this.canWrite = canWrite;
    }

    public Boolean isCanWriteOnParent() {
        return canWriteOnParent;
    }

    public void setCanWriteOnParent(Boolean canWriteOnParent) {
        this.canWriteOnParent = canWriteOnParent;
    }

    public Long getAssigsId() {
        return assigsId;
    }

    public void setAssigsId(Long assigsId) {
        this.assigsId = assigsId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TasksDTO tasksDTO = (TasksDTO) o;
        if(tasksDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tasksDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TasksDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", level=" + getLevel() +
            ", taskStatus='" + getTaskStatus() + "'" +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            ", duration=" + getDuration() +
            ", startIsMilestone='" + isStartIsMilestone() + "'" +
            ", endIsMilestone='" + isEndIsMilestone() + "'" +
            ", depends='" + getDepends() + "'" +
            ", description='" + getDescription() + "'" +
            ", progress=" + getProgress() +
            ", selectedRow=" + getSelectedRow() +
            ", canWrite='" + isCanWrite() + "'" +
            ", canWriteOnParent='" + isCanWriteOnParent() + "'" +
            "}";
    }
}
