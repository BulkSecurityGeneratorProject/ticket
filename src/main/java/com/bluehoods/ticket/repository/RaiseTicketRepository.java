package com.bluehoods.ticket.repository;

import com.bluehoods.ticket.domain.RaiseTicket;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the RaiseTicket entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RaiseTicketRepository extends JpaRepository<RaiseTicket, Long> {

    @Query("select raise_ticket from RaiseTicket raise_ticket where raise_ticket.assignedTo.login = ?#{principal.username}")
    List<RaiseTicket> findByAssignedToIsCurrentUser();

}
