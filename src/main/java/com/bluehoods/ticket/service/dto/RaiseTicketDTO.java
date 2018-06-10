package com.bluehoods.ticket.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;
import com.bluehoods.ticket.domain.enumeration.IssueStatus;

/**
 * A DTO for the RaiseTicket entity.
 */
public class RaiseTicketDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    private String description;

    private LocalDate openDate;

    private LocalDate dueDate;

    private IssueStatus issueStatus;

    @Lob
    private byte[] attachScreenshot;
    private String attachScreenshotContentType;

    @Size(max = 200)
    private String remarks;

    private Long projectId;

    private String projectName;

    private Long assignedToId;

    private String assignedToLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getOpenDate() {
        return openDate;
    }

    public void setOpenDate(LocalDate openDate) {
        this.openDate = openDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public IssueStatus getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(IssueStatus issueStatus) {
        this.issueStatus = issueStatus;
    }

    public byte[] getAttachScreenshot() {
        return attachScreenshot;
    }

    public void setAttachScreenshot(byte[] attachScreenshot) {
        this.attachScreenshot = attachScreenshot;
    }

    public String getAttachScreenshotContentType() {
        return attachScreenshotContentType;
    }

    public void setAttachScreenshotContentType(String attachScreenshotContentType) {
        this.attachScreenshotContentType = attachScreenshotContentType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public Long getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(Long userId) {
        this.assignedToId = userId;
    }

    public String getAssignedToLogin() {
        return assignedToLogin;
    }

    public void setAssignedToLogin(String userLogin) {
        this.assignedToLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RaiseTicketDTO raiseTicketDTO = (RaiseTicketDTO) o;
        if(raiseTicketDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), raiseTicketDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RaiseTicketDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", openDate='" + getOpenDate() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", issueStatus='" + getIssueStatus() + "'" +
            ", attachScreenshot='" + getAttachScreenshot() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
