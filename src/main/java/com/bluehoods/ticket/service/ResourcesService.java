package com.bluehoods.ticket.service;

import com.bluehoods.ticket.domain.Resources;
import com.bluehoods.ticket.repository.ResourcesRepository;
import com.bluehoods.ticket.repository.search.ResourcesSearchRepository;
import com.bluehoods.ticket.service.dto.ResourcesDTO;
import com.bluehoods.ticket.service.mapper.ResourcesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Resources.
 */
@Service
@Transactional
public class ResourcesService {

    private final Logger log = LoggerFactory.getLogger(ResourcesService.class);

    private final ResourcesRepository resourcesRepository;

    private final ResourcesMapper resourcesMapper;

    private final ResourcesSearchRepository resourcesSearchRepository;

    public ResourcesService(ResourcesRepository resourcesRepository, ResourcesMapper resourcesMapper, ResourcesSearchRepository resourcesSearchRepository) {
        this.resourcesRepository = resourcesRepository;
        this.resourcesMapper = resourcesMapper;
        this.resourcesSearchRepository = resourcesSearchRepository;
    }

    /**
     * Save a resources.
     *
     * @param resourcesDTO the entity to save
     * @return the persisted entity
     */
    public ResourcesDTO save(ResourcesDTO resourcesDTO) {
        log.debug("Request to save Resources : {}", resourcesDTO);
        Resources resources = resourcesMapper.toEntity(resourcesDTO);
        resources = resourcesRepository.save(resources);
        ResourcesDTO result = resourcesMapper.toDto(resources);
        resourcesSearchRepository.save(resources);
        return result;
    }

    /**
     * Get all the resources.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ResourcesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Resources");
        return resourcesRepository.findAll(pageable)
            .map(resourcesMapper::toDto);
    }

    /**
     * Get one resources by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ResourcesDTO findOne(Long id) {
        log.debug("Request to get Resources : {}", id);
        Resources resources = resourcesRepository.findOne(id);
        return resourcesMapper.toDto(resources);
    }

    /**
     * Delete the resources by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Resources : {}", id);
        resourcesRepository.delete(id);
        resourcesSearchRepository.delete(id);
    }

    /**
     * Search for the resources corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ResourcesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Resources for query {}", query);
        Page<Resources> result = resourcesSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(resourcesMapper::toDto);
    }
}
