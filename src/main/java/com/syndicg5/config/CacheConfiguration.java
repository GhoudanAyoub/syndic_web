package com.syndicg5.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.syndicg5.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.syndicg5.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.syndicg5.domain.User.class.getName());
            createCache(cm, com.syndicg5.domain.Authority.class.getName());
            createCache(cm, com.syndicg5.domain.User.class.getName() + ".authorities");
            createCache(cm, com.syndicg5.domain.Resident.class.getName());
            createCache(cm, com.syndicg5.domain.Resident.class.getName() + ".appartements");
            createCache(cm, com.syndicg5.domain.Syndic.class.getName());
            createCache(cm, com.syndicg5.domain.Syndic.class.getName() + ".immeubles");
            createCache(cm, com.syndicg5.domain.Immeuble.class.getName());
            createCache(cm, com.syndicg5.domain.Immeuble.class.getName() + ".appartements");
            createCache(cm, com.syndicg5.domain.Immeuble.class.getName() + ".depenses");
            createCache(cm, com.syndicg5.domain.Immeuble.class.getName() + ".revenus");
            createCache(cm, com.syndicg5.domain.Appartement.class.getName());
            createCache(cm, com.syndicg5.domain.Appartement.class.getName() + ".payements");
            createCache(cm, com.syndicg5.domain.Revenu.class.getName());
            createCache(cm, com.syndicg5.domain.Revenu.class.getName() + ".categories");
            createCache(cm, com.syndicg5.domain.Depense.class.getName());
            createCache(cm, com.syndicg5.domain.Depense.class.getName() + ".categories");
            createCache(cm, com.syndicg5.domain.Categorie.class.getName());
            createCache(cm, com.syndicg5.domain.Payement.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
