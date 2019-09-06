package com.carhouse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

/**
 * Spring MVC configuration with thymeleaf.
 *
 * @author Katuranau Maksimilyan
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.carhouse")
@PropertySource("classpath:endpoints.properties")
public class WebConfig implements WebMvcConfigurer {

    /**
     * Create instance of a new template engine class that performs all configuration steps automatically.
     * Set cacheable to false to see changes in real time.
     *
     * @return templateResolver
     */
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    /**
     * Create template engine and sets template resolver and message source.
     *
     * @param templateResolver the template resolver
     * @param messageSource    the message source
     * @return templateEngine spring template engine
     */
    @Bean
    public SpringTemplateEngine templateEngine(final SpringResourceTemplateResolver templateResolver,
                                               final ResourceBundleMessageSource messageSource) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.setTemplateEngineMessageSource(messageSource);
        return templateEngine;
    }

    /**
     * Create view resolver use template engine for all configuration.
     *
     * @param templateEngine the template engine
     * @return viewResolver thymeleaf view resolver
     */
    @Bean
    public ThymeleafViewResolver viewResolver(final SpringTemplateEngine templateEngine) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine);
        return viewResolver;
    }

    /**
     * Create message source to resolve messages from resources.
     * @return messageSource
     */
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        return messageSource;
    }

    /**
     * Add handler for static resource.
     * @param registry is object to register resource handlers
     */
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/WEB-INF/static/");
    }

    /**
     * Gets rest template.
     * This class used to work with rest api
     *
     * @return the rest template
     */
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
