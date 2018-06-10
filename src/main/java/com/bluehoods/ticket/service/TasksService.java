package com.bluehoods.ticket.service;

import com.bluehoods.ticket.domain.Tasks;
import com.bluehoods.ticket.repository.TasksRepository;
import com.bluehoods.ticket.repository.search.TasksSearchRepository;
import com.bluehoods.ticket.service.dto.TasksDTO;
import com.bluehoods.ticket.service.mapper.TasksMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Tasks.
 */
@Service
@Transactional
public class TasksService {

    private final Logger log = LoggerFactory.getLogger(TasksService.class);

    private final TasksRepository tasksRepository;

    private final TasksMapper tasksMapper;

    private final TasksSearchRepository tasksSearchRepository;

    public TasksService(TasksRepository tasksRepository, TasksMapper tasksMapper, TasksSearchRepository tasksSearchRepository) {
        this.tasksRepository = tasksRepository;
        this.tasksMapper = tasksMapper;
        this.tasksSearchRepository = tasksSearchRepository;
    }

    /**
     * Save a tasks.
     *
     * @param tasksDTO the entity to save
     * @return the persisted entity
     */
    public TasksDTO save(TasksDTO tasksDTO) {
        log.debug("Request to save Tasks : {}", tasksDTO);
        Tasks tasks = tasksMapper.toEntity(tasksDTO);
        tasks = tasksRepository.save(tasks);
        TasksDTO result = tasksMapper.toDto(tasks);
        tasksSearchRepository.save(tasks);
        return result;
    }

    /**
     * Get all the tasks.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TasksDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tasks");
        return tasksRepository.findAll(pageable)
            .map(tasksMapper::toDto);
    }

    /**
     * Get one tasks by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TasksDTO findOne(Long id) {
        log.debug("Request to get Tasks : {}", id);
        Tasks tasks = tasksRepository.findOne(id);
        return tasksMapper.toDto(tasks);
    }

    /**
     * Delete the tasks by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tasks : {}", id);
        tasksRepository.delete(id);
        tasksSearchRepository.delete(id);
    }

    /**
     * Search for the tasks corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TasksDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Tasks for query {}", query);
        Page<Tasks> result = tasksSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(tasksMapper::toDto);
    }
}
