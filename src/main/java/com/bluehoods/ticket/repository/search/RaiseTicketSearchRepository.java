package com.bluehoods.ticket.repository.search;

import com.bluehoods.ticket.domain.RaiseTicket;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RaiseTicket entity.
 */
public interface RaiseTicketSearchRepository extends ElasticsearchRepository<RaiseTicket, Long> {
}
