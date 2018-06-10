package com.bluehoods.ticket.repository.search;

import com.bluehoods.ticket.domain.Resources;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Resources entity.
 */
public interface ResourcesSearchRepository extends ElasticsearchRepository<Resources, Long> {
}
