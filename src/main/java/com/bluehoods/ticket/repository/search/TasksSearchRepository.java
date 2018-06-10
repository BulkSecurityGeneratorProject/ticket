package com.bluehoods.ticket.repository.search;

import com.bluehoods.ticket.domain.Tasks;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Tasks entity.
 */
public interface TasksSearchRepository extends ElasticsearchRepository<Tasks, Long> {
}
