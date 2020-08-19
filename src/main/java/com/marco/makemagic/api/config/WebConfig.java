package com.marco.makemagic.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.servlet.Filter;

/**
 * Classe de configuração relacionado a web.
 *
 * @author Marco Antônio
 */
@Configuration
public class WebConfig {

    /**
     * Bean de inicialização do filter para o eTag do cache.
     *
     * @return -
     */
    @Bean
    public Filter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }
}
