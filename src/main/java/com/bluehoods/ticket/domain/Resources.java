package com.bluehoods.ticket.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Resources.
 */
@Entity
@Table(name = "resources")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "resources")
public class Resources implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

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

    public Resources name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public Resources user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Assigs getAssigs() {
        return assigs;
    }

    public Resources assigs(Assigs assigs) {
        this.assigs = assigs;
        return this;
    }

    public void setAssigs(Assigs assigs) {
        this.assigs = assigs;
    }

    public Tasks getTasks() {
        return tasks;
    }

    public Resources tasks(Tasks tasks) {
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
        Resources resources = (Resources) o;
        if (resources.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resources.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Resources{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
