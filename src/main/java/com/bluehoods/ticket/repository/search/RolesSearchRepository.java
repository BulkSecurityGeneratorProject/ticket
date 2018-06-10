package com.bluehoods.ticket.repository.search;

import com.bluehoods.ticket.domain.Roles;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Roles entity.
 */
public interface RolesSearchRepository extends ElasticsearchRepository<Roles, Long> {
}
