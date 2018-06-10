package com.bluehoods.ticket.repository.search;

import com.bluehoods.ticket.domain.File;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the File entity.
 */
public interface FileSearchRepository extends ElasticsearchRepository<File, Long> {
}
