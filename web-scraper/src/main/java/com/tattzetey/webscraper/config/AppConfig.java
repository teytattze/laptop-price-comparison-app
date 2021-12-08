package com.tattzetey.webscraper.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * This class is responsible for
 * scan for all the beans in
 * the whole projects
 * */
@Configuration
@ComponentScan(basePackages = "com.tattzetey.webscraper")
public class AppConfig {
}
