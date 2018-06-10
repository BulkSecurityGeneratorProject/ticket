package com.bluehoods.ticket.service;

import com.bluehoods.ticket.domain.RaiseTicket;
import com.bluehoods.ticket.repository.RaiseTicketRepository;
import com.bluehoods.ticket.repository.search.RaiseTicketSearchRepository;
import com.bluehoods.ticket.service.dto.RaiseTicketDTO;
import com.bluehoods.ticket.service.mapper.RaiseTicketMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing RaiseTicket.
 */
@Service
@Transactional
public class RaiseTicketService {

    private final Logger log = LoggerFactory.getLogger(RaiseTicketService.class);

    private final RaiseTicketRepository raiseTicketRepository;

    private final RaiseTicketMapper raiseTicketMapper;

    private final RaiseTicketSearchRepository raiseTicketSearchRepository;

    public RaiseTicketService(RaiseTicketRepository raiseTicketRepository, RaiseTicketMapper raiseTicketMapper, RaiseTicketSearchRepository raiseTicketSearchRepository) {
        this.raiseTicketRepository = raiseTicketRepository;
        this.raiseTicketMapper = raiseTicketMapper;
        this.raiseTicketSearchRepository = raiseTicketSearchRepository;
    }

    /**
     * Save a raiseTicket.
     *
     * @param raiseTicketDTO the entity to save
     * @return the persisted entity
     */
    public RaiseTicketDTO save(RaiseTicketDTO raiseTicketDTO) {
        log.debug("Request to save RaiseTicket : {}", raiseTicketDTO);
        RaiseTicket raiseTicket = raiseTicketMapper.toEntity(raiseTicketDTO);
        raiseTicket = raiseTicketRepository.save(raiseTicket);
        RaiseTicketDTO result = raiseTicketMapper.toDto(raiseTicket);
        raiseTicketSearchRepository.save(raiseTicket);
        return result;
    }

    /**
     * Get all the raiseTickets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RaiseTicketDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RaiseTickets");
        return raiseTicketRepository.findAll(pageable)
            .map(raiseTicketMapper::toDto);
    }

    /**
     * Get one raiseTicket by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public RaiseTicketDTO findOne(Long id) {
        log.debug("Request to get RaiseTicket : {}", id);
        RaiseTicket raiseTicket = raiseTicketRepository.findOne(id);
        return raiseTicketMapper.toDto(raiseTicket);
    }

    /**
     * Delete the raiseTicket by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RaiseTicket : {}", id);
        raiseTicketRepository.delete(id);
        raiseTicketSearchRepository.delete(id);
    }

    /**
     * Search for the raiseTicket corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RaiseTicketDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RaiseTickets for query {}", query);
        Page<RaiseTicket> result = raiseTicketSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(raiseTicketMapper::toDto);
    }
}
