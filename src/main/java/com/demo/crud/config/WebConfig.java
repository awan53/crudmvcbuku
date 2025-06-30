// File: src/main/java/com/demo/crud/config/WebConfig.java
package com.demo.crud.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.format.DateTimeFormatter; // Pastikan ini diimport

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        // Mendaftarkan formatter untuk LocalDate agar diformat ke ISO_LOCAL_DATE (yyyy-MM-dd)
        registrar.setDateFormatter(DateTimeFormatter.ISO_LOCAL_DATE);
        registrar.registerFormatters(registry);
    }
}