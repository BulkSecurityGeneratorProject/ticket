package com.bluehoods.ticket.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bluehoods.ticket.service.RaiseTicketService;
import com.bluehoods.ticket.web.rest.errors.BadRequestAlertException;
import com.bluehoods.ticket.web.rest.util.HeaderUtil;
import com.bluehoods.ticket.web.rest.util.PaginationUtil;
import com.bluehoods.ticket.service.dto.RaiseTicketDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing RaiseTicket.
 */
@RestController
@RequestMapping("/api")
public class RaiseTicketResource {

    private final Logger log = LoggerFactory.getLogger(RaiseTicketResource.class);

    private static final String ENTITY_NAME = "raiseTicket";

    private final RaiseTicketService raiseTicketService;

    public RaiseTicketResource(RaiseTicketService raiseTicketService) {
        this.raiseTicketService = raiseTicketService;
    }

    /**
     * POST  /raise-tickets : Create a new raiseTicket.
     *
     * @param raiseTicketDTO the raiseTicketDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new raiseTicketDTO, or with status 400 (Bad Request) if the raiseTicket has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/raise-tickets")
    @Timed
    public ResponseEntity<RaiseTicketDTO> createRaiseTicket(@Valid @RequestBody RaiseTicketDTO raiseTicketDTO) throws URISyntaxException {
        log.debug("REST request to save RaiseTicket : {}", raiseTicketDTO);
        if (raiseTicketDTO.getId() != null) {
            throw new BadRequestAlertException("A new raiseTicket cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RaiseTicketDTO result = raiseTicketService.save(raiseTicketDTO);
        return ResponseEntity.created(new URI("/api/raise-tickets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /raise-tickets : Updates an existing raiseTicket.
     *
     * @param raiseTicketDTO the raiseTicketDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated raiseTicketDTO,
     * or with status 400 (Bad Request) if the raiseTicketDTO is not valid,
     * or with status 500 (Internal Server Error) if the raiseTicketDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/raise-tickets")
    @Timed
    public ResponseEntity<RaiseTicketDTO> updateRaiseTicket(@Valid @RequestBody RaiseTicketDTO raiseTicketDTO) throws URISyntaxException {
        log.debug("REST request to update RaiseTicket : {}", raiseTicketDTO);
        if (raiseTicketDTO.getId() == null) {
            return createRaiseTicket(raiseTicketDTO);
        }
        RaiseTicketDTO result = raiseTicketService.save(raiseTicketDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, raiseTicketDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /raise-tickets : get all the raiseTickets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of raiseTickets in body
     */
    @GetMapping("/raise-tickets")
    @Timed
    public ResponseEntity<List<RaiseTicketDTO>> getAllRaiseTickets(Pageable pageable) {
        log.debug("REST request to get a page of RaiseTickets");
        Page<RaiseTicketDTO> page = raiseTicketService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/raise-tickets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /raise-tickets/:id : get the "id" raiseTicket.
     *
     * @param id the id of the raiseTicketDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the raiseTicketDTO, or with status 404 (Not Found)
     */
    @GetMapping("/raise-tickets/{id}")
    @Timed
    public ResponseEntity<RaiseTicketDTO> getRaiseTicket(@PathVariable Long id) {
        log.debug("REST request to get RaiseTicket : {}", id);
        RaiseTicketDTO raiseTicketDTO = raiseTicketService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(raiseTicketDTO));
    }

    /**
     * DELETE  /raise-tickets/:id : delete the "id" raiseTicket.
     *
     * @param id the id of the raiseTicketDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/raise-tickets/{id}")
    @Timed
    public ResponseEntity<Void> deleteRaiseTicket(@PathVariable Long id) {
        log.debug("REST request to delete RaiseTicket : {}", id);
        raiseTicketService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/raise-tickets?query=:query : search for the raiseTicket corresponding
     * to the query.
     *
     * @param query the query of the raiseTicket search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/raise-tickets")
    @Timed
    public ResponseEntity<List<RaiseTicketDTO>> searchRaiseTickets(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RaiseTickets for query {}", query);
        Page<RaiseTicketDTO> page = raiseTicketService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/raise-tickets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
