package com.bluehoods.ticket.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.bluehoods.ticket.domain.enumeration.ProjectStatus;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "project")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_status")
    private ProjectStatus projectStatus;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Size(max = 100)
    @Column(name = "owner", length = 100)
    private String owner;

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tasks> tasks = new HashSet<>();

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<File> documents = new HashSet<>();

    @ManyToOne
    private User assignedTo;

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RaiseTicket> issueTickets = new HashSet<>();

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

    public Project name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Project description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public Project projectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
        return this;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Project startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Project endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getOwner() {
        return owner;
    }

    public Project owner(String owner) {
        this.owner = owner;
        return this;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Set<Tasks> getTasks() {
        return tasks;
    }

    public Project tasks(Set<Tasks> tasks) {
        this.tasks = tasks;
        return this;
    }

    public Project addTasks(Tasks tasks) {
        this.tasks.add(tasks);
        tasks.setProject(this);
        return this;
    }

    public Project removeTasks(Tasks tasks) {
        this.tasks.remove(tasks);
        tasks.setProject(null);
        return this;
    }

    public void setTasks(Set<Tasks> tasks) {
        this.tasks = tasks;
    }

    public Set<File> getDocuments() {
        return documents;
    }

    public Project documents(Set<File> files) {
        this.documents = files;
        return this;
    }

    public Project addDocument(File file) {
        this.documents.add(file);
        file.setProject(this);
        return this;
    }

    public Project removeDocument(File file) {
        this.documents.remove(file);
        file.setProject(null);
        return this;
    }

    public void setDocuments(Set<File> files) {
        this.documents = files;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public Project assignedTo(User user) {
        this.assignedTo = user;
        return this;
    }

    public void setAssignedTo(User user) {
        this.assignedTo = user;
    }

    public Set<RaiseTicket> getIssueTickets() {
        return issueTickets;
    }

    public Project issueTickets(Set<RaiseTicket> raiseTickets) {
        this.issueTickets = raiseTickets;
        return this;
    }

    public Project addIssueTicket(RaiseTicket raiseTicket) {
        this.issueTickets.add(raiseTicket);
        raiseTicket.setProject(this);
        return this;
    }

    public Project removeIssueTicket(RaiseTicket raiseTicket) {
        this.issueTickets.remove(raiseTicket);
        raiseTicket.setProject(null);
        return this;
    }

    public void setIssueTickets(Set<RaiseTicket> raiseTickets) {
        this.issueTickets = raiseTickets;
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
        Project project = (Project) o;
        if (project.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), project.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", projectStatus='" + getProjectStatus() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", owner='" + getOwner() + "'" +
            "}";
    }
}
