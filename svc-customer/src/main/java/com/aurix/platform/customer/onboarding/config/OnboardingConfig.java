package com.aurix.platform.customer.onboarding.config;

import com.aurix.platform.shared.tenant.TenantFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OnboardingConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public FilterRegistrationBean<TenantFilter> tenantFilterRegistration() {
        FilterRegistrationBean<TenantFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new TenantFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }
}
