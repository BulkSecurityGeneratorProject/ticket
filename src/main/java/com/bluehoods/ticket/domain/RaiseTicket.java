package com.bluehoods.ticket.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.bluehoods.ticket.domain.enumeration.IssueStatus;

/**
 * A RaiseTicket.
 */
@Entity
@Table(name = "raise_ticket")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "raiseticket")
public class RaiseTicket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "open_date")
    private LocalDate openDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "issue_status")
    private IssueStatus issueStatus;

    @Lob
    @Column(name = "attach_screenshot")
    private byte[] attachScreenshot;

    @Column(name = "attach_screenshot_content_type")
    private String attachScreenshotContentType;

    @Size(max = 200)
    @Column(name = "remarks", length = 200)
    private String remarks;

    @ManyToOne
    private Project project;

    @ManyToOne
    private User assignedTo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public RaiseTicket title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public RaiseTicket description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getOpenDate() {
        return openDate;
    }

    public RaiseTicket openDate(LocalDate openDate) {
        this.openDate = openDate;
        return this;
    }

    public void setOpenDate(LocalDate openDate) {
        this.openDate = openDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public RaiseTicket dueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public IssueStatus getIssueStatus() {
        return issueStatus;
    }

    public RaiseTicket issueStatus(IssueStatus issueStatus) {
        this.issueStatus = issueStatus;
        return this;
    }

    public void setIssueStatus(IssueStatus issueStatus) {
        this.issueStatus = issueStatus;
    }

    public byte[] getAttachScreenshot() {
        return attachScreenshot;
    }

    public RaiseTicket attachScreenshot(byte[] attachScreenshot) {
        this.attachScreenshot = attachScreenshot;
        return this;
    }

    public void setAttachScreenshot(byte[] attachScreenshot) {
        this.attachScreenshot = attachScreenshot;
    }

    public String getAttachScreenshotContentType() {
        return attachScreenshotContentType;
    }

    public RaiseTicket attachScreenshotContentType(String attachScreenshotContentType) {
        this.attachScreenshotContentType = attachScreenshotContentType;
        return this;
    }

    public void setAttachScreenshotContentType(String attachScreenshotContentType) {
        this.attachScreenshotContentType = attachScreenshotContentType;
    }

    public String getRemarks() {
        return remarks;
    }

    public RaiseTicket remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Project getProject() {
        return project;
    }

    public RaiseTicket project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public RaiseTicket assignedTo(User user) {
        this.assignedTo = user;
        return this;
    }

    public void setAssignedTo(User user) {
        this.assignedTo = user;
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
        RaiseTicket raiseTicket = (RaiseTicket) o;
        if (raiseTicket.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), raiseTicket.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RaiseTicket{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", openDate='" + getOpenDate() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", issueStatus='" + getIssueStatus() + "'" +
            ", attachScreenshot='" + getAttachScreenshot() + "'" +
            ", attachScreenshotContentType='" + getAttachScreenshotContentType() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
