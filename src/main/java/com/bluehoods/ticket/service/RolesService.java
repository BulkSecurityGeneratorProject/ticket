package com.bluehoods.ticket.service;

import com.bluehoods.ticket.domain.Roles;
import com.bluehoods.ticket.repository.RolesRepository;
import com.bluehoods.ticket.repository.search.RolesSearchRepository;
import com.bluehoods.ticket.service.dto.RolesDTO;
import com.bluehoods.ticket.service.mapper.RolesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Roles.
 */
@Service
@Transactional
public class RolesService {

    private final Logger log = LoggerFactory.getLogger(RolesService.class);

    private final RolesRepository rolesRepository;

    private final RolesMapper rolesMapper;

    private final RolesSearchRepository rolesSearchRepository;

    public RolesService(RolesRepository rolesRepository, RolesMapper rolesMapper, RolesSearchRepository rolesSearchRepository) {
        this.rolesRepository = rolesRepository;
        this.rolesMapper = rolesMapper;
        this.rolesSearchRepository = rolesSearchRepository;
    }

    /**
     * Save a roles.
     *
     * @param rolesDTO the entity to save
     * @return the persisted entity
     */
    public RolesDTO save(RolesDTO rolesDTO) {
        log.debug("Request to save Roles : {}", rolesDTO);
        Roles roles = rolesMapper.toEntity(rolesDTO);
        roles = rolesRepository.save(roles);
        RolesDTO result = rolesMapper.toDto(roles);
        rolesSearchRepository.save(roles);
        return result;
    }

    /**
     * Get all the roles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RolesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Roles");
        return rolesRepository.findAll(pageable)
            .map(rolesMapper::toDto);
    }

    /**
     * Get one roles by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public RolesDTO findOne(Long id) {
        log.debug("Request to get Roles : {}", id);
        Roles roles = rolesRepository.findOne(id);
        return rolesMapper.toDto(roles);
    }

    /**
     * Delete the roles by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Roles : {}", id);
        rolesRepository.delete(id);
        rolesSearchRepository.delete(id);
    }

    /**
     * Search for the roles corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RolesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Roles for query {}", query);
        Page<Roles> result = rolesSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(rolesMapper::toDto);
    }
}
