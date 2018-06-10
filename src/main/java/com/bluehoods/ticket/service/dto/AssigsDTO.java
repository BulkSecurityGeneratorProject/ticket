package com.bluehoods.ticket.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Assigs entity.
 */
public class AssigsDTO implements Serializable {

    private Long id;

    private Long resourceId;

    private Long roleId;

    private Long taskId;

    private Long effort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getEffort() {
        return effort;
    }

    public void setEffort(Long effort) {
        this.effort = effort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AssigsDTO assigsDTO = (AssigsDTO) o;
        if(assigsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), assigsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AssigsDTO{" +
            "id=" + getId() +
            ", resourceId=" + getResourceId() +
            ", roleId=" + getRoleId() +
            ", taskId=" + getTaskId() +
            ", effort=" + getEffort() +
            "}";
    }
}
