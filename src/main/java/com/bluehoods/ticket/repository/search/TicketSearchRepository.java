package com.bluehoods.ticket.repository.search;

import com.bluehoods.ticket.domain.Ticket;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Ticket entity.
 */
public interface TicketSearchRepository extends ElasticsearchRepository<Ticket, Long> {
}
