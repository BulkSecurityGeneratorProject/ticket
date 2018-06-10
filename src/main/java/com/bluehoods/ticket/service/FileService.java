package com.bluehoods.ticket.service;

import com.bluehoods.ticket.domain.File;
import com.bluehoods.ticket.repository.FileRepository;
import com.bluehoods.ticket.repository.search.FileSearchRepository;
import com.bluehoods.ticket.service.dto.FileDTO;
import com.bluehoods.ticket.service.mapper.FileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing File.
 */
@Service
@Transactional
public class FileService {

    private final Logger log = LoggerFactory.getLogger(FileService.class);

    private final FileRepository fileRepository;

    private final FileMapper fileMapper;

    private final FileSearchRepository fileSearchRepository;

    public FileService(FileRepository fileRepository, FileMapper fileMapper, FileSearchRepository fileSearchRepository) {
        this.fileRepository = fileRepository;
        this.fileMapper = fileMapper;
        this.fileSearchRepository = fileSearchRepository;
    }

    /**
     * Save a file.
     *
     * @param fileDTO the entity to save
     * @return the persisted entity
     */
    public FileDTO save(FileDTO fileDTO) {
        log.debug("Request to save File : {}", fileDTO);
        File file = fileMapper.toEntity(fileDTO);
        file = fileRepository.save(file);
        FileDTO result = fileMapper.toDto(file);
        fileSearchRepository.save(file);
        return result;
    }

    /**
     * Get all the files.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Files");
        return fileRepository.findAll(pageable)
            .map(fileMapper::toDto);
    }

    /**
     * Get one file by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public FileDTO findOne(Long id) {
        log.debug("Request to get File : {}", id);
        File file = fileRepository.findOne(id);
        return fileMapper.toDto(file);
    }

    /**
     * Delete the file by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete File : {}", id);
        fileRepository.delete(id);
        fileSearchRepository.delete(id);
    }

    /**
     * Search for the file corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FileDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Files for query {}", query);
        Page<File> result = fileSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(fileMapper::toDto);
    }
}
