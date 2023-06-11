package com.luv2code.springbootlibrary.config;

import com.luv2code.springbootlibrary.entity.Book;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
    //To allow request from React
    private String allowedOrigins = "http://localhost:3000";
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod[] unsupportedActions = {HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH, HttpMethod.PUT};
        config.exposeIdsFor(Book.class);
        disableHttpMethods(Book.class, config, unsupportedActions);

        /* Configure CORS Mapping */
        cors.addMapping(config.getBasePath() + "/**")
                .allowedOrigins(allowedOrigins);
    }

    private void disableHttpMethods(Class<Book> bookClass, RepositoryRestConfiguration config, HttpMethod[] unsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(bookClass)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(unsupportedActions))
                .withCollectionExposure((metData, httpMethods) -> httpMethods.disable(unsupportedActions));
    }
}
