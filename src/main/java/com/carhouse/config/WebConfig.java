package com.carhouse.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
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
@PropertySources({
        @PropertySource("classpath:endpoints.properties"),
        @PropertySource("classpath:application.properties")
})
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
     * Create MultipartResolver to upload files in html form.
     * @return multipartResolver
     */
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(10000000);
        return multipartResolver;
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

    /**
     * Gets object mapper.
     * The class is used to convert error response object to json
     *
     * @return the object mapper
     */
    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
}
