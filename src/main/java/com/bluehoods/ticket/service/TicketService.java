package com.bluehoods.ticket.service;

import com.bluehoods.ticket.domain.Ticket;
import com.bluehoods.ticket.repository.TicketRepository;
import com.bluehoods.ticket.repository.search.TicketSearchRepository;
import com.bluehoods.ticket.service.dto.TicketDTO;
import com.bluehoods.ticket.service.mapper.TicketMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Ticket.
 */
@Service
@Transactional
public class TicketService {

    private final Logger log = LoggerFactory.getLogger(TicketService.class);

    private final TicketRepository ticketRepository;

    private final TicketMapper ticketMapper;

    private final TicketSearchRepository ticketSearchRepository;

    public TicketService(TicketRepository ticketRepository, TicketMapper ticketMapper, TicketSearchRepository ticketSearchRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
        this.ticketSearchRepository = ticketSearchRepository;
    }

    /**
     * Save a ticket.
     *
     * @param ticketDTO the entity to save
     * @return the persisted entity
     */
    public TicketDTO save(TicketDTO ticketDTO) {
        log.debug("Request to save Ticket : {}", ticketDTO);
        Ticket ticket = ticketMapper.toEntity(ticketDTO);
        ticket = ticketRepository.save(ticket);
        TicketDTO result = ticketMapper.toDto(ticket);
        ticketSearchRepository.save(ticket);
        return result;
    }

    /**
     * Get all the tickets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TicketDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tickets");
        return ticketRepository.findAll(pageable)
            .map(ticketMapper::toDto);
    }

    /**
     * Get one ticket by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TicketDTO findOne(Long id) {
        log.debug("Request to get Ticket : {}", id);
        Ticket ticket = ticketRepository.findOne(id);
        return ticketMapper.toDto(ticket);
    }

    /**
     * Delete the ticket by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Ticket : {}", id);
        ticketRepository.delete(id);
        ticketSearchRepository.delete(id);
    }

    /**
     * Search for the ticket corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TicketDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Tickets for query {}", query);
        Page<Ticket> result = ticketSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(ticketMapper::toDto);
    }
}
