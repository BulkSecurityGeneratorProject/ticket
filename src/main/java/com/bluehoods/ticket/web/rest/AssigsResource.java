package com.bluehoods.ticket.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bluehoods.ticket.service.AssigsService;
import com.bluehoods.ticket.web.rest.errors.BadRequestAlertException;
import com.bluehoods.ticket.web.rest.util.HeaderUtil;
import com.bluehoods.ticket.web.rest.util.PaginationUtil;
import com.bluehoods.ticket.service.dto.AssigsDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Assigs.
 */
@RestController
@RequestMapping("/api")
public class AssigsResource {

    private final Logger log = LoggerFactory.getLogger(AssigsResource.class);

    private static final String ENTITY_NAME = "assigs";

    private final AssigsService assigsService;

    public AssigsResource(AssigsService assigsService) {
        this.assigsService = assigsService;
    }

    /**
     * POST  /assigs : Create a new assigs.
     *
     * @param assigsDTO the assigsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new assigsDTO, or with status 400 (Bad Request) if the assigs has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/assigs")
    @Timed
    public ResponseEntity<AssigsDTO> createAssigs(@RequestBody AssigsDTO assigsDTO) throws URISyntaxException {
        log.debug("REST request to save Assigs : {}", assigsDTO);
        if (assigsDTO.getId() != null) {
            throw new BadRequestAlertException("A new assigs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AssigsDTO result = assigsService.save(assigsDTO);
        return ResponseEntity.created(new URI("/api/assigs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /assigs : Updates an existing assigs.
     *
     * @param assigsDTO the assigsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated assigsDTO,
     * or with status 400 (Bad Request) if the assigsDTO is not valid,
     * or with status 500 (Internal Server Error) if the assigsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/assigs")
    @Timed
    public ResponseEntity<AssigsDTO> updateAssigs(@RequestBody AssigsDTO assigsDTO) throws URISyntaxException {
        log.debug("REST request to update Assigs : {}", assigsDTO);
        if (assigsDTO.getId() == null) {
            return createAssigs(assigsDTO);
        }
        AssigsDTO result = assigsService.save(assigsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, assigsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /assigs : get all the assigs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of assigs in body
     */
    @GetMapping("/assigs")
    @Timed
    public ResponseEntity<List<AssigsDTO>> getAllAssigs(Pageable pageable) {
        log.debug("REST request to get a page of Assigs");
        Page<AssigsDTO> page = assigsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/assigs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /assigs/:id : get the "id" assigs.
     *
     * @param id the id of the assigsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the assigsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/assigs/{id}")
    @Timed
    public ResponseEntity<AssigsDTO> getAssigs(@PathVariable Long id) {
        log.debug("REST request to get Assigs : {}", id);
        AssigsDTO assigsDTO = assigsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(assigsDTO));
    }

    /**
     * DELETE  /assigs/:id : delete the "id" assigs.
     *
     * @param id the id of the assigsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/assigs/{id}")
    @Timed
    public ResponseEntity<Void> deleteAssigs(@PathVariable Long id) {
        log.debug("REST request to delete Assigs : {}", id);
        assigsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/assigs?query=:query : search for the assigs corresponding
     * to the query.
     *
     * @param query the query of the assigs search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/assigs")
    @Timed
    public ResponseEntity<List<AssigsDTO>> searchAssigs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Assigs for query {}", query);
        Page<AssigsDTO> page = assigsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/assigs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
