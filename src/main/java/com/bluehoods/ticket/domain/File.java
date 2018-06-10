package com.bluehoods.ticket.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A File.
 */
@Entity
@Table(name = "file")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "file")
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "document_name")
    private String documentName;

    @Column(name = "document_one_details")
    private String documentOneDetails;

    @Lob
    @Column(name = "document_one")
    private byte[] documentOne;

    @Column(name = "document_one_content_type")
    private String documentOneContentType;

    @Column(name = "document_two_details")
    private String documentTwoDetails;

    @Lob
    @Column(name = "document_two")
    private byte[] documentTwo;

    @Column(name = "document_two_content_type")
    private String documentTwoContentType;

    @Column(name = "document_three_details")
    private String documentThreeDetails;

    @Lob
    @Column(name = "document_three")
    private byte[] documentThree;

    @Column(name = "document_three_content_type")
    private String documentThreeContentType;

    @ManyToOne
    private Project project;

    @ManyToOne
    private Tasks tasks;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public File documentName(String documentName) {
        this.documentName = documentName;
        return this;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentOneDetails() {
        return documentOneDetails;
    }

    public File documentOneDetails(String documentOneDetails) {
        this.documentOneDetails = documentOneDetails;
        return this;
    }

    public void setDocumentOneDetails(String documentOneDetails) {
        this.documentOneDetails = documentOneDetails;
    }

    public byte[] getDocumentOne() {
        return documentOne;
    }

    public File documentOne(byte[] documentOne) {
        this.documentOne = documentOne;
        return this;
    }

    public void setDocumentOne(byte[] documentOne) {
        this.documentOne = documentOne;
    }

    public String getDocumentOneContentType() {
        return documentOneContentType;
    }

    public File documentOneContentType(String documentOneContentType) {
        this.documentOneContentType = documentOneContentType;
        return this;
    }

    public void setDocumentOneContentType(String documentOneContentType) {
        this.documentOneContentType = documentOneContentType;
    }

    public String getDocumentTwoDetails() {
        return documentTwoDetails;
    }

    public File documentTwoDetails(String documentTwoDetails) {
        this.documentTwoDetails = documentTwoDetails;
        return this;
    }

    public void setDocumentTwoDetails(String documentTwoDetails) {
        this.documentTwoDetails = documentTwoDetails;
    }

    public byte[] getDocumentTwo() {
        return documentTwo;
    }

    public File documentTwo(byte[] documentTwo) {
        this.documentTwo = documentTwo;
        return this;
    }

    public void setDocumentTwo(byte[] documentTwo) {
        this.documentTwo = documentTwo;
    }

    public String getDocumentTwoContentType() {
        return documentTwoContentType;
    }

    public File documentTwoContentType(String documentTwoContentType) {
        this.documentTwoContentType = documentTwoContentType;
        return this;
    }

    public void setDocumentTwoContentType(String documentTwoContentType) {
        this.documentTwoContentType = documentTwoContentType;
    }

    public String getDocumentThreeDetails() {
        return documentThreeDetails;
    }

    public File documentThreeDetails(String documentThreeDetails) {
        this.documentThreeDetails = documentThreeDetails;
        return this;
    }

    public void setDocumentThreeDetails(String documentThreeDetails) {
        this.documentThreeDetails = documentThreeDetails;
    }

    public byte[] getDocumentThree() {
        return documentThree;
    }

    public File documentThree(byte[] documentThree) {
        this.documentThree = documentThree;
        return this;
    }

    public void setDocumentThree(byte[] documentThree) {
        this.documentThree = documentThree;
    }

    public String getDocumentThreeContentType() {
        return documentThreeContentType;
    }

    public File documentThreeContentType(String documentThreeContentType) {
        this.documentThreeContentType = documentThreeContentType;
        return this;
    }

    public void setDocumentThreeContentType(String documentThreeContentType) {
        this.documentThreeContentType = documentThreeContentType;
    }

    public Project getProject() {
        return project;
    }

    public File project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Tasks getTasks() {
        return tasks;
    }

    public File tasks(Tasks tasks) {
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
        File file = (File) o;
        if (file.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), file.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "File{" +
            "id=" + getId() +
            ", documentName='" + getDocumentName() + "'" +
            ", documentOneDetails='" + getDocumentOneDetails() + "'" +
            ", documentOne='" + getDocumentOne() + "'" +
            ", documentOneContentType='" + getDocumentOneContentType() + "'" +
            ", documentTwoDetails='" + getDocumentTwoDetails() + "'" +
            ", documentTwo='" + getDocumentTwo() + "'" +
            ", documentTwoContentType='" + getDocumentTwoContentType() + "'" +
            ", documentThreeDetails='" + getDocumentThreeDetails() + "'" +
            ", documentThree='" + getDocumentThree() + "'" +
            ", documentThreeContentType='" + getDocumentThreeContentType() + "'" +
            "}";
    }
}
