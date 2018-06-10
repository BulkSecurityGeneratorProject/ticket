package com.bluehoods.ticket.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Assigs.
 */
@Entity
@Table(name = "assigs")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "assigs")
public class Assigs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "resource_id")
    private Long resourceId;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "effort")
    private Long effort;

    @OneToMany(mappedBy = "assigs")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tasks> tasks = new HashSet<>();

    @OneToMany(mappedBy = "assigs")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Resources> resources = new HashSet<>();

    @OneToMany(mappedBy = "assigs")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Roles> roles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public Assigs resourceId(Long resourceId) {
        this.resourceId = resourceId;
        return this;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public Assigs roleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public Assigs taskId(Long taskId) {
        this.taskId = taskId;
        return this;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getEffort() {
        return effort;
    }

    public Assigs effort(Long effort) {
        this.effort = effort;
        return this;
    }

    public void setEffort(Long effort) {
        this.effort = effort;
    }

    public Set<Tasks> getTasks() {
        return tasks;
    }

    public Assigs tasks(Set<Tasks> tasks) {
        this.tasks = tasks;
        return this;
    }

    public Assigs addTasks(Tasks tasks) {
        this.tasks.add(tasks);
        tasks.setAssigs(this);
        return this;
    }

    public Assigs removeTasks(Tasks tasks) {
        this.tasks.remove(tasks);
        tasks.setAssigs(null);
        return this;
    }

    public void setTasks(Set<Tasks> tasks) {
        this.tasks = tasks;
    }

    public Set<Resources> getResources() {
        return resources;
    }

    public Assigs resources(Set<Resources> resources) {
        this.resources = resources;
        return this;
    }

    public Assigs addResources(Resources resources) {
        this.resources.add(resources);
        resources.setAssigs(this);
        return this;
    }

    public Assigs removeResources(Resources resources) {
        this.resources.remove(resources);
        resources.setAssigs(null);
        return this;
    }

    public void setResources(Set<Resources> resources) {
        this.resources = resources;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public Assigs roles(Set<Roles> roles) {
        this.roles = roles;
        return this;
    }

    public Assigs addRoles(Roles roles) {
        this.roles.add(roles);
        roles.setAssigs(this);
        return this;
    }

    public Assigs removeRoles(Roles roles) {
        this.roles.remove(roles);
        roles.setAssigs(null);
        return this;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Assigs assigs = (Assigs) o;
        if (assigs.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), assigs.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Assigs{" +
            "id=" + getId() +
            ", resourceId=" + getResourceId() +
            ", roleId=" + getRoleId() +
            ", taskId=" + getTaskId() +
            ", effort=" + getEffort() +
            "}";
    }
}
