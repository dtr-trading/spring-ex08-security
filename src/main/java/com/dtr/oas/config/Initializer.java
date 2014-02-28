package com.dtr.oas.config;

import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.dtr.oas.config.DatabaseConfig;
import com.dtr.oas.config.ThymeleafConfig;
import com.dtr.oas.config.WebAppConfig;

@Order(1)
public class Initializer extends
        AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { DatabaseConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { ThymeleafConfig.class, WebAppConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

}