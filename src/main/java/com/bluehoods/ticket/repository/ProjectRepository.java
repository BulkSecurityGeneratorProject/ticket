package com.bluehoods.ticket.repository;

import com.bluehoods.ticket.domain.Project;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Project entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("select project from Project project where project.assignedTo.login = ?#{principal.username}")
    List<Project> findByAssignedToIsCurrentUser();

}
