package com.dbl.nsl.erp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
public class AppConf {
	
    @Value("${static.resource.path}")
    String staticResourcePath;
	
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                .allowedOrigins("*")
//              .allowedOrigins("http://localhost:3000")
                .allowedMethods("POST", "GET", "PUT", "DELETE");
            }
            
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                
        		registry.addResourceHandler("/img/**")
                        .addResourceLocations(staticResourcePath + "/static/img/");
        		
        		registry.addResourceHandler("/edu/**")
                        .addResourceLocations(staticResourcePath + "/static/edu/");
        		
        		registry.addResourceHandler("/exp/**")
                        .addResourceLocations(staticResourcePath + "/static/exp/");
        		
        		registry.addResourceHandler("/file/**")
                .addResourceLocations(staticResourcePath + "/static/file/");
        		
            }

        };
    }
    
}