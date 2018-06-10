package com.bluehoods.ticket.repository;

import com.bluehoods.ticket.domain.Assigs;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Assigs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssigsRepository extends JpaRepository<Assigs, Long> {

}
