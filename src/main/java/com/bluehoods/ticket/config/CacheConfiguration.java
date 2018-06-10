package com.bluehoods.ticket.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.bluehoods.ticket.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.bluehoods.ticket.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.bluehoods.ticket.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.bluehoods.ticket.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.bluehoods.ticket.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.bluehoods.ticket.domain.Ticket.class.getName(), jcacheConfiguration);
            cm.createCache(com.bluehoods.ticket.domain.Project.class.getName(), jcacheConfiguration);
            cm.createCache(com.bluehoods.ticket.domain.Project.class.getName() + ".tasks", jcacheConfiguration);
            cm.createCache(com.bluehoods.ticket.domain.Project.class.getName() + ".documents", jcacheConfiguration);
            cm.createCache(com.bluehoods.ticket.domain.Project.class.getName() + ".issueTickets", jcacheConfiguration);
            cm.createCache(com.bluehoods.ticket.domain.Tasks.class.getName(), jcacheConfiguration);
            cm.createCache(com.bluehoods.ticket.domain.Tasks.class.getName() + ".roles", jcacheConfiguration);
            cm.createCache(com.bluehoods.ticket.domain.Tasks.class.getName() + ".resources", jcacheConfiguration);
            cm.createCache(com.bluehoods.ticket.domain.Tasks.class.getName() + ".documents", jcacheConfiguration);
            cm.createCache(com.bluehoods.ticket.domain.Resources.class.getName(), jcacheConfiguration);
            cm.createCache(com.bluehoods.ticket.domain.Roles.class.getName(), jcacheConfiguration);
            cm.createCache(com.bluehoods.ticket.domain.Assigs.class.getName(), jcacheConfiguration);
            cm.createCache(com.bluehoods.ticket.domain.Assigs.class.getName() + ".tasks", jcacheConfiguration);
            cm.createCache(com.bluehoods.ticket.domain.Assigs.class.getName() + ".resources", jcacheConfiguration);
            cm.createCache(com.bluehoods.ticket.domain.Assigs.class.getName() + ".roles", jcacheConfiguration);
            cm.createCache(com.bluehoods.ticket.domain.File.class.getName(), jcacheConfiguration);
            cm.createCache(com.bluehoods.ticket.domain.RaiseTicket.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
