package com.bluehoods.ticket.repository.search;

import com.bluehoods.ticket.domain.Assigs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Assigs entity.
 */
public interface AssigsSearchRepository extends ElasticsearchRepository<Assigs, Long> {
}
