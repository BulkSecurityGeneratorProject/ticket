package com.bluehoods.ticket.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Roles.
 */
@Entity
@Table(name = "roles")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "roles")
public class Roles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @ManyToOne
    private Assigs assigs;

    @ManyToOne
    private Tasks tasks;

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

    public Roles name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Assigs getAssigs() {
        return assigs;
    }

    public Roles assigs(Assigs assigs) {
        this.assigs = assigs;
        return this;
    }

    public void setAssigs(Assigs assigs) {
        this.assigs = assigs;
    }

    public Tasks getTasks() {
        return tasks;
    }

    public Roles tasks(Tasks tasks) {
        this.tasks = tasks;
        return this;
    }

    public void setTasks(Tasks tasks) {
        this.tasks = tasks;
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
        Roles roles = (Roles) o;
        if (roles.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), roles.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Roles{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
