package com.tattzetey.webscraper;

import com.tattzetey.webscraper.config.AppConfig;
import com.tattzetey.webscraper.config.ScraperConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class App {
    public static void main( String[] args ) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Get scraper config bean from spring
        ScraperConfig scraperConfig = context.getBean(ScraperConfig.class);

        // Initialize all scrapers
        scraperConfig.startAllScrapers();

        // If the program receive input
        // the scraper will stop after
        // finishing the current work
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNext()) {
            scraperConfig.stopAllScrapers();
        }

    }
}
