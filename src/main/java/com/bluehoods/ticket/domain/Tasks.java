package com.bluehoods.ticket.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.bluehoods.ticket.domain.enumeration.TaskStatus;

/**
 * A Tasks.
 */
@Entity
@Table(name = "tasks")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tasks")
public class Tasks implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @Size(max = 20)
    @Column(name = "code", length = 20)
    private String code;

    @Column(name = "jhi_level")
    private Integer level;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_status")
    private TaskStatus taskStatus;

    @Column(name = "jhi_start")
    private String start;

    @Column(name = "jhi_end")
    private String end;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "start_is_milestone")
    private Boolean startIsMilestone;

    @Column(name = "end_is_milestone")
    private Boolean endIsMilestone;

    @Size(max = 15)
    @Column(name = "depends", length = 15)
    private String depends;

    @Column(name = "description")
    private String description;

    @Column(name = "progress")
    private Integer progress;

    @Column(name = "selected_row")
    private Integer selectedRow;

    @Column(name = "can_write")
    private Boolean canWrite;

    @Column(name = "can_write_on_parent")
    private Boolean canWriteOnParent;

    @OneToMany(mappedBy = "tasks")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Roles> roles = new HashSet<>();

    @OneToMany(mappedBy = "tasks")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Resources> resources = new HashSet<>();

    @OneToMany(mappedBy = "tasks")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<File> documents = new HashSet<>();

    @ManyToOne
    private Assigs assigs;

    @ManyToOne(optional = false)
    @NotNull
    private Project project;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Tasks name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public Tasks code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getLevel() {
        return level;
    }

    public Tasks level(Integer level) {
        this.level = level;
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public Tasks taskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
        return this;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getStart() {
        return start;
    }

    public Tasks start(String start) {
        this.start = start;
        return this;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public Tasks end(String end) {
        this.end = end;
        return this;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Integer getDuration() {
        return duration;
    }

    public Tasks duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Boolean isStartIsMilestone() {
        return startIsMilestone;
    }

    public Tasks startIsMilestone(Boolean startIsMilestone) {
        this.startIsMilestone = startIsMilestone;
        return this;
    }

    public void setStartIsMilestone(Boolean startIsMilestone) {
        this.startIsMilestone = startIsMilestone;
    }

    public Boolean isEndIsMilestone() {
        return endIsMilestone;
    }

    public Tasks endIsMilestone(Boolean endIsMilestone) {
        this.endIsMilestone = endIsMilestone;
        return this;
    }

    public void setEndIsMilestone(Boolean endIsMilestone) {
        this.endIsMilestone = endIsMilestone;
    }

    public String getDepends() {
        return depends;
    }

    public Tasks depends(String depends) {
        this.depends = depends;
        return this;
    }

    public void setDepends(String depends) {
        this.depends = depends;
    }

    public String getDescription() {
        return description;
    }

    public Tasks description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getProgress() {
        return progress;
    }

    public Tasks progress(Integer progress) {
        this.progress = progress;
        return this;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Integer getSelectedRow() {
        return selectedRow;
    }

    public Tasks selectedRow(Integer selectedRow) {
        this.selectedRow = selectedRow;
        return this;
    }

    public void setSelectedRow(Integer selectedRow) {
        this.selectedRow = selectedRow;
    }

    public Boolean isCanWrite() {
        return canWrite;
    }

    public Tasks canWrite(Boolean canWrite) {
        this.canWrite = canWrite;
        return this;
    }

    public void setCanWrite(Boolean canWrite) {
        this.canWrite = canWrite;
    }

    public Boolean isCanWriteOnParent() {
        return canWriteOnParent;
    }

    public Tasks canWriteOnParent(Boolean canWriteOnParent) {
        this.canWriteOnParent = canWriteOnParent;
        return this;
    }

    public void setCanWriteOnParent(Boolean canWriteOnParent) {
        this.canWriteOnParent = canWriteOnParent;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public Tasks roles(Set<Roles> roles) {
        this.roles = roles;
        return this;
    }

    public Tasks addRoles(Roles roles) {
        this.roles.add(roles);
        roles.setTasks(this);
        return this;
    }

    public Tasks removeRoles(Roles roles) {
        this.roles.remove(roles);
        roles.setTasks(null);
        return this;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public Set<Resources> getResources() {
        return resources;
    }

    public Tasks resources(Set<Resources> resources) {
        this.resources = resources;
        return this;
    }

    public Tasks addResources(Resources resources) {
        this.resources.add(resources);
        resources.setTasks(this);
        return this;
    }

    public Tasks removeResources(Resources resources) {
        this.resources.remove(resources);
        resources.setTasks(null);
        return this;
    }

    public void setResources(Set<Resources> resources) {
        this.resources = resources;
    }

    public Set<File> getDocuments() {
        return documents;
    }

    public Tasks documents(Set<File> files) {
        this.documents = files;
        return this;
    }

    public Tasks addDocument(File file) {
        this.documents.add(file);
        file.setTasks(this);
        return this;
    }

    public Tasks removeDocument(File file) {
        this.documents.remove(file);
        file.setTasks(null);
        return this;
    }

    public void setDocuments(Set<File> files) {
        this.documents = files;
    }

    public Assigs getAssigs() {
        return assigs;
    }

    public Tasks assigs(Assigs assigs) {
        this.assigs = assigs;
        return this;
    }

    public void setAssigs(Assigs assigs) {
        this.assigs = assigs;
    }

    public Project getProject() {
        return project;
    }

    public Tasks project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
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
        Tasks tasks = (Tasks) o;
        if (tasks.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tasks.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tasks{" +
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
