package com.bluehoods.ticket.service;

import com.bluehoods.ticket.domain.Assigs;
import com.bluehoods.ticket.repository.AssigsRepository;
import com.bluehoods.ticket.repository.search.AssigsSearchRepository;
import com.bluehoods.ticket.service.dto.AssigsDTO;
import com.bluehoods.ticket.service.mapper.AssigsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Assigs.
 */
@Service
@Transactional
public class AssigsService {

    private final Logger log = LoggerFactory.getLogger(AssigsService.class);

    private final AssigsRepository assigsRepository;

    private final AssigsMapper assigsMapper;

    private final AssigsSearchRepository assigsSearchRepository;

    public AssigsService(AssigsRepository assigsRepository, AssigsMapper assigsMapper, AssigsSearchRepository assigsSearchRepository) {
        this.assigsRepository = assigsRepository;
        this.assigsMapper = assigsMapper;
        this.assigsSearchRepository = assigsSearchRepository;
    }

    /**
     * Save a assigs.
     *
     * @param assigsDTO the entity to save
     * @return the persisted entity
     */
    public AssigsDTO save(AssigsDTO assigsDTO) {
        log.debug("Request to save Assigs : {}", assigsDTO);
        Assigs assigs = assigsMapper.toEntity(assigsDTO);
        assigs = assigsRepository.save(assigs);
        AssigsDTO result = assigsMapper.toDto(assigs);
        assigsSearchRepository.save(assigs);
        return result;
    }

    /**
     * Get all the assigs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AssigsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Assigs");
        return assigsRepository.findAll(pageable)
            .map(assigsMapper::toDto);
    }

    /**
     * Get one assigs by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public AssigsDTO findOne(Long id) {
        log.debug("Request to get Assigs : {}", id);
        Assigs assigs = assigsRepository.findOne(id);
        return assigsMapper.toDto(assigs);
    }

    /**
     * Delete the assigs by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Assigs : {}", id);
        assigsRepository.delete(id);
        assigsSearchRepository.delete(id);
    }

    /**
     * Search for the assigs corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AssigsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Assigs for query {}", query);
        Page<Assigs> result = assigsSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(assigsMapper::toDto);
    }
}
