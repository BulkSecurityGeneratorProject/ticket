package com.bluehoods.ticket.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Roles entity.
 */
public class RolesDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String name;

    private Long assigsId;

    private Long tasksId;

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

    public Long getAssigsId() {
        return assigsId;
    }

    public void setAssigsId(Long assigsId) {
        this.assigsId = assigsId;
    }

    public Long getTasksId() {
        return tasksId;
    }

    public void setTasksId(Long tasksId) {
        this.tasksId = tasksId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RolesDTO rolesDTO = (RolesDTO) o;
        if(rolesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rolesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RolesDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
