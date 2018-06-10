package com.bluehoods.ticket.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the File entity.
 */
public class FileDTO implements Serializable {

    private Long id;

    private String documentName;

    private String documentOneDetails;

    @Lob
    private byte[] documentOne;
    private String documentOneContentType;

    private String documentTwoDetails;

    @Lob
    private byte[] documentTwo;
    private String documentTwoContentType;

    private String documentThreeDetails;

    @Lob
    private byte[] documentThree;
    private String documentThreeContentType;

    private Long projectId;

    private String projectName;

    private Long tasksId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentOneDetails() {
        return documentOneDetails;
    }

    public void setDocumentOneDetails(String documentOneDetails) {
        this.documentOneDetails = documentOneDetails;
    }

    public byte[] getDocumentOne() {
        return documentOne;
    }

    public void setDocumentOne(byte[] documentOne) {
        this.documentOne = documentOne;
    }

    public String getDocumentOneContentType() {
        return documentOneContentType;
    }

    public void setDocumentOneContentType(String documentOneContentType) {
        this.documentOneContentType = documentOneContentType;
    }

    public String getDocumentTwoDetails() {
        return documentTwoDetails;
    }

    public void setDocumentTwoDetails(String documentTwoDetails) {
        this.documentTwoDetails = documentTwoDetails;
    }

    public byte[] getDocumentTwo() {
        return documentTwo;
    }

    public void setDocumentTwo(byte[] documentTwo) {
        this.documentTwo = documentTwo;
    }

    public String getDocumentTwoContentType() {
        return documentTwoContentType;
    }

    public void setDocumentTwoContentType(String documentTwoContentType) {
        this.documentTwoContentType = documentTwoContentType;
    }

    public String getDocumentThreeDetails() {
        return documentThreeDetails;
    }

    public void setDocumentThreeDetails(String documentThreeDetails) {
        this.documentThreeDetails = documentThreeDetails;
    }

    public byte[] getDocumentThree() {
        return documentThree;
    }

    public void setDocumentThree(byte[] documentThree) {
        this.documentThree = documentThree;
    }

    public String getDocumentThreeContentType() {
        return documentThreeContentType;
    }

    public void setDocumentThreeContentType(String documentThreeContentType) {
        this.documentThreeContentType = documentThreeContentType;
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

        FileDTO fileDTO = (FileDTO) o;
        if(fileDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fileDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FileDTO{" +
            "id=" + getId() +
            ", documentName='" + getDocumentName() + "'" +
            ", documentOneDetails='" + getDocumentOneDetails() + "'" +
            ", documentOne='" + getDocumentOne() + "'" +
            ", documentTwoDetails='" + getDocumentTwoDetails() + "'" +
            ", documentTwo='" + getDocumentTwo() + "'" +
            ", documentThreeDetails='" + getDocumentThreeDetails() + "'" +
            ", documentThree='" + getDocumentThree() + "'" +
            "}";
    }
}
